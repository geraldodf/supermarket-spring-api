package br.com.supermarket.services;

import br.com.supermarket.models.Sale;
import br.com.supermarket.repositories.SaleRepository;
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
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    void buscaPorTodasVendasDeveRetornalArrayVazio() {
        ArrayList<Sale> listaVazia = new ArrayList();

        when(saleService.getAllSales()).thenReturn(listaVazia);
        ArrayList<Sale> retorno = saleService.getAllSales();

        Assert.assertTrue(retorno.isEmpty());
    }

    @Test
    void buscaPorTodasVendasDeveRetornalArray() {
        ArrayList<Sale> lista = new ArrayList();
        lista.add(new Sale());

        when(saleRepository.findAll()).thenReturn(lista);
        ArrayList<Sale> retorno = saleService.getAllSales();

        assertFalse(retorno.isEmpty());
    }

    @Test
    void deveRetornarTresVendasQuandoForSolicitadoPegarTodos() {
        ArrayList<Sale> lista = new ArrayList();
        lista.add(new Sale());
        lista.add(new Sale());
        lista.add(new Sale());

        when(saleRepository.findAll()).thenReturn(lista);
        ArrayList<Sale> retorno = saleService.getAllSales();

        Assert.assertEquals(lista.size(), retorno.size());
    }

    @Test
    void buscarVendaPorIdRetornaUmaVenda() throws Exception {
        Sale sale = new Sale();
        Long id = 1L;
        sale.setId(id);
        when(saleRepository.findById(id)).thenReturn(java.util.Optional.of(sale));
        Sale retorno = saleService.getSaleById(id);
        assertNotNull(retorno);
        assertEquals(retorno.getId(), sale.getId());
    }

    @Test
    void buscarVendaSemIdRetornaException() {
        String mensagemException = "Sale inexistente!";
        Throwable retorno = assertThrows(Exception.class, () -> saleService.getSaleById(null), mensagemException);
        assertTrue(retorno.getMessage().equals(mensagemException));
    }

    @Test
    void buscarVendaPorIdRetornaVenda() throws Exception {
        Sale sale = new Sale();
        sale.setSaleValue(BigDecimal.valueOf(9.99));
        when(saleRepository.findById(656L)).thenReturn(Optional.of(new Sale()));
        Optional<Sale> vendaOptional = saleRepository.findById(656L);
        Sale sale1 = vendaOptional.get();
        sale1.setSaleValue(BigDecimal.valueOf(9.99));
        Sale saleRetorno = saleService.getSaleById(656L);
        assertEquals(sale.getSaleValue(), saleRetorno.getSaleValue());
    }

}