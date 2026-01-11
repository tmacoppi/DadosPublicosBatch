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
public class UltimoStatus {
    public Integer id;
    public String uri;
    public String nome;
    public String siglaPartido;
    public String uriPartido;
    public String siglaUf;
    public Integer idLegislatura;
    public String urlFoto;
    public String email;
    public String data;
    public String nomeEleitoral;
    public Gabinete gabinete;
    public String situacao;
    public String condicaoEleitoral;
    public String descricaoStatus;

    @SuppressWarnings("unchecked")
    public static UltimoStatus fromMap(Map<String, Object> dados) {
        return UltimoStatus.builder()
                .id((Integer) dados.get("id"))
                .uri((String) dados.get("uri"))
                .nome((String) dados.get("nome"))
                .siglaPartido((String) dados.get("siglaPartido"))
                .uriPartido((String) dados.get("uriPartido"))
                .siglaUf((String) dados.get("siglaUf"))
                .idLegislatura((Integer) dados.get("idLegislatura"))
                .urlFoto((String) dados.get("urlFoto"))
                .email((String) dados.get("email"))
                .data((String) dados.get("data"))
                .nomeEleitoral((String) dados.get("nomeEleitoral"))
                .situacao((String) dados.get("situacao"))
                .condicaoEleitoral((String) dados.get("condicaoEleitoral"))
                .descricaoStatus((String) dados.get("descricaoStatus"))
                .gabinete(Gabinete.fromMap((Map<String, Object>) dados.get("gabinete")))
                .build();
    }
}
