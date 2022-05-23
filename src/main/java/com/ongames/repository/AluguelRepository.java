package com.ongames.repository;

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
    
    @Query(value = "SELECT * FROM aluguel a JOIN aluguel_contas ac ON a.id = ac.aluguel_id WHERE ac.contas_id = :idConta", nativeQuery = true)
    public List<Aluguel> findByConta(Long idConta);
    
    @Query("SELECT a FROM Aluguel a WHERE a.dataFimAluguel >= CURRENT_DATE AND a.dataInicioAluguel <= CURRENT_DATE")
    public List<Aluguel> findOngoing();       
   
}