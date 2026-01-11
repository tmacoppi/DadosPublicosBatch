package com.dados.deputados.batch.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class Proposicao extends Document {

    public int id;
    public String uri;
    public String siglaTipo;
    public int numero;
    public int ano;
    public int codTipo;
    public String descricaoTipo;
    public String ementa;
    public String ementaDetalhada;
    public String keywords;
    public LocalDateTime dataApresentacao;
    public String uriOrgaoNumerador;
    public String uriPropAnterior;
    public String uriPropPrincipal;
    public String uriPropPosterior;
    public String urlInteiroTeor;
    public String urnFinal;

    public LocalDateTime ultimoStatus_dataHora;
    public int ultimoStatus_sequencia;
    public int ultimoStatus_ideRelator;
    public int ultimoStatus_idOrgao;
    public String ultimoStatus_siglaOrgao;
    public String ultimoStatus_uriOrgao;
    public String ultimoStatus_regime;
    public String ultimoStatus_descricaoTramitacao;
    public int ultimoStatus_idTipoTramitacao;
    public String ultimoStatus_descricaoSituacao;
    public int ultimoStatus_idSituacao;
    public String ultimoStatus_despacho;
    public String ultimoStatus_apreciacao;
    public String ultimoStatus_url;

    public static Proposicao fromMap(Map<String, Object> dados) {
        return Proposicao.builder()
                .id((Integer) dados.get("id"))
                .uri((String) dados.get("uri"))
                .siglaTipo((String) dados.get("siglaTipo"))
                .codTipo((Integer) dados.get("codTipo"))
                .numero((Integer) dados.get("numero"))
                .ano((Integer) dados.get("ano"))
                .ementa((String) dados.get("ementa"))
                .dataApresentacao(LocalDateTime.parse((String) dados.get("dataApresentacao")))
                .uriOrgaoNumerador((String) dados.get("uriOrgaoNumerador"))
                //.statusProposicao(dados.get("statusProposicao") != null ?
                //        StatusProposicao.fromMap((Map<String, Object>) dados.get("statusProposicao")) : null)
                //.uriAutores((String) dados.get("uriAutores"))
                .descricaoTipo((String) dados.get("descricaoTipo"))
                .ementaDetalhada((String) dados.get("ementaDetalhada"))
                .keywords((String) dados.get("keywords"))
                .uriPropPrincipal((String) dados.get("uriPropPrincipal"))
                .uriPropAnterior((String) dados.get("uriPropAnterior"))
                .uriPropPosterior((String) dados.get("uriPropPosterior"))
                .urlInteiroTeor((String) dados.get("urlInteiroTeor"))
                .urnFinal((String) dados.get("urnFinal"))
                //.texto((String) dados.get("texto"))
                //.justificativa((String) dados.get("justificativa"))
                .build();
    }

    public static Proposicao fromArq(String linha) {

        String marcadorTemporario = "@#$";
        String marcadorEscapado = Matcher.quoteReplacement(marcadorTemporario);
        String literal_aspas_pontoevirgula = "\";\"";
        String passoA_mascarado = linha.replaceAll(literal_aspas_pontoevirgula, marcadorEscapado);
        String passoB_substituido = passoA_mascarado.replaceAll(";", ",");
        linha = passoB_substituido.replaceAll(
                Matcher.quoteReplacement(marcadorTemporario),
                Matcher.quoteReplacement(literal_aspas_pontoevirgula)
        );
        linha = linha.replaceAll("\"", "");

        String[] dados = linha.split(";");

        return Proposicao.builder()
                .id(Integer.parseInt(dados[0]))
                .uri(dados[1])
                .siglaTipo(dados[2])
                .numero(Integer.parseInt(dados[3]))
                .ano(Integer.parseInt(dados[4]))
                .codTipo(dados[5] != null ? Integer.parseInt(dados[5]) : 0)
                .descricaoTipo(dados[6])
                .ementa(dados[7])
                .ementaDetalhada(dados[8])
                .keywords(dados[9])
                .dataApresentacao(LocalDateTime.parse(dados[10]))
                .uriOrgaoNumerador(dados[11])
                .uriPropAnterior(dados[12])
                .uriPropPrincipal(dados[13])
                .uriPropPosterior(dados[14])
                .urlInteiroTeor(dados[15])
                .urnFinal(dados[16])
                .ultimoStatus_dataHora(dados[17] != null ? LocalDateTime.parse(dados[17]) : null)
                .ultimoStatus_sequencia(dados[18] != null ? Integer.parseInt(dados[18]) : 0)
                .ultimoStatus_ideRelator(Integer.parseInt(dados[19].substring(dados[19].lastIndexOf('/') + 1)))
                .ultimoStatus_idOrgao(dados[20] != null ? Integer.parseInt(dados[20]) : 0)
                .ultimoStatus_siglaOrgao(dados[21])
                .ultimoStatus_uriOrgao(dados[22])
                .ultimoStatus_regime(dados[23])
                .ultimoStatus_descricaoTramitacao(dados[24])
                .ultimoStatus_idTipoTramitacao(dados[25] != null ? Integer.parseInt(dados[25]) : 0)
                .ultimoStatus_descricaoSituacao(dados[26])
                .ultimoStatus_idSituacao(dados[27] != null ? Integer.parseInt(dados[27]) : 0)
                .ultimoStatus_despacho(dados[28])
                .ultimoStatus_apreciacao(dados[29])
                .ultimoStatus_url(dados[30])
                .build();

    }
}
