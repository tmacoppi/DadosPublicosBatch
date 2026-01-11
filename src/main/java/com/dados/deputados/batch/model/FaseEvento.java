package com.dados.deputados.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaseEvento{
    public LocalDateTime dataHoraFim;
    public LocalDateTime dataHoraInicio;
    public String titulo;

    public static FaseEvento fromMap(Map<String, Object> dados) {
        return FaseEvento.builder()
                .dataHoraFim(dados.get("dataHoraFim") != null ? LocalDateTime.parse((String) dados.get("dataHoraFim")) : null)
                .dataHoraInicio(dados.get("dataHoraInicio") != null ? LocalDateTime.parse((String) dados.get("dataHoraInicio")) : null)
                .titulo((String) dados.get("titulo"))
                .build();
    }
}
