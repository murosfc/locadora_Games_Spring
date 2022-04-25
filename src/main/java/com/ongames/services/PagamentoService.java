package com.ongames.services;

import com.ongames.exception.NotAllowedException;
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
        List<Pagamento> pagamentos = repo.findAll();
        if (pagamentos.isEmpty()){
            throw new NotFoundException ("Não foram encontrados pagamentos");
        }
        return pagamentos;        
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
            throw new NotAllowedException("Não é possível atualizar um pagamento já confirmado");
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
            throw new NotAllowedException("Pagamento já cadastrado com a id informada");
        }
    }
    
    
}
