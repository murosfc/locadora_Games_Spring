package com.ongames.model;

import java.io.Serializable;
import java.util.Calendar;

public class Pagamento implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Calendar dataPagamento;
    private float valor;
    private String validacao;
    
    private Aluguel alguel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getValidacao() {
        return validacao;
    }

    //validação é um processo interno da classe, não pode ser definido ou alterado por outra classe
    private void setValidacao(String validacao) {
        this.validacao = validacao;
    }

    public Aluguel getAlguel() {
        return alguel;
    }

    public void setAlguel(Aluguel alguel) {
        this.alguel = alguel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
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
        final Pagamento other = (Pagamento) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
