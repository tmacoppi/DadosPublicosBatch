package com.dados.deputados.batch.model;

import lombok.*;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Deputado {

    public int id;
    public String uri;
    public String nome;
    public String siglaPartido;
    public String uriPartido;
    public String siglaUf;
    public int idLegislatura;
    public int idLegislaturaInicial;
    public int idLegislaturaFinal;
    public String urlFoto;
    public String email;
    public String nomeCivil;
    public UltimoStatus ultimoStatus;
    public String cpf;
    public String siglaSexo;
    public String urlWebsite;
    public List<String> redeSocial;
    public LocalDate dataNascimento;
    public LocalDate dataFalecimento;
    public String ufNascimento;
    public String municipioNascimento;
    public String escolaridade;

    public double totalDespesas;

    private List<Despesa> despesas;
    private List<Discurso> discursos;
    private List<Proposicao> proposicoes;

    @SuppressWarnings("unchecked")
    public static Deputado fromMap(Map<String, Object> dados) {

        return Deputado.builder()
                .id(dados.get("id") != null ? (Integer) dados.get("id") : 0)
                .nomeCivil((String) dados.get("nomeCivil"))
                .cpf((String) dados.get("cpf"))
                .siglaSexo((String) dados.get("sexo"))
                .urlWebsite((String) dados.get("urlWebsite"))
                .redeSocial((List<String>) dados.get("redeSocial"))
                .dataNascimento(dados.get("dataNascimento") != null? LocalDate.parse((String) dados.get("dataNascimento")) : null)
                .dataFalecimento(dados.get("dataFalecimento") != null ? LocalDate.parse((String) dados.get("dataFalecimento")) : null)
                .ufNascimento((String) dados.get("ufNascimento"))
                .municipioNascimento((String) dados.get("municipioNascimento"))
                .escolaridade((String) dados.get("escolaridade"))
                .ultimoStatus(dados.get("ultimoStatus")!= null ? UltimoStatus
                        .fromMap((Map<String, Object>) dados.get("ultimoStatus")) : null)
                .nome((String) dados.get("nome"))
                .siglaPartido((String) dados.get("siglaPartido"))
                .uriPartido((String) dados.get("uriPartido"))
                .siglaUf((String) dados.get("siglaUf"))
                .idLegislatura(dados.get("idLegislatura") != null ? (Integer) dados.get("idLegislatura") : 0)
                .idLegislaturaInicial(dados.get("idLegislaturaInicial") != null ? (Integer) dados.get("idLegislaturaInicial") : 0)
                .idLegislaturaFinal(dados.get("idLegislaturaFinal") != null ? (Integer) dados.get("idLegislaturaFinal") : 0)
                .urlFoto((String) dados.get("urlFoto"))
                .email((String) dados.get("email"))
                .build();
    }

    public static Deputado fromArq(String linha) {

        String[] dados = linha.replaceAll("\"", "").split(";");

        //log.info(Arrays.toString(dados));

        return Deputado.builder()
                .id(Integer.parseInt(dados[0].substring(dados[0].lastIndexOf('/') + 1)))
                .uri(dados[0])
                .nome(dados[1])
                .idLegislaturaInicial(dados[2] != null ? Integer.parseInt(dados[2]) : 0)
                .idLegislaturaFinal(dados[3] != null ? Integer.parseInt(dados[3]) : 0)
                .nomeCivil(dados[4])
                .cpf(dados[5])
                .siglaSexo(dados[6])
                .redeSocial(dados.length > 7 ? (dados[7] != null ? List.of(dados[7].split(",")) : null) : null)
                .urlWebsite(dados.length > 8 ? dados[8] : null)
                .dataNascimento(dados.length > 9 ? (dados[9] != null && !dados[9].isEmpty() ? LocalDate.parse(dados[9]) : null) : null)
                .dataFalecimento(dados.length > 10 ? (dados[10] != null && !dados[10].isEmpty() ? LocalDate.parse(dados[10]) : null) : null)
                .ufNascimento(dados.length > 11 ? dados[11] : null)
                .municipioNascimento(dados.length > 12 ? dados[12] : null)
                .build();
    }
}
