package com.ongames;

import com.ongames.model.Aluguel;
import com.ongames.model.Categoria;
import com.ongames.model.Cliente;
import com.ongames.model.Conta;
import com.ongames.model.Funcionario;
import com.ongames.model.Jogo;
import com.ongames.model.Pagamento;
import com.ongames.model.PlataformaEnum;
import com.ongames.repository.CategoriaRepository;
import com.ongames.repository.ClienteRepository;
import com.ongames.repository.ContaRepository;
import com.ongames.repository.FuncionarioRepository;
import com.ongames.repository.JogoRepository;
import com.ongames.repository.PagamentoRepository;
import com.ongames.repository.AluguelRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	public static void main(String[] args) {
		SpringApplication.run(LocadoraApplication.class, args);          
	}

    @Override
    public void run(String... args) throws Exception { 
        
                
        //cliente
        Cliente objCliente = new Cliente("28015-150", "34", "Ap 302", "318.091.980-97", "Julio Oliveira Campos", "jocampos@gmail.com", "MzH45b561!"); 
        clienteRepo.save(objCliente);
        //categoria
        Categoria cat1 = new Categoria("RPG");
        Categoria cat2 = new Categoria("Puzzle");
        Categoria cat3 = new Categoria("Ação");
        catRepo.save(cat1);
        catRepo.save(cat2);
        catRepo.save(cat3);
        //jogo
        Jogo objJogo = new Jogo("70010000000025", "The Legend of Zelda™: Breath of the Wild", "https://assets.nintendo.com/image/upload/c_pad,f_auto,h_613,q_auto,w_1089/ncom/pt_BR/games/switch/t/the-legend-of-zelda-breath-of-the-wild-switch/hero?v=2022021722", 15.50f);
        objJogo.setCategorias(List.of(cat1, cat2, cat3));
        objJogo.setPlataforma(PlataformaEnum.NS.name());
        jogoRepo.save(objJogo);
        //conta
        Conta objConta = new Conta("conta01@ongames.com", "Hi34Ub");
        objConta.setJogo(objJogo); 
        contaRepo.save(objConta);
        //funcionario public Funcionario(String nick, String whatsapp, String cpf, String nome, String email, String senha)
        Funcionario objFunc = new Funcionario("SuperHiggs","(22)9977-0001","942.275.060-18", "Hugo Villela Silva", "superhiggs@ongames.com", "So6410Hge!");
        funcRepo.save(objFunc);
        //aluguel
        Aluguel objAluguel = new Aluguel();
        objAluguel.setCliente(objCliente);
        objAluguel.setContas(List.of(objConta));
        objAluguel.setContatoSuporte(objFunc);
        //pagamento
        Pagamento pag = objAluguel.getPagamento();        
        pag.setValor(objAluguel.getValor());               
        pag.setDataPagamento(LocalDate.now());      
        pag.setValidacao(gerarStringAleatoria());
        aluguelRepo.save(objAluguel);
        pagRepo.save(pag);
        //finalização aluguel         
        objConta.setAluguel(objAluguel);
        contaRepo.save(objConta);  
        System.out.println(clienteRepo.findAll());
    }  
    
    public String gerarStringAleatoria(){
        int leftLimit = 97; // letra 'a'
        int rightLimit = 122; // letra 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

}
