package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.models.Product;
import br.com.supermarket.models.ProductType;
import br.com.supermarket.util.DataUtilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.supermarket.repositories.ProdutoRepository;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private TipoProdutoService tipoProdutoService;

    public ArrayList<Product> pegarTodosProdutos() {
        return (ArrayList<Product>) produtoRepository.findAll();
    }

    public Product pegarUmProduto(Long id) throws Exception {

        Optional<Product> produtoBuscadoPeloID = produtoRepository.findById(id);
        return produtoBuscadoPeloID.get();
    }

    public ArrayList<Product> pesquisaProdutoPorCodigo(Long codigo) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(codigo) == null) {
            throw new Exception("Product inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorCodigo(codigo);
    }

    public ArrayList<Product> pesquisaProdutoPorDescricao(String descricao) throws Exception {
        if (produtoRepository.pesquisaPorDescricao(descricao) == null) {
            throw new Exception("Product inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorDescricao(descricao);
    }

    public Page<Product> pesquisaPaginada(Pageable pageable) throws Exception {
        Page<Product> page;
        try {
            page = produtoRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception("Erro ao fazer consulta no banco de dados.");
        }
        return page;
    }

    public Page<Product> pesquisaPorDescricaoPaginada(String descricao, Pageable pageable) {
        return produtoRepository.pesquisaPorDescricaoPaginada(descricao, pageable);
    }

    // public ArrayList<Product> pesquisaPorTipoPaginada(String nomeTipo, Pageable pageable) {
    //     ArrayList<ProductType> listaRetornoDeTipos = tipoDoProdutoService.pegarTipoDoProdutoPorNome(nomeTipo);
    //     ProductType tipoDoProdutoParaBusca = listaRetornoDeTipos.get(0);
    //     return produtoRepository.pesquisaPorTipoPaginada(tipoDoProdutoParaBusca.getId(), pageable);
    // }
        //fixme


    public Product criarProduto(ProductDto productDto) throws Exception {
        verificarProdutoDto(productDto);
        Product product = criandoProdutoComDto(productDto);
        product.autoVerify();
        try {
            if (product.verifyProductAttributesNoNull()) {
                product.setNetProfit(product.getPriceSale().subtract(product.getPriceBuy()));
                produtoRepository.save(product);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Product está com algum atributo inválido! Tente novamente.");
        }
        return product;
    }

    public void excluirProduto(Long id) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(id) == null) {
            throw new Exception("Product inexistente! verifique e tente novamente.");
        }
        produtoRepository.deleteById(id);
    }

    public void atualizarProduto(Long id, ProductDto productDto) throws Exception {
        if (id == null) {
            throw new Exception("Product inexistente!");
        }
        Optional<Product> resposta = produtoRepository.findById(id);
        Product productAAtualizar = resposta.get();
        Product product = criandoProdutoComDto(productDto);
        product.autoVerify();

        try {
            if (product.verifyProductAttributesNoNull()) {

                productAAtualizar.setBarCode(product.getBarCode());
                productAAtualizar.setDescription(product.getDescription());
                productAAtualizar.setPriceSale(product.getPriceSale());
                productAAtualizar.setPriceBuy(product.getPriceBuy());
                productAAtualizar.setQuantity(product.getQuantity());
                productAAtualizar.setNetProfit(product.getPriceSale().subtract(product.getPriceBuy()));
                produtoRepository.save(productAAtualizar);

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Product está com algum atributo inválido! Tente novamente.");
        }
    }

    public void verificarProdutoDto(ProductDto productDto) throws Exception {
        if (productDto.getBarCode() == null) {
            throw new ProductNullBarcodeException("O código está nulo.");
        }
        if (productDto.getBarCode() < 0) {
            throw new ProductInvalidBarcodeException("O código fornecido é inválido.");
        }
        if (productDto.getDescription() == null) {
            throw new ProductDescriptionNullException(

                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (productDto.getDescription().length() <= 5) {
            throw new ProductDescriptionInvalidException(

                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (productDto.getPriceSale() == null) {
            throw new ProductSalePriceNullException("O produto deve ter um preço de venda.");
        }
        if (productDto.getPriceBuy() == null) {
            throw new ProductPurchasePriceNullException("O produto deve ter um preço de compra.");
        }
        if (productDto.getQuantity() == null) {
            throw new ProductQuantityNullException("Quantidade deve ser listada.");
        }
        if (productDto.getIdProductType() == null) {
            throw new TypeProductNullException("Tipo do produto inválido");
        }
    }

    public Product criandoProdutoComDto(ProductDto productDto) throws Exception {
        Product product = new Product();
        product.setBarCode(productDto.getBarCode());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPriceBuy(productDto.getPriceBuy());
        product.setPriceSale(productDto.getPriceSale());
        product.setNetProfit(productDto.getPriceSale().subtract(productDto.getPriceBuy()));
        product.setCreationDate(DataUtilitario.getDataAtualComoString());

        ProductType productTypeOptional = tipoProdutoService
                .pegarUmTipoDoProdutoPeloId(productDto.getIdProductType());

        product.setProductType(productTypeOptional);

        return product;
    }

   
}
