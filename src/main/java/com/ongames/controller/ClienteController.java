package com.ongames.controller;

import com.ongames.model.Cliente;
import com.ongames.services.ClienteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "apirest/cliente")
public class ClienteController {
    @Autowired
    public ClienteService service;
    
    @GetMapping("/{id}")
    public ResponseEntity findByID(@PathVariable("id") long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping()
    public ResponseEntity save (@Valid  @RequestBody Cliente cliente){        
        service.save(cliente);
        return ResponseEntity.ok(cliente);
   }
      
    @PutMapping()
    public ResponseEntity update(@PathVariable("id") long id, @Valid @RequestBody Cliente cliente){
        cliente.setId(id);
        service.save(cliente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable("id") long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}