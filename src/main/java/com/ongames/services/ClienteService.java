package com.ongames.services;

import com.ongames.model.Cliente;
import com.ongames.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    
    public Cliente findById(Long id){
        Optional<Cliente> clientes = repo.findById(id);
        if (clientes.isEmpty()){
            throw new RuntimeException("Cliente não encontrado");
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
        atualizaSenha(clienteDB, senhaAtual, novaSenha, confirmaNovaSenha);
        try{
            c.setSenha(clienteDB.getSenha());
            return repo.save(c);         
        }
        catch (Exception e){
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
           throw new RuntimeException ("Já existe um cadastro com o e-mail ou cpf informado");
       }       
   }
   
   private void hasAluguel(Cliente c){
       if (!c.getAlugueis().isEmpty()){
           throw new RuntimeException("O cliente não pode ser apagado, pois possui aluguel registrado");
       }
   }
   
   private void checkIfExists (Cliente c){
        Optional<Cliente> resultado = repo.findById(c.getId());
        if (resultado.isEmpty()){
            throw new RuntimeException ("Cliente não encontrado");
        }
   }
   
    private void atualizaSenha(Cliente c, String senhaAtual, String novaSenha, String confirmaNovaSenha){
        if(!senhaAtual.isBlank() && !novaSenha.isBlank() && confirmaNovaSenha.isBlank()){
            if (!senhaAtual.equals(c.getSenha())){
                throw new RuntimeException ("A senha atual não confere");
            }
            if (senhaAtual.equals(novaSenha)){
                throw new RuntimeException("A nova senha não pode ser igual à senha atual");
            }
            if (!novaSenha.equals(confirmaNovaSenha)){
                throw new RuntimeException("Confirmação de senha não está igual à nova senha informada");
            }            
            c.setSenha(novaSenha);
        }  
    }
    
}
