package br.com.supermercado.resources;

import br.com.supermercado.Dto.VendaDto;
import br.com.supermercado.models.Venda;
import br.com.supermercado.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("vendas")
public class VendaResource {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ArrayList<Venda> pegarTodasVendas() {
        return vendaService.pegarTodasVendas();
    }

    @GetMapping("/{id}")
    public Venda pegarVendaPeloId(@PathVariable("id") Long id) {
        return vendaService.pegarVendaPeloId(id);
    }

    @PostMapping
    public void criarVenda(@RequestBody VendaDto vendaDto) throws Exception {
        vendaService.criarVenda(vendaDto);
    }

    @PutMapping("/{id}")
    public void atualizarVenda(@RequestBody VendaDto vendaDto, @PathVariable("id") Long id) throws Exception {
        vendaService.atualizarVenda(vendaDto, id);
    }

    @DeleteMapping("/{id}")
    public void excluirVenda(@PathVariable("id") Long id) {
        vendaService.excluirVenda(id);
    }


}
