package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listarTodas(){
        return categoriaService.listarTodas();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable(name = "codigo") Long codigo){
        Optional<Categoria> optCategoria = categoriaService.buscarPorId(codigo);
        return optCategoria.isPresent()
                ? ResponseEntity.ok(optCategoria)
                : ResponseEntity.notFound().build();
    }
}
