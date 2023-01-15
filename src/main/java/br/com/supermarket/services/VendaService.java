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
public class VendaService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProdutoService produtoService;


    public ArrayList<Sale> pegarTodasVendas() {

        return (ArrayList<Sale>) saleRepository.findAll();
    }

    public Sale pegarVendaPeloId(Long id) throws Exception {
        try {
            Optional<Sale> vendaOptional = saleRepository.findById(id);
            return vendaOptional.get();
        } catch (Exception e) {
            throw new Exception("Sale inexistente!");
        }

    }

    public void criarVenda(SaleDto saleDto) throws Exception {
        try {
            Sale sale = criarVendaComDto(saleDto);
            verificarVenda(sale);
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new Exception("Erro ao criar a venda!");
        }

    }

    public void excluirVenda(Long id) throws Exception {
        if (id != null) {
            saleRepository.deleteById(id);
        } else {
            throw new Exception("Sale não encontrada.");
        }
    }

    public void atualizarVenda(SaleDto saleDto, Long id) throws Exception {


        try {
            Optional<Sale> vendaOptional = saleRepository.findById(id);
            Sale saleASerAtualizada = vendaOptional.get();

            Sale sale = criarVendaComDto(saleDto);
            saleASerAtualizada.setProductList(sale.getProductList());

            sale.getProductList().forEach(p -> {
                saleASerAtualizada.setSaleValue(saleASerAtualizada.getSaleValue().add(p.getPriceSale()));
            });

            verificarVenda(saleASerAtualizada);
            saleRepository.save(saleASerAtualizada);
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o produto.");
        }


    }

    public Sale criarVendaComDto(SaleDto saleDto) throws Exception {

        Sale saleCriada = new Sale();
        ArrayList<Product> listaDeProducts = new ArrayList<>();

        saleCriada.setSaleDate(DateUtility.getTimeDateCurrentString());

        saleDto.getIdProduto().forEach(p -> {
            try {
                listaDeProducts.add(produtoService.pegarUmProduto(p));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            listaDeProducts.forEach(produto -> {
                saleCriada.setSaleValue(saleCriada.getSaleValue().add(produto.getPriceSale()));
            });
        } catch (Exception e) {
            throw new Exception("Erro ao adicionar produtos na venda");
        }

        saleCriada.setProductList(listaDeProducts);
        verificarVenda(saleCriada);
        return saleCriada;
    }

    public void verificarVenda(Sale sale) throws Exception {
        if (sale.getSaleValue() == null) {
            throw new Exception("Não pode criar uma sale que não tenha um valor.");
        }
        if (sale.getSaleDate() == null) {
            throw new Exception("A Sale deve ter uma data válida!");
        }
        if (sale.getProductList() == null) {
            throw new Exception("A sale deve ter ao menos 1 produto.");
        }
    }
}
