package com.ongames.controller.view;

import com.ongames.exception.NotAllowedException;
import com.ongames.model.Funcionario;
import com.ongames.repository.PermissaoRepository;
import com.ongames.services.AluguelService;
import com.ongames.services.FuncionarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class FuncionarioViewController {
    
    @Autowired
    private FuncionarioService service;
    @Autowired
    private AluguelService aluguelService;
    @Autowired
    private PermissaoRepository permissaoRepo;
    
    @GetMapping(path="/funcionarios/funcionario/{id}/alugueis")
    public String findAlugueisByFuncionario(@PathVariable("id") Long id, Model model){
        model.addAttribute("alugueis", aluguelService.findByFuncionario(id));
        return "alugueis";
    }
    
    @GetMapping(path="/funcionarios")
    private String findAll(Model model){
        model.addAttribute("funcionarios", service.findAll());
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "funcionarios";    
    }
    
    @PostMapping(path="/funcionarios/busca")
    public String busca(@RequestParam("nome") String nome, Model model){
        model.addAttribute("funcionarios", service.findByName(nome));
        return "funcionarios";
    }
    
    
    @GetMapping(path="/funcionarios/funcionario")
    private String cadastrar(Model model){
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";    
    }
    
    @GetMapping(path="/funcionarios/funcionario/{id}")
    private String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("funcionario", service.findById(id));
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";    
    }
    
    @PostMapping(path="/funcionarios/funcionario/{id}")
    public String update (@ModelAttribute Funcionario func,@PathVariable("id") Long id, BindingResult result, Model model){
        func.setSenha(service.findById(id).getSenha());
        model.addAttribute("permissoes", permissaoRepo.findAll());
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
    
    @GetMapping(path = "/meusdados")
    public String getMeusDados (@AuthenticationPrincipal User meuUser, Model model){
        Funcionario func = service.findByEmail(meuUser.getUsername());       
        model.addAttribute("funcionario", func);
        return "formMeusDados";
    }   
    
    @PostMapping(path="/meusdados")
    public String updateMeusDados (@ModelAttribute Funcionario func, BindingResult result, Model model,
            @AuthenticationPrincipal User meuUser, @RequestParam("senhaAtual") String senhaAtual, 
            @RequestParam("novaSenha") String novaSenha,@RequestParam("confirmaNovaSenha") String confirmaNovaSenha){                
        Funcionario funcDB = service.findByEmail(meuUser.getUsername());
        if (!func.getId().equals(funcDB.getId())){
            throw new NotAllowedException("Atualização de funcionário não autorizada");
        }               
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formMeusDados";
        }
        func.setPermissoes(funcDB.getPermissoes());        
        try{            
            service.update(func, senhaAtual, novaSenha, confirmaNovaSenha);
            model.addAttribute("msgSucesso", "Funcionario atualizado com sucesso");
            return "formMeusDados";            
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("funcionarios", e.getMessage()));
            return "formMeusDados";    
        }
    }
    
    @PostMapping(path="/funcionarios/funcionario")
    public String save (@Valid @ModelAttribute Funcionario func, BindingResult result, Model model, @RequestParam("confirmaSenha") String confirmaSenha){
        model.addAttribute("permissoes", permissaoRepo.findAll());
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionario";
        }
        if (!confirmaSenha.equals(func.getSenha())){
           model.addAttribute("msgErros", new ObjectError("funcionarios", "Senhas não conferem"));
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
    
    @GetMapping(path="/funcionarios/{id}/deletar")
    public String deletar(@PathVariable("id") Long id, Model model){       
        try{
            service.delete(service.findById(id));
            return "redirect:/funcionarios";
        }
        catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("cliente", e.getMessage()));
            this.findAll(model);
            return "funcionarios";
        }        
    }
}
