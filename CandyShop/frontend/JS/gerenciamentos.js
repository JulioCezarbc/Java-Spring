const API_URL = 'http://localhost:8080/product';
const rowsPerPage = 5;
let currentPage = 1;
let products = [];
let idToUpdate = null;

document.getElementById('product-form').addEventListener('submit', addProduct);
document.getElementById('deleteSelected').addEventListener('click', deleteSelectedProducts);
document.getElementById('selectAllCheckbox').addEventListener('click', toggleSelectAll);
document.getElementById('atualizationSelected').addEventListener('click', () => {
    const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
    if (selectedCheckboxes.length !== 1) {
        alert('Selecione exatamente um produto para atualizar.');
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


function renderTable() {
    const tableBody = document.querySelector('#product-table tbody');
    tableBody.innerHTML = '';

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedProducts = products.slice(start, end);

    paginatedProducts.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><input type="checkbox" class="delete-checkbox" data-id="${product.id}"></td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${product.quantity}</td>
        `;
        tableBody.appendChild(row);
    });

    for (let i = paginatedProducts.length; i < rowsPerPage; i++) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = `
            <td colspan="5" style="height: 48px;"></td>
        `;
        tableBody.appendChild(emptyRow);
    }

    document.getElementById('prevPage').disabled = currentPage === 1;
    document.getElementById('nextPage').disabled = end >= products.length;
    document.getElementById('pageNumber').textContent = currentPage;
}

function loadProducts() {
    fetch(API_URL)
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
            console.error('Erro ao carregar produtos: ', error);
        });
}

function addProduct(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const price = document.getElementById('price').value;
    const quantity = document.getElementById('quantity').value;

    const product = { name, description, price, quantity };

    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
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
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na resposta da rede.');
            }
            products = products.filter(product => product.id !== id);
            const totalProducts = products.length;
            const totalPages = Math.ceil(totalProducts / rowsPerPage);
            currentPage = Math.min(currentPage, totalPages); // Mantém a página atual se possível
            renderTable();
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



    document.getElementById('update-name').value = product.name;
    document.getElementById('update-description').value = product.description;
    document.getElementById('update-price').value = product.price;
    document.getElementById('update-quantity').value = product.quantity;

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
    const price = document.getElementById('update-price').value;
    const quantity = document.getElementById('update-quantity').value;

    const updatedProduct = { name, description, price, quantity };

    fetch(`${API_URL}/${idToUpdate}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
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

