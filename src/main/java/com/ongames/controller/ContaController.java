package com.ongames.controller;

import com.ongames.model.Conta;
import com.ongames.services.ContaService;
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
@RequestMapping(path = "apirest/conta")
public class ContaController {
    @Autowired
    public ContaService service;

   @GetMapping
   public ResponseEntity getAll(){
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
   }
   
   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Conta conta){       
       service.save(conta);
       return ResponseEntity.ok(conta);
   }
   
   @PutMapping("/{id}")
   public ResponseEntity update (@PathVariable("id") Long id, @Valid @RequestBody Conta conta){
       conta.setId(id);
       service.update(conta);
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
}
