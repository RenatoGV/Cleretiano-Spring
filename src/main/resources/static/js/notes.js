let editingNoteId = null;

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addNoteBtn").addEventListener("click", () => openNoteModal());
    document.getElementById("noteForm").addEventListener("submit", handleNoteSubmit);
    document.getElementById("cancelNoteBtn").addEventListener("click", closeNoteModal);

    await loadSelectOptions();
    await loadNotes();
});

async function loadSelectOptions() {
    const [students, courses, teachers] = await Promise.all([
        api.get("/students"),
        api.get("/courses"),
        api.get("/teachers")
    ]);

    fillSelect("noteStudentId", students, (s) => `${s.firstName} ${s.lastName} (${s.code})`);
    fillSelect("noteCourseId", courses, (c) => c.name);
    fillSelect("noteTeacherId", teachers, (t) => `${t.firstName} ${t.lastName}`);
}

function fillSelect(elementId, items, labelFn) {
    const select = document.getElementById(elementId);
    select.innerHTML = `<option value="">Seleccione</option>`;
    items.forEach((item) => {
        const option = document.createElement("option");
        option.value = item.id;
        option.textContent = labelFn(item);
        select.appendChild(option);
    });
}

async function loadNotes() {
    const tbody = document.getElementById("notesTableBody");
    tbody.innerHTML = "";

    try {
        const notes = await api.get("/notes");

        if (notes.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="empty-state">No hay notas registradas</td></tr>`;
            return;
        }

        notes.forEach((note) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${note.studentFullName}</td>
                <td>${note.courseName}</td>
                <td>${note.teacherFullName}</td>
                <td><span class="badge">${note.score}</span></td>
                <td>${note.observation || "-"}</td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editNote(${note.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteNote(${note.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="6" class="empty-state">${err.message}</td></tr>`;
    }
}

function openNoteModal() {
    editingNoteId = null;
    document.getElementById("noteModalTitle").textContent = "Agregar nota";
    document.getElementById("noteForm").reset();
    document.getElementById("noteError").style.display = "none";
    document.getElementById("noteModal").classList.add("open");
}

async function editNote(id) {
    try {
        const note = await api.get(`/notes/${id}`);
        editingNoteId = id;
        document.getElementById("noteModalTitle").textContent = "Editar nota";
        document.getElementById("noteStudentId").value = note.studentId;
        document.getElementById("noteCourseId").value = note.courseId;
        document.getElementById("noteTeacherId").value = note.teacherId;
        document.getElementById("noteScore").value = note.score;
        document.getElementById("noteObservation").value = note.observation || "";
        document.getElementById("noteError").style.display = "none";
        document.getElementById("noteModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeNoteModal() {
    document.getElementById("noteModal").classList.remove("open");
}

async function handleNoteSubmit(event) {
    event.preventDefault();

    const payload = {
        studentId: Number(document.getElementById("noteStudentId").value),
        courseId: Number(document.getElementById("noteCourseId").value),
        teacherId: Number(document.getElementById("noteTeacherId").value),
        score: Number(document.getElementById("noteScore").value),
        observation: document.getElementById("noteObservation").value.trim()
    };

    const errorBox = document.getElementById("noteError");
    errorBox.style.display = "none";

    try {
        if (editingNoteId) {
            await api.put(`/notes/${editingNoteId}`, payload);
        } else {
            await api.post("/notes", payload);
        }
        closeNoteModal();
        await loadNotes();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteNote(id) {
    if (!confirm("¿Está seguro de eliminar esta nota?")) return;

    try {
        await api.delete(`/notes/${id}`);
        await loadNotes();
    } catch (err) {
        alert(err.message);
    }
}
