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

    public Venda pegarVendaPeloId(Long id) throws Exception {
        try {
            Optional<Venda> vendaOptional = vendaRepository.findById(id);
            return vendaOptional.get();
        } catch (Exception e) {
            throw new Exception("Venda inexistente!");
        }

    }

    public void criarVenda(VendaDto vendaDto) throws Exception {
        try {
            Venda venda = criarVendaComDto(vendaDto);
            verificarVenda(venda);
            vendaRepository.save(venda);
        } catch (Exception e) {
            throw new Exception("Erro ao criar a venda!");
        }

    }

    public void excluirVenda(Long id) throws Exception {
        if (id != null) {
            vendaRepository.deleteById(id);
        } else {
            throw new Exception("Venda não encontrada.");
        }
    }

    public void atualizarVenda(VendaDto vendaDto, Long id) throws Exception {


        try {
            Optional<Venda> vendaOptional = vendaRepository.findById(id);
            Venda vendaASerAtualizada = vendaOptional.get();

            Venda venda = criarVendaComDto(vendaDto);
            vendaASerAtualizada.setListaDeProdutos(venda.getListaDeProdutos());

            venda.getListaDeProdutos().forEach(p -> {
                vendaASerAtualizada.setVendaValor(vendaASerAtualizada.getVendaValor().add(p.getPrecoDeVenda()));
            });

            verificarVenda(vendaASerAtualizada);
            vendaRepository.save(vendaASerAtualizada);
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o produto.");
        }


    }

    public Venda criarVendaComDto(VendaDto vendaDto) throws Exception {

        Venda vendaCriada = new Venda();
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();

        vendaCriada.setVendaData(DataUtilitario.getHorarioEDataAtualString());

        vendaDto.getIdProduto().forEach(p -> {
            try {
                listaDeProdutos.add(produtoService.pegarUmProduto(p));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            listaDeProdutos.forEach(produto -> {
                vendaCriada.setVendaValor(vendaCriada.getVendaValor().add(produto.getPrecoDeVenda()));
            });
        } catch (Exception e) {
            throw new Exception("Erro ao adicionar produtos na venda");
        }

        vendaCriada.setListaDeProdutos(listaDeProdutos);
        verificarVenda(vendaCriada);
        return vendaCriada;
    }

    public void verificarVenda(Venda venda) throws Exception {
        if (venda.getVendaValor() == null) {
            throw new Exception("Não pode criar uma venda que não tenha um valor.");
        }
        if (venda.getVendaData() == null) {
            throw new Exception("A Venda deve ter uma data válida!");
        }
        if (venda.getListaDeProdutos() == null) {
            throw new Exception("A venda deve ter ao menos 1 produto.");
        }
    }
}
