let editingTeacherId = null;
let allCourses = [];

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addTeacherBtn").addEventListener("click", () => openTeacherModal());
    document.getElementById("teacherForm").addEventListener("submit", handleTeacherSubmit);
    document.getElementById("cancelTeacherBtn").addEventListener("click", closeTeacherModal);

    await loadCoursesForCheckboxes();
    await loadTeachers();
});

async function loadCoursesForCheckboxes() {
    allCourses = await api.get("/courses");
    const container = document.getElementById("teacherCoursesList");
    container.innerHTML = "";

    allCourses.forEach((course) => {
        const label = document.createElement("label");
        label.innerHTML = `<input type="checkbox" value="${course.id}" class="course-checkbox" style="width: auto;"> ${course.name}`;
        container.appendChild(label);
    });
}

async function loadTeachers() {
    const tbody = document.getElementById("teachersTableBody");
    tbody.innerHTML = "";

    try {
        const teachers = await api.get("/teachers");

        if (teachers.length === 0) {
            tbody.innerHTML = `<tr><td colspan="5" class="empty-state">No hay docentes registrados</td></tr>`;
            return;
        }

        teachers.forEach((teacher) => {
            const courseNames = teacher.courses.map((c) => c.name).join(", ") || "-";
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${teacher.code}</td>
                <td>${teacher.dni}</td>
                <td>${teacher.firstName} ${teacher.lastName}</td>
                <td>${courseNames}</td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editTeacher(${teacher.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteTeacher(${teacher.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="5" class="empty-state">${err.message}</td></tr>`;
    }
}

function openTeacherModal() {
    editingTeacherId = null;
    document.getElementById("teacherModalTitle").textContent = "Agregar docente";
    document.getElementById("teacherForm").reset();
    document.getElementById("teacherError").style.display = "none";
    document.getElementById("teacherModal").classList.add("open");
}

async function editTeacher(id) {
    try {
        const teacher = await api.get(`/teachers/${id}`);
        editingTeacherId = id;
        document.getElementById("teacherModalTitle").textContent = "Editar docente";
        document.getElementById("teacherCode").value = teacher.code;
        document.getElementById("teacherDni").value = teacher.dni;
        document.getElementById("teacherFirstName").value = teacher.firstName;
        document.getElementById("teacherLastName").value = teacher.lastName;

        const selectedCourseIds = teacher.courses.map((c) => c.id);
        document.querySelectorAll(".course-checkbox").forEach((checkbox) => {
            checkbox.checked = selectedCourseIds.includes(Number(checkbox.value));
        });

        document.getElementById("teacherError").style.display = "none";
        document.getElementById("teacherModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeTeacherModal() {
    document.getElementById("teacherModal").classList.remove("open");
}

async function handleTeacherSubmit(event) {
    event.preventDefault();

    const courseIds = Array.from(document.querySelectorAll(".course-checkbox:checked"))
        .map((checkbox) => Number(checkbox.value));

    const payload = {
        code: document.getElementById("teacherCode").value.trim(),
        dni: document.getElementById("teacherDni").value.trim(),
        firstName: document.getElementById("teacherFirstName").value.trim(),
        lastName: document.getElementById("teacherLastName").value.trim(),
        courseIds
    };

    const errorBox = document.getElementById("teacherError");
    errorBox.style.display = "none";

    try {
        if (editingTeacherId) {
            await api.put(`/teachers/${editingTeacherId}`, payload);
        } else {
            await api.post("/teachers", payload);
        }
        closeTeacherModal();
        await loadTeachers();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteTeacher(id) {
    if (!confirm("¿Está seguro de eliminar este docente?")) return;

    try {
        await api.delete(`/teachers/${id}`);
        await loadTeachers();
    } catch (err) {
        alert(err.message);
    }
}
