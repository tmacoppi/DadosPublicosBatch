package com.dados.deputados.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gabinete {
    public String nome;
    public String predio;
    public String sala;
    public String andar;
    public String telefone;
    public String email;
    
    public static Gabinete fromMap(Map<String, Object> dados) {
        return Gabinete.builder()
                .nome((String) dados.get("nome"))
                .predio((String) dados.get("predio"))
                .sala((String) dados.get("sala"))
                .andar((String) dados.get("andar"))
                .telefone((String) dados.get("telefone"))
                .email((String) dados.get("email"))
                .build();
    }
}
