package com.ongames;

import com.ongames.model.Aluguel;
import com.ongames.model.Categoria;
import com.ongames.model.Cliente;
import com.ongames.model.Conta;
import com.ongames.model.Funcionario;
import com.ongames.model.Jogo;
import com.ongames.model.Pagamento;
import com.ongames.model.Permissao;
import com.ongames.model.PlataformaEnum;
import com.ongames.repository.CategoriaRepository;
import com.ongames.repository.ClienteRepository;
import com.ongames.repository.ContaRepository;
import com.ongames.repository.FuncionarioRepository;
import com.ongames.repository.JogoRepository;
import com.ongames.repository.PagamentoRepository;
import com.ongames.repository.AluguelRepository;
import com.ongames.repository.PermissaoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LocadoraApplication implements CommandLineRunner{
    
    @Autowired
    private AluguelRepository aluguelRepo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private CategoriaRepository catRepo;
    @Autowired
    private JogoRepository jogoRepo;
    @Autowired
    private ContaRepository contaRepo;
    @Autowired
    private FuncionarioRepository funcRepo;    
    @Autowired
    private PagamentoRepository pagRepo;  
    @Autowired
    private PermissaoRepository permRepo;

    public static void main(String[] args) {
            SpringApplication.run(LocadoraApplication.class, args);          
    }

    @Override
    public void run(String... args) throws Exception { 
        //Permissões
        Permissao pAdm = new Permissao("ADMIN");
        Permissao pFunc = new Permissao("FUNC"); 
        Permissao pCliente = new Permissao("CLIENTE");
        permRepo.saveAll(List.of(pAdm, pFunc, pCliente));        
        //cliente
        Cliente objCliente = new Cliente("28015150", "34", "Ap 302", "318.091.980-97", "Julio Oliveira Campos", "jocampos@gmail.com", new BCryptPasswordEncoder().encode("MzH45b561!")); 
        Cliente objCliente2 = new Cliente("27943141", "592", "", "350.011.410-52", "Luiza Nascimento Pena", "lupena@hotmail.com", new BCryptPasswordEncoder().encode("LP@3n4Na")); 
        objCliente.setPermissoes(List.of(pCliente));
        objCliente2.setPermissoes(List.of(pCliente));
        clienteRepo.saveAll(List.of(objCliente,objCliente2));
        //categoria
        Categoria cat1 = new Categoria("RPG");
        Categoria cat2 = new Categoria("Puzzle");
        Categoria cat3 = new Categoria("Ação");
        catRepo.save(cat1);
        catRepo.save(cat2);
        catRepo.save(cat3);
        //jogo
        Jogo objJogo = new Jogo("70010000000025", "The Legend of Zelda™: Breath of the Wild", "https://assets.nintendo.com/image/upload/c_pad,f_auto,h_613,q_auto,w_1089/ncom/pt_BR/games/switch/t/the-legend-of-zelda-breath-of-the-wild-switch/hero?v=2022021722", 15.50f);
        Jogo objJogo2 = new Jogo("70010000046395", "Splatoon™ 3", "https://assets.nintendo.com/image/upload/c_pad,f_auto,h_613,q_auto,w_1089/ncom/en_US/games/switch/s/splatoon-3-switch/hero?v=2022042820", 20.00f);
        objJogo.setCategorias(List.of(cat1, cat2, cat3));
        objJogo2.setCategorias(List.of(cat3));
        objJogo.setPlataforma(PlataformaEnum.NS.name());
        objJogo2.setPlataforma(PlataformaEnum.NS.name());
        jogoRepo.saveAll(List.of(objJogo, objJogo2));
        //contas
        Conta objConta = new Conta("conta01@ongames.com", "Hi34Ub");
        objConta.setJogo(objJogo);
        Conta objConta2 = new Conta("conta02@ongames.com", "H2fs44!");
        objConta2.setJogo(objJogo2);
        contaRepo.saveAll(List.of(objConta,objConta2));
        //funcionarios
        Funcionario objFunc = new Funcionario("SuperHiggs","(22)9977-0001","942.275.060-18", "Hugo Villela Silva", "superhiggs@ongames.com", new BCryptPasswordEncoder().encode("Senha123!"));
        Funcionario objFunc2 = new Funcionario("lariSniper","(22)9977-0002","902.382.480-62", "Larissa Hespanho", "larisniper@ongames.com", new BCryptPasswordEncoder().encode("LSn1p3r@"));
        objFunc.setPermissoes(List.of(pAdm, pFunc));
        objFunc2.setPermissoes(List.of(pFunc));
        funcRepo.saveAll(List.of(objFunc,objFunc2));
        //aluguel
        Aluguel objAluguel = new Aluguel();
        objAluguel.setCliente(objCliente);
        objAluguel.setContas(List.of(objConta));
        objConta.setAluguel(objAluguel);
        objAluguel.setContatoSuporte(objFunc);
        //pagamento 
        Pagamento objPag = new Pagamento (); 
        objPag.setAluguel(objAluguel);
        objPag.setDataPagamento(LocalDate.now()); 
        objPag.setValidacao(gerarStringAleatoria());
        objPag.iniciaAluguel();
        objAluguel.setPagamento(objPag);
        objAluguel.atualizaTotal();
        aluguelRepo.save(objAluguel);        
        pagRepo.save(objPag);
        //finalização aluguel         
        objConta.setAluguel(objAluguel);
        contaRepo.save(objConta);        
    }  
    
    public String gerarStringAleatoria(){
        int leftLimit = 97; // letra 'a'
        int rightLimit = 122; // letra 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

}
