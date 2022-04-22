package br.com.supermercado.services;

import br.com.supermercado.Dto.VendaDto;
import br.com.supermercado.models.Produto;
import br.com.supermercado.models.Venda;
import br.com.supermercado.repositories.ProdutoRepository;
import br.com.supermercado.repositories.VendaRepository;
import br.com.supermercado.util.DataUtilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;


    public ArrayList<Venda> pegarTodasVendas() {
        return (ArrayList<Venda>) vendaRepository.findAll();
    }

    public void criarVenda(VendaDto vendaDto) throws Exception {
        Venda venda = new Venda();
        ArrayList<Produto> listaDeProdutos = new ArrayList<Produto>();
        Optional<Produto> produtoOptionalBuscadoPeloID = produtoRepository.findById(vendaDto.getIdProduto());
        Produto produto = produtoOptionalBuscadoPeloID.get();

        listaDeProdutos.add(produto);
        venda.setListaDeProdutos(listaDeProdutos);
        venda.setVendaData(DataUtilitario.getHorarioEDataAtualString());
        vendaRepository.save(venda);
    }
}
