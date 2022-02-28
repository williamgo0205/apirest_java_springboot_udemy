package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET - localhost:8080/categoria
    @ApiOperation(value = "Listar")
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listarTodas();
    }

    // GET - localhost:8080/categoria/{codigo}
    @ApiOperation(value = "listar Por Código")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> listarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Categoria> optCategoria = categoriaService.buscarPorCodigo(codigo);
        return optCategoria.isPresent()
                ? ResponseEntity.ok(optCategoria)
                : ResponseEntity.notFound().build();
    }

    // POST - localhost:8080/categoria
    @ApiOperation(value = "Salvar")
    @PostMapping
    public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria) {
        Categoria categoriaSalva = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    // PUT - localhost:8080/categoria/{codigo}
    @ApiOperation(value = "Atualizar")
    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@PathVariable(name = "codigo") Long codigo,
                                               @Valid @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.atualizar(codigo, categoria));
    }
}
