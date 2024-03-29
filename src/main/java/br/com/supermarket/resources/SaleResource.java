package br.com.supermarket.resources;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.Sale;
import br.com.supermarket.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("sales")
public class SaleResource {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ArrayList<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable("id") Long id) throws Exception {
        return saleService.getSaleById(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PostMapping
    public Sale createSale(@RequestBody SaleDto saleDto) throws Exception {
        return saleService.createSale(saleDto);
    }

    @PutMapping("/{id}")
    public Sale updateSale(@RequestBody SaleDto saleDto, @PathVariable("id") Long id) throws Exception {
        return saleService.updateSale(saleDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable("id") Long id) throws Exception {
        saleService.deleteSale(id);
    }
}

