package com.ongames;

import com.ongames.model.Aluguel;
import com.ongames.model.Categoria;
import com.ongames.model.Cliente;
import com.ongames.model.Conta;
import com.ongames.model.Funcionario;
import com.ongames.model.Jogo;
import com.ongames.model.Pagamento;
import com.ongames.model.PlataformaEnum;
import com.ongames.model.repository.CategoriaRepository;
import com.ongames.model.repository.ClienteRepository;
import com.ongames.model.repository.ContaRepository;
import com.ongames.model.repository.FuncionarioRepository;
import com.ongames.model.repository.JogoRepository;
import com.ongames.model.repository.PagamentoRepository;
import com.ongames.model.repository.AluguelRepository;
import java.util.Calendar;
import java.util.Collections;
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
        //funcionario
        Funcionario objFunc = new Funcionario("SuperHiggs","(22)9977-0001","592.344.610-37", "Hugo Villela Silva", "superhiggs@ongames.com", "So6410Hge!");
        funcRepo.save(objFunc);
        //aluguel
        Aluguel objAluguel = new Aluguel();
        objAluguel.setCliente(objCliente);
        objAluguel.setContas(List.of(objConta));
        objAluguel.setContatoSuporte(objFunc);
        //pagamento
        Pagamento pag = objAluguel.getPagamento();        
        pag.setValor(objAluguel.getValor());
        Calendar dataPagamento = Calendar.getInstance();        
        //dataPagamento.set(2022, 03, 29);
        pag.setDataPagamento(dataPagamento);      
        pag.setValidacao(gerarStringAleatoria());
        aluguelRepo.save(objAluguel);
        pagRepo.save(pag);
        //finalização aluguel 
        objAluguel.setDataInicioAluguel(dataPagamento);
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
