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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/jogos")
public class JogoController {
    
    @Autowired
    public JogoService service;

   @GetMapping
   public ResponseEntity getAll(){
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
   }
   
   @GetMapping(path = "/pageable")
   public ResponseEntity getAll(@RequestParam(name="page", defaultValue = "1", required = false) int page,
   @RequestParam(name="page", defaultValue = "10", required = false) int size ) {
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll(page, size));
   }
   
   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Jogo jogo){       
       service.save(jogo);
       return ResponseEntity.ok(jogo);
   }
   
   @PutMapping(path = "/{id}")
   public ResponseEntity update (@PathVariable("id") long id, @Valid @RequestBody Jogo jogo){
       jogo.setId(id);
       service.update(jogo);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();       
   }
   
   @DeleteMapping(path = "/{id}")
   public ResponseEntity delete (@PathVariable("id") long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
   }
   
   @GetMapping(path = "/{id}")
   public ResponseEntity findByid(@PathVariable("id") long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
   }
   
   @GetMapping(path = "/porTitulo")
   public ResponseEntity findByTitulo (@RequestParam(name ="titulo", defaultValue ="", required = true) String titulo){
       return ResponseEntity.status(HttpStatus.OK).body(service.findByTitulo(titulo));
   }
   
   @GetMapping(path = "/alugueis/{id}")
   public ResponseEntity countJogoInAluguel (@PathVariable("id") long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.countJogoInAluguel(id));
   }
   
}
