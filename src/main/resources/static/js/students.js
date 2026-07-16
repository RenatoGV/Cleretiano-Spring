let editingStudentId = null;

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addStudentBtn").addEventListener("click", () => openStudentModal());
    document.getElementById("studentForm").addEventListener("submit", handleStudentSubmit);
    document.getElementById("cancelStudentBtn").addEventListener("click", closeStudentModal);

    await loadGradesForSelect();
    await loadSectionsForSelect();
    await loadStudents();
});

async function loadGradesForSelect() {
    const grades = await api.get("/grades");
    const select = document.getElementById("studentGradeId");
    select.innerHTML = `<option value="">Seleccione un grado</option>`;
    grades.forEach((grade) => {
        const option = document.createElement("option");
        option.value = grade.id;
        option.textContent = grade.name;
        select.appendChild(option);
    });
}

async function loadSectionsForSelect() {
    const sections = await api.get("/sections");
    const select = document.getElementById("studentSectionId");
    select.innerHTML = `<option value="">Seleccione una sección</option>`;
    sections.forEach((section) => {
        const option = document.createElement("option");
        option.value = section.id;
        option.textContent = section.name;
        select.appendChild(option);
    });
}

async function loadStudents() {
    const tbody = document.getElementById("studentsTableBody");
    tbody.innerHTML = "";

    try {
        const students = await api.get("/students");

        if (students.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="empty-state">No hay estudiantes registrados</td></tr>`;
            return;
        }

        students.forEach((student) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${student.code}</td>
                <td>${student.dni}</td>
                <td>${student.firstName} ${student.lastName}</td>
                <td>${student.gradeName}</td>
                <td>${student.sectionName}</td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editStudent(${student.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteStudent(${student.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="6" class="empty-state">${err.message}</td></tr>`;
    }
}

function openStudentModal() {
    editingStudentId = null;
    document.getElementById("studentModalTitle").textContent = "Agregar estudiante";
    document.getElementById("studentForm").reset();
    document.getElementById("studentError").style.display = "none";
    document.getElementById("studentModal").classList.add("open");
}

async function editStudent(id) {
    try {
        const student = await api.get(`/students/${id}`);
        editingStudentId = id;
        document.getElementById("studentModalTitle").textContent = "Editar estudiante";
        document.getElementById("studentCode").value = student.code;
        document.getElementById("studentDni").value = student.dni;
        document.getElementById("studentFirstName").value = student.firstName;
        document.getElementById("studentLastName").value = student.lastName;
        document.getElementById("studentBirthDate").value = student.birthDate || "";
        document.getElementById("studentGender").value = student.gender || "";
        document.getElementById("studentAddress").value = student.address || "";
        document.getElementById("studentGradeId").value = student.gradeId;
        document.getElementById("studentSectionId").value = student.sectionId;

        document.getElementById("studentError").style.display = "none";
        document.getElementById("studentModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeStudentModal() {
    document.getElementById("studentModal").classList.remove("open");
}

async function handleStudentSubmit(event) {
    event.preventDefault();

    const payload = {
        code: document.getElementById("studentCode").value.trim(),
        dni: document.getElementById("studentDni").value.trim(),
        firstName: document.getElementById("studentFirstName").value.trim(),
        lastName: document.getElementById("studentLastName").value.trim(),
        birthDate: document.getElementById("studentBirthDate").value || null,
        gender: document.getElementById("studentGender").value || null,
        address: document.getElementById("studentAddress").value.trim(),
        gradeId: Number(document.getElementById("studentGradeId").value),
        sectionId: Number(document.getElementById("studentSectionId").value)
    };

    const errorBox = document.getElementById("studentError");
    errorBox.style.display = "none";

    try {
        if (editingStudentId) {
            await api.put(`/students/${editingStudentId}`, payload);
        } else {
            await api.post("/students", payload);
        }
        closeStudentModal();
        await loadStudents();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteStudent(id) {
    if (!confirm("¿Está seguro de eliminar este estudiante?")) return;

    try {
        await api.delete(`/students/${id}`);
        await loadStudents();
    } catch (err) {
        alert(err.message);
    }
}
