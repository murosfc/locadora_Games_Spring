package com.ongames.services;

import com.ongames.model.Funcionario;
import com.ongames.model.repository.FuncionarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    private FuncionarioRepository repo;
    
     public Funcionario FindById(Long id){
        Funcionario c = repo.getById(id);
        if (c == null){
            throw new RuntimeException("Funcionário não encontrado");
        }
        return c;
    }
    
    public Funcionario save(Funcionario f){
        checkDuplicity(f.getCpf(), f.getEmail());
        try{
            return repo.save(f);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar funcionário");
        }        
    }
    
    public Funcionario update(Funcionario f){
        Optional<Funcionario> resultado = repo.findById(f.getId());
        if (resultado.isEmpty()){
            throw new RuntimeException ("Funcionario não encontrado");
        }
        f.setCpf(resultado.get().getCpf());
        try{
            return repo.save(f);         
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao atualizar funcionario");
        }
    }  
    
    public void delete(Funcionario f){
        hasAluguel(f);
        try{
            repo.delete(f);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao excluir funcionario");                  
        }        
    }
    
   private void checkDuplicity(String cpf, String email){
       List<Funcionario> func = repo.findByCpfOrEmail(cpf, email);
       if (!func.isEmpty()){
           throw new RuntimeException ("Já existe um cadastro com o e-mail ou cpf informado");
       }       
   }
   
   private void hasAluguel(Funcionario f){
       if (!f.getAlugueis().isEmpty()){
           throw new RuntimeException("O funcionario não pode ser apagado, pois possui aluguel registrado para suporte");
       }
   }
    
}

