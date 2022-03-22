package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

@Entity
public class Aluguel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long pedidoNumero;
    
    @Temporal(TemporalType.DATE)
    @futureOrPresent
    private Calendar dataInicioAluguel, dataFimAluguel;
    
    @ManyToOne @JsonManagedReference @JoinColumn(name = "id_cliente")
    @Valid
    private Cliente cliente;
    @OneToMany @JsonManagedReference
    @Valid
    private List<Conta> contas = new ArrayList<>();
    @ManyToOne @JsonManagedReference @JoinColumn(name = "id_funcionario")
    @Valid
    private Funcionario contatoSuporte;
    @OneToOne(cascade = CascadeType.ALL) @JsonManagedReference @JoinColumn(name = "id_pagamento")
    @Valid
    private Pagamento pagamento;

    public Aluguel(Calendar dataInicioAluguel, Calendar dataFimAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
        this.dataFimAluguel = dataFimAluguel;
    }

    public Aluguel() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public Funcionario getContatoSuporte() {
        return contatoSuporte;
    }

    public void setContatoSuporte(Funcionario contatoSuporte) {
        this.contatoSuporte = contatoSuporte;
    }

    public long getPedidoNumero() {
        return pedidoNumero;
    }

    public void setPedidoNumero(long pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
    }

    public Calendar getDataInicioAluguel() {
        return dataInicioAluguel;
    }

    public void setDataInicioAluguel(Calendar dataInicioAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
    }

    public Calendar getDataFimAluguel() {
        return dataFimAluguel;
    }

    public void setDataFimAluguel(Calendar dataFimAluguel) {
        this.dataFimAluguel = dataFimAluguel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.pedidoNumero;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aluguel other = (Aluguel) obj;
        if (this.pedidoNumero != other.pedidoNumero) {
            return false;
        }
        return true;
    }
    
    
}
