const API_URL = 'http://localhost:8080/product';
const rowsPerPage = 10; 
let currentPage = 1;
let products = [];

function renderTable() {
    const tableBody = document.querySelector('#product-table tbody');
    tableBody.innerHTML = '';

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedProducts = products.slice(start, end);

    paginatedProducts.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
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
            <td colspan="4" style="height: 48px;"></td>
        `;
        tableBody.appendChild(emptyRow);
    }

    document.getElementById('prevPage').disabled = currentPage === 1;
    document.getElementById('nextPage').disabled = end >= products.length;
    document.getElementById('pageNumber').textContent = currentPage;
}

function loadProducts() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            products = data;
            currentPage = 1;
            renderTable();
        }).catch(error => console.error('Erro ao carregar produtos: ', error));
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
    .then(response => response.json())
    .then(data => {
        console.log('Produto Adicionado', data);
        const totalPages = Math.ceil((products.length + 1) / rowsPerPage);
        currentPage = Math.min(currentPage, totalPages);
        loadProducts();
    })
    .catch(error => console.error('Erro ao adicionar produto: ', error));
}

document.getElementById('product-form').addEventListener('submit', addProduct);

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        renderTable();
    }
});

document.getElementById('nextPage').addEventListener('click', () => {
    if ((currentPage - 1) * rowsPerPage + rowsPerPage < products.length) {
        currentPage++;
        renderTable();
    }
});

document.addEventListener('DOMContentLoaded', loadProducts);
