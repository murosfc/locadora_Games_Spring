package com.ongames.model.repository;

import com.ongames.model.Aluguel;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long>{    
    
    @Query("SELECT a FROM Aluguel a WHERE id_cliente = :idCliente")
    public List<Aluguel> findByCliente(Long idCliente);
    
    @Query("SELECT a FROM Aluguel a WHERE id_funcionario = :idFuncionario")
    public List<Aluguel> findByFuncionario(Long idFuncionario);
    
    @Query("SELECT a FROM Aluguel a WHERE a.dataFimAluguel >= :hoje")
    public List<Aluguel> findOngoing(LocalDate hoje);   
}