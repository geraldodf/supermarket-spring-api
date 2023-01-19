package br.com.supermarket.services;

import br.com.supermarket.models.Sale;
import br.com.supermarket.repositories.SaleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    void searchByAllSalesMustReturnArrayEmpty() {
        ArrayList<Sale> emptySaleList = new ArrayList<>();

        when(saleService.getAllSales()).thenReturn(emptySaleList);
        ArrayList<Sale> sales = saleService.getAllSales();

        assertTrue(sales.isEmpty());
    }

    @Test
    void searchForAllSalesMustReturnArrayWithOneSale() {
        ArrayList<Sale> salesList = new ArrayList<>();
        salesList.add(new Sale());

        when(saleRepository.findAll()).thenReturn(salesList);
        ArrayList<Sale> sales = saleService.getAllSales();

        assertFalse(sales.isEmpty());
    }

    @Test
    void shouldReturnThreeSalesWhenRequestedGetAll() {
        ArrayList<Sale> salesList = new ArrayList<>();
        salesList.add(new Sale());
        salesList.add(new Sale());
        salesList.add(new Sale());

        when(saleRepository.findAll()).thenReturn(salesList);
        ArrayList<Sale> sales = saleService.getAllSales();

        Assert.assertEquals(salesList.size(), sales.size());
    }

    @Test
    void fetchSaleByIdReturnsASale() throws Exception {
        Sale sale = new Sale();
        Long id = 1L;
        sale.setId(id);
        when(saleRepository.findById(id)).thenReturn(java.util.Optional.of(sale));
        Sale saleReturn = saleService.getSaleById(id);
        assertNotNull(saleReturn);
        assertEquals(saleReturn.getId(), sale.getId());
    }

    @Test
    void fetchSaleWithoutIdReturnsException() {
        String msgException = "Sale nonexistent!";
        Throwable throwable = assertThrows(Exception.class, () -> saleService.getSaleById(null), msgException);
        assertEquals(throwable.getMessage(), msgException);
    }
}