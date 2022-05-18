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
    
    public List<Aluguel> findByConta(Long idConta){        
        List<Aluguel> result = repo.findByConta(idConta);
        if (result.isEmpty()){
            throw new NotFoundException("Não encontrado aluguel para a conta informada");
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
        List<Aluguel> result = repo.findByFuncionario(idFuncionario);
        if (result.isEmpty()){
            throw new NotFoundException("Não encontrado aluguel para o funcionario informado");
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
        this.validaDatasAluguel(obj);
        try{
           return repo.save(obj);
        }
        catch (Exception e){
            throw new RuntimeException("Falha ao cadastrar o aluguel");
        }        
    }
    
    public Aluguel update (Aluguel obj){
        obj.getPagamento().setId(repo.getById(obj.getId()).getPagamento().getId());
        this.validaDatasAluguel(obj);
        try{
           return repo.save(obj);
        }
        catch (Exception e){            
            throw new RuntimeException("Falha ao atualizar o aluguel");
        }        
    }  
    
    public boolean checkIfPaid (Aluguel a){
        Aluguel aDB = repo.getById(a.getId());
        if (aDB == null){
            throw new NotFoundException("Aluguel não encontrado");                   
        }
        return ! (aDB.getPagamento().getValidacao().isEmpty());          
    }    
    
    private void validaDatasAluguel(Aluguel obj){        
        if (obj.getDataInicioAluguel() == null && obj.getPagamento().getDataPagamento() != null){
           obj.setDataInicioAluguel(obj.getPagamento().getDataPagamento()); //se o pagamento foi confirmado então o aluguel iniciou
           if(obj.getDataFimAluguel() == null){
                LocalDate dtfim = obj.getDataInicioAluguel();
                obj.setDataFimAluguel(dtfim.plusDays(7));
           }            
        }
        if (obj.getPagamento().getDataPagamento() == null && obj.getDataInicioAluguel() != null){
            throw new NotAllowedException("O aluguel não pode iniciar sem a confirmação do pagamento.");
        }    	
        if (obj.getPagamento().getDataPagamento().isAfter(obj.getDataInicioAluguel())){
            throw new NotAllowedException("O aluguel não pode iniciar antes da data do pagamento");
        }
        if (obj.getDataInicioAluguel().plusDays(7).isAfter(obj.getDataFimAluguel())) {
            throw new NotAllowedException("O período de aluguel está incorreto, não pode ser menor que 7 dias");
        }        
    }
    
    
}
