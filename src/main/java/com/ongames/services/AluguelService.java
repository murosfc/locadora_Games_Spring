package com.ongames.services;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Aluguel;
import com.ongames.repository.AluguelRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AluguelService {
    
    @Autowired
    private AluguelRepository repo;
    
    public Aluguel findById(Long id){
        Optional<Aluguel> alu = repo.findById(id);
        if(alu.isEmpty()){
            throw new NotFoundException("Não encontrado cliente com o id informado");
        }
        return alu.get();
    }
    
    public List<Aluguel> findByCliente(Long idCliente){        
        List<Aluguel> result = repo.findByCliente(idCliente);
        if (result.isEmpty()){
            throw new NotFoundException("Não encontrado aluguel para o cliente informado");
        }
        return result;
    }
    
    public List<Aluguel> findAll(){
        List<Aluguel> result = repo.findAll();
         if (result.isEmpty()){
            throw new NotFoundException("Nenhum aluguel registrado");
        }
        return result;
    }
    
     public List<Aluguel> findByFuncionario(long idFuncionario){        
        List<Aluguel> result = repo.findByCliente(idFuncionario);
        if (result.isEmpty()){
            throw new NotFoundException("Não encontrado aluguel para o cliente informado");
        }
        return result;
    }
    
    public List<Aluguel> findOngoing ()
    {
        LocalDate hoje = LocalDate.now();
        List<Aluguel> result = repo.findOngoing(hoje);
        if (result.isEmpty()){
            throw new NotFoundException("Não encontrado aluguel em andamento");
        }
        return result;
    }
    
    public Aluguel save (Aluguel obj){
        if (obj.getDataInicioAluguel() == null || obj.getDataFimAluguel() == null){
            throw new NotAllowedException("As datas de início e fim não podem estar vazias");
        }
    	if (obj.getDataInicioAluguel().plusDays(7).isAfter(obj.getDataFimAluguel())) {
            throw new NotAllowedException("O período de aluguel está incorreto, não pode ser menor que 7 dias");
    	}
        try{
           return repo.save(obj);
        }
        catch (Exception e){
            throw new RuntimeException("Falha ao cadastrar o aluguel");
        }        
    }
    
    public Aluguel update (Aluguel obj){
        if (obj.getDataInicioAluguel() == null || obj.getDataFimAluguel() == null){
            throw new NotAllowedException("As datas de início e fim não podem estar vazias");
        }
    	if (obj.getDataInicioAluguel().plusDays(7).isAfter(obj.getDataFimAluguel())) {
            throw new NotAllowedException("O período de aluguel está incorreto, não pode ser menor que 7 dias");
    	}
        try{
           return repo.save(obj);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Falha ao atualzar o aluguel");
        }        
    }
    
    public boolean checkIfPaid (Aluguel a){
        Aluguel aDB = repo.getById(a.getId());
        if (aDB == null){
            throw new NotFoundException("Aluguel não encontrado");                   
        }
        return ! (aDB.getPagamento().getValidacao().isEmpty());          
    }    
    
    
}
