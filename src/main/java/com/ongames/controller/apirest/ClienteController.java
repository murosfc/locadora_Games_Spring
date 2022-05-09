package com.ongames.controller.apirest;

import com.ongames.model.Cliente;
import com.ongames.services.ClienteService;
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
@RequestMapping(path = "apirest/clientes")
public class ClienteController {
    @Autowired
    public ClienteService service;
    
    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity findByID(@PathVariable("id") long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping()
    public ResponseEntity save (@Valid  @RequestBody Cliente cliente){        
        service.save(cliente);
        return ResponseEntity.ok(cliente);
   }
      
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        service.update(cliente, "","","");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @PutMapping(path = "/{id}/newPass")
    public ResponseEntity updatePassord(@PathVariable("id") long id, @RequestBody Cliente cliente,    
           @RequestParam(name = "senhaAtual", defaultValue= "", required = true) String senhaAtual,
           @RequestParam(name = "novaSenha", defaultValue= "", required = true) String novaSenha, 
           @RequestParam(name = "confirmaNovaSenha", defaultValue= "", required = true) String confirmaNovaSenha){
        cliente.setId(id);
        service.update(cliente, senhaAtual,novaSenha,confirmaNovaSenha);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @DeleteMapping(path = "/{id}/excluir")
    public ResponseEntity delete (@PathVariable("id") long id){
       service.delete(service.findById(id));
       return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}