package br.com.supermarket.services;

import br.com.supermarket.dtos.SaleDto;
import br.com.supermarket.models.Produto;
import br.com.supermarket.models.Venda;
import br.com.supermarket.repositories.VendaRepository;
import br.com.supermarket.util.DataUtilitario;
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

    public void criarVenda(SaleDto saleDto) throws Exception {
        try {
            Venda venda = criarVendaComDto(saleDto);
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

    public void atualizarVenda(SaleDto saleDto, Long id) throws Exception {


        try {
            Optional<Venda> vendaOptional = vendaRepository.findById(id);
            Venda vendaASerAtualizada = vendaOptional.get();

            Venda venda = criarVendaComDto(saleDto);
            vendaASerAtualizada.setListaDeProdutos(venda.getListaDeProdutos());

            venda.getListaDeProdutos().forEach(p -> {
                vendaASerAtualizada.setVendaValor(vendaASerAtualizada.getVendaValor().add(p.getPrecoVenda()));
            });

            verificarVenda(vendaASerAtualizada);
            vendaRepository.save(vendaASerAtualizada);
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o produto.");
        }


    }

    public Venda criarVendaComDto(SaleDto saleDto) throws Exception {

        Venda vendaCriada = new Venda();
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();

        vendaCriada.setVendaData(DataUtilitario.getHorarioEDataAtualString());

        saleDto.getIdProduto().forEach(p -> {
            try {
                listaDeProdutos.add(produtoService.pegarUmProduto(p));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            listaDeProdutos.forEach(produto -> {
                vendaCriada.setVendaValor(vendaCriada.getVendaValor().add(produto.getPrecoVenda()));
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
