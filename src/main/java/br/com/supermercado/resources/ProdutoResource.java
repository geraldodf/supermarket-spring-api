package br.com.supermercado.resources;

import br.com.supermercado.dtos.ProdutoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import br.com.supermercado.models.Produto;
import br.com.supermercado.services.ProdutoService;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/produtos")
@RestController
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping
    public ArrayList<Produto> pegarTodosProdutos() {
        return produtoService.pegarTodosProdutos();
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/pesquisa-por-descricao")
    public ArrayList<Produto> pesquisaProdutoPorDescricao(@RequestParam(name = "descricao") String descricao)
            throws Exception {
        return produtoService.pesquisaProdutoPorDescricao(descricao);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/pagina")
    public List<Produto> pesquisaPaginada(Pageable pageable) throws Exception {
        return produtoService.pesquisaPaginada(pageable);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/teste")
    public List<Produto> pesquisaPorDescricaoPaginada(@RequestParam(name = "descricao") String descricao,
            Pageable pageable) throws Exception {
        return produtoService.pesquisaPorDescricaoPaginada(descricao, pageable);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/pesquisa-por-codigo")
    public ArrayList<Produto> pesquisarProdutoPorCodigo(@RequestParam(name = "codigo") Long codigo) throws Exception {
        return produtoService.pesquisaProdutoPorCodigo(codigo);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/{id}")
    public Produto pegarUmProduto(@PathVariable("id") Long id) throws Exception {
        return produtoService.pegarUmProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PostMapping
    public void adicionarProduto(@RequestBody ProdutoDto produtoDto) throws Exception {
        produtoService.criarProduto(produtoDto);
    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable("id") Long id) throws Exception {
        produtoService.excluirProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable("id") Long id, @RequestBody ProdutoDto produtoDto) throws Exception {
        produtoService.atualizarProduto(id, produtoDto);
    }

}
