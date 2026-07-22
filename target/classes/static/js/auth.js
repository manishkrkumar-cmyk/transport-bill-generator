document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMsg = document.getElementById('errorMsg');

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            window.location.href = data.redirect;
        } else {
            errorMsg.innerText = "Invalid credentials. Try admin / admin123";
        }
    } catch (err) {
        errorMsg.innerText = "Error connecting to server.";
    }
});