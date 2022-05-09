package com.ongames.annotation.view;

import com.ongames.exception.NotAllowedException;
import com.ongames.model.Aluguel;
import com.ongames.model.Pagamento;
import com.ongames.services.AluguelService;
import com.ongames.services.ClienteService;
import com.ongames.services.ContaService;
import com.ongames.services.FuncionarioService;
import com.ongames.services.PagamentoService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path ="/alugueis")
public class AluguelViewController {
    
    @Autowired
    private AluguelService service;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ContaService contaService;
    @Autowired
    private FuncionarioService funcionarioService;
       
    @GetMapping
    public String findAll(Model model){
        model.addAttribute("alugueis", service.findAll());
        return "alugueis";
    }
    
    @GetMapping(path ="aluguel")
    public String cadastro(Model model){
        model.addAttribute("aluguel", new Aluguel());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("contas", contaService.findAll());
        model.addAttribute("funcionarios", funcionarioService.findAll());        
        return "formAluguel";
    }
    
    @GetMapping(path ="aluguel/{id}")
    public String atualizar(Model model, @PathVariable("id") Long id){
        model.addAttribute("aluguel", service.findById(id));
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("contas", contaService.findAll());
        model.addAttribute("funcionarios", funcionarioService.findAll());        
        return "formAluguel";
    }
    
    @PostMapping(path="/aluguel")
    public String save(@Valid @ModelAttribute Aluguel aluguel, BindingResult result, Model model){
        if (result.hasErrors()){
            this.cadastro(model);
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluguel";
        }
        try{
            aluguel.setId(null);
            service.save(aluguel);        
            model.addAttribute("msgSucesso", "Aluguel registrado com sucesso!");
            return "formAluguel";
        }catch (Exception e){
            this.cadastro(model);
            model.addAttribute("msgErros", new ObjectError("msgErros", e.getMessage()));            
            return "formAluguel";
        }
    }
    
    @PostMapping(path="/aluguel/{id}")
    public String update(@Valid @ModelAttribute Aluguel aluguel, BindingResult result, @PathVariable("id") Long id, Model model){
       
        if (result.hasErrors()){
            this.atualizar(model, id);
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluguel";
        }
        try{
            aluguel.setId(aluguel.getId());
            service.update(aluguel);        
            model.addAttribute("msgSucesso", "Aluguel atualizado com sucesso!");
            return "formAluguel";
        }catch (Exception e){
            this.atualizar(model, id);
            model.addAttribute("msgErros", new ObjectError("msgErros", e.getMessage()));            
            return "formAluguel";
        }
    }
    
}
