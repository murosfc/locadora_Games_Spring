
package com.ongames.controller;

import com.ongames.model.Pagamento;
import com.ongames.services.PagamentoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pagamentos")
public class PagamentoController {
    @Autowired
    public PagamentoService service;

   @GetMapping
   public ResponseEntity getAll(){
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
   }
   
   @GetMapping(path = "/pageable")
   public ResponseEntity getAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
           @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
       return ResponseEntity.status(HttpStatus.OK).body(service.findAll(page, size));
   }
   
   @PostMapping
   public ResponseEntity save(@Valid @RequestBody Pagamento pag){       
       service.save(pag);
       return ResponseEntity.ok(pag);
   }
   
   @PutMapping(path = "/{id}")
   public ResponseEntity update (@PathVariable("id") long id, @Valid @RequestBody Pagamento pag){
       pag.setId(id);
       service.update(pag);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();       
   }
 
}
