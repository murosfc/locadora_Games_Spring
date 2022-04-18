package com.ongames.repository;

import com.ongames.model.Funcionario;
import com.ongames.model.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    
   @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf OR f.email = :email") 
   public List<Funcionario> findByCpfOrEmail(String cpf, String email);
    
}
