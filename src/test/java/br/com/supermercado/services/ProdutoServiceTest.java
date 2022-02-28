package br.com.supermercado.services;

import br.com.supermercado.Dto.ProdutoDto;
import br.com.supermercado.models.Produto;
import br.com.supermercado.repositories.ProdutoRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;

@SpringBootTest
class ProdutoServiceTest {

    @InjectMocks
    private  ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Rule
    public ErrorCollector error = new ErrorCollector();



    @Test
    public void criarProduto() throws Exception {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setCodigo(1L);
        produtoDto.setQuantidade(1L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2));

        Produto produto = produtoService.criarProduto(produtoDto);

        error.checkThat(produto.getCodigo(), is(1L));

    }
}