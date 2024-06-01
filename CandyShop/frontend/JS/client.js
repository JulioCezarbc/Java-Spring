const API_URL = 'http://localhost:8080/client';
const rowsPerPage = 5;
let currentPage = 1;
let clients = [];
let idToUpdate = null;

document.getElementById('client-form').addEventListener('submit', addClient);
document.getElementById('deleteSelected').addEventListener('click', deleteSelectedClients);
document.getElementById('selectAllCheckbox').addEventListener('click', toggleSelectAll);
document.getElementById('atualizationSelected').addEventListener('click', () => {
    const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
    if(selectedCheckboxes.length !== 1){
        alert ('Selecione apenas um cliente para atualizar.');
        return;
    }
    const id = selectedCheckboxes[0].getAttribute('data-id');
    const client = clients.find(p => p.id == id);
    if(client){
        openUpdateModal(client);
    }else {
        console.log('Cliente não encontrado para atualização.')
    }
});

document.getElementById('update-form').addEventListener('submit', updateSelectedClient);
document.querySelector('.close').addEventListener('click', closeUpdateModal);
document.addEventListener('DOMContentLoaded', loadClients);

function renderTable(){
    const tableBody = document.querySelector('#client-table tbody');
    tableBody.innerHTML = '';

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedClients = clients.slice(start, end);

    paginatedClients.forEach(client => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td><input type="checkbox" class="delete-checkbox" data-id="${client.id}"></td>
        <td>${client.name}</td>
        <td>${client.number}</td>
        `;
        tableBody.appendChild(row);
    });

    for(let i = paginatedClients.length; i < rowsPerPage; i++){
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = `
        <td colspan="5" style="height: 48px;"></td>
        `;
        tableBody.appendChild(emptyRow);
    }

    document.getElementById('prevPage').disabled = currentPage === 1;
    document.getElementById('nextPage').disabled = end >= clients.length;
    document.getElementById('pageNumber').textContent = currentPage;
}

function loadClients(){
    fetch(API_URL)
    .then(response => {
        if(!response.ok){
            throw new Error('Erro na resposta da rede');
        }
        return response.json();
    })
    .then(data => {
        clients = data;
        renderTable();
    })
    .catch(error => {
        console.log("Erro ao carregar clientes: ", error);
    });
}

function addClient(event){
    event.preventDefault();

    const name = document.getElementById('name').value;
    const number = document.getElementById('number').value;
    const client = { name, number };

    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(client)
    })
    .then(response => {
        if (!response.ok){
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        clients.push(data);
        renderTable();
        document.getElementById('client-form').reset();
    })
    .catch(error => console.log('Erro ao adicionar cliente: ', error));
}

function deleteSelectedClients(){
    const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
    const idsToDelete = Array.from(selectedCheckboxes).map(cb => cb.getAttribute('data-id'));

    idsToDelete.forEach(id => {
        fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if(!response.ok){
                throw new Error('Erro na resposta da rede');
            }
            clients = clients.filter(client => client.id !== id);
            const totalClients = clients.length;
            const totalPages = Math.ceil(totalClients / rowsPerPage);
            currentPage = Math.min(currentPage, totalPages);
            renderTable();
        })
        .catch(error => console.error('Erro ao excluir cliente: ', error));
    });
}

function openUpdateModal(client) {
    const modal = document.getElementById('updateModal');
    modal.style.display = 'block';
    document.getElementById('update-form').reset();

    document.getElementById('update-name').value = client.name;
    document.getElementById('update-number').value = client.number;
    idToUpdate = client.id;
}

function closeUpdateModal() {
    const modal = document.getElementById('updateModal');
    modal.style.display = 'none';
}

function updateSelectedClient(event){
    event.preventDefault();

    if(!idToUpdate) return;

    const name = document.getElementById('update-name').value;
    const number = document.getElementById('update-number').value;

    const updatedClient = {name, number};

    fetch(`${API_URL}/${idToUpdate}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedClient)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        const index = clients.findIndex(client => client.id === idToUpdate);
        clients[index] = data;
        renderTable();
        closeUpdateModal();
    })
    .catch(error => console.error('Erro ao atualizar cliente: ', error));
}

function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.querySelectorAll('.delete-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
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
