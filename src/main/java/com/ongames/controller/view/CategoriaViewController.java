package com.ongames.controller.view;

import com.ongames.model.Categoria;
import com.ongames.services.CategoriaService;
import com.ongames.services.JogoService;
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
@RequestMapping(path="categorias")
public class CategoriaViewController {
    @Autowired
    private CategoriaService service;    
    
    @GetMapping
    public String findAll(Model model){          
        model.addAttribute("categorias", service.findAll());         
        return "categorias";
    }
    
    @GetMapping(path="/categoria")
    public String cadastrar(Model model){
        model.addAttribute("categoria", new Categoria());        
        return "formCategoria";
    }
    
    @GetMapping(path="/categoria/{id}")
    public String findAll(@PathVariable("id") Long id, Model model){
        model.addAttribute("categoria", service.findById(id));        
        return "formCategoria";
    }
    
    @PostMapping(path="/categoria/{id}")
    public String editar(@Valid @ModelAttribute Categoria cat, BindingResult result, @PathVariable("id") Long id, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formCategoria";
        }
        else{
            try{
                cat.setId(id);
                service.update(cat);
                model.addAttribute("categoria", cat); 
                model.addAttribute("msgSucesso", "Categoria atualizada com sucesso");
                return "formCategoria";
            }catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("categorias",e.getMessage()));
                return "formCategoria";
            }
        }
    }
    
    @PostMapping(path="/categoria")
    public String save(@Valid @ModelAttribute Categoria cat, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formCategoria";
        }
        else{
            try{
                cat.setId(null);
                service.save(cat);
                model.addAttribute("categoria", cat); 
                model.addAttribute("msgSucesso", "Categoria cadastrada com sucesso");
                return "formCategoria";
            }catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("categorias",e.getMessage()));
                return "formCategoria";
            }
        }
    }   
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id, Model model){        
        try{            
            service.delete(service.findById(id));            
            return "redirect:/categorias";
        }
        catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("categoria", e.getMessage()));
            this.findAll(model);
            return "categorias";
        }        
    }
}
