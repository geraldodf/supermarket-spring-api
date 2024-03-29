package br.com.supermarket.resources;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import br.com.supermarket.services.ProductService;
import java.util.ArrayList;

@RequestMapping("/products")
@RestController
public class ProductResource {

    @Autowired
    private ProductService productService;

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping
    public ArrayList<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/description")
    public ArrayList<Product> searchProductsByDescription(@RequestParam(name = "description") String description)
            throws Exception {
        return productService.getProductsByDescription(description);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/products")
    public Page<Product> searchPaginatedProducts(Pageable pageable) throws Exception {
        return productService.paginatedSearch(pageable);
    }


    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/page")
    public Page<Product> searchProductsPaged(@RequestParam(required = false) Long typeId,
                                             @RequestParam(required = false) String typeName,
                                             @RequestParam(required = false) String description,
                                             Pageable pageable) {
        return productService.searchProductsPaged(typeId, typeName, description, pageable);
    }


    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/bar-code")
    public ArrayList<Product> getProductsByBarCode(@RequestParam(name = "barCode") Long barCode) throws Exception {
        return productService.getProductsByBarCode(barCode);

    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) throws Exception {
        return productService.getProductById(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PostMapping
    public Product createProduct(@RequestBody ProductDto productDto) throws Exception {
        return productService.createProduct(productDto);
    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws Exception {
        productService.DeleteProduct(id);
    }

    @CrossOrigin(allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) throws Exception {
        productService.updateProduct(id, productDto);
    }

}
