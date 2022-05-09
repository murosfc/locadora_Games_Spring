package com.ongames.annotation.view;

import com.ongames.model.Cliente;
import com.ongames.services.ClienteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteViewController {
    
    @Autowired
    private ClienteService service;  
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id, Model model){       
        try{
            service.delete(service.findById(id));
            return "redirect:/clientes";
        }
        catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("cliente", e.getMessage()));
            this.findAll(model);
            return "clientes";
        }        
    }
    
    @GetMapping
    public String findAll(Model model){
        model.addAttribute("clientes", service.findAll());        
        return "clientes";
    }   
    
    @GetMapping(path = "/cliente")
    public String cadastro(Model model){
        model.addAttribute("cliente", new Cliente());        
        return "formCliente";
    }
    
    @PostMapping(path = "/cliente")
    public String save(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formCliente"; 
        }
        else{
            try{ 
                cliente.setId(null);
                service.save(cliente);
                model.addAttribute("cliente", new Cliente()); 
                model.addAttribute("msgSucesso", "Cliente cadastrado com sucesso!");  
                return "formCliente";
            }
            catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("cliente", e.getMessage()));
                return "formCliente";
            }
        }        
    }
    
    @PostMapping(path = "/cliente/{id}")
    public String update(@Valid @ModelAttribute Cliente cliente, BindingResult result, @PathVariable("id") Long id, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formCliente"; 
        }
        else{
            try{ 
                cliente.setId(cliente.getId());
                service.update(cliente, "","","");
                model.addAttribute("cliente", cliente); 
                model.addAttribute("msgSucesso", "Cliente atualizado com sucesso!");  
                return "formCliente";
            }
            catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("cliente", e.getMessage()));
                return "formCliente";
            }
        }        
    }
    
    @GetMapping(path = "/cliente/{id}")
    public String atualizar(@PathVariable("id") Long id, Model model){
        model.addAttribute("cliente", service.findById(id));        
        return "formCliente";
    }
    
   
    
}
