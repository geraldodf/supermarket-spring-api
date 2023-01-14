package br.com.supermarket.resources;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.Sale;
import br.com.supermarket.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("sales")
public class SaleResource {

    @Autowired
    private VendaService saleService;

    @GetMapping
    public ArrayList<Sale> getAllSales() {
        return saleService.pegarTodasVendas();
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable("id") Long id) throws Exception {
        return saleService.pegarVendaPeloId(id);
    }

    @PostMapping
    public void createSale(@RequestBody SaleDto saleDto) throws Exception {
        saleService.criarVenda(saleDto);
    }

    @PutMapping("/{id}")
    public void updateVenda(@RequestBody SaleDto saleDto, @PathVariable("id") Long id) throws Exception {
        saleService.atualizarVenda(saleDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable("id") Long id) throws Exception {
        saleService.excluirVenda(id);
    }
}

