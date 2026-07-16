let editingSectionId = null;

document.addEventListener("DOMContentLoaded", async () => {
    const user = initAuthenticatedPage();
    if (!user) return;

    document.getElementById("addSectionBtn").addEventListener("click", () => openSectionModal());
    document.getElementById("sectionForm").addEventListener("submit", handleSectionSubmit);
    document.getElementById("cancelSectionBtn").addEventListener("click", closeSectionModal);

    await loadSections();
});

async function loadSections() {
    const tbody = document.getElementById("sectionsTableBody");
    tbody.innerHTML = "";

    try {
        const sections = await api.get("/sections");

        if (sections.length === 0) {
            tbody.innerHTML = `<tr><td colspan="3" class="empty-state">No hay secciones registradas</td></tr>`;
            return;
        }

        sections.forEach((section) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${section.id}</td>
                <td>${section.name}</td>
                <td class="actions-cell">
                    <button class="btn btn-secondary btn-sm" onclick="editSection(${section.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteSection(${section.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="3" class="empty-state">${err.message}</td></tr>`;
    }
}

function openSectionModal() {
    editingSectionId = null;
    document.getElementById("sectionModalTitle").textContent = "Agregar sección";
    document.getElementById("sectionForm").reset();
    document.getElementById("sectionError").style.display = "none";
    document.getElementById("sectionModal").classList.add("open");
}

async function editSection(id) {
    try {
        const section = await api.get(`/sections/${id}`);
        editingSectionId = id;
        document.getElementById("sectionModalTitle").textContent = "Editar sección";
        document.getElementById("sectionName").value = section.name;
        document.getElementById("sectionError").style.display = "none";
        document.getElementById("sectionModal").classList.add("open");
    } catch (err) {
        alert(err.message);
    }
}

function closeSectionModal() {
    document.getElementById("sectionModal").classList.remove("open");
}

async function handleSectionSubmit(event) {
    event.preventDefault();

    const payload = {
        name: document.getElementById("sectionName").value.trim()
    };

    const errorBox = document.getElementById("sectionError");
    errorBox.style.display = "none";

    try {
        if (editingSectionId) {
            await api.put(`/sections/${editingSectionId}`, payload);
        } else {
            await api.post("/sections", payload);
        }
        closeSectionModal();
        await loadSections();
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}

async function deleteSection(id) {
    if (!confirm("¿Está seguro de eliminar esta sección?")) return;

    try {
        await api.delete(`/sections/${id}`);
        await loadSections();
    } catch (err) {
        alert(err.message);
    }
}
