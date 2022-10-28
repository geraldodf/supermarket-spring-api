package br.com.supermercado.dtos;

import java.util.ArrayList;

import br.com.supermercado.models.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoacaoDto {

    private ArrayList<Produto> listaDeProdutosAteF;
    private ArrayList<Produto> listaDeProdutosAteO;
    private ArrayList<Produto> listaDeProdutosAteZ;

}
