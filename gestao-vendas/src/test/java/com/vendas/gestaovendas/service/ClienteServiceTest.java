package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.factory.CategoriaMockFactory;
import com.vendas.gestaovendas.factory.ClienteMockFactory;
import com.vendas.gestaovendas.factory.ProdutoMockFactory;
import com.vendas.gestaovendas.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    private static final Long    COD_CLIENTE_1         = 1L;
    private static final String  NOME_CLIENTE_1        = "Tony Stark";
    private static final String  TELEFONE_CLIENTE_1    = "(19)91234-5678";
    private static final Boolean ATIVO_CLIENTE_1       = true;
    private static final String  LOGRADOURO_CLIENTE_1  = "Rua 1";
    private static final Integer NUMERO_CLIENTE_1      = 100;
    private static final String  COMPLEMENTO_CLIENTE_1 = "Proximo Escola Rua 1";
    private static final String  BAIRRO_CLIENTE_1      = "Jardim Amanda";
    private static final String  CEP_CLIENTE_1         = "13188-000";
    private static final String  CIDADE_CLIENTE_1      = "Hortolândia";
    private static final String  ESTADO_CLIENTE_1      = "SP";

    private static final Long    COD_CLIENTE_2         = 2L;
    private static final String  NOME_CLIENTE_2        = "Peter Parker";
    private static final String  TELEFONE_CLIENTE_2    = "(19)98765-4321";
    private static final Boolean ATIVO_CLIENTE_2       = true;
    private static final String  LOGRADOURO_CLIENTE_2  = "Rua 2";
    private static final Integer NUMERO_CLIENTE_2      = 200;
    private static final String  COMPLEMENTO_CLIENTE_2 = "Proximo Escola Rua 2";
    private static final String  BAIRRO_CLIENTE_2      = "Jardim Amanda 2";
    private static final String  CEP_CLIENTE_2         = "13188-001";
    private static final String  CIDADE_CLIENTE_2      = "Hortolândia";
    private static final String  ESTADO_CLIENTE_2      = "SP";

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Test
    public void listarTodosClientesTest() {
        Cliente primeiroCliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        Cliente segundoCliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_2, NOME_CLIENTE_2, TELEFONE_CLIENTE_2, ATIVO_CLIENTE_2,
                        LOGRADOURO_CLIENTE_2, NUMERO_CLIENTE_2, COMPLEMENTO_CLIENTE_2, BAIRRO_CLIENTE_2, CEP_CLIENTE_2,
                        CIDADE_CLIENTE_2, ESTADO_CLIENTE_2);

        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(primeiroCliente);
        clienteList.add(segundoCliente);

        doReturn(clienteList).when(clienteRepositoryMock).findAll();

        List<Cliente> clienteListService = clienteService.listarTodos();

        verify(clienteRepositoryMock, times(1)).findAll();

        // Asserts Primeiro Cliente
        assertEquals(clienteListService.get(0).getCodigo(),   primeiroCliente.getCodigo());
        assertEquals(clienteListService.get(0).getNome(),     primeiroCliente.getNome());
        assertEquals(clienteListService.get(0).getTelefone(), primeiroCliente.getTelefone());
        assertEquals(clienteListService.get(0).getAtivo(),    primeiroCliente.getAtivo());

        assertEquals(clienteListService.get(0).getEndereco().getLogradouro(),
                primeiroCliente.getEndereco().getLogradouro());
        assertEquals(clienteListService.get(0).getEndereco().getNumero(),
                primeiroCliente.getEndereco().getNumero());
        assertEquals(clienteListService.get(0).getEndereco().getComplemento(),
                primeiroCliente.getEndereco().getComplemento());
        assertEquals(clienteListService.get(0).getEndereco().getBairro(),
                primeiroCliente.getEndereco().getBairro());
        assertEquals(clienteListService.get(0).getEndereco().getCep(),
                primeiroCliente.getEndereco().getCep());
        assertEquals(clienteListService.get(0).getEndereco().getCidade(),
                primeiroCliente.getEndereco().getCidade());
        assertEquals(clienteListService.get(0).getEndereco().getEstado(),
                primeiroCliente.getEndereco().getEstado());

        // Asserts Segundo Cliente
        assertEquals(clienteListService.get(1).getCodigo(),   segundoCliente.getCodigo());
        assertEquals(clienteListService.get(1).getNome(),     segundoCliente.getNome());
        assertEquals(clienteListService.get(1).getTelefone(), segundoCliente.getTelefone());
        assertEquals(clienteListService.get(1).getAtivo(),    segundoCliente.getAtivo());

        assertEquals(clienteListService.get(1).getEndereco().getLogradouro(),
                segundoCliente.getEndereco().getLogradouro());
        assertEquals(clienteListService.get(1).getEndereco().getNumero(),
                segundoCliente.getEndereco().getNumero());
        assertEquals(clienteListService.get(1).getEndereco().getComplemento(),
                segundoCliente.getEndereco().getComplemento());
        assertEquals(clienteListService.get(1).getEndereco().getBairro(),
                segundoCliente.getEndereco().getBairro());
        assertEquals(clienteListService.get(1).getEndereco().getCep(),
                segundoCliente.getEndereco().getCep());
        assertEquals(clienteListService.get(1).getEndereco().getCidade(),
                segundoCliente.getEndereco().getCidade());
        assertEquals(clienteListService.get(1).getEndereco().getEstado(),
                segundoCliente.getEndereco().getEstado());
    }

    @Test
    public void buscarPorCodigoTest() {
        Optional<Cliente> optCliente =
                Optional.of(ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1));

        doReturn(optCliente).when(clienteRepositoryMock).findById(any());

        Optional<Cliente> clienteListService = clienteService.buscarPorCodigo(COD_CLIENTE_1);

        verify(clienteRepositoryMock, times(1)).findById(any());

        assertEquals(clienteListService.get().getCodigo(),   optCliente.get().getCodigo());
        assertEquals(clienteListService.get().getNome(),     optCliente.get().getNome());
        assertEquals(clienteListService.get().getTelefone(), optCliente.get().getTelefone());
        assertEquals(clienteListService.get().getAtivo(),    optCliente.get().getAtivo());

        assertEquals(clienteListService.get().getEndereco().getLogradouro(),
                optCliente.get().getEndereco().getLogradouro());
        assertEquals(clienteListService.get().getEndereco().getNumero(),
                optCliente.get().getEndereco().getNumero());
        assertEquals(clienteListService.get().getEndereco().getComplemento(),
                optCliente.get().getEndereco().getComplemento());
        assertEquals(clienteListService.get().getEndereco().getBairro(),
                optCliente.get().getEndereco().getBairro());
        assertEquals(clienteListService.get().getEndereco().getCep(),
                optCliente.get().getEndereco().getCep());
        assertEquals(clienteListService.get().getEndereco().getCidade(),
                optCliente.get().getEndereco().getCidade());
        assertEquals(clienteListService.get().getEndereco().getEstado(),
                optCliente.get().getEndereco().getEstado());
    }

    @Test
    public void salvarClienteTest() {
        Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        doReturn(cliente).when(clienteRepositoryMock).findByNome(NOME_CLIENTE_1);
        doReturn(cliente).when(clienteRepositoryMock).save(cliente);

        Cliente clienteSalvo = clienteService.salvar(cliente);

        verify(clienteRepositoryMock, times(1)).findByNome(any());

        assertEquals(cliente.getCodigo(),   clienteSalvo.getCodigo());
        assertEquals(cliente.getNome(),     clienteSalvo.getNome());
        assertEquals(cliente.getTelefone(), clienteSalvo.getTelefone());
        assertEquals(cliente.getAtivo(),    clienteSalvo.getAtivo());

        assertEquals(cliente.getEndereco().getLogradouro(),  clienteSalvo.getEndereco().getLogradouro());
        assertEquals(cliente.getEndereco().getNumero(),      clienteSalvo.getEndereco().getNumero());
        assertEquals(cliente.getEndereco().getComplemento(), clienteSalvo.getEndereco().getComplemento());
        assertEquals(cliente.getEndereco().getBairro(),      clienteSalvo.getEndereco().getBairro());
        assertEquals(cliente.getEndereco().getCep(),         clienteSalvo.getEndereco().getCep());
        assertEquals(cliente.getEndereco().getCidade(),      clienteSalvo.getEndereco().getCidade());
        assertEquals(cliente.getEndereco().getEstado(),      clienteSalvo.getEndereco().getEstado());
    }

    @Test
    public void erroSalvarClienteDuplicadoTest() {
        Cliente clienteExistente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        Cliente clienteNovo =
                ClienteMockFactory.createCliente(COD_CLIENTE_2, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        doReturn(clienteExistente).when(clienteRepositoryMock).findByNome(NOME_CLIENTE_1);

        assertThrows(RegraNegocioException.class, () -> clienteService.salvar(clienteNovo));

        verify(clienteRepositoryMock, times(1)).findByNome(any());
        verify(clienteRepositoryMock, never()).save(any());
    }
}
