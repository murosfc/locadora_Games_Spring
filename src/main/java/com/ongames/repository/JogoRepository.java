package com.ongames.repository;

import com.ongames.model.Jogo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{
    
    @Query("SELECT j FROM Jogo j WHERE j.titulo = :titulo AND j.plataforma = :plataforma")
    public List<Jogo> findByTituloAndPlataforma(String titulo, String plataforma);
    
    @Query("SELECT j FROM Jogo j WHERE j.titulo LIKE %:titulo%")
    public List<Jogo> findByTitulo(String titulo);
    
    @Query("SELECT j FROM Jogo j WHERE j.sku = :sku")
    public Jogo findBySku(String sku); 
    
    @Query(value = "SELECT * FROM Jogo j JOIN Conta c ON c.id_jogo = j.id WHERE c.id_aluguel IS NOT NULL AND j.id = :idJogo", nativeQuery = true)
    public Jogo findJogoInAluguel (long idJogo);
    
    @Query("Select count(a) FROM Conta c JOIN c.aluguel a WHERE c.jogo.id = :idJogo")
    public int countJogoInAluguel (Long idJogo);
    
    @Query(value = "SELECT * FROM Jogo j JOIN Conta c ON j.id=c.id_jogo WHERE c.id_jogo = :idJogo", nativeQuery = true)
    public Jogo findJogoInConta(Long idJogo);       
}
