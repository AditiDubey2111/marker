package com.example.marker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a student's enrolment in a subject AND their marks for it.
 *
 * Composite unique constraint ensures one record per (student, subject) pair.
 * Marks can only be assigned/updated by the teacher who owns the subject
 * (enforced at service / security layer, not DB layer).
 */
@Entity
@Table(name = "marks",
       uniqueConstraints = @UniqueConstraint(
               name = "uq_marks_student_subject",
               columnNames = {"student_id", "subject_id"}))
@Getter @Setter @NoArgsConstructor
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Relationships ──────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    /**
     * The teacher who last entered/updated these marks.
     * Must be the same teacher who owns the subject (validated in service).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entered_by_teacher_id")
    private Teacher enteredBy;

    // ── Mark columns ────────────────────────────────────────────────────────

    /** Internal / midterm assessment marks (nullable until entered). */
    @Column(precision = 5, scale = 2)
    private BigDecimal internalMarks;

    /** Final / end-semester marks (nullable until entered). */
    @Column(precision = 5, scale = 2)
    private BigDecimal externalMarks;

    /**
     * Total = internalMarks + externalMarks.
     * Stored for easy querying; recomputed on every update.
     */
    @Column(precision = 6, scale = 2)
    private BigDecimal totalMarks;

    /** Maximum possible total marks for this subject. */
    @Column(precision = 6, scale = 2)
    private BigDecimal maxMarks;

    /** Letter grade derived from percentage (e.g. A+, B, F). */
    @Column(length = 5)
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ENROLLED;     // starts as ENROLLED, progresses

    // ── Audit ───────────────────────────────────────────────────────────────

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime enrolledAt;

    @UpdateTimestamp
    private LocalDateTime marksUpdatedAt;

    @Column(length = 500)
    private String remarks;

    // ── Status enum ─────────────────────────────────────────────────────────

    public enum Status {
        /** Student is enrolled but no marks entered yet. */
        ENROLLED,
        /** Marks have been entered (partial or complete). */
        MARKS_ENTERED,
        /** Marks have been finalised / published. */
        PUBLISHED,
        /** Student was absent / result withheld. */
        WITHHELD
    }

    // ── Helper ──────────────────────────────────────────────────────────────

    /** Recompute total and status after any mark change. */
    @PreUpdate
    @PrePersist
    public void recalculate() {
        if (internalMarks != null && externalMarks != null) {
            totalMarks = internalMarks.add(externalMarks);
            if (status == Status.ENROLLED) {
                status = Status.MARKS_ENTERED;
            }
        }
    }
}
