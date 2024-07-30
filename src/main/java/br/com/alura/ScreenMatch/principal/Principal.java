package br.com.alura.ScreenMatch.principal;

import br.com.alura.ScreenMatch.model.DadosEpisodios;
import br.com.alura.ScreenMatch.model.DadosSerie;
import br.com.alura.ScreenMatch.model.DadosTemporada;
import br.com.alura.ScreenMatch.model.Episodio;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scan = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=1f60bcb4";

    public void exibeMenu(){
        System.out.println("Insira o nome da série");
        var nomeDaSerie = scan.nextLine().replace(" ", "+");
        var json = consumo.obterDados(ENDERECO + nomeDaSerie + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadasLista = new ArrayList<>();

        for(int i = 1; i<= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeDaSerie + "&season=" + i + API_KEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            temporadasLista.add(temporada);
        }
        temporadasLista.forEach(System.out::println);

//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List <DadosEpisodios> episodiosTemporada = temporadasLista.get(i).episodios();
//            System.out.println("--Season " + i + " Episodes:");
//            for(int j = 1; j < episodiosTemporada.size(); j++){
//                System.out.println("Episode "+ j + ": " +episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadasLista.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadasLista.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("---\n ==>TOP 5");
        dadosEpisodios.stream()
                        .filter(e-> !e.avalicao().equalsIgnoreCase("N/A"))
                        .sorted(Comparator.comparing(DadosEpisodios::avalicao).reversed())
                            .limit(5)
                        .forEach(System.out::println);

        List<Episodio> episodios = temporadasLista.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                        .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println();

        System.out.println("Insira um trecho do título do episódio");
        var trechoTitulo = scan.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else{
            System.out.println("Episódio não encontrado");
        }


        System.out.println("Apartir de que ano você deseja ver os episódios?");
        var ano = scan.nextInt();
        scan.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvalicao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvalicao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvalicao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvalicao));

        System.out.println("Média: " + est.getAverage()+ "\n" +
                "Melhor episodio: " + est.getMax() + "\n" +
                "Pior episodio: " +est.getMin() + "\n" +
                "Quantidade: " +est.getCount());
    }
}
