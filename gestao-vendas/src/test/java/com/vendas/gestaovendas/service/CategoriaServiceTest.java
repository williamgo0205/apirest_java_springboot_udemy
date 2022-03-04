package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    private static final Long ID_CATEGORIA_1 = 1L;
    private static final Long ID_CATEGORIA_2 = 1L;
    private static final String NOME_CATEGORIA_TECNOLOGIA = "Tecnologia";
    private static final String NOME_CATEGORIA_AUTOMOTIVA = "Automotiva";

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    public void listarCategoriasTest() {
        Categoria categoriaTecnologia = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaAutomotiva = createCategoria(ID_CATEGORIA_2, NOME_CATEGORIA_AUTOMOTIVA);

        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(categoriaTecnologia);
        categoriaList.add(categoriaAutomotiva);

        doReturn(categoriaList).when(categoriaRepository).findAll();

        List<Categoria> categoriaListService = categoriaService.listarTodas();

        verify(categoriaRepository, Mockito.times(1)).findAll();

        assertEquals(categoriaListService.get(0).getCodigo(), categoriaTecnologia.getCodigo());
        assertEquals(categoriaListService.get(0).getNome(),   categoriaTecnologia.getNome());

        assertEquals(categoriaListService.get(1).getCodigo(), categoriaAutomotiva.getCodigo());
        assertEquals(categoriaListService.get(1).getNome(),   categoriaAutomotiva.getNome());
    }

    @Test
    public void buscarPorCodigoTest() {
        Optional<Categoria> optCategoria =
                Optional.of(createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA));

        doReturn(optCategoria).when(categoriaRepository).findById(any());

        Optional<Categoria> categoriaListService = categoriaService.buscarPorCodigo(ID_CATEGORIA_1);

        verify(categoriaRepository, Mockito.times(1)).findById(any());

        assertEquals(categoriaListService.get().getCodigo(), optCategoria.get().getCodigo());
        assertEquals(categoriaListService.get().getNome(),   optCategoria.get().getNome());
    }

    @Test
    public void salvarCategoriaTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(null).when(categoriaRepository).findByNome(any());
        doReturn(categoria).when(categoriaRepository).save(any());

        Categoria categoriaSalva = categoriaService.salvar(categoria);

        verify(categoriaRepository, Mockito.times(1)).findByNome(any());
        verify(categoriaRepository, Mockito.times(1)).save(any());

        assertEquals(categoria.getCodigo(), categoriaSalva.getCodigo());
        assertEquals(categoria.getNome(),   categoriaSalva.getNome());
    }

    @Test
    public void erroSalvarCategoriaDuplicadaTest() {
        Categoria categoriaExistente = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaNova = createCategoria(ID_CATEGORIA_2, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(categoriaExistente).when(categoriaRepository).findByNome(NOME_CATEGORIA_TECNOLOGIA);

        Categoria categoriaSalva = categoriaService.salvar(categoriaNova);

        verify(categoriaRepository, Mockito.times(1)).findByNome(any());
        verify(categoriaRepository, Mockito.times(1)).save(any());

//        assertEquals(categoria.getCodigo(), categoriaSalva.getCodigo());
//        assertEquals(categoria.getNome(),   categoriaSalva.getNome());
    }

    private Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return  categoriaCriada;
    }
}
