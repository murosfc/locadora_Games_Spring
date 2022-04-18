package com.ongames.services;

import com.ongames.model.Cliente;
import com.ongames.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private ClienteRepository repo;
    
    public Cliente findById(Long id){
        Cliente c = repo.getById(id);
        if (c == null){
            throw new RuntimeException("Cliente não encontrado");
        }
        return c;
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
    
    public Cliente update(Cliente c){
        Optional<Cliente> resultado = repo.findById(c.getId());
        if (resultado.isEmpty()){
            throw new RuntimeException ("Cliente não encontrado");
        }
        c.setCpf(resultado.get().getCpf());
        try{
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
    
}
