package com.ongames.repository;

import com.ongames.model.Conta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{
    
    public Conta findByEmail(String email);
    
    @Query(value = "SELECT * FROM Conta c JOIN Jogo j WHERE j.titulo LIKE %:titulo%", nativeQuery = true)
    public List<Conta> findByTituloJogo (String titulo);
    
}
