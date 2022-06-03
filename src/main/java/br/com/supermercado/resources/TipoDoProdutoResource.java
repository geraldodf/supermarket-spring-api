package br.com.supermercado.resources;

import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.services.TipoDoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/tipo-do-produto")
public class TipoDoProdutoResource {

    @Autowired
    private TipoDoProdutoService tipoDoProdutoService;

    @GetMapping
    public ArrayList<TipoDoProduto> pegarTodosTiposDosProdutos() {
        return tipoDoProdutoService.pegarTodosTiposDosProdutos();
    }

    @GetMapping("/{id}")
    public TipoDoProduto pegarUmTipoDoProdutoPeloId(@PathVariable("id") Long id) {
        return tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(id);
    }

    @PostMapping
    public TipoDoProduto criarTipoDoProduto(@RequestBody TipoDoProduto tipo) throws Exception {
        return tipoDoProdutoService.criarTipoDoProduto(tipo);
    }

    @PutMapping("/{id}")
    public TipoDoProduto atualizarTipoDoProduto(@PathVariable("id") Long id, @RequestBody TipoDoProduto tipo) throws Exception {
        return tipoDoProdutoService.atualizarTipoDoProduto(id, tipo);
    }

    @DeleteMapping("/{id}")
    public void excluirTipoDoProdutoPeloId(@PathVariable("id") Long id) {
        tipoDoProdutoService.excluirTipoDoProdutoPeloId(id);
    }


}
