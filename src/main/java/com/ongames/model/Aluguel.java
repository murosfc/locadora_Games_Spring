package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluguel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Data de início e do fim do aluguel são obrigatórias.")
    @FutureOrPresent(message = "Data de inicio do aluguel deve ser atual ou no futuro.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
        pagamento.setValor(0f);
        this.pagamento = new Pagamento(this);
    }

    public Aluguel() {
        this.pagamento = new Pagamento(this);
        pagamento.setValor(0f);
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
        float total = pagamento.getValor();
        Conta tempConta = new Conta();
        for (Conta conta : contas) {            
            total = total + conta.getJogo().getValor();
        }
        pagamento.setValor(total);
    }
     
    public float getValor(){
        return this.pagamento.getValor();
    }
    

    public Funcionario getContatoSuporte() {
        return contatoSuporte;
    }

    public void setContatoSuporte(Funcionario contatoSuporte) {
        this.contatoSuporte = contatoSuporte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDataInicioAluguel() {
        return dataInicioAluguel;
    }

    public void setDataInicioAluguel(Calendar dataInicioAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
        Calendar datafim = this.dataInicioAluguel;
        datafim.add(Calendar.DATE, 7);
        this.dataFimAluguel = datafim;
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
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    } 
    
}
