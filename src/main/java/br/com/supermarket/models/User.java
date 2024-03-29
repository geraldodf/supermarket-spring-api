package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_fk")
    private List<Address> addresses;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_surname")
    private String surname;
    @Column(name = "user_phone_number")
    private String phoneNumber;
    @Column(name = "user_phone_number_reserve")
    private String phoneNumberReserve;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password")
    private String password;

    public User() {
        this.addresses = new ArrayList<>();
    }
}