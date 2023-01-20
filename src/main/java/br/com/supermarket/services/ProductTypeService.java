package br.com.supermarket.services;

import br.com.supermarket.models.ProductType;
import br.com.supermarket.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public ArrayList<ProductType> getAllProductsTypes() {
        return (ArrayList<ProductType>) productTypeRepository.findAll();
    }


    public ArrayList<ProductType> getProductsTypesByName(String name) {
        return productTypeRepository.searchByName(name);
    }

    public ArrayList<ProductType> getProductsTypesSort(Sort sort) {
        return (ArrayList<ProductType>) productTypeRepository.findAll(sort);
    }

    public ProductType getProductTypeById(Long id) {
        Optional<ProductType> productTYpe = productTypeRepository.findById(id);
        return productTYpe.get();
    }

    public ProductType createProductType(ProductType tipo) throws Exception {
        if (tipo.getNameProductType() == null || tipo.getNameProductType().length() < 5)
            throw new Exception("Type cannot be created without a name, or with a name of less than 5 characters.");
        return productTypeRepository.save(tipo);
    }

    public ProductType updateProductType(Long id, ProductType tipo) throws Exception {
        if (id == null) throw new Exception("Unable to update a type without specifying who will update");
        if (tipo.getNameProductType() == null) throw new Exception("Type cannot be created without a name.");


        Optional<ProductType> productTypeOptional = null;
        try {
            productTypeOptional = productTypeRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Error fetching type to be updated.");
        }

        ProductType productType = null;
        try {
            productType = productTypeOptional.get();
        } catch (Exception e) {
            throw new Exception("Error handling type to be updated.");
        }

        try {
            productType.setNameProductType(tipo.getNameProductType());
        } catch (Exception e) {
            throw new Exception("Error updating type.");
        }

        return productTypeRepository.save(productType);
    }

    public void deleteProductType(Long id) throws Exception {
        if (id == null)
            throw new Exception("To exclude a type it must be specified which type will be excluded, this answer is not coming!");
        productTypeRepository.deleteById(id);
    }

}