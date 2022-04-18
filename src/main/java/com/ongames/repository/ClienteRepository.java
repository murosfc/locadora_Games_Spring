package com.ongames.repository;

import com.ongames.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf OR c.email = :email")
    public List<Cliente> findByCpfOrEmail(String cpf, String email);
        
    
}
