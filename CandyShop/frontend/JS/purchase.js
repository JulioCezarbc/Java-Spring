const API_URL = 'http://localhost:8080/client';
const APIURL = 'http://localhost:8080/purchase';

const rowsPerPage = 5;
let currentPage = 1;
let clients = [];

// Recupera o token de autenticação do localStorage
const authToken = localStorage.getItem('accessToken');

document.addEventListener('DOMContentLoaded', loadClients);

function loadClients() {
    fetch(API_URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${authToken}`, // Incluir o token de autenticação no cabeçalho Authorization
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        clients = data;
        renderTable();
    })
    .catch(error => console.error('Erro ao carregar clientes:', error));
}

function renderTable() {
    const tableBody = document.querySelector('#client-table tbody');
    tableBody.innerHTML = '';
    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedClients = clients.slice(start, end);

    paginatedClients.forEach(client => {
        const clientRow = document.createElement('tr');
        clientRow.innerHTML = `
            <td>${client.name}</td>
            <td><span class="arrow" data-id="${client.id}">&#9654;</span></td>
        `;
        tableBody.appendChild(clientRow);

        const purchaseRow = document.createElement('tr');
        purchaseRow.classList.add('purchase-row');
        purchaseRow.id = `purchases-${client.id}`;

        purchaseRow.innerHTML = `
            <td colspan="2">
                <table class="purchase-table">
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Data</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </td>
        `;
        tableBody.appendChild(purchaseRow);

        loadPurchases(client.id);
    });

    document.querySelectorAll('.arrow').forEach(arrow => {
        arrow.addEventListener('click', togglePurchases);
    });

    document.getElementById('prevPage').disabled = currentPage === 1;
    document.getElementById('nextPage').disabled = end >= clients.length;
    document.getElementById('pageNumber').textContent = currentPage;
}

function togglePurchases(event) {
    const arrow = event.target;
    const clientId = arrow.getAttribute('data-id');
    const purchaseRow = document.getElementById(`purchases-${clientId}`);

    if (purchaseRow.style.display === 'table-row') {
        purchaseRow.style.display = 'none';
        arrow.innerHTML = '&#9654;';
    } else {
        purchaseRow.style.display = 'table-row';
        arrow.innerHTML = '&#9660;'; 
    }
}

function loadPurchases(clientId) {
    fetch(`${APIURL}/client/${clientId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${authToken}`, // Incluir o token de autenticação no cabeçalho Authorization
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        const purchaseTableBody = document.querySelector(`#purchases-${clientId} .purchase-table tbody`);
        purchaseTableBody.innerHTML = '';
        data.forEach(purchase => {
            purchase.products.forEach(product => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${product.name || 'Não especificado'}</td>
                    <td>${formatDate(purchase.date)}</td>
                `;
                purchaseTableBody.appendChild(row);
            });
        });
    })
    .catch(error => console.error('Erro ao carregar compras:', error));
}

function formatDate(dateString) {
    const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
    return new Date(dateString).toLocaleDateString('pt-BR', options);
}

document.getElementById('nextPage').addEventListener('click', () => {
    if ((currentPage - 1) * rowsPerPage + rowsPerPage < clients.length) {
        currentPage++;
        renderTable();
    }
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        renderTable();
    }
});
