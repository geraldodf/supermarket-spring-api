package br.com.supermercado.services;

import br.com.supermercado.models.Produto;
import br.com.supermercado.models.Venda;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.ArrayList;

class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Test
    void pegarTodasVendas() {
        Venda venda = new Venda();
        Produto produto = new Produto();
        Produto produto1 = new Produto();
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(produto1);
        listaDeProdutos.add(produto);

        produto.setCodigo(0L);
        produto.setPrecoDeCompra(BigDecimal.valueOf(1.50));
        produto.setPrecoDeVenda(BigDecimal.valueOf(1.99));
        produto.setLucroLiquido(BigDecimal.valueOf(0.49));
        produto.setDescricao("Produto Teste");
        produto.setDataDeCriacao("Data Criação Teste");

        produto1.setCodigo(01L);
        produto1.setPrecoDeCompra(BigDecimal.valueOf(1.501));
        produto1.setPrecoDeVenda(BigDecimal.valueOf(1.991));
        produto1.setLucroLiquido(BigDecimal.valueOf(0.491));
        produto1.setDescricao("Produto Teste1");
        produto1.setDataDeCriacao("Data Criação Teste1");


        venda.setVendaData("Data Teste");
        venda.setVendaValor(BigDecimal.valueOf(9.00));
        venda.setListaDeProdutos(listaDeProdutos);


    }

    @Test
    void pegarVendaPeloId() {
    }

    @Test
    void criarVenda() {
    }

    @Test
    void excluirVenda() {
    }

    @Test
    void atualizarVenda() {
    }

    @Test
    void criarVendaComDto() {
    }

    @Test
    void verificarVenda() {
    }
}