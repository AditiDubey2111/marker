"use client";

import React from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { BookOpen, LogOut, Award, CheckCircle2 } from "lucide-react";

export default function StudentDashboard() {
  const marks = [
    { id: 1, code: "CS401", name: "Database Management Systems", internal: 35, external: 52, total: 87, grade: "A+", status: "PUBLISHED" },
    { id: 2, code: "CS402", name: "Operating Systems", internal: 32, external: 48, total: 80, grade: "A", status: "PUBLISHED" },
    { id: 3, code: "CS403", name: "Algorithm Design", internal: null, external: null, total: null, grade: null, status: "ENROLLED" },
  ];

  const publishedMarks = marks.filter(m => m.status === "PUBLISHED");

  return (
    <div className="flex min-h-screen flex-col bg-slate-50">
      <header className="sticky top-0 z-30 flex h-16 items-center border-b bg-white px-6 shadow-sm">
        <div className="flex items-center gap-2 font-bold text-primary">
          <BookOpen className="h-6 w-6" />
          <span>STUDENT PORTAL</span>
        </div>
        <div className="ml-auto flex items-center gap-4">
          <span className="text-sm font-medium text-slate-600">Jane Smith (S101)</span>
          <Button variant="ghost" size="icon" onClick={() => window.location.href = "/"}>
            <LogOut className="h-5 w-5" />
          </Button>
        </div>
      </header>

      <main className="flex-1 p-6 md:p-8">
        <div className="mx-auto max-w-7xl">
          <div className="mb-8 flex flex-col gap-2">
            <h1 className="text-3xl font-bold tracking-tight">My Academic Record</h1>
            <p className="text-muted-foreground">View your results for the current semester.</p>
          </div>

          <div className="grid gap-6 md:grid-cols-4">
            <Card className="md:col-span-1 h-fit">
              <CardHeader>
                <CardTitle className="text-lg">Profile Summary</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-1">
                  <p className="text-xs text-muted-foreground uppercase font-bold tracking-wider">Programme</p>
                  <p className="text-sm font-medium">B.Tech (Computer Science)</p>
                </div>
                <div className="space-y-1">
                  <p className="text-xs text-muted-foreground uppercase font-bold tracking-wider">Semester</p>
                  <p className="text-sm font-medium">4th Semester</p>
                </div>
                <div className="space-y-1">
                  <p className="text-xs text-muted-foreground uppercase font-bold tracking-wider">Roll Number</p>
                  <p className="text-sm font-medium">S101</p>
                </div>
              </CardContent>
            </Card>

            <div className="md:col-span-3 space-y-6">
              <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
                {marks.map((mark) => (
                  <Card key={mark.id} className={mark.status === "PUBLISHED" ? "border-l-4 border-l-green-500" : "border-l-4 border-l-amber-500 opacity-80"}>
                    <CardHeader className="pb-2">
                      <div className="flex items-center justify-between">
                        <span className="text-xs font-bold text-primary">{mark.code}</span>
                        {mark.status === "PUBLISHED" ? (
                          <CheckCircle2 className="h-4 w-4 text-green-500" />
                        ) : (
                          <span className="text-[10px] bg-amber-100 text-amber-800 px-1.5 py-0.5 rounded uppercase">Pending</span>
                        )}
                      </div>
                      <CardTitle className="text-base mt-1">{mark.name}</CardTitle>
                    </CardHeader>
                    <CardContent>
                      {mark.status === "PUBLISHED" ? (
                        <div className="flex items-end justify-between">
                          <div className="space-y-1">
                            <p className="text-2xl font-bold">{mark.total}<span className="text-xs text-muted-foreground">/100</span></p>
                            <p className="text-[10px] text-muted-foreground">Grade: <span className="font-bold text-foreground">{mark.grade}</span></p>
                          </div>
                          <div className="text-right text-[10px] text-muted-foreground space-y-1">
                            <p>Internal: {mark.internal}</p>
                            <p>External: {mark.external}</p>
                          </div>
                        </div>
                      ) : (
                        <div className="flex flex-col items-center py-4 text-muted-foreground italic text-xs">
                          <p>Marks not yet published</p>
                        </div>
                      )}
                    </CardContent>
                  </Card>
                ))}
              </div>

              {publishedMarks.length > 0 && (
                <Card>
                  <CardHeader>
                    <CardTitle className="text-lg flex items-center gap-2">
                      <Award className="h-5 w-5 text-primary" /> Semester Summary
                    </CardTitle>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-center">
                      <div className="p-4 rounded-lg bg-slate-50 border">
                        <p className="text-xs text-muted-foreground">SGPA</p>
                        <p className="text-2xl font-bold">8.35</p>
                      </div>
                      <div className="p-4 rounded-lg bg-slate-50 border">
                        <p className="text-xs text-muted-foreground">Credits Earned</p>
                        <p className="text-2xl font-bold">22</p>
                      </div>
                      <div className="p-4 rounded-lg bg-slate-50 border">
                        <p className="text-xs text-muted-foreground">Result</p>
                        <p className="text-2xl font-bold text-green-600">PASS</p>
                      </div>
                      <div className="p-4 rounded-lg bg-slate-50 border">
                        <p className="text-xs text-muted-foreground">Rank</p>
                        <p className="text-2xl font-bold">12 / 120</p>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              )}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
