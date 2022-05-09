package com.ongames.services;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Funcionario;
import com.ongames.repository.FuncionarioRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repo;
    
     public Funcionario findById(Long id){
        Optional<Funcionario> funcionarios = repo.findById(id);
        if (funcionarios.isEmpty()){
            throw new NotFoundException("Funcionário não encontrado");
        }
        return funcionarios.get();
    }
     
     public List<Funcionario> findAll(){
         return repo.findAll();
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
    
    public Funcionario update(Funcionario f, String senhaAtual, String novaSenha, String confirmaNovaSenha){
        checkIfExists(f);        
        f.setCpf(repo.getById(f.getId()).getCpf());
        Funcionario funcDB = repo.getById(f.getId());
        atualizaSenha(funcDB, senhaAtual, novaSenha, confirmaNovaSenha);
        try{
            f.setSenha(funcDB.getSenha());
            return repo.save(f);         
        }
        catch (Exception e){
            Throwable t = e;
            while (t.getCause() != null){
                t = t.getCause();
                if (t instanceof ConstraintViolationException){
                    throw (ConstraintViolationException) t;
                }
            }
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
           throw new NotAllowedException("Já existe um cadastro com o e-mail ou cpf informado");
       }       
   }
   
   private void hasAluguel(Funcionario f){
       if (!f.getAlugueis().isEmpty()){
           throw new NotAllowedException("O funcionario não pode ser apagado, pois possui aluguel registrado para suporte");
       }
   }
   
   private void checkIfExists (Funcionario f){
       Optional<Funcionario> resultado = repo.findById(f.getId());
        if (resultado.isEmpty()){
            throw new NotFoundException("Funcionario não encontrado");
        }
   }
   
   private void atualizaSenha(Funcionario f, String senhaAtual, String novaSenha, String confirmaNovaSenha){
        if(!senhaAtual.isBlank() && !novaSenha.isBlank() && confirmaNovaSenha.isBlank()){
            if (!senhaAtual.equals(f.getSenha())){
                throw new NotAllowedException("A senha atual não confere");
            }
            if (senhaAtual.equals(novaSenha)){
                throw new NotAllowedException("A nova senha não pode ser igual à senha atual");
            }
            if (!novaSenha.equals(confirmaNovaSenha)){
                throw new NotAllowedException("Confirmação de senha não está igual à nova senha informada");
            }            
            f.setSenha(novaSenha);
        } 
   }
    
}

