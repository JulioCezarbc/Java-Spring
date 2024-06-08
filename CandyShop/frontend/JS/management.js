const API_URL = 'http://localhost:8080/product';
const rowsPerPage = 5;
let currentPage = 1;
let products = [];
let idToUpdate = null;

// Recupera o token de autenticação do localStorage
const authToken = localStorage.getItem('authToken');

document.getElementById('product-form').addEventListener('submit', addProduct);
document.getElementById('deleteSelected').addEventListener('click', deleteSelectedProducts);
document.getElementById('selectAllCheckbox').addEventListener('click', toggleSelectAll);
document.getElementById('atualizationSelected').addEventListener('click', () => {
    const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
    if (selectedCheckboxes.length !== 1) {
        alert('Selecione apenas um produto para atualizar.');
        return;
    }
    const id = selectedCheckboxes[0].getAttribute('data-id');
    const product = products.find(p => p.id == id);
    if (product) {
        openUpdateModal(product);
    } else {
        console.error('Produto não encontrado para atualização.');
    }
});
document.querySelector('.close').addEventListener('click', closeUpdateModal);
document.getElementById('update-form').addEventListener('submit', updateSelectedProduct);
document.addEventListener('DOMContentLoaded', loadProducts);

function formatCurrency(amount) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(amount);
}

function renderTable() {
    const tableBody = document.querySelector('#product-table tbody');
    tableBody.innerHTML = '';

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedProducts = products.slice(start, end);

    paginatedProducts.forEach(product => {
        const row = document.createElement('tr');
        const totalPrice = product.price * product.quantity; 
        row.innerHTML = `
            <td><input type="checkbox" class="delete-checkbox" data-id="${product.id}"></td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.quantity}</td>
            <td>${formatCurrency(product.price)}</td>
            <td>${formatCurrency(totalPrice)}</td>
        `;
        tableBody.appendChild(row);
    });

    for (let i = paginatedProducts.length; i < rowsPerPage; i++) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = `
            <td colspan="6" style="height: 48px;"></td>
        `;
        tableBody.appendChild(emptyRow);
    }

    document.getElementById('prevPage').disabled = currentPage === 1;
    document.getElementById('nextPage').disabled = end >= products.length;
    document.getElementById('pageNumber').textContent = currentPage;
}

function loadProducts() {
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
        products = data;
        renderTable();
    })
    .catch(error => {
        console.error('Erro ao carregar produtos:', error);
    });
}

function addProduct(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const price = document.getElementById('price').value;
    const quantity = document.getElementById('quantity').value;

    if (!isValidName(name)) {
        alert("O nome não pode conter números ou símbolos.");
        return;
    }
    if (!isValidName(description)) {
        alert("A descrição não pode conter números ou símbolos.");
        return;
    }

    const product = { name, description, price, quantity };

    fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${authToken}` // Incluir o token de autenticação no cabeçalho Authorization
        },
        body: JSON.stringify(product)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        products.push(data);
        renderTable();
        document.getElementById('product-form').reset();
    })
    .catch(error => console.error('Erro ao adicionar produto: ', error));
}

function deleteSelectedProducts() {
    const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
    const idsToDelete = Array.from(selectedCheckboxes).map(cb => cb.getAttribute('data-id'));

    idsToDelete.forEach(id => {
        fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${authToken}` // Incluir o token de autenticação no cabeçalho Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na resposta da rede.');
            }
    
            products = products.filter(product => product.id !== id);
            const totalProducts = products.length;
            const totalPages = Math.ceil(totalProducts / rowsPerPage);
            currentPage = Math.min(currentPage, totalPages); 
            loadProducts();            
        })
        .catch(error => console.error('Erro ao excluir produto: ', error));
    });
}

function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.querySelectorAll('.delete-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
}

function openUpdateModal(product) {
    const modal = document.getElementById('updateModal');
    modal.style.display = 'block';
    document.getElementById('update-form').reset();
    idToUpdate = product.id;
}

function closeUpdateModal() {
    const modal = document.getElementById('updateModal');
    modal.style.display = 'none';
}

function updateSelectedProduct(event) {
    event.preventDefault();
    
    if (!idToUpdate) return;

    const name = document.getElementById('update-name').value;
    const description = document.getElementById('update-description').value;
    const quantity = document.getElementById('update-quantity').value;
    const price = document.getElementById('update-price').value;

    const updatedProduct = { name, description, price, quantity };

    fetch(`${API_URL}/${idToUpdate}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${authToken}` // Incluir o token de autenticação no cabeçalho Authorization
        },
        body: JSON.stringify(updatedProduct)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na resposta da rede.');
        }
        return response.json();
    })
    .then(data => {
        const index = products.findIndex(product => product.id === idToUpdate);
        products[index] = data;
        renderTable();
        closeUpdateModal();
    })
    .catch(error => console.error('Erro ao atualizar produto: ', error));
}

document.getElementById('nextPage').addEventListener('click', () => {
    if ((currentPage - 1) * rowsPerPage + rowsPerPage < products.length) {
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

function isValidName(name) {
    const nameRegex = /^[a-zA-Z\s]+$/;
    return nameRegex.test(name);
}
