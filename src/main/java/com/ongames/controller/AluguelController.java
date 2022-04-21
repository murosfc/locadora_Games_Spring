package com.ongames.controller;

import com.ongames.model.Aluguel;
import com.ongames.services.AluguelService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/aluguel")
public class AluguelController {
    @Autowired
    public AluguelService service;

   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Aluguel aluguel){       
       service.save(aluguel);
       return ResponseEntity.ok(aluguel);
   }   
   
   @GetMapping(path = "/cliente/{id}")
   public ResponseEntity findByCliente(@PathVariable("id") long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.findByCliente(id));
   }
   
   @GetMapping(path = "/funcionario/{id}")
   public ResponseEntity findByFuncionario(@PathVariable("id") long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.findByFuncionario(id));
   }
   
   @GetMapping(path = "/ongoing")
   public ResponseEntity findOngoing(){
       return ResponseEntity.ok(service.findOngoing());
   }
   
   @GetMapping(path = "/isPaid")
   public ResponseEntity checkIfPaid(@RequestBody Aluguel aluguel){
       return ResponseEntity.status(HttpStatus.OK).body(service.checkIfPaid(aluguel));
   }
}
