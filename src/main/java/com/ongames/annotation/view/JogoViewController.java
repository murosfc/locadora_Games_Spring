package com.ongames.annotation.view;

import com.ongames.exception.NotAllowedException;
import com.ongames.model.Categoria;
import com.ongames.model.Jogo;
import com.ongames.model.PlataformaEnum;
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
@RequestMapping(path="/jogos")
public class JogoViewController {
    @Autowired
    private JogoService service;
    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    public String findAll(Model model){
        model.addAttribute("jogos", service.findAll());
        return "jogos";
    }
    
    @GetMapping(path="/jogo")
    public String cadastrar(Model model){
       model.addAttribute("jogo", new Jogo());
       model.addAttribute("plataformas", PlataformaEnum.values());
       model.addAttribute("categorias", categoriaService.findAll());
       return "formJogo";     
    }
    
    @GetMapping(path="/jogo/{id}")
    public String cadastrar(Model model, @PathVariable("id") Long id){
       model.addAttribute("jogo", service.findById(id));
       model.addAttribute("plataformas", PlataformaEnum.values());
       model.addAttribute("categorias", categoriaService.findAll());
       return "formJogo";     
    }
    
    @PostMapping(path="/jogo/{id}")
    public String atualizar(@Valid @ModelAttribute Jogo jogo,@PathVariable("id") Long id, BindingResult result, Model model){
        this.cadastrar(model, id);
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formJogo";  
        }
        try{
            this.removeCategoriasNulas(jogo);
            jogo.setId(id);
            service.update(jogo);
            model.addAttribute("msgSucesso", "Jogo atualizado com sucesso");
            return "formJogo";  
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("jogos", e.getMessage()));
            return "formJogo"; 
        }      
    }
    
    @PostMapping(path="/jogo")
    public String salvar(@Valid @ModelAttribute Jogo jogo, BindingResult result, Model model){
        this.cadastrar(model);
        if (result.hasErrors()){
            model.addAttribute("msgErros", result.getAllErrors());
            return "formJogo";  
        }
        try{
            jogo.setId(null);
            this.removeCategoriasNulas(jogo);            
            service.save(jogo);
            model.addAttribute("msgSucesso", "Jogo salvo com sucesso");
            return "formJogo";  
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("jogos", e.getMessage()));
            return "formJogo"; 
        }      
    }
    
     @GetMapping(path="/{id}/deletar")
    public String remover(@PathVariable("id") Long id, Model model){
        try{
            service.delete(service.findById(id));            
            return "redirect:/jogos";
        }catch (Exception e){
            model.addAttribute("msgErros", new ObjectError("jogos", e.getMessage()));
            this.findAll(model);      
            return "jogos";  
        }
          
    }
    
    private void removeCategoriasNulas(Jogo j){
        j.getCategorias().removeIf( (Categoria c) -> {
            return c.getId()==null;
        });
        if(j.getCategorias().isEmpty()){
            throw new NotAllowedException("Ao menos uma categoria precisa ser selecionada para salvar um jogo");
        }
    }
    
}
 