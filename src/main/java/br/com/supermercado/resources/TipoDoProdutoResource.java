package br.com.supermercado.resources;

import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.services.TipoDoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/tipo-do-produto")
public class TipoDoProdutoResource {

    @Autowired
    private TipoDoProdutoService tipoDoProdutoService;

    @GetMapping
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoDoProduto> pegarTodosTiposDosProdutos() {
        return tipoDoProdutoService.pegarTodosTiposDosProdutos();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public TipoDoProduto pegarUmTipoDoProdutoPeloId(@PathVariable("id") Long id) {
        return tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(id);
    }

    @GetMapping("/nome-tipo-produto")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoDoProduto> pegarTipoDoProdutoPorNome(@RequestParam(name = "nome") String nome){
        return tipoDoProdutoService.pegarTipoDoProdutoPorNome(nome);
    }

    @GetMapping("/pag-com-sort")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<TipoDoProduto> pegarTiposDoProdutoComSort(Sort sort){
        return tipoDoProdutoService.pegarTiposDoProdutoComSort(sort);
    }

    @PostMapping
    @CrossOrigin(allowedHeaders = "*")
    public TipoDoProduto criarTipoDoProduto(@RequestBody TipoDoProduto tipo) throws Exception {
        return tipoDoProdutoService.criarTipoDoProduto(tipo);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public TipoDoProduto atualizarTipoDoProduto(@PathVariable("id") Long id, @RequestBody TipoDoProduto tipo) throws Exception {
        return tipoDoProdutoService.atualizarTipoDoProduto(id, tipo);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public void excluirTipoDoProdutoPeloId(@PathVariable("id") Long id) throws Exception {
        tipoDoProdutoService.excluirTipoDoProdutoPeloId(id);
    }

    }
