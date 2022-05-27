package br.com.supermercado.resources;

import br.com.supermercado.services.TipoDoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipo-do-produto")
public class TipoDoProdutoResource {

    @Autowired
    private TipoDoProdutoService tipoDoProdutoService;

    @GetMapping
    public void pegarTodosTiposDosProdutos(){
        tipoDoProdutoService.pegarTodosTiposDosProdutos();
    }

}
