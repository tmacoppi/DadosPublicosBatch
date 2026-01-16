package com.dados.deputados.batch.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Legislatura {

    public int id;
    public String uri;
    public LocalDate dataInicio;
    public LocalDate dataFim;
    public int anoEleicao;

    public static Legislatura fromMap(Map<String, Object> dados) {
        return Legislatura.builder()
                .id((Integer) dados.get("id"))
                .uri((String) dados.get("uri"))
                .dataInicio(LocalDate.parse((String) dados.get("dataInicio")))
                .dataFim(LocalDate.parse((String) dados.get("dataFim")))
                .build();
    }

    public static Legislatura fromArq(String linha) {

        String[] dados = linha.replaceAll("\"", "").split(";");

        return Legislatura.builder()
                .id(Integer.parseInt(dados[0]))
                .uri(dados[1])
                .dataInicio(LocalDate.parse(dados[2]))
                .dataFim(LocalDate.parse(dados[3]))
                .anoEleicao(Integer.parseInt(dados[4]))
                .build();
    }
}
