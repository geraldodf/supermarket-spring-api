package br.com.supermarket.resources;

import br.com.supermarket.models.ProductType;
import br.com.supermarket.services.TipoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/products-types")
public class ProductTypeResource {

    @Autowired
    private TipoProdutoService productTypeService;

    @GetMapping
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getAllProductsTypes() {
        return productTypeService.pegarTodosTiposDosProdutos();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType getProductTypeById(@PathVariable("id") Long id) {
        return productTypeService.pegarUmTipoDoProdutoPeloId(id);
    }

    @GetMapping("/name")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getProductsTypesByName(@RequestParam(name = "name") String name) {
        return productTypeService.pegarTipoDoProdutoPorNome(name);
    }

    @GetMapping("/types")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getProductsTypesSort(Sort sort) {
        return productTypeService.pegarTiposDoProdutoComSort(sort);
    }

    @PostMapping
    @CrossOrigin(allowedHeaders = "*")
    public ProductType createProductType(@RequestBody ProductType productType) throws Exception {
        return productTypeService.criarTipoDoProduto(productType);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType updateProductType
            (@PathVariable("id") Long id, @RequestBody ProductType productType)
            throws Exception {
        return productTypeService.atualizarTipoDoProduto(id, productType);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public void deleteProductTypeById(@PathVariable("id") Long id) throws Exception {
        productTypeService.excluirTipoDoProdutoPeloId(id);
    }

}
