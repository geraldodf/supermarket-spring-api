package br.com.supermercado.services;

import br.com.supermercado.models.Venda;
import br.com.supermercado.repositories.VendaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    void buscaPorTodasVendasDeveRetornalArrayVazio() {
        ArrayList<Venda> listaVazia = new ArrayList();

        when(vendaService.pegarTodasVendas()).thenReturn(listaVazia);
        ArrayList<Venda> retorno = vendaService.pegarTodasVendas();

        Assert.assertTrue(retorno.isEmpty());
    }

    @Test
    void buscaPorTodasVendasDeveRetornalArray() {
        ArrayList<Venda> lista = new ArrayList();
        lista.add(new Venda());

        when(vendaRepository.findAll()).thenReturn(lista);
        ArrayList<Venda> retorno = vendaService.pegarTodasVendas();

        assertFalse(retorno.isEmpty());
    }

    @Test
    void deveRetornarTresVendasQuandoForSolicitadoPegarTodos() {
        ArrayList<Venda> lista = new ArrayList();
        lista.add(new Venda());
        lista.add(new Venda());
        lista.add(new Venda());

        when(vendaRepository.findAll()).thenReturn(lista);
        ArrayList<Venda> retorno = vendaService.pegarTodasVendas();

        Assert.assertEquals(lista.size(), retorno.size());
    }

    @Test
    void buscarVendaPorIdRetornaUmaVenda() throws Exception {
        Venda venda = new Venda();
        Long id = 1L;
        venda.setId(id);
        when(vendaRepository.findById(id)).thenReturn(java.util.Optional.of(venda));
        Venda retorno = vendaService.pegarVendaPeloId(id);
        assertNotNull(retorno);
        assertEquals(retorno.getId(), venda.getId());
    }

    @Test
    void buscarVendaSemIdRetornaException() {
        String mensagemException = "Venda inexistente!";
        Throwable retorno = assertThrows(Exception.class, () -> vendaService.pegarVendaPeloId(null), mensagemException);
        assertTrue(retorno.getMessage().equals(mensagemException));
    }

    @Test
    void buscarVendaPorIdRetornaVenda() throws Exception {
        Venda venda = new Venda();
        venda.setVendaValor(BigDecimal.valueOf(9.99));
        when(vendaRepository.findById(656L)).thenReturn(Optional.of(new Venda()));
        Optional<Venda> vendaOptional = vendaRepository.findById(656L);
        Venda venda1 = vendaOptional.get();
        venda1.setVendaValor(BigDecimal.valueOf(9.99));
        Venda vendaRetorno = vendaService.pegarVendaPeloId(656L);
        assertEquals(venda.getVendaValor(), vendaRetorno.getVendaValor());
    }

}