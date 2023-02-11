package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.models.Product;
import br.com.supermarket.models.ProductType;
import br.com.supermarket.repositories.ProductRepository;
import br.com.supermarket.util.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeService productTypeService;

    public ArrayList<Product> getAllProducts() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    public Product getProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

    public ArrayList<Product> getProductsByBarCode(Long barCode) throws Exception {
        if (productRepository.searchByBarCode(barCode) == null) {
            throw new Exception("Product nonexistent! Verify and try again.");
        }
        return productRepository.searchByBarCode(barCode);
    }

    public ArrayList<Product> getProductsByDescription(String description) throws Exception {
        if (productRepository.searchByDescription(description) == null) {
            throw new Exception("Product nonexistent! Verify and try again.");
        }
        return productRepository.searchByDescription(description);
    }

    public Page<Product> paginatedSearch(Pageable pageable) throws Exception {
        Page<Product> page;
        try {
            page = productRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception("Error querying the database.");
        }
        return page;
    }

    public Page<Product> searchByDescriptionPaginated(String description, Pageable pageable) {
        return productRepository.searchByDescriptionPaged(description, pageable);
    }

    public Page<Product> searchByProductTypeNamePaged(String typeName, Pageable pageable) {
        ArrayList<ProductType> productsTypes = productTypeService.getProductsTypesByName(typeName);
        ProductType productType = productsTypes.get(0);
        return productRepository.searchByProductTypeIdPaged(productType.getId(), pageable);
    }

    public Page<Product> searchByProductTypeIdPaged(Long typeId, Pageable pageable) {
        ProductType productType = productTypeService.getProductTypeById(typeId);
        return productRepository.searchByProductTypeIdPaged(productType.getId(), pageable);
    }

    public Page<Product> searchProductsPaged(Long typeId, String typeName, String description, Pageable pageable) {
        if (typeName != null) {
            return searchByProductTypeNamePaged(typeName, pageable);
        }
        if (typeId != null) {
            return searchByProductTypeIdPaged(typeId, pageable);
        }
        if (description != null) {
            return searchByDescriptionPaginated(description, pageable);
        }
        return productRepository.findAll(pageable);
    }


    public Product createProduct(ProductDto productDto) throws Exception {
        verifyProductDto(productDto);
        Product product = createProductReceivingDto(productDto);
        product.autoVerify();
        try {
            if (product.verifyProductAttributesNoNull()) {
                product.setNetProfit(product.getPriceSale().subtract(product.getPriceBuy()));
                productRepository.save(product);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Product has an invalid attribute! Try again.");
        }
        return product;
    }

    public void DeleteProduct(Long id) throws Exception {
        if (productRepository.searchByBarCode(id) == null) {
            throw new Exception("Product does not exist! Please check and try again.");
        }
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductDto productDto) throws Exception {
        if (id == null) {
            throw new Exception("Product does not exist!");
        }
        Optional<Product> productReturn = productRepository.findById(id);
        Product productUpdated = productReturn.get();
        Product product = createProductReceivingDto(productDto);
        product.autoVerify();

        try {
            if (product.verifyProductAttributesNoNull()) {

                productUpdated.setBarCode(product.getBarCode());
                productUpdated.setDescription(product.getDescription());
                productUpdated.setPriceSale(product.getPriceSale());
                productUpdated.setPriceBuy(product.getPriceBuy());
                productUpdated.setQuantity(product.getQuantity());
                productUpdated.setNetProfit(product.getPriceSale().subtract(product.getPriceBuy()));
                productRepository.save(productUpdated);

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Product has an invalid attribute! Try again.");
        }
    }

    public void verifyProductDto(ProductDto productDto) {
        if (productDto.getBarCode() == null) {
            throw new ProductNullBarcodeException("The bar code is null.");
        }
        if (productDto.getBarCode() < 0) {
            throw new ProductInvalidBarcodeException("The bard code is invalid.");
        }
        if (productDto.getDescription() == null) {
            throw new ProductDescriptionNullException(
                    "The product description is void! Product description must be at least 5 characters long.");
        }
        if (productDto.getDescription().length() <= 5) {
            throw new ProductDescriptionInvalidException(
                    "The product description is void! Product description must be at least 5 characters long.");
        }
        if (productDto.getPriceSale() == null) {
            throw new ProductSalePriceNullException("The product must have a selling price.");
        }
        if (productDto.getPriceBuy() == null) {
            throw new ProductPurchasePriceNullException("The product must have a purchase price.");
        }
        if (productDto.getQuantity() == null) {
            throw new ProductQuantityNullException("Quantity must be listed.");
        }
        if (productDto.getIdProductType() == null) {
            throw new TypeProductNullException("Invalid product type");
        }
    }

    public Product createProductReceivingDto(ProductDto productDto) {
        Product product = new Product();
        product.setBarCode(productDto.getBarCode());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPriceBuy(productDto.getPriceBuy());
        product.setPriceSale(productDto.getPriceSale());
        product.setNetProfit(productDto.getPriceSale().subtract(productDto.getPriceBuy()));
        product.setCreationDate(DateUtility.getCurrentDateString());

        ProductType productTypeOptional = productTypeService
                .getProductTypeById(productDto.getIdProductType());

        product.setProductType(productTypeOptional);
        return product;
    }


}
