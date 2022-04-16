package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;
import com.vendas.gestaovendas.factory.ClienteMockFactory;
import com.vendas.gestaovendas.factory.ProdutoMockFactory;
import com.vendas.gestaovendas.factory.VendaMockFactory;
import com.vendas.gestaovendas.repository.ItemVendaRepository;
import com.vendas.gestaovendas.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    private static final Long       COD_VENDA           = 1L;
    private static final LocalDate  DATA_VENDA          = LocalDate.of(2022, 04, 27);
    private static final Long       COD_ITEM_VENDA      = 1L;
    private static final Integer    QUANTIDADE          = 1;
    private static final BigDecimal PRECO_VENDIDO       = new BigDecimal(3000);

    private static final Long    COD_CLIENTE            = 1L;
    private static final String  NOME_CLIENTE           = "Tony Stark";
    private static final String  TELEFONE_CLIENTE       = "(19)91234-5678";
    private static final Boolean ATIVO_CLIENTE          = true;
    private static final String  LOGRADOURO_CLIENTE     = "Rua 1";
    private static final Integer NUMERO_CLIENTE         = 100;
    private static final String  COMPLEMENTO_CLIENTE    = "Proximo Escola Rua 1";
    private static final String  BAIRRO_CLIENTE         = "Jardim Amanda";
    private static final String  CEP_CLIENTE            = "13188-000";
    private static final String  CIDADE_CLIENTE         = "Hortolândia";
    private static final String  ESTADO_CLIENTE         = "SP";

    private static final Long       ID_CATEGORIA        = 1L;
    private static final String     NOME_CATEGORIA      = "Tecnologia";

    private static final Long       ID_PRODUTO          = 1L;
    private static final String     DESCRICAO_PRODUTO   = "Notebook";
    private static final Integer    QUANTIDADE_PRODUTO  = 10;
    private static final BigDecimal PRECO_CUSTO_PRODUTO = new BigDecimal("2000");
    private static final BigDecimal PRECO_VENDA_PRODUTO = new BigDecimal("3000");
    private static final String     OBSERVACAO_PRODUTO  = "Notebook Dell Inspiron 15 polegadas";

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private ClienteService clienteServiceMock;

    @Mock
    private VendaRepository vendaRepositoryMock;

    @Mock
    private ItemVendaRepository itemVendaRepositoryMock;

    @Test
    public void listarVendasPorCliente() {
        // Criando Cliente
        Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE, NOME_CLIENTE, TELEFONE_CLIENTE, ATIVO_CLIENTE,
                        LOGRADOURO_CLIENTE, NUMERO_CLIENTE, COMPLEMENTO_CLIENTE, BAIRRO_CLIENTE, CEP_CLIENTE,
                        CIDADE_CLIENTE, ESTADO_CLIENTE);
        // Criando Produto
        Produto produto =
                ProdutoMockFactory.createProduto(ID_PRODUTO, DESCRICAO_PRODUTO, QUANTIDADE_PRODUTO,
                        PRECO_CUSTO_PRODUTO, PRECO_VENDA_PRODUTO, OBSERVACAO_PRODUTO,
                        ID_CATEGORIA, NOME_CATEGORIA);
        // Criando Venda
        Venda venda =
                VendaMockFactory.createVenda(COD_VENDA, DATA_VENDA, cliente);
        // Criando Item Venda
        ItemVenda itemVenda =
                VendaMockFactory.createItemVenda(COD_ITEM_VENDA, QUANTIDADE, PRECO_VENDIDO, produto, venda);

        doReturn(Optional.of(cliente)).when(clienteServiceMock).buscarPorCodigo(COD_CLIENTE);
        doReturn(Arrays.asList(venda)).when(vendaRepositoryMock).findByClienteCodigo(COD_CLIENTE);
        doReturn(Arrays.asList(itemVenda)).when(itemVendaRepositoryMock).findByVendaCodigo(COD_VENDA);

        ClienteVendaResponseDTO clienteVendaResponseDTO = vendaService.listarVendasPorCliente(COD_CLIENTE);

        verify(clienteServiceMock, times(1)).buscarPorCodigo(anyLong());
        verify(vendaRepositoryMock, times(1)).findByClienteCodigo(anyLong());
        verify(itemVendaRepositoryMock, times(1)).findByVendaCodigo(anyLong());

        // Nome do cliente
        assertEquals(clienteVendaResponseDTO.getNome(),                                cliente.getNome());
        // Dados Venda
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0).getCodigo(), venda.getCodigo());
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0).getData(),   venda.getData());
        // Dados Itens da Venda
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                .getItemVendaResponseDTO().get(0).getCodigo(),  itemVenda.getCodigo());
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                .getItemVendaResponseDTO().get(0).getQuantidade(),  itemVenda.getQuantidade());
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                .getItemVendaResponseDTO().get(0).getPrecoVendido(),  itemVenda.getPrecoVendido());
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                .getItemVendaResponseDTO().get(0).getCodProduto(),  itemVenda.getProduto().getCodigo());
        assertEquals(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                .getItemVendaResponseDTO().get(0).getProdutoDescricao(),  itemVenda.getProduto().getDescricao());
    }
}
