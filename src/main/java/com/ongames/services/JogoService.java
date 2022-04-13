package com.ongames.services;

import com.ongames.model.Jogo;
import com.ongames.model.repository.JogoRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JogoService {
    private JogoRepository repo;
    
    public List<Jogo> fildAll(){
        return repo.findAll();
    }
    
     public List<Jogo> fildAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Jogo> findByTitulo(String name){
        return repo.findByTitulo(name);
    }
    
    public Jogo save(Jogo j){
        checkDuplicity(j);
        try{
            repo.save(j);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar jogo");
        }
        return j;
    }
    
    public Jogo update(Jogo j){
        try{
            repo.save(j);
        }
        catch (Exception e){
            throw new RuntimeException ("Erro ao atualizar jogo");
        }
        return j;
    }
    
    public void delete (Jogo j){
        checkIfInAluguel(j);
        try{
            repo.delete(j);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao excluir jogo");
        }
    }
    
    private void checkDuplicity (Jogo j){
        Jogo jDB = repo.findBySku(j.getSku());
        if (jDB == null){
            throw new RuntimeException("SKU já cadastrado");
        }       
    }

    private void checkIfInAluguel(Jogo j) {
        Jogo jogo = repo.findJogoInAluguel(j.getId());
        if (jogo != null){
            throw new RuntimeException("Não é possível excluir um jogo alugado");
        }
    }
    
    
}
