package com.ongames.services;

import com.ongames.exception.NotFoundException;
import com.ongames.model.Pagamento;
import com.ongames.repository.PagamentoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repo;
    
    public List<Pagamento> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        List<Pagamento> pagamentos = repo.findAll(p).toList();
        if (pagamentos.isEmpty()){
            throw new NotFoundException ("Não foram encontrados pagamentos");
        }
        return pagamentos;
    }
    
    public List<Pagamento> findAll(){        
        return repo.findAll();        
    }
    
    public Pagamento save(Pagamento p){
        p.setId(null);  
        try{ 
            repo.save(p); 
            p.iniciaAluguel(); 
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao registrar pagamento: "+ e.getMessage());
        }
        return p;
    }
    
    public Pagamento update(Pagamento p){ 
        try{
            repo.save(p); 
            p.iniciaAluguel();                    
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao atualizar pagamento: "+ e.getMessage());
        }
        return p;
    } 
    
}
