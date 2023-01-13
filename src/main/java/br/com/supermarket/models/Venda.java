package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "venda_valor")
    private BigDecimal vendaValor = new BigDecimal(0.0);

    @ManyToMany
    @JoinTable(
            name = "vendas_produtos",
            joinColumns = @JoinColumn(name = "vendafk"),
            inverseJoinColumns = @JoinColumn(name = "produtofk")
    )
    private List<Produto> listaDeProdutos;

}
