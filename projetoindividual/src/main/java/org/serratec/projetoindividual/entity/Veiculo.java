package org.serratec.projetoindividual.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private double maximoDesconto;

    @Column(nullable = false)
    private boolean vendido;

    // O Double serve para a possibilidade de ser null, neste caso
    @Column
    private Double valorVenda;

    public Veiculo() {}
    public Veiculo(Cliente cliente, String marca, String modelo, int ano, double valor, String placa, double maximoDesconto, boolean vendido, Double valorVenda) {
        this.cliente = cliente;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.placa = placa;
        this.maximoDesconto = maximoDesconto;
        this.vendido = vendido;
        if(isVendido())
            this.valorVenda = valorVenda;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getMaximoDesconto() {
        return maximoDesconto;
    }

    public void setMaximoDesconto(double maximoDesconto) {
        this.maximoDesconto = maximoDesconto;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }
}
