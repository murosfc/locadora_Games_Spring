package com.ongames.annotation.view;

import com.ongames.model.Conta;
import com.ongames.services.AluguelService;
import com.ongames.services.ContaService;
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
@RequestMapping(path = "/contas")
public class ContaViewController {
    @Autowired
    private ContaService service;
    @Autowired
    private JogoService serviceJogo;
     @Autowired
    private AluguelService aluguelService;
    
    @GetMapping(path ="/conta/{id}/alugueis")
    public String findAlugueisByConta(@PathVariable("id") Long id, Model model){
        model.addAttribute("alugueis", aluguelService.findByConta(id));
        return "alugueis";
    }
    
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("contas", service.findAll());        
        return "contas";
    }
    
    @GetMapping(path="/conta")
    public String cadastar(Model model){
        model.addAttribute("conta", new Conta());
        model.addAttribute("jogos", serviceJogo.findAll());         
        return "formConta";
    }
    
    @GetMapping(path="/conta/{id}")
    public String findByID(@PathVariable("id") Long id, Model model){
        model.addAttribute("conta", service.findById(id));
        model.addAttribute("jogos", serviceJogo.findAll()); 
        return "formConta";
    }
    
    @PostMapping(path="/conta/{id}")
    public String editar(@Valid @ModelAttribute Conta conta, BindingResult result, @PathVariable("id") Long id, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formConta";
        }
        else{
            try{
                conta.setId(id);
                service.update(conta);
                model.addAttribute("conta", conta); 
                model.addAttribute("msgSucesso", "Conta atualizada com sucesso");
                return "formConta";
            }catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("contas",e.getMessage()));
                return "formConta";
            }
        }
    }
    
    @PostMapping(path="/conta")
    public String salvar(@Valid @ModelAttribute Conta conta, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formConta";
        }
        else{
            try{
                conta.setId(null);
                service.save(conta);
                model.addAttribute("conta", conta); 
                model.addAttribute("msgSucesso", "Conta cadastrada com sucesso");
                return "formConta";
            }catch (Exception e){
                model.addAttribute("msgErros", new ObjectError("contas",e.getMessage()));
                return "formConta";
            }
        }
    }
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id, Model model){
        try{
            service.delete(service.findById(id));
            return "redirect:/contas";
        }
        catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("conta", e.getMessage()));
            this.getAll(model);
            return "contas";
        }        
    }
    
}
