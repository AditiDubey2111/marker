const API_BASE_URL = "http://localhost:8080/api/v1";

export async function apiFetch(endpoint: string, options: RequestInit = {}) {
  const token = typeof window !== 'undefined' ? localStorage.getItem("token") : null;
  
  const headers = {
    "Content-Type": "application/json",
    ...(token && { "Authorization": `Bearer ${token}` }),
    ...options.headers,
  };

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: "An error occurred" }));
    throw new Error(error.message || "Request failed");
  }

  if (response.status === 204) return null;
  return response.json();
}

export const authApi = {
  login: (data: any) => apiFetch("/auth/login", { method: "POST", body: JSON.stringify(data) }),
};

export const adminApi = {
  getTeachers: (page = 0, size = 20) => apiFetch(`/admin/teachers?page=${page}&size=${size}`),
  createTeacher: (data: any) => apiFetch("/admin/teachers", { method: "POST", body: JSON.stringify(data) }),
  
  getStudents: (page = 0, size = 20) => apiFetch(`/admin/students?page=${page}&size=${size}`),
  createStudent: (data: any) => apiFetch("/admin/students", { method: "POST", body: JSON.stringify(data) }),
  
  getSubjects: (page = 0, size = 20) => apiFetch(`/subjects?page=${page}&size=${size}`),
  createSubject: (data: any) => apiFetch("/subjects", { method: "POST", body: JSON.stringify(data) }),

  publishMarks: (subjectId: number) => apiFetch("/admin/marks/publish", { 
    method: "POST", 
    body: JSON.stringify({ subjectId }) 
  }),
};

export const teacherApi = {
  getSubjects: () => apiFetch("/teacher/subjects"),
  getSubjectMarks: (subjectId: number) => apiFetch(`/teacher/subjects/${subjectId}/marks`),
  enterMarks: (markId: number, data: any) => apiFetch(`/marks/${markId}`, { 
    method: "PUT", 
    body: JSON.stringify(data) 
  }),
};

export const studentApi = {
  getMarks: () => apiFetch("/student/marks"),
  getSubjects: () => apiFetch("/student/subjects"),
};
