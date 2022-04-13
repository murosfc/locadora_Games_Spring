package com.ongames.model.repository;

import com.ongames.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{    
    
    @Query ("SELECT c FROM Categoria c WHERE c.tipo = :tipo")
    public List<Categoria> findByTipo (String tipo);
}