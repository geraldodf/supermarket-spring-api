package br.com.supermercado.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.supermercado.models.Produto;
import br.com.supermercado.services.ProdutoService;


import java.math.BigInteger;
import java.util.ArrayList;

@RequestMapping("/produtos")
@RestController
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ArrayList<Produto> pegarTodosProdutos () {
        return produtoService.pegarTodosProdutos();
    }

    @GetMapping("/search")
    public ArrayList<Produto> pesquisaProdutoPorNomeOuCodigo(@RequestParam (name = "descricao") String descricao, @RequestParam (name = "codigo") Long codigo){
        if (descricao == null){
            return produtoService.pesquisaProdutoPorCodigo(codigo);
        } else
        return produtoService.pesquisaProdutoPorDescricao(descricao);
    }

    @GetMapping("/{id}")
    public Produto pegarUmProduto(@PathVariable ("id") Long id){
        return produtoService.pegarUmProduto(id);
    }

    @PostMapping
    public void adicionarProduto(@RequestBody Produto produto){
        produtoService.criarProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable("id") Long id){
        produtoService.excluirProduto(id);
    }

    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable ("id") Long id,@RequestBody Produto produto){
        produtoService.atualizarProduto(id, produto);
    }




}
