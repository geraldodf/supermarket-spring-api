package br.com.supermercado.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_nome")
    private String nome;

    @Column(name = "usuario_cargo")
    private String cargo;

    @Column(name = "usuario_senha")
    private BigInteger senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigInteger getSenha() {
        return senha;
    }

    public void setSenha(BigInteger senha) {
        this.senha = senha;
    }
}
