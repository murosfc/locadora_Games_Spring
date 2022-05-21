package com.ongames.repository;

import com.ongames.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{    
    
    @Query ("SELECT c FROM Categoria c WHERE c.tipo = :tipo")
    public List<Categoria> findByTipo (String tipo);
    
    @Query (value = "SELECT DISTINCT * FROM categoria c JOIN jogo_categorias jc ON c.id = jc.categorias_id WHERE c.id = :idCategoria", nativeQuery = true)
    public List<Categoria> checkIfInJogo (Long idCategoria);
    
    @Query("SELECT c FROM Categoria c WHERE c.tipo LIKE %:nome%")
    public List<Categoria> findByName(String nome);
}