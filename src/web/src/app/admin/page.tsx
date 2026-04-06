"use client";

import React, { useState, useEffect } from "react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { BookOpen, Users, LogOut, LayoutDashboard, Plus, Loader2, ShieldAlert } from "lucide-react";
import { adminApi } from "@/lib/api";

export default function AdminDashboard() {
  const [isAuthorized, setIsAuthorized] = useState<boolean | null>(null);
  const [activeTab, setActiveTab] = useState("overview");
  const [teachers, setTeachers] = useState<any[]>([]);
  const [students, setStudents] = useState<any[]>([]);
  const [subjects, setSubjects] = useState<any[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState<any>({});

  useEffect(() => {
    // Basic route protection
    const role = localStorage.getItem("role");
    const token = localStorage.getItem("token");
    
    if (token && role === "ADMIN") {
      setIsAuthorized(true);
      fetchData();
    } else {
      setIsAuthorized(false);
      // Optional: redirect to login after a delay
      setTimeout(() => {
        if (typeof window !== "undefined") window.location.href = "/";
      }, 2000);
    }
  }, []);

  const fetchData = async () => {
    setIsLoading(true);
    try {
      const [tData, sData, subData] = await Promise.all([
        adminApi.getTeachers(),
        adminApi.getStudents(),
        adminApi.getSubjects()
      ]);
      setTeachers(tData.content || []);
      setStudents(sData.content || []);
      setSubjects(subData.content || []);
    } catch (error) {
      console.error("Fetch failed", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCreate = async (type: string) => {
    setIsLoading(true);
    try {
      if (type === "Teacher") await adminApi.createTeacher(formData);
      else if (type === "Student") await adminApi.createStudent(formData);
      else if (type === "Subject") await adminApi.createSubject(formData);
      setFormData({});
      fetchData();
    } catch (error) {
      alert("Creation failed: " + (error as any).message);
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.href = "/";
  };

  if (isAuthorized === null) return <div className="flex h-screen items-center justify-center"><Loader2 className="animate-spin" /></div>;
  
  if (isAuthorized === false) {
    return (
      <div className="flex h-screen flex-col items-center justify-center gap-4 text-center">
        <ShieldAlert className="h-16 w-16 text-destructive" />
        <h1 className="text-2xl font-bold text-destructive">Unauthorized Access</h1>
        <p className="text-muted-foreground">You do not have permission to view this page. Redirecting to login...</p>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen flex-col bg-slate-50">
      <header className="sticky top-0 z-30 flex h-16 items-center border-b bg-white px-6 shadow-sm">
        <div className="flex items-center gap-2 font-bold text-primary">
          <BookOpen className="h-6 w-6" />
          <span>ADMIN PORTAL</span>
        </div>
        <div className="ml-auto flex items-center gap-4">
          <span className="text-sm font-medium mr-2">{localStorage.getItem("fullName")}</span>
          <Button variant="ghost" size="icon" onClick={handleLogout}>
            <LogOut className="h-5 w-5" />
          </Button>
        </div>
      </header>

      <main className="flex-1 p-6 md:p-8">
        <div className="mx-auto max-w-7xl">
          <div className="mb-8 flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold tracking-tight">Dashboard Overview</h1>
              <p className="text-muted-foreground">Manage faculty, students, and curriculum.</p>
            </div>
            {isLoading && <Loader2 className="h-6 w-6 animate-spin text-primary" />}
          </div>

          <Tabs defaultValue="overview" className="space-y-6" onValueChange={setActiveTab}>
            <TabsList className="bg-white border p-1">
              <TabsTrigger value="overview">Overview</TabsTrigger>
              <TabsTrigger value="teachers">Teachers</TabsTrigger>
              <TabsTrigger value="students">Students</TabsTrigger>
              <TabsTrigger value="subjects">Subjects</TabsTrigger>
            </TabsList>

            <TabsContent value="overview">
              <div className="grid gap-6 md:grid-cols-3">
                <StatCard title="Total Teachers" value={teachers.length.toString()} icon={<Users />} trend="Active faculty" />
                <StatCard title="Total Students" value={students.length.toString()} icon={<Users />} trend="Enrolled students" />
                <StatCard title="Total Subjects" value={subjects.length.toString()} icon={<BookOpen />} trend="Course catalog" />
              </div>
            </TabsContent>

            <TabsContent value="teachers">
              <div className="grid gap-6 md:grid-cols-2">
                <CreateEntityForm 
                  entityName="Teacher" 
                  fields={[
                    { name: "username", label: "Username" },
                    { name: "password", label: "Password", type: "password" },
                    { name: "fullName", label: "Full Name" },
                    { name: "email", label: "Email", type: "email" },
                    { name: "department", label: "Department" },
                    { name: "employeeCode", label: "Employee Code" },
                  ]}
                  formData={formData}
                  setFormData={setFormData}
                  onSave={() => handleCreate("Teacher")}
                  isLoading={isLoading}
                />
                <EntityTable title="Faculty Members" headers={["Name", "Code"]} data={teachers.map(t => ({ name: t.fullName, code: t.employeeCode }))} />
              </div>
            </TabsContent>

            <TabsContent value="students">
              <div className="grid gap-6 md:grid-cols-2">
                <CreateEntityForm 
                  entityName="Student" 
                  fields={[
                    { name: "username", label: "Username" },
                    { name: "password", label: "Password", type: "password" },
                    { name: "fullName", label: "Full Name" },
                    { name: "email", label: "Email", type: "email" },
                    { name: "rollNumber", label: "Roll Number" },
                    { name: "programme", label: "Programme" },
                    { name: "semester", label: "Semester" },
                  ]}
                  formData={formData}
                  setFormData={setFormData}
                  onSave={() => handleCreate("Student")}
                  isLoading={isLoading}
                />
                <EntityTable title="Student Directory" headers={["Name", "Roll No"]} data={students.map(s => ({ name: s.fullName, code: s.rollNumber }))} />
              </div>
            </TabsContent>

            <TabsContent value="subjects">
              <div className="grid gap-6 md:grid-cols-2">
                <CreateEntityForm 
                  entityName="Subject" 
                  fields={[
                    { name: "code", label: "Subject Code" },
                    { name: "name", label: "Subject Name" },
                    { name: "creditHours", label: "Credit Hours", type: "number" },
                    { name: "department", label: "Department" },
                    { name: "teacherId", label: "Teacher ID", type: "number" },
                  ]}
                  formData={formData}
                  setFormData={setFormData}
                  onSave={() => handleCreate("Subject")}
                  isLoading={isLoading}
                />
                <EntityTable title="Curriculum" headers={["Name", "Code"]} data={subjects.map(s => ({ name: s.name, code: s.code }))} />
              </div>
            </TabsContent>
          </Tabs>
        </div>
      </main>
    </div>
  );
}

function CreateEntityForm({ entityName, fields, formData, setFormData, onSave, isLoading }: any) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2"><Plus className="h-5 w-5" /> Add {entityName}</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        {fields.map((f: any) => (
          <div key={f.name} className="space-y-1">
            <Label>{f.label}</Label>
            <Input 
              type={f.type || "text"} 
              value={formData[f.name] || ""} 
              onChange={(e) => setFormData({ ...formData, [f.name]: e.target.value })} 
            />
          </div>
        ))}
      </CardContent>
      <CardFooter>
        <Button className="w-full" onClick={onSave} disabled={isLoading}>
          {isLoading ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : null}
          Create {entityName}
        </Button>
      </CardFooter>
    </Card>
  );
}

function EntityTable({ title, headers, data }: any) {
  return (
    <Card>
      <CardHeader><CardTitle>{title}</CardTitle></CardHeader>
      <CardContent>
        <div className="rounded-md border overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-slate-50 border-b">
              <tr>
                {headers.map((h: any) => <th key={h} className="px-4 py-3 text-left">{h}</th>)}
              </tr>
            </thead>
            <tbody className="divide-y">
              {data.map((row: any, i: number) => (
                <tr key={i} className="hover:bg-slate-50/50">
                  <td className="px-4 py-3 font-medium">{row.name}</td>
                  <td className="px-4 py-3 text-muted-foreground">{row.code}</td>
                </tr>
              ))}
              {data.length === 0 && (
                <tr>
                  <td colSpan={headers.length} className="px-4 py-8 text-center text-muted-foreground">No records found</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </CardContent>
    </Card>
  );
}

function StatCard({ title, value, icon, trend }: any) {
  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-sm font-medium">{title}</CardTitle>
        <div className="text-muted-foreground">{icon}</div>
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">{value}</div>
        <p className="text-xs text-muted-foreground">{trend}</p>
      </CardContent>
    </Card>
  );
}
