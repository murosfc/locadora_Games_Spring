package com.ongames.controller;

import com.ongames.model.Jogo;
import com.ongames.services.JogoService;
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
@RequestMapping(path = "apirest/jogo")
public class JogoController {
    @Autowired
    public JogoService service;

   @GetMapping
   public ResponseEntity getAll(){
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
   }
   
   @GetMapping
   public ResponseEntity getAll(@PathVariable("page") int page,@PathVariable("size") int size) {
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll(page, size));
   }
   
   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Jogo jogo){       
       service.save(jogo);
       return ResponseEntity.ok(jogo);
   }
   
   @PutMapping("/{id}")
   public ResponseEntity update (@PathVariable("id") long id, @Valid @RequestBody Jogo jogo){
       jogo.setId(id);
       service.update(jogo);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();       
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity delete (@PathVariable("id") long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
   }
   
   @GetMapping("/{id}")
   public ResponseEntity findByid(@PathVariable("id") long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
   }
   
   @GetMapping("/{titulo}")
   public ResponseEntity findByTitulo (@PathVariable ("titulo") String titulo){
       return ResponseEntity.status(HttpStatus.OK).body(service.findByTitulo(titulo));
   }
}
