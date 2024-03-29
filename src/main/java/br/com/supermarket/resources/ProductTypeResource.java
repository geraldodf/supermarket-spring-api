package br.com.supermarket.resources;

import br.com.supermarket.models.ProductType;
import br.com.supermarket.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/products-types")
public class ProductTypeResource {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getAllProductsTypes() {
        return productTypeService.getAllProductsTypes();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType getProductTypeById(@PathVariable("id") Long id) {
        return productTypeService.getProductTypeById(id);
    }

    @GetMapping("/name")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getProductsTypesByName(@RequestParam(name = "name") String name) {
        return productTypeService.getProductsTypesByName(name);
    }

    @GetMapping("/types")
    @CrossOrigin(allowedHeaders = "*")
    public ArrayList<ProductType> getProductsTypesSort(Sort sort) {
        return productTypeService.getProductsTypesSort(sort);
    }

    @PostMapping
    @CrossOrigin(allowedHeaders = "*")
    public ProductType createProductType(@RequestBody ProductType productType) throws Exception {
        return productTypeService.createProductType(productType);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public ProductType updateProductType
            (@PathVariable("id") Long id, @RequestBody ProductType productType)
            throws Exception {
        return productTypeService.updateProductType(id, productType);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*")
    public void deleteProductTypeById(@PathVariable("id") Long id) throws Exception {
        productTypeService.deleteProductType(id);
    }

}
