let editingGradeId = null;

const levelLabels = {
    INITIAL: "Inicial",
    PRIMARY: "Primaria",
    SECONDARY: "Secundaria"
};

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addGradeBtn").addEventListener("click", () => openGradeModal());
    document.getElementById("gradeForm").addEventListener("submit", handleGradeSubmit);
    document.getElementById("cancelGradeBtn").addEventListener("click", closeGradeModal);

    await loadGrades();
});

async function loadGrades() {
    const tbody = document.getElementById("gradesTableBody");
    tbody.innerHTML = "";

    try {
        const grades = await api.get("/grades");

        if (grades.length === 0) {
            tbody.innerHTML = `<tr><td colspan="4" class="empty-state">No hay grados registrados</td></tr>`;
            return;
        }

        grades.forEach((grade) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${grade.id}</td>
                <td>${grade.name}</td>
                <td><span class="badge">${levelLabels[grade.level] || grade.level}</span></td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editGrade(${grade.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteGrade(${grade.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="4" class="empty-state">${err.message}</td></tr>`;
    }
}

function openGradeModal() {
    editingGradeId = null;
    document.getElementById("gradeModalTitle").textContent = "Agregar grado";
    document.getElementById("gradeForm").reset();
    document.getElementById("gradeError").style.display = "none";
    document.getElementById("gradeModal").classList.add("open");
}

async function editGrade(id) {
    try {
        const grade = await api.get(`/grades/${id}`);
        editingGradeId = id;
        document.getElementById("gradeModalTitle").textContent = "Editar grado";
        document.getElementById("gradeName").value = grade.name;
        document.getElementById("gradeLevel").value = grade.level;
        document.getElementById("gradeError").style.display = "none";
        document.getElementById("gradeModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeGradeModal() {
    document.getElementById("gradeModal").classList.remove("open");
}

async function handleGradeSubmit(event) {
    event.preventDefault();

    const payload = {
        name: document.getElementById("gradeName").value.trim(),
        level: document.getElementById("gradeLevel").value
    };

    const errorBox = document.getElementById("gradeError");
    errorBox.style.display = "none";

    try {
        if (editingGradeId) {
            await api.put(`/grades/${editingGradeId}`, payload);
        } else {
            await api.post("/grades", payload);
        }
        closeGradeModal();
        await loadGrades();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteGrade(id) {
    if (!confirm("¿Está seguro de eliminar este grado?")) return;

    try {
        await api.delete(`/grades/${id}`);
        await loadGrades();
    } catch (err) {
        alert(err.message);
    }
}
