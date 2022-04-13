package com.ongames.services;

import com.ongames.model.Pagamento;
import com.ongames.model.repository.PagamentoRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    private PagamentoRepository repo;
    
    public List<Pagamento> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Pagamento> findAll(){
        return repo.findAll();        
    }
    
    public Pagamento save(Pagamento p){
        checkIfExist(p);
        try{
            repo.save(p);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao registrar pagamento");
        }
        return p;
    }
    
    public Pagamento update(Pagamento p){
        Pagamento pDB = repo.getById(p.getId());
        if (!pDB.getValidacao().isEmpty()){
            throw new RuntimeException("Não é possível atualizar um pagamento já confirmado");
        } 
        pDB.setValidacao(p.getValidacao());
        try{
            repo.save(pDB);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao atualizar pagamento");
        }
        return pDB;
    }

    private void checkIfExist(Pagamento p) {
        Pagamento pag = repo.getById(p.getId());
        if (pag != null){
            throw new RuntimeException("Funcionário já cadastrado");
        }
    }
    
    
}
