package com.ongames.services;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Conta;
import com.ongames.repository.ContaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    @Autowired
    public ContaRepository repo;
    
    public Conta findById (Long id){
        Optional<Conta> contas = repo.findById(id);
        if (contas.isEmpty()){
            throw new NotFoundException("Conta não encontrada");
        }
        return contas.get();
    }
    
    public List<Conta> findAll(){
        List contas = repo.findAll();
        if (contas.isEmpty()){
            throw new NotFoundException("Nehuma conta foi encontrada");
        }
        return contas;       
    }
    
    public List<Conta> findByJogo (String titulo){
        List<Conta> contas = repo.findByTituloJogo(titulo);
        if (contas.isEmpty()){
            throw new NotFoundException("Conta não encontrada para o jogo informado");
        }
        return contas;
    }
    
    public Conta save(Conta c){
        checkIfExists (c);
        try{
            return repo.save(c);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar conta");
        }
    }
    
    public Conta update (Conta c){           
        Conta contaDB = repo.getById(c.getId());
        if(contaDB.getAluguel() != null){
           if (contaDB.getAluguel().getDataFimAluguel().isAfter(LocalDate.now()) ||
                contaDB.getAluguel().getDataFimAluguel().isEqual(LocalDate.now())){
            throw new NotAllowedException("Não é possível editar uma conta com aluguel em andamento");
        } 
        }                
        try{            
            return repo.save(c);
        }
        catch (Exception e){
            Throwable t = e;
            while (t.getCause() != null){
                t = t.getCause();
                if (t instanceof ConstraintViolationException){
                    throw (ConstraintViolationException) t;
                }
            }
            throw new RuntimeException("Erro ao atualizar conta");
        }
    }
    
    public void delete(Conta c){        
        checkIfThereIsAluguel(c);
        try{
            repo.delete(c);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao excluir conta");
        }
    }    
   
    private void checkIfExists (Conta c){
        Conta conta = repo.findByEmail(c.getEmail());
        if (conta != null){
            throw new NotAllowedException("Já existe uma conta com este e-mail");
        }
    }
    
    private void checkIfThereIsAluguel(Conta c){
        if (c.getAluguel() != null){
            throw new NotAllowedException("Esta conta possui um aluguel vigente, não é possível excluir a conta");
        }
    }
       
    
}
