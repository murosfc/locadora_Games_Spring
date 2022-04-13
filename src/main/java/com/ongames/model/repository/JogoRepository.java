package com.ongames.model.repository;

import com.ongames.model.Jogo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{
    
    @Query("SELECT j FROM Jogo j WHERE j.titulo = :titulo AND j.plataforma = :plataforma")
    public List<Jogo> FindByTituloAndPlataforma(String titulo, String plataforma);
    
    
    @Query("SELECT j FROM Jogo j WHERE j.titulo LIKE %:titulo%")
    public List<Jogo> findByTitulo(String titulo);
    
    @Query("SELECT j FROM Jogo j WHERE j.sku = :sku")
    public Jogo findBySku(String sku); 
    
    @Query("Select j FROM Jogo j JOIN Conta c WHERE id_aluguel IS NOT NULL AND j.id = :idJogo")
    public Jogo findJogoInAluguel (Long idJogo);
    
}
