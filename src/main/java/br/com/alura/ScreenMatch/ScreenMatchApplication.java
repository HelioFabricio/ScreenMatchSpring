package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.principal.Principal;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;
import br.com.alura.ScreenMatch.model.DadosEpisodios;
import br.com.alura.ScreenMatch.model.DadosSerie;
import br.com.alura.ScreenMatch.model.DadosTemporada;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var principal = new Principal();
		principal.exibeMenu();



	}
}
