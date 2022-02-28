package com.ongames.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Aluguel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int pedidoNumero;
    private Calendar dataInicioAluguel, dataFimAluguel;
    
    private Cliente cliente;
    private List<Conta> contas;
    private Funcionario contatoSuporte;

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

    public int getPedidoNumero() {
        return pedidoNumero;
    }

    public void setPedidoNumero(int pedidoNumero) {
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
