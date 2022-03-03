package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.entities.Produto;
import com.vendas.gestaovendas.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // GET - localhost:8080/categoria/{codigoCategoria}/produto
    @ApiOperation(value = "Listar Todos os Produtos Existentes de uma Determinada Categoria",
                  nickname = "listarTodos")
    @GetMapping
    public List<Produto> listarTodos(@PathVariable(name = "codigoCategoria") Long codigoCategoria) {
        return produtoService.listarTodos(codigoCategoria);
    }

    // GET - localhost:8080/categoria/{codigoCategoria}/produto/{codigo}
    @ApiOperation(value = "Listar o Produto Informado Pelo Codigo e Categoria Informados",
                  nickname = "listarPorCodigoECategoria")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> listarPorCodigoECategoria (
            @PathVariable(name = "codigoCategoria") Long codigoCategoria,
            @PathVariable(name = "codigo") Long codigo)  {
        Optional<Produto> optProduto = produtoService.buscaPorCodigoProduto(codigo, codigoCategoria);
        return optProduto.isPresent()
                ? ResponseEntity.ok(optProduto)
                : ResponseEntity.notFound().build();
    }

    // POST - localhost:8080/categoria/{codigoCategoria}/produto
    @ApiOperation(value = "Salvar/Criar um Produto",
                  nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produto));
    }

}
