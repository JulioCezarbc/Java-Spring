const produtosEndpoint = "http://localhost:8080/produtos";

async function getProdutos() {
  const response = await fetch(produtosEndpoint);

  if (response.ok) {
    const data = await response.json();
    const produtosTable = document.getElementById("produtos");
    let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Nome</th>
            <th scope="col">Descrição</th>
            <th scope="col">Valor</th>
            <th scope="col">Disponibilidade</th>
        </thead>`;

    for (let produto of data) {
      tab += `
            <tr>
                <td scope="row">${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.descricao}</td>
                <td>${produto.valor}</td>
                <td>${produto.disponibilidade ? 'Sim' : 'Não'}</td>
            </tr>
        `;
    }

    produtosTable.innerHTML = tab;
  } else {
    console.error('Erro ao buscar produtos:', response.status);
    alert('Erro ao buscar produtos. Por favor, tente novamente mais tarde.');
  }
}

document.addEventListener('DOMContentLoaded', function () {
    const produtoForm = document.getElementById('produtoForm');

    produtoForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const descricao = document.getElementById('descricao').value;
        const idElement = document.getElementById('id');
        const id = idElement ? idElement.value : ''; 
        const valor = parseFloat(document.getElementById('valor').value);
        const disponibilidade = document.getElementById('disponibilidade').checked;

        const novoProduto = { nome, id, descricao, valor, disponibilidade };

        try {
            const response = await fetch(produtosEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(novoProduto)
            });

            if (response.ok) {
                alert('Produto cadastrado com sucesso!');
                
                produtoForm.reset();
                
                getProdutos();
            } else {
                alert('Erro ao cadastrar produto!');
            }
        } catch (error) {
            console.error('Erro ao enviar dados para o backend:', error);
            alert('Erro ao enviar dados para o backend!');
        }
    });

    
    getProdutos();
});
