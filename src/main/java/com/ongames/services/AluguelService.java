package com.ongames.services;

import com.ongames.model.Aluguel;
import com.ongames.model.repository.AluguelRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AluguelService {
    
    @Autowired
    private AluguelRepository repo;
    
    public List<Aluguel> FindByCliente(Long idCliente){        
        List<Aluguel> result = repo.findByCliente(idCliente);
        if (result.isEmpty()){
            throw new RuntimeException("Não encontrado aluguel para o cliente informado");
        }
        return result;
    }
    
    public List<Aluguel> findOngoing (LocalDate hoje)
    {
        hoje = LocalDate.now();
        List<Aluguel> result = repo.findOngoing(hoje);
        if (result.isEmpty()){
            throw new RuntimeException("Não encontrado aluguel para o cliente informado");
        }
        return result;
    }
    
    public Aluguel save (Aluguel obj){
        try{
           return repo.save(obj);
        }
        catch (Exception e){
            throw new RuntimeException("Falha ao cadastrar o aluguel");
        }        
    }
    
    public boolean checkIfPaid (Aluguel a){
        Aluguel aDB = repo.getById(a.getId());
        if (aDB == null){
            throw new RuntimeException("Aluguel não encontrado");                   
        }
        return ! (aDB.getPagamento().getValidacao().isEmpty());          
    }    
    
    
}
