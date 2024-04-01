package com.julio.exec.ExerciocOak.service;

import com.julio.exec.ExerciocOak.entities.Produto;
import com.julio.exec.ExerciocOak.repository.ProdutoRepositorio;
import com.julio.exec.ExerciocOak.service.exceptions.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    public List<Produto> findAll() {
        return produtoRepositorio.findAll();
    }

    @PostMapping
    @Validated
    public Produto insert(Produto produto) {
        if (produto.getNome().isEmpty()) {
            throw new ValidacaoException("O nome do produto é obrigatório");
        }
        if (produto.getValor() == null || produto.getValor() <= 0) {
            throw new ValidacaoException("O valor do produto é obrigatório e deve ser maior que zero");
        }
        return produtoRepositorio.save(produto);
    }

    public Produto findById(Long id) {
        return produtoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto update(Produto produto) {
        Produto existingProduto = findById(produto.getId());
        existingProduto.setNome(produto.getNome());
        existingProduto.setDescricao(produto.getDescricao());
        existingProduto.setValor(produto.getValor());
        existingProduto.setDisponibilidade(produto.isDisponibilidade());

        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new ValidacaoException("O nome do produto é obrigatório");
        }
        return this.produtoRepositorio.save(existingProduto);
    }

    public void delete(Long id) {
        produtoRepositorio.deleteById(id);
    }

    public List<Produto> findAllOrderedByValor() {
        return produtoRepositorio.findAll(Sort.by(Sort.Direction.ASC, "valor"));
    }
}
