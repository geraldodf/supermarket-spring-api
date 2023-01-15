package br.com.supermarket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipo_produto")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_produto_id")
    private Long id;

    @Column(name = "nome_tipo_produto")
    private String nameProductType;

    @OneToMany(mappedBy = "tipoProduto")
    @JsonIgnore
    private List<Product> ProductList;


}