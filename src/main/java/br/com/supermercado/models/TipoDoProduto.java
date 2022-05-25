package br.com.supermercado.models;

import javax.persistence.*;

@Entity
@Table(name = "tipo_do_produto")
public class TipoDoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String tipoDoProduto;


}
