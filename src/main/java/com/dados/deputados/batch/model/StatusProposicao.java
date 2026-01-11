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
public class StatusProposicao {
    public LocalDateTime dataHora;
    public int sequencia;
    public String siglaOrgao;
    public String uriOrgao;
    public String uriUltimoRelator;
    public String regime;
    public String descricaoTramitacao;
    public String codTipoTramitacao;
    public String descricaoSituacao;
    public int codSituacao;
    public String despacho;
    public String url;
    public String ambito;
    public String apreciacao;

    public static StatusProposicao fromMap(Map<String, Object> dados) {
        return StatusProposicao.builder()
                .dataHora(dados.get("dataHora") != null ? LocalDateTime.parse((String) dados.get("dataHora")) : null)
                .sequencia(dados.get("sequencia") != null ? (Integer) dados.get("sequencia") : 0)
                .siglaOrgao((String) dados.get("siglaOrgao"))
                .uriOrgao((String) dados.get("uriOrgao"))
                .uriUltimoRelator((String) dados.get("uriUltimoRelator"))
                .regime((String) dados.get("regime"))
                .descricaoTramitacao((String) dados.get("descricaoTramitacao"))
                .codTipoTramitacao((String) dados.get("codTipoTramitacao"))
                .descricaoSituacao((String) dados.get("descricaoSituacao"))
                .codSituacao(dados.get("codSituacao") != null ? (Integer) dados.get("codSituacao") : 0)
                .despacho((String) dados.get("despacho"))
                .url((String) dados.get("url"))
                .ambito((String) dados.get("ambito"))
                .apreciacao((String) dados.get("apreciacao"))
                .build();
    }
}
