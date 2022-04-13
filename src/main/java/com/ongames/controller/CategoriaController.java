package com.ongames.controller;

import com.ongames.model.Categoria;
import com.ongames.services.CategoriaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "apirest//categoria")
public class CategoriaController {
    @Autowired
    public CategoriaService service;

   @GetMapping
   public ResponseEntity getAll(){
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
   }
   
   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Categoria categoria){
       categoria.setId(null);
       service.save(categoria);
       return ResponseEntity.ok(categoria);
   }
   
   @PutMapping("/{id}")
   public ResponseEntity update (@PathVariable("id") Long id, @Valid @RequestBody Categoria categoria){
       categoria.setId(id);
       service.update(categoria);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();       
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity delete (@PathVariable("id") Long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
   }
   
   @GetMapping
   public ResponseEntity findByid(@PathVariable("id") Long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
   }
}
