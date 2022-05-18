package com.ongames.security;

import com.ongames.model.Funcionario;
import com.ongames.model.Permissao;
import com.ongames.repository.FuncionarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioDetailsService implements UserDetailsService{
    
    @Autowired
    private FuncionarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = repo.findByEmail(email);
        if (funcionario == null){
            throw new UsernameNotFoundException("Funcionario não encontrado com o e-mail: " + email);
        }
        return new User(email, funcionario.getSenha(), getAuthorities(funcionario.getPermissoes()));
    }
    
    private List<GrantedAuthority> getAuthorities(List<Permissao> lista){
        List<GrantedAuthority> list = new ArrayList<>();
            for (Permissao p: lista){
                list.add(new SimpleGrantedAuthority("ROLE_"+p.getNome()));
            }
        return list;            
    }
    
}
