package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_street_name")
    private String streetName;
    @Column(name = "address_number")
    private Long number;
    @Column(name = "address_complement")
    private String complement;
    @Column(name = "address_reference_point")
    private String referencePoint;
    @Column(name = "address_neighborhood")
    private String neighborhood;
    @Column(name = "address_cep")
    private String cep;
    @Column(name = "address_city")
    private String city;
    @Column(name = "address_uf")
    private String uf;


}

