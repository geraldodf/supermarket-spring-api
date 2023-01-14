package br.com.supermarket.resources;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.Sale;
import br.com.supermarket.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("vendas")
public class VendaResource {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ArrayList<Sale> pegarTodasVendas() {
        return vendaService.pegarTodasVendas();
    }

    @GetMapping("/{id}")
    public Sale pegarVendaPeloId(@PathVariable("id") Long id) throws Exception {
        return vendaService.pegarVendaPeloId(id);
    }

    @PostMapping
    public void criarVenda(@RequestBody SaleDto saleDto) throws Exception {
        vendaService.criarVenda(saleDto);
    }

    @PutMapping("/{id}")
    public void atualizarVenda(@RequestBody SaleDto saleDto, @PathVariable("id") Long id) throws Exception {
        vendaService.atualizarVenda(saleDto, id);
    }

    @DeleteMapping("/{id}")
    public void excluirVenda(@PathVariable("id") Long id) throws Exception {
        vendaService.excluirVenda(id);
    }
}

