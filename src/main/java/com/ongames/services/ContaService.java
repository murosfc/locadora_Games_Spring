package com.ongames.services;

import com.ongames.model.Conta;
import com.ongames.model.repository.ContaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    public ContaRepository repo;
    
    public Conta findByid (Long id){
        Conta c = repo.getById(id);
        if (c == null){
            throw new RuntimeException("Conta não encontrada");
        }
        return c;
    }
    
    public List<Conta> findByJogo (String titulo){
        List<Conta> contas = repo.findByTituloJogo(titulo);
        if (contas.isEmpty()){
            throw new RuntimeException("Conta não encontrada para o jogo informado");
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
        Optional<Conta> resultado = repo.findById(c.getId());
        if (resultado.isEmpty()){
            throw new RuntimeException("Conta não cadastrada, não é possível atualizar");
        }
        checkIfThereIsAluguel(c);
        c.setAluguel(null); //quando uma conta é atualizada o aluguel termina possibilitando que ela seja alugada novamente
        try{
            return repo.save(c);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao atualizar conta");
        }
    }
    
    public void delete(Conta c){
        checkIfExists(c);
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
            throw new RuntimeException("Já existe uma conta com este e-mail");
        }
    }
    
    private void checkIfThereIsAluguel(Conta c){
        if (c.getAluguel() != null){
            throw new RuntimeException("Esta conta possui um aluguel vigente, não é possível excluir ou atualizar");
        }
    }
}
