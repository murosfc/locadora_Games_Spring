package com.ongames.controller.view;

import com.ongames.exception.NotAllowedException;
import com.ongames.model.Aluguel;
import com.ongames.model.Conta;
import com.ongames.model.Pagamento;
import com.ongames.services.AluguelService;
import com.ongames.services.ClienteService;
import com.ongames.services.ContaService;
import com.ongames.services.FuncionarioService;
import com.ongames.services.PagamentoService;
import java.time.LocalDate;
import java.util.List;
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
    @Autowired
    private PagamentoService pagamentoService;
       
    @GetMapping(path="/aluguel/{id}/cliente")
    public String findByCliente(Model model, @PathVariable("id") Long idAluguel){
        model.addAttribute("clientes", clienteService.findByAluguelById(idAluguel));
        return "clientes";
    }
    
    @GetMapping(path="/aluguel/{id}/funcionario")
    public String findByFuncionario(Model model, @PathVariable("id") Long idAluguel){
        model.addAttribute("funcionarios", funcionarioService.findByAluguelById(idAluguel));
        return "funcionarios";
    }
    
    @GetMapping
    public String findAll(Model model){
        model.addAttribute("alugueis", service.findAll());  
        model.addAttribute("hoje", LocalDate.now());
        return "alugueis";
    }
    
    @PostMapping(path="/busca")
    public String findByPeriod (Model model){
        model.addAttribute("alugueis", service.findOngoing());
        return "alugueis";
    }
    
    @GetMapping(path ="aluguel")
    public String cadastro(Model model){
        Aluguel aluguel = new Aluguel();
        Pagamento pagamento = new Pagamento();
        aluguel.setPagamento(pagamento);       
        model.addAttribute("aluguel", aluguel);
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
    public String save(@ModelAttribute Aluguel aluguel, BindingResult result, Model model){ 
        this.removeContasNulas(aluguel);
        aluguel.getContas().forEach((Conta c) -> {
            c.setAluguel(aluguel);
        });
        aluguel.getPagamento().setAluguel(aluguel);        
        if (result.hasErrors()){
            this.cadastro(model);
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluguel";
        }
        try{           
            service.save(aluguel);          
            this.cadastro(model);
            model.addAttribute("msgSucesso", "Aluguel registrado com sucesso!");
            return "formAluguel";
        }catch (Exception e){
            this.cadastro(model);
            model.addAttribute("msgErros", List.of(new ObjectError("aluguel", e.getMessage())));            
            return "formAluguel";
        }
    }
    
    @PostMapping(path="/aluguel/{id}")
    public String update(@ModelAttribute Aluguel aluguel, BindingResult result, @PathVariable("id") Long id, Model model){         
        this.removeContasNulas(aluguel);
        aluguel.getContas().forEach((Conta c) -> {
            if (c.getAluguel() = null)
            c.setAluguel(aluguel);
        });
        if (result.hasErrors()){
            this.atualizar(model, id);
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluguel";
        }
        try{    
            service.update(aluguel);            
            this.atualizar(model, id);
            model.addAttribute("msgSucesso", "Aluguel atualizado com sucesso!");
            return "formAluguel";
        }catch (Exception e){            
            this.atualizar(model, id);
            model.addAttribute("msgErros", List.of(new ObjectError("aluguel", e.getMessage())));            
            return "formAluguel";
        }
    } 
    
    private void removeContasNulas(Aluguel aluguel){
        aluguel.getContas().removeIf( (Conta c) -> {
            return c.getId()== null;
        });
        if(aluguel.getContas().isEmpty()){
            throw new NotAllowedException("Ao menos uma conta precisa ser selecionada para cadastrar um aluguel");
        }
       
    }
   
}
