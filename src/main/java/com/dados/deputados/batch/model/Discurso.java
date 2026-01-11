package com.dados.deputados.batch.model;

import lombok.*;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Discurso {

    public int idDeputado;
    public LocalDateTime dataHoraFim;
    public LocalDateTime dataHoraInicio;
    public FaseEvento faseEvento;
    public String keywords;
    public String sumario;
    public String tipoDiscurso;
    public String transcricao;
    public String uriEvento;
    public String urlAudio;
    public String urlTexto;
    public String urlVideo;

    public static Discurso fromMap(Map<String, Object> dados, int idDeputado) {
        return Discurso.builder()
                .idDeputado(idDeputado)
                .dataHoraFim(dados.get("dataHoraFim") != null ? LocalDateTime.parse((String) dados.get("dataHoraFim")) : null)
                .dataHoraInicio(dados.get("dataHoraInicio") != null ? LocalDateTime.parse((String) dados.get("dataHoraInicio")) : null)
                .faseEvento(FaseEvento.fromMap(dados.get("faseEvento") != null ? (Map<String, Object>) dados.get("faseEvento") : null))
                .keywords((String) dados.get("keywords"))
                .sumario((String) dados.get("sumario"))
                .tipoDiscurso((String) dados.get("tipoDiscurso"))
                .transcricao((String) dados.get("transcricao"))
                .uriEvento((String) dados.get("uriEvento"))
                .urlAudio((String) dados.get("urlAudio"))
                .urlTexto((String) dados.get("urlTexto"))
                .urlVideo((String) dados.get("urlVideo"))
                .build();
    }
}

