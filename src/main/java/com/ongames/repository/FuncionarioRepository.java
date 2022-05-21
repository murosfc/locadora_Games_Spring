package com.ongames.repository;

import com.ongames.model.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    
   @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf OR f.email = :email") 
   public Funcionario findByCpfOrEmail(String cpf, String email);
   
   @Query("SELECT f FROM Funcionario f JOIN f.alugueis a WHERE a.id = :idAluguel")
   public List<Funcionario> findByAluguelById(Long idAluguel);

   @Query("SELECT f FROM Funcionario f WHERE f.email = :email")
   public Funcionario findByEmail(String email);
   
    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE %:nome%")
   public List<Funcionario> findByName(String nome);
    
}
