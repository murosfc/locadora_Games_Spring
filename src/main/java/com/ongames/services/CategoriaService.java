package com.ongames.services;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.model.Categoria;
import com.ongames.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    public CategoriaRepository repo;
    
    public Categoria save(Categoria cat){
        verificaCategoriaSeCadastrada(cat);
        try{
            return repo.save(cat);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar/atualizar categoria");
        }        
    }
    
     public Categoria update(Categoria cat){
        Categoria cDB = repo.getById(cat.getId());
        if (cDB == null){
            throw new NotAllowedException("Não é possível atualizar uma categoria não cadastrada");
        }
        try{
            return repo.save(cat);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar/atualizar categoria");
        }        
    }
    
    public void delete(Categoria cat){
        checkIfInJogo(cat);
        try{
            repo.delete(cat);
        }
        catch (Exception e){
            throw new RuntimeException ("Erro ao excluir categoria");
        }
    }  
    
    public List<Categoria> findAll(){
        List categorias = repo.findAll();
        if (categorias.isEmpty()){
            throw new NotFoundException("Nehuma categoria foi encontrada");
        }
        return categorias;        
    }
    
    public Categoria findById(Long id){
        Optional<Categoria> cats = repo.findById(id);
        if (cats.isEmpty()){
            throw new NotFoundException("Categoria não encontrada com o id informado");
        }
        return cats.get();        
    }
    
    private void verificaCategoriaSeCadastrada(Categoria cat){
        List<Categoria> resultado = repo.findByTipo(cat.getTipo());
        if (!resultado.isEmpty()){
            throw new NotAllowedException("Categoria já cadastrada");
        }
    }
    
    private void checkIfInJogo(Categoria cat){
        List<Categoria> resultado = repo.checkIfInJogo();
        if (!resultado.isEmpty()){
            throw new NotAllowedException("Não é possível excluir categoria vinculada a um jogo!");
        }        
    }
}


