package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    private static final Long ID_CATEGORIA_1 = 1L;
    private static final Long ID_CATEGORIA_2 = 2L;
    private static final String NOME_CATEGORIA_TECNOLOGIA = "Tecnologia";
    private static final String NOME_CATEGORIA_AUTOMOTIVA = "Automotiva";

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepositoryMock;

    @Test
    public void listarTodasCategoriasTest() {
        Categoria categoriaTecnologia = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaAutomotiva = createCategoria(ID_CATEGORIA_2, NOME_CATEGORIA_AUTOMOTIVA);

        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(categoriaTecnologia);
        categoriaList.add(categoriaAutomotiva);

        doReturn(categoriaList).when(categoriaRepositoryMock).findAll();

        List<Categoria> categoriaListService = categoriaService.listarTodas();

        verify(categoriaRepositoryMock, times(1)).findAll();

        assertEquals(categoriaListService.get(0).getCodigo(), categoriaTecnologia.getCodigo());
        assertEquals(categoriaListService.get(0).getNome(),   categoriaTecnologia.getNome());

        assertEquals(categoriaListService.get(1).getCodigo(), categoriaAutomotiva.getCodigo());
        assertEquals(categoriaListService.get(1).getNome(),   categoriaAutomotiva.getNome());
    }

    @Test
    public void buscarPorCodigoCategoriaTest() {
        Optional<Categoria> optCategoria =
                Optional.of(createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA));

        doReturn(optCategoria).when(categoriaRepositoryMock).findById(any());

        Optional<Categoria> categoriaListService = categoriaService.buscarPorCodigo(ID_CATEGORIA_1);

        verify(categoriaRepositoryMock, times(1)).findById(any());

        assertEquals(categoriaListService.get().getCodigo(), optCategoria.get().getCodigo());
        assertEquals(categoriaListService.get().getNome(),   optCategoria.get().getNome());
    }

    @Test
    public void salvarCategoriaTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(null).when(categoriaRepositoryMock).findByNome(any());
        doReturn(categoria).when(categoriaRepositoryMock).save(any());

        Categoria categoriaSalva = categoriaService.salvar(categoria);

        verify(categoriaRepositoryMock, times(1)).findByNome(any());
        verify(categoriaRepositoryMock, times(1)).save(any());

        assertEquals(categoria.getCodigo(), categoriaSalva.getCodigo());
        assertEquals(categoria.getNome(),   categoriaSalva.getNome());
    }

    @Test
    public void erroSalvarCategoriaDuplicadaTest() {
        Categoria categoriaExistente = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaNova = createCategoria(ID_CATEGORIA_2, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(categoriaExistente).when(categoriaRepositoryMock).findByNome(NOME_CATEGORIA_TECNOLOGIA);

        assertThrows(RegraNegocioException.class, () -> categoriaService.salvar(categoriaNova));

        verify(categoriaRepositoryMock, times(1)).findByNome(any());
        verify(categoriaRepositoryMock, never()).save(any());
    }

    @Test
    public void atualizarCategoriaTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(Optional.of(categoria)).when(categoriaRepositoryMock).findById(any());
        doReturn(categoria).when(categoriaRepositoryMock).findByNome(any());
        doReturn(categoria).when(categoriaRepositoryMock).save(any());

        Categoria categoriaAtualizada = categoriaService.atualizar(ID_CATEGORIA_1, categoria);

        verify(categoriaRepositoryMock, times(1)).findById(any());
        verify(categoriaRepositoryMock, times(1)).findByNome(any());
        verify(categoriaRepositoryMock, times(1)).save(any());

        assertEquals(categoria.getCodigo(), categoriaAtualizada.getCodigo());
        assertEquals(categoria.getNome(),   categoriaAtualizada.getNome());
    }

    @Test
    public void erroAtualizarCategoriaInexistenteTest() {
        Long codigoCategoriaInexistente = 3L;
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(Optional.empty()).when(categoriaRepositoryMock).findById(any());

        assertThrows(EmptyResultDataAccessException.class, () -> categoriaService.atualizar(codigoCategoriaInexistente, categoria));

        verify(categoriaRepositoryMock, times(1)).findById(any());
        verify(categoriaRepositoryMock, never()).findByNome(any());
        verify(categoriaRepositoryMock, never()).save(any());
    }

    @Test
    public void erroAtualizarCategoriaDuplicadaTest() {
        Categoria categoriaExistente = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaNova = createCategoria(ID_CATEGORIA_2, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(Optional.of(categoriaNova)).when(categoriaRepositoryMock).findById(any());
        doReturn(categoriaExistente).when(categoriaRepositoryMock).findByNome(NOME_CATEGORIA_TECNOLOGIA);

        assertThrows(RegraNegocioException.class, () -> categoriaService.atualizar(ID_CATEGORIA_2, categoriaNova));

        verify(categoriaRepositoryMock, times(1)).findByNome(any());
        verify(categoriaRepositoryMock, times(1)).findById(any());
        verify(categoriaRepositoryMock, never()).save(any());
    }

    @Test
    public void deletarCategoriaTest() {
        createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        categoriaService.deletar(ID_CATEGORIA_1);

        verify(categoriaRepositoryMock, times(1)).deleteById(any());
    }

    private Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return  categoriaCriada;
    }
}
