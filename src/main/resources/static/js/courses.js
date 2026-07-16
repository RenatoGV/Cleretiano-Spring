let editingCourseId = null;

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addCourseBtn").addEventListener("click", () => openCourseModal());
    document.getElementById("courseForm").addEventListener("submit", handleCourseSubmit);
    document.getElementById("cancelCourseBtn").addEventListener("click", closeCourseModal);

    await loadCourses();
});

async function loadCourses() {
    const tbody = document.getElementById("coursesTableBody");
    tbody.innerHTML = "";

    try {
        const courses = await api.get("/courses");

        if (courses.length === 0) {
            tbody.innerHTML = `<tr><td colspan="3" class="empty-state">No hay cursos registrados</td></tr>`;
            return;
        }

        courses.forEach((course) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${course.id}</td>
                <td>${course.name}</td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editCourse(${course.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteCourse(${course.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="3" class="empty-state">${err.message}</td></tr>`;
    }
}

function openCourseModal() {
    editingCourseId = null;
    document.getElementById("courseModalTitle").textContent = "Agregar curso";
    document.getElementById("courseForm").reset();
    document.getElementById("courseError").style.display = "none";
    document.getElementById("courseModal").classList.add("open");
}

async function editCourse(id) {
    try {
        const course = await api.get(`/courses/${id}`);
        editingCourseId = id;
        document.getElementById("courseModalTitle").textContent = "Editar curso";
        document.getElementById("courseName").value = course.name;
        document.getElementById("courseError").style.display = "none";
        document.getElementById("courseModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeCourseModal() {
    document.getElementById("courseModal").classList.remove("open");
}

async function handleCourseSubmit(event) {
    event.preventDefault();

    const payload = {
        name: document.getElementById("courseName").value.trim()
    };

    const errorBox = document.getElementById("courseError");
    errorBox.style.display = "none";

    try {
        if (editingCourseId) {
            await api.put(`/courses/${editingCourseId}`, payload);
        } else {
            await api.post("/courses", payload);
        }
        closeCourseModal();
        await loadCourses();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteCourse(id) {
    if (!confirm("¿Está seguro de eliminar este curso?")) return;

    try {
        await api.delete(`/courses/${id}`);
        await loadCourses();
    } catch (err) {
        alert(err.message);
    }
}
