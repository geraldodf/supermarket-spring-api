package supermercado.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supermercado.api.models.Produto;
import supermercado.api.services.ProdutoService;

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

    @PostMapping
    public void adicionarProduto(Produto produto){
        produtoService.criarProduto(produto);
    }


}
