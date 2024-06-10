const LOGIN_API_URL = 'http://localhost:8080/login';
const REFRESH_TOKEN_URL = 'http://localhost:8080/token/refresh';

function login(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!username || !password) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const user = { username, password };

    fetch(LOGIN_API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        console.log('Login bem-sucedido:', data);
        
        if (!data.accessToken || !data.refreshToken) {
            throw new Error('Tokens não encontrados na resposta.');
        }

        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);

        window.location.href = '/HTML/index.html';
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Verifique suas credenciais e tente novamente.');
    });
}

async function fetchWithToken(url, options = {}) {
    const accessToken = localStorage.getItem('accessToken');

    const response = await fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            'Authorization': `Bearer ${accessToken}`
        }
    });

    if (response.status === 401) {
        const refreshToken = localStorage.getItem('refreshToken');
        const newTokens = await refreshTokenFunc(refreshToken);
        if (newTokens) {
            localStorage.setItem('accessToken', newTokens.accessToken);
            return fetchWithToken(url, options);
        } else {
            window.location.href = '/login.html';
        }
    }

    return response;
}

async function refreshTokenFunc(refreshToken) {
    try {
        const response = await fetch(REFRESH_TOKEN_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token: refreshToken })
        });

        if (!response.ok) throw new Error('Erro na resposta da rede.');

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Erro ao renovar token:', error);
        return null;
    }
}

document.getElementById('loginForm').addEventListener('submit', login);
