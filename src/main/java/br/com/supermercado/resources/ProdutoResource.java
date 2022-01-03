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

    @GetMapping("/search-descricao")
    public ArrayList<Produto> pesquisaProdutoPorDescricao(@RequestParam  (name ="descricao" ) String descricao){
        return produtoService.pesquisaProdutoPorDescricao(descricao);
    }
    @GetMapping("/search-codigo")
    public ArrayList<Produto> pesquisarProdutoPorCodigo(@RequestParam (name = "codigo") Long codigo) throws Exception {
        return produtoService.pesquisaProdutoPorCodigo(codigo);
    }

    @GetMapping("/{id}")
    public Produto pegarUmProduto(@PathVariable ("id") Long id){
        return produtoService.pegarUmProduto(id);
    }

    @PostMapping
    public void adicionarProduto(@RequestBody Produto produto) throws Exception {
        produtoService.criarProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable("id") Long id) throws Exception {
        produtoService.excluirProduto(id);
    }

    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable ("id") Long id,@RequestBody Produto produto){
        produtoService.atualizarProduto(id, produto);
    }




}
