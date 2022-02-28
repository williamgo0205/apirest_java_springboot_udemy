package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Busca todas as categorias
    public List<Categoria> listarTodas(){
        return categoriaRepository.findAll();
    }

    // Busca apenas a categoria repassada por codigo
    public Optional<Categoria> buscarPorCodigo(Long codigo){
        return categoriaRepository.findById(codigo);
    }

    // Metodo para salvar uma categoria no banco de dados
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    // Método para atualizar a Categoria no banco de dados
    public Categoria atualizar(Long codigo, Categoria categoria){
        Categoria categoriaSalvar = validarCategoriaExiste(codigo);

       /* BeanUtils substitui a entidade recebida via parametro no banco de dados
          > SOURCE = entidade a ser salva (recebida por parametro)
          > TARGET = entidade do banco de dados
          > Terceiro parâmetro = campo que nãos eve ser modificado nessa acao*/
        BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");

        // Persiste a entidade no banco de dados
        return categoriaRepository.save(categoriaSalvar);
    }

    // Método para deletar a Categoria no banco de dados
    public void deletar(Long codigo){
        categoriaRepository.deleteById(codigo);
    }

    private Categoria validarCategoriaExiste(Long codigo) {
        Optional<Categoria> categoria = buscarPorCodigo(codigo);
        // Caso a categoria nao exista lanca uma exception
        if (categoria.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return categoria.get();
    }
}
