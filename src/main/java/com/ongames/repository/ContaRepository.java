package com.ongames.repository;

import com.ongames.model.Aluguel;
import com.ongames.model.Conta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{
    
    public Conta findByEmail(String email);
    
    @Query("SELECT c FROM Conta c WHERE c.jogo.titulo LIKE %:titulo%")
    public List<Conta> findByTituloJogo (String titulo);  
    
    @Query("SELECT a FROM Aluguel a JOIN a.contas ac WHERE ac.id = :idConta AND a.dataFimAluguel >= CURRENT_DATE")
    public Aluguel isInOngoingAluguel (Long idConta);    
}
