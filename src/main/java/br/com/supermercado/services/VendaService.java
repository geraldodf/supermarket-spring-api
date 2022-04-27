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
        venda = criarVendaComDto(vendaDto);
        vendaRepository.save(venda);
    }

    public void excluirVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    public void atualizarVenda(VendaDto vendaDto, Long id) {

        Optional<Venda> vendaOptional = vendaRepository.findById(id);
        Venda venda = vendaOptional.get();



    }

    public Venda criarVendaComDto(VendaDto vendaDto) throws Exception {

        Venda vendaCriada = new Venda();
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();

        vendaCriada.setVendaData(DataUtilitario.getHorarioEDataAtualString());
        vendaDto.getIdProduto().forEach( p -> {
            try {
                listaDeProdutos.add(produtoService.pegarUmProduto(p));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        vendaCriada.setListaDeProdutos(listaDeProdutos);

        return vendaCriada;
    }
}
