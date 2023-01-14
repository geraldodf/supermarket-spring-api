package br.com.supermarket.resources;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import br.com.supermarket.services.ProdutoService;
import java.util.ArrayList;

@RequestMapping("/produtos")
@RestController
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping
    public ArrayList<Product> pegarTodosProdutos() {
        return produtoService.pegarTodosProdutos();
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/name")
    public ArrayList<Product> pesquisaProdutoPorDescricao(@RequestParam(name = "descricao") String descricao)
            throws Exception {
        return produtoService.pesquisaProdutoPorDescricao(descricao);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/produtos")
    public Page<Product> pesquisaPaginada(Pageable pageable) throws Exception {
        return produtoService.pesquisaPaginada(pageable);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/produtos-descricao")
    public Page<Product> pesquisaPorDescricaoPaginada(@RequestParam(name = "descricao") String descricao,
                                                      Pageable pageable) throws Exception {
        return produtoService.pesquisaPorDescricaoPaginada(descricao, pageable);
    }

    // @CrossOrigin(allowedHeaders = "*")
    // @GetMapping("/produtos-tipo")
    // public ArrayList<Product> pesquisaPorTipoPaginada(@RequestParam(name = "nomeTipo") String nomeTipo,
    //         Pageable pageable) throws Exception {
    //     return produtoService.pesquisaPorTipoPaginada(nomeTipo, pageable);
    // }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/pesquisa-por-codigo")
    public ArrayList<Product> pesquisarProdutoPorCodigo(@RequestParam(name = "codigo") Long codigo) throws Exception {
        return produtoService.pesquisaProdutoPorCodigo(codigo);

    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/{id}")
    public Product pegarUmProduto(@PathVariable("id") Long id) throws Exception {
        return produtoService.pegarUmProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PostMapping
    public void adicionarProduto(@RequestBody ProductDto productDto) throws Exception {
        produtoService.criarProduto(productDto);
    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable("id") Long id) throws Exception {
        produtoService.excluirProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable("id") Long id, @RequestBody ProductDto productDto) throws Exception {
        produtoService.atualizarProduto(id, productDto);
    }

}
