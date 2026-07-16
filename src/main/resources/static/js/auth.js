function getSessionUser() {
    const raw = localStorage.getItem("user");
    if (!raw) {
        return null;
    }
    try {
        return JSON.parse(raw);
    } catch (e) {
        return null;
    }
}

function requireAuth() {
    const user = getSessionUser();
    if (!user) {
        window.location.href = "login.html";
        return null;
    }
    return user;
}

function logout() {
    localStorage.removeItem("user");
    window.location.href = "login.html";
}

function renderUserInfo(user) {
    const nameEl = document.getElementById("currentUserName");
    const roleEl = document.getElementById("currentUserRole");
    if (nameEl) {
        nameEl.textContent = `${user.firstName} ${user.lastName}`;
    }
    if (roleEl) {
        roleEl.textContent = user.role === "ADMIN" ? "Administrador" : "Docente";
    }
}

function setActiveNavLink() {
    const page = window.location.pathname.split("/").pop() || "index.html";
    document.querySelectorAll(".sidebar nav a").forEach((link) => {
        if (link.getAttribute("href") === page) {
            link.classList.add("active");
        }
    });
}

function initAuthenticatedPage() {
    const user = requireAuth();
    if (!user) return null;
    renderUserInfo(user);
    setActiveNavLink();

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logout);
    }

    return user;
}

// Login page logic
async function handleLoginSubmit(event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const errorBox = document.getElementById("loginError");

    errorBox.style.display = "none";

    try {
        const user = await api.post("/auth/login", { username, password });
        localStorage.setItem("user", JSON.stringify(user));
        window.location.href = "index.html";
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.style.display = "block";
    }
}
