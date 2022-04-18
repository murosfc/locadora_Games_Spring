package com.ongames.controller;

import com.ongames.model.Funcionario;
import com.ongames.services.FuncionarioService;
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
@RequestMapping(path = "apirest/funcionario")
public class FuncionarioController {
    @Autowired
    public FuncionarioService service;
    
    @GetMapping("/{id}")
    public ResponseEntity findByID(@PathVariable("id") long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping()
    public ResponseEntity save (@Valid  @RequestBody Funcionario func){        
        service.save(func);
        return ResponseEntity.ok(func);
   }
      
    @PutMapping()
    public ResponseEntity update(@PathVariable("id") long id, @Valid @RequestBody Funcionario func){
        func.setId(id);
        service.save(func);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable("id") long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}