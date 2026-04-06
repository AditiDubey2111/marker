"use client";

import React, { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { BookOpen, LogOut, CheckCircle2, AlertCircle, Save } from "lucide-react";

export default function TeacherDashboard() {
  const [selectedSubject, setSelectedSubject] = useState<any>(null);

  const subjects = [
    { id: 1, code: "CS401", name: "Database Management Systems", students: 45 },
    { id: 2, code: "CS402", name: "Operating Systems", students: 42 },
  ];

  return (
    <div className="flex min-h-screen flex-col bg-slate-50">
      <header className="sticky top-0 z-30 flex h-16 items-center border-b bg-white px-6 shadow-sm">
        <div className="flex items-center gap-2 font-bold text-primary">
          <BookOpen className="h-6 w-6" />
          <span>TEACHER PORTAL</span>
        </div>
        <div className="ml-auto flex items-center gap-4">
          <span className="text-sm font-medium text-slate-600">Dr. John Doe</span>
          <Button variant="ghost" size="icon" onClick={() => window.location.href = "/"}>
            <LogOut className="h-5 w-5" />
          </Button>
        </div>
      </header>

      <main className="flex-1 p-6 md:p-8">
        <div className="mx-auto max-w-7xl">
          {!selectedSubject ? (
            <div className="space-y-6">
              <div className="flex flex-col gap-2">
                <h1 className="text-3xl font-bold tracking-tight">My Subjects</h1>
                <p className="text-muted-foreground">Select a subject to enter or update student marks.</p>
              </div>
              <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
                {subjects.map((sub) => (
                  <Card key={sub.id} className="hover:shadow-md transition-shadow cursor-pointer" onClick={() => setSelectedSubject(sub)}>
                    <CardHeader>
                      <CardTitle className="text-lg">{sub.code}</CardTitle>
                      <CardDescription className="font-medium text-slate-900">{sub.name}</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <CheckCircle2 className="h-4 w-4 text-green-500" />
                        <span>{sub.students} Students Enrolled</span>
                      </div>
                    </CardContent>
                    <CardFooter>
                      <Button variant="outline" className="w-full">Enter Marks</Button>
                    </CardFooter>
                  </Card>
                ))}
              </div>
            </div>
          ) : (
            <div className="space-y-6">
              <div className="flex items-center gap-4">
                <Button variant="ghost" onClick={() => setSelectedSubject(null)}>← Back to Subjects</Button>
                <div>
                  <h1 className="text-2xl font-bold">{selectedSubject.code}: {selectedSubject.name}</h1>
                  <p className="text-sm text-muted-foreground">Enter marks for all enrolled students</p>
                </div>
                <Button className="ml-auto flex items-center gap-2">
                  <Save className="h-4 w-4" /> Save All Changes
                </Button>
              </div>

              <Card>
                <CardContent className="p-0">
                  <table className="w-full text-sm">
                    <thead className="bg-slate-50 border-b">
                      <tr>
                        <th className="px-6 py-4 text-left font-semibold">Student Name</th>
                        <th className="px-6 py-4 text-left font-semibold">Roll Number</th>
                        <th className="px-6 py-4 text-center font-semibold w-32">Internal (40)</th>
                        <th className="px-6 py-4 text-center font-semibold w-32">External (60)</th>
                        <th className="px-6 py-4 text-center font-semibold w-24">Total</th>
                        <th className="px-6 py-4 text-center font-semibold">Status</th>
                      </tr>
                    </thead>
                    <tbody className="divide-y">
                      {[1, 2, 3, 4, 5].map((i) => (
                        <tr key={i} className="hover:bg-slate-50/50">
                          <td className="px-6 py-4 font-medium">Student User {i}</td>
                          <td className="px-6 py-4 text-muted-foreground">S100{i}</td>
                          <td className="px-6 py-4">
                            <Input className="text-center" placeholder="0" type="number" max="40" />
                          </td>
                          <td className="px-6 py-4">
                            <Input className="text-center" placeholder="0" type="number" max="60" />
                          </td>
                          <td className="px-6 py-4 text-center font-bold">-</td>
                          <td className="px-6 py-4">
                            <div className="flex justify-center">
                              <span className="inline-flex items-center rounded-full bg-amber-100 px-2.5 py-0.5 text-xs font-medium text-amber-800">
                                Pending
                              </span>
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </CardContent>
              </Card>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}
