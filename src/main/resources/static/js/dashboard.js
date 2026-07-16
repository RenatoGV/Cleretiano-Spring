document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    try {
        const summary = await api.get("/dashboard/summary");
        document.getElementById("totalStudents").textContent = summary.totalStudents;
        document.getElementById("totalTeachers").textContent = summary.totalTeachers;
        document.getElementById("totalCourses").textContent = summary.totalCourses;
        document.getElementById("totalGrades").textContent = summary.totalGrades;
        document.getElementById("totalSections").textContent = summary.totalSections;
        document.getElementById("totalNotes").textContent = summary.totalNotes;
    } catch (err) {
        console.error(err);
    }
});
