package br.com.supermercado.services;

import br.com.supermercado.models.Venda;
import br.com.supermercado.repositories.VendaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    void busacPorTodasVendasDeveRetornalArrayVazio() {
        ArrayList<Venda> listaVazia = new ArrayList();

        Mockito.when(vendaService.pegarTodasVendas()).thenReturn(listaVazia);
        ArrayList<Venda> retorno = vendaService.pegarTodasVendas();

        Assert.assertTrue(retorno.isEmpty());

    }

    @Test
    void buscaPorTodasVendasDeveRetornalArray() {
        ArrayList<Venda> lista = new ArrayList();
        lista.add(new Venda());

        Mockito.when(vendaRepository.findAll()).thenReturn(lista);
        ArrayList<Venda> retorno = vendaService.pegarTodasVendas();

        Assert.assertFalse(retorno.isEmpty());


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