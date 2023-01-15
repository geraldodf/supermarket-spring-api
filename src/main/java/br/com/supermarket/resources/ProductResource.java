package br.com.supermarket.resources;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import br.com.supermarket.services.ProdutoService;
import java.util.ArrayList;

@RequestMapping("/products")
@RestController
public class ProductResource {

    @Autowired
    private ProdutoService productService;

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping
    public ArrayList<Product> getAllProducts() {
        return productService.pegarTodosProdutos();
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/name")
    public ArrayList<Product> searchProductsByDescription(@RequestParam(name = "description") String description)
            throws Exception {
        return productService.pesquisaProdutoPorDescricao(description);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/products")
    public Page<Product> searchPaginatedProducts(Pageable pageable) throws Exception {
        return productService.pesquisaPaginada(pageable);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/products-description")
    public Page<Product> searchByDescriptionPaged(@RequestParam(name = "description") String description,
                                                  Pageable pageable) throws Exception {
        return productService.pesquisaPorDescricaoPaginada(description, pageable);
    }

    // @CrossOrigin(allowedHeaders = "*")
    // @GetMapping("/produtos-tipo")
    // public ArrayList<Product> pesquisaPorTipoPaginada(@RequestParam(name = "nomeTipo") String nomeTipo,
    //         Pageable pageable) throws Exception {
    //     return produtoService.pesquisaPorTipoPaginada(nomeTipo, pageable);
    // }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/bar-code")
    public ArrayList<Product> pesquisarProdutoPorCodigo(@RequestParam(name = "barCode") Long barCode) throws Exception {
        return productService.pesquisaProdutoPorCodigo(barCode);

    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) throws Exception {
        return productService.pegarUmProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) throws Exception {
        productService.criarProduto(productDto);
    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws Exception {
        productService.excluirProduto(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) throws Exception {
        productService.atualizarProduto(id, productDto);
    }

}