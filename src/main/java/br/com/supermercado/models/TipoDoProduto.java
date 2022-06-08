package br.com.supermercado.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipo_do_produto")
public class TipoDoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_do_produto_id")
    private Long id;

    @Column(name = "nome_tipo_do_produto")
    private String nomeTipoDoProduto;

    @OneToMany(mappedBy = "tipoDoProduto")
    private List<Produto> listaDeProdutos;


}
