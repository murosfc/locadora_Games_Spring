package com.ongames.services;

import com.ongames.annotation.PasswordValidator;
import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Cliente;
import com.ongames.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    
    public List<Cliente> findByName(String nome){
        return repo.findByName(nome);
    }
    
    public List<Cliente> findAll(){
        List<Cliente> clientes = repo.findAll();
        if (clientes.isEmpty()){
            throw new NotFoundException("Não encontrado cliente cadastrado");
        }
        return clientes;
    }
    
    public List<Cliente> findByAluguelById(Long idAluguel){
        return repo.findByAluguelById(idAluguel);
    }
    
    public Cliente findById(Long id){
        Optional<Cliente> clientes = repo.findById(id);
        if (clientes.isEmpty()){
            throw new NotFoundException("Cliente não encontrado");
        }
        return clientes.get();
    }
    
    public Cliente save(Cliente c){
        checkDuplicity(c.getCpf(), c.getEmail());
        try{
            return repo.save(c);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar cliente");
        }        
    }
    
    public Cliente update(Cliente c, String senhaAtual, String novaSenha, String confirmaNovaSenha){
        checkIfExists(c);       
        c.setCpf(repo.getById(c.getId()).getCpf());
        Cliente clienteDB = repo.getById(c.getId());
        if (senhaAtual.equals("") || novaSenha.equals("") || confirmaNovaSenha.equals("")){        
            c.setSenha(clienteDB.getSenha());
        }
        else {
            atualizaSenha(clienteDB, senhaAtual, novaSenha, confirmaNovaSenha);
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
            throw new RuntimeException("Erro ao atualizar cliente");
        }
    } 
    
    public void delete(Cliente c){
        hasAluguel(c);
        try{
            repo.delete(c);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao excluir cliente");                  
        }        
    }
    
   private void checkDuplicity(String cpf, String email){
       List<Cliente> clientes = repo.findByCpfOrEmail(cpf, email);
       if (!clientes.isEmpty()){
           throw new NotAllowedException("Já existe um cadastro com o e-mail ou cpf informado");
       }       
   }
   
   private void hasAluguel(Cliente c){
       if (!c.getAlugueis().isEmpty()){
           throw new NotAllowedException("O cliente não pode ser apagado, pois possui aluguel registrado");
       }
   }
   
   private void checkIfExists (Cliente c){
        Optional<Cliente> resultado = repo.findById(c.getId());
        if (resultado.isEmpty()){
            throw new NotFoundException("Cliente não encontrado");
        }
   }
   
    private void atualizaSenha(Cliente c, String senhaAtual, String novaSenha, String confirmaNovaSenha){     
            if (!new BCryptPasswordEncoder().matches(senhaAtual, c.getSenha())){
                throw new NotAllowedException ("A senha atual não confere");
            }
            if (senhaAtual.equals(novaSenha)){
                throw new NotAllowedException("A nova senha não pode ser igual à senha atual");
            }
            if (!novaSenha.equals(confirmaNovaSenha)){
                throw new NotAllowedException("Confirmação de senha não está igual à nova senha informada");
            }
            if (!PasswordValidator.isValid(novaSenha)){
                throw new NotAllowedException("O password precisa conter: "
                 + "mínimo 6 e máximo 20 caracteres, "
                 + "pelo menos uma letra maiúscula, "
                 + "uma letra minúscula "
                 + "e um caractere especial");
            }            
            c.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        
    }
    
}
