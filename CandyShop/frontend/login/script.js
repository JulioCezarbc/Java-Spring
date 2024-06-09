const LOGIN_API_URL = 'http://localhost:8080/login';

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
        
        // Verifica se a resposta contém o token
        if (!data.accessToken) {
            throw new Error('Token não encontrado na resposta.');
        }

        // Armazena o token no localStorage
        localStorage.setItem('accessToken', data.accessToken);

        // Redireciona para a página principal
        window.location.href = '/HTML/index.html';
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Verifique suas credenciais e tente novamente.');
    });
}

document.getElementById('loginForm').addEventListener('submit', login);
