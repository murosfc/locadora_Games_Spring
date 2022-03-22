package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;


@Entity
public class Aluguel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long pedidoNumero;
    
    @Temporal(TemporalType.DATE)
    @FutureOrPresent
    @NotNull (message="A data é indispensável")
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
        int hash = 3;
        hash = 97 * hash + (int) (this.pedidoNumero ^ (this.pedidoNumero >>> 32));
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
