const API_URL = 'http://localhost:8080/products';
const rowsPerPage = 5;
let currentPage = 1;
let products = [];
let idToUpdate = null;
let sortOrder = 'asc';


document.getElementById('product-form').addEventListener('submit', addProduct);

function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}

function renderTable() {
    const tableBody = document.querySelector('#product-table tbody');
    tableBody.innerHTML = '';

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedProducts = products.slice(start, end);

    paginatedProducts.forEach(product => {
        const row = document.createElement('tr');
        const totalPrice = product.value * product.quantity; 
        row.innerHTML = `
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${formatCurrency(product.value)}</td>
            <td>${product.availability ? 'Sim' : 'Não'}</td>
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
    fetch(API_URL, {
        method: 'GET',
        headers: {
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
    const value = parseFloat(document.getElementById('value').value.replace(',', '.'));
    let availability = document.getElementById('availability').value;
    
    let selectedValue = availability;


    if (selectedValue === 'Sim') {
        availability = true
    } else if (selectedValue === 'Não') {
        availability = false
    }

    if (!isValidName(name)) {
        alert("O nome não pode conter números ou símbolos.");
        return;
    }

    const product = { name, description, value, availability };

    fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
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
        if (sortOrder === 'asc') {
            products.sort((a, b) => a.value - b.value);
        } else {
            products.sort((a, b) => b.value - a.value);
        }

        // Check if the new product should be inserted at the beginning of the table
        const newProductIndex = products.findIndex(item => item.id === data.id);
        if (sortOrder === 'desc' && newProductIndex === 0 && products.length > rowsPerPage) {
            products.splice(rowsPerPage, 0, products.shift());
        }
        renderTable();
        document.getElementById('product-form').reset();
    })
    .catch(error => console.error('Erro ao adicionar produto: ', error));
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

document.getElementById('sortAsc').addEventListener('click', () => {
    products.sort((a, b) => a.value - b.value);
    renderTable();
});

document.getElementById('sortDesc').addEventListener('click', () => {
    products.sort((a, b) => b.value - a.value);
    renderTable();
});

function isValidName(name) {
    const nameRegex = /^[a-zA-Z\s]+$/;
    return nameRegex.test(name);
}

loadProducts();
