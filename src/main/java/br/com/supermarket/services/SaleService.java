package br.com.supermarket.services;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.ProductSale;
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
            Sale sale = createSaleReceivingDTO(saleDto);
            sale.autoVerify();
            saleRepository.save(sale);
    }

    public void deleteSale(Long id) throws Exception {
        if (id != null) {
            saleRepository.deleteById(id);
        } else {
            throw new Exception("Sale not found.");
        }
    }

    public Sale updateSale(SaleDto saleDto, Long id) throws Exception {
        try {
            Sale saleUpdated = saleRepository.findById(id).get();
            Sale sale = createSaleReceivingDTO(saleDto);
            sale.setSaleValue(saleDto.getSaleValue());
            saleUpdated.autoVerify();
            return saleRepository.save(saleUpdated);

        } catch (Exception e) {
            throw new Exception("Error updating the sale.");
        }
    }

    public Sale createSaleReceivingDTO(SaleDto saleDto) throws Exception {
        Sale sale = new Sale();
        sale = saleRepository.save(sale);
        sale.setSaleDate(DateUtility.getTimeDateCurrentString());
        sale.setSaleValue(saleDto.getSaleValue());
        ProductSale productSale = new ProductSale();
        productSale.setIdProduct(saleDto.getProductList().get(1).getId());
        productSale.setProductPrice(saleDto.getProductList().get(1).getPriceSale());
        productSale.setQuantityProduct(3L);
        sale.getProductSaleList().add(productSale);
        sale.getProductSaleList().get(0).setSale(sale);
        return sale;
    }

}
