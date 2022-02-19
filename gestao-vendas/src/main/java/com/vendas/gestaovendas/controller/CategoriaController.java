package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET - localhost:8080/categoria
    @GetMapping
    public List<Categoria> listarTodas() {
        return categoriaService.listarTodas();
    }

    // GET - localhost:8080/categoria/{codigo}
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable(name = "codigo") Long codigo) {
        Optional<Categoria> optCategoria = categoriaService.buscarPorCodigo(codigo);
        return optCategoria.isPresent()
                ? ResponseEntity.ok(optCategoria)
                : ResponseEntity.notFound().build();
    }

    // POST - localhost:8080/categoria
    @PostMapping
    public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) {
        Categoria categoriaSalva = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    // PUT - localhost:8080/categoria/{codigo}
    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@PathVariable(name = "codigo") Long codigo,
                                               @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.atualizar(codigo, categoria));
    }
}
