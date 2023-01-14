package br.com.supermarket.resources;

import br.com.supermarket.models.ProductType;
import br.com.supermarket.services.TipoProdutoService;
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
    public ArrayList<ProductType> pegarTodosTiposDosProdutos() {
        return tipoProdutoService.pegarTodosTiposDosProdutos();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType pegarUmTipoDoProdutoPeloId(@PathVariable("id") Long id) {
        return tipoProdutoService.pegarUmTipoDoProdutoPeloId(id);
    }

    @GetMapping("/name")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> pegarTipoDoProdutoPorNome(@RequestParam(name = "nome") String nome) {
        return tipoProdutoService.pegarTipoDoProdutoPorNome(nome);
    }

    @GetMapping("/tipos")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> pegarTiposDoProdutoComSort(Sort sort) {
        return tipoProdutoService.pegarTiposDoProdutoComSort(sort);
    }

    @PostMapping
    @CrossOrigin(allowedHeaders = "*")
    public ProductType criarTipoDoProduto(@RequestBody ProductType tipo) throws Exception {
        return tipoProdutoService.criarTipoDoProduto(tipo);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType atualizarTipoDoProduto(@PathVariable("id") Long id, @RequestBody ProductType tipo)
            throws Exception {
        return tipoProdutoService.atualizarTipoDoProduto(id, tipo);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public void excluirTipoDoProdutoPeloId(@PathVariable("id") Long id) throws Exception {
        tipoProdutoService.excluirTipoDoProdutoPeloId(id);
    }

}
