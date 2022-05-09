package com.ongames.annotation.view;

import com.ongames.model.Funcionario;
import com.ongames.services.FuncionarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
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
@RequestMapping(path="/funcionarios")
public class FuncionarioViewController {
    
    @Autowired
    private FuncionarioService service;
    
    @GetMapping
    private String findAll(Model model){
        model.addAttribute("funcionarios", service.findAll());
        return "funcionarios";    
    }
    
    @GetMapping(path="/funcionario")
    private String cadastrar(Model model){
        model.addAttribute("funcionario", new Funcionario());
        return "formFuncionario";    
    }
    
    @GetMapping(path="/funcionario/{id}")
    private String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("funcionario", service.findById(id));
        return "formFuncionario";    
    }
    
    @PostMapping(path="/funcionario/{id}")
    public String update (@Valid @ModelAttribute Funcionario func,@PathVariable("id") Long id, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionario";
        }
        try{
            func.setId(id);
            service.update(func, "", "", "");
            model.addAttribute("msgSucesso", "Funcionario atualizado com sucesso");
            return "formFuncionario";            
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("funcionarios", e.getMessage()));
            return "formFuncionario";    
        }
    }
    
    @PostMapping(path="/funcionario")
    public String save (@Valid @ModelAttribute Funcionario func, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionario";
        }
        try{
            func.setId(null);
            service.save(func);
            model.addAttribute("msgSucesso", "Funcionario cadastrado com sucesso");
            return "formFuncionario";            
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("funcionarios", e.getMessage()));
            return "formFuncionario";    
        }
    }
    
}
