package br.com.alura.ScreenMatch.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer episodio;
    private Double avalicao;
    private LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodios dadosEpisodios) {
        this.temporada = temporada;
        this.titulo = dadosEpisodios.titulo();
        this.episodio = dadosEpisodios.episodio();

        try{
            this.avalicao = Double.valueOf(dadosEpisodios.avalicao());
        }catch (NumberFormatException e){
            this.avalicao = 0.0;
        }
        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Double getAvalicao() {
        return avalicao;
    }

    public void setAvalicao(Double avalicao) {
        this.avalicao = avalicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", episodio=" + episodio +
                ", avalicao=" + avalicao +
                ", dataLancamento=" + dataLancamento;
    }
}
