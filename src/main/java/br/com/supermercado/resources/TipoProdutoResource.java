package br.com.supermercado.resources;

import br.com.supermercado.models.TipoProduto;
import br.com.supermercado.services.TipoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/tipos-produtos")
public class TipoProdutoResource {

    @Autowired
    private TipoProdutoService tipoProdutoService;

    @GetMapping
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoProduto> pegarTodosTiposDosProdutos() {
        return tipoProdutoService.pegarTodosTiposDosProdutos();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public TipoProduto pegarUmTipoDoProdutoPeloId(@PathVariable("id") Long id) {
        return tipoProdutoService.pegarUmTipoDoProdutoPeloId(id);
    }

    @GetMapping("/name")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoProduto> pegarTipoDoProdutoPorNome(@RequestParam(name = "nome") String nome) {
        return tipoProdutoService.pegarTipoDoProdutoPorNome(nome);
    }

    @GetMapping("/tipos")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoProduto> pegarTiposDoProdutoComSort(Sort sort) {
        return tipoProdutoService.pegarTiposDoProdutoComSort(sort);
    }

    @PostMapping
    @CrossOrigin(allowedHeaders = "*")
    public TipoProduto criarTipoDoProduto(@RequestBody TipoProduto tipo) throws Exception {
        return tipoProdutoService.criarTipoDoProduto(tipo);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public TipoProduto atualizarTipoDoProduto(@PathVariable("id") Long id, @RequestBody TipoProduto tipo)
            throws Exception {
        return tipoProdutoService.atualizarTipoDoProduto(id, tipo);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public void excluirTipoDoProdutoPeloId(@PathVariable("id") Long id) throws Exception {
        tipoProdutoService.excluirTipoDoProdutoPeloId(id);
    }

}
