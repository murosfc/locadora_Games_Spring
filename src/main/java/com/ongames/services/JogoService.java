package com.ongames.services;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Jogo;
import com.ongames.repository.JogoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JogoService {
    @Autowired
    private JogoRepository repo;
    
    public List<Jogo> findAll(){
        return repo.findAll();
    }
    
    public Jogo findById(long id){
        Optional<Jogo> jogos = repo.findById(id);
        if (jogos.isEmpty()){
            throw new NotFoundException("Jogo não encontrado");
        }
        return jogos.get();
    }
    
     public List<Jogo> findAll(int page, int size){
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
        checkIfJogoInConta(j);
        try{
            repo.delete(j);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao excluir jogo");
        }
    }
    
    private void checkDuplicity (Jogo j){
        Jogo jDB = repo.findBySku(j.getSku());
        if (jDB != null){
            throw new NotAllowedException("SKU já cadastrado");
        }       
    }

    private void checkIfInAluguel(Jogo j) {
        Jogo jogo = repo.findJogoInAluguel(j.getId());
        if (jogo != null){
            throw new NotAllowedException("Não é possível excluir um jogo alugado");
        }
    }
    
    private void checkIfJogoInConta(Jogo j) {
        Jogo jogo = repo.findJogoInConta(j.getId());
        if (jogo != null){
            throw new NotAllowedException("Não é possível excluir um jogo vinculado a uma conta. Exclua a conta ou remova o jogo dela, depois tente novamente.");
        }
    }
    
    public int countJogoInAluguel (Long idJogo) {
    	return repo.countJogoInAluguel(idJogo);
    }
    
    
}
