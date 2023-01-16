package br.com.supermarket.services;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.Product;
import br.com.supermarket.models.Sale;
import br.com.supermarket.repositories.SaleRepository;
import br.com.supermarket.util.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductService productService;


    public ArrayList<Sale> getAllSales() {
        return (ArrayList<Sale>) saleRepository.findAll();
    }

    public Sale getSaleById(Long id) throws Exception {
        try {
            Optional<Sale> saleOptional = saleRepository.findById(id);
            return saleOptional.get();
        } catch (Exception e) {
            throw new Exception("Sale nonexistent!");
        }
    }

    public void createSale(SaleDto saleDto) throws Exception {
        try {
            Sale sale = createSaleReceivingDTO(saleDto);
            verifySale(sale);
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new Exception("Error creating sale.");
        }
    }

    public void deleteSale(Long id) throws Exception {
        if (id != null) {
            saleRepository.deleteById(id);
        } else {
            throw new Exception("Sale not found.");
        }
    }

    public void updateSale(SaleDto saleDto, Long id) throws Exception {
        try {
            Optional<Sale> optionalSale = saleRepository.findById(id);
            Sale saleUpdated = optionalSale.get();
            Sale sale = createSaleReceivingDTO(saleDto);
            saleUpdated.setProductList(sale.getProductList());
            sale.getProductList().forEach(p -> {
                saleUpdated.setSaleValue(saleUpdated.getSaleValue().add(p.getPriceSale()));
            });
            verifySale(saleUpdated);
            saleRepository.save(saleUpdated);
        } catch (Exception e) {
            throw new Exception("Error updating the sale.");
        }
    }

    public Sale createSaleReceivingDTO(SaleDto saleDto) throws Exception {

        Sale sale = new Sale();
        ArrayList<Product> productList = new ArrayList<>();

        sale.setSaleDate(DateUtility.getTimeDateCurrentString());

        saleDto.getIdProduto().forEach(p -> {
            try {
                productList.add(productService.getProductById(p));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            productList.forEach(p -> {
                sale.setSaleValue(sale.getSaleValue().add(p.getPriceSale()));
            });
        } catch (Exception e) {
            throw new Exception("Error adding products to sale.");
        }

        sale.setProductList(productList);
        verifySale(sale);
        return sale;
    }

    public void verifySale(Sale sale) throws Exception {
        if (sale.getSaleValue() == null) {
            throw new Exception("You cannot create a sale that does not have a value.");
        }
        if (sale.getSaleDate() == null) {
            throw new Exception("The Sale must have a valid date.");
        }
        if (sale.getProductList() == null) {
            throw new Exception("The sale must have at least 1 product.");
        }
    }
}
