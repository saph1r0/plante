async function fetchProtegido(url, options = {}) {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/web/login";
        return null;
    }

    const response = await fetch(url, {
        ...options,
        headers: {
            ...(options.headers || {}),
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    });

    if (response.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "/web/login";
        return null;
    }

    return response;
}
