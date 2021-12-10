package br.com.supermercado.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.supermercado.models.Produto;
import br.com.supermercado.services.ProdutoService;

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

    @GetMapping("/{id}")
    public Produto pegarUmProduto(@PathVariable ("id") Long id){
        return produtoService.pegarUmProduto(id);
    }

    @PostMapping
    public void adicionarProduto(Produto produto){
        produtoService.criarProduto(produto);
    }




}
