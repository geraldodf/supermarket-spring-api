package br.com.supermercado.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venda_id")
    private Long id;

    @Column(name = "venda_data")
    private String vendaData;

    @ManyToMany
    @JoinTable(
            name = "vendas_produtos",
            joinColumns = @JoinColumn(name = "vendafk"),
            inverseJoinColumns = @JoinColumn(name = "produtofk")
    )
    private List<Produto> listaDeProdutos;

}
