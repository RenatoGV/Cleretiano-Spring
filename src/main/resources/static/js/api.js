const API_BASE_URL = "/api";

async function apiRequest(url, options = {}) {
    const finalOptions = { ...options };

    if (finalOptions.body && typeof finalOptions.body !== "string") {
        finalOptions.body = JSON.stringify(finalOptions.body);
    }

    finalOptions.headers = {
        "Content-Type": "application/json",
        ...(finalOptions.headers || {})
    };

    const response = await fetch(API_BASE_URL + url, finalOptions);

    if (response.status === 204) {
        return null;
    }

    const data = await response.json().catch(() => null);

    if (!response.ok) {
        const message = (data && (data.message || data.error)) || "Ocurrió un error en la petición";
        throw new Error(message);
    }

    return data;
}

const api = {
    get: (url) => apiRequest(url, { method: "GET" }),
    post: (url, body) => apiRequest(url, { method: "POST", body }),
    put: (url, body) => apiRequest(url, { method: "PUT", body }),
    delete: (url) => apiRequest(url, { method: "DELETE" })
};
