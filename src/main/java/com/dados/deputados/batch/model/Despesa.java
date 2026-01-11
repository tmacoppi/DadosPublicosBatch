package com.dados.deputados.batch.model;

import lombok.*;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despesa {
    private String txNomeParlamentar;
    private String cpf;
    private Integer ideCadastro;
    private Integer nuCarteiraParlamentar;
    private Integer nuLegislatura;
    private String sgUF;
    private String sgPartido;
    private Integer codLegislatura;
    private Integer numSubCota;
    private String txtDescricao;
    private Integer numEspecificacaoSubCota;
    private String txtDescricaoEspecificacao;
    private String txtFornecedor;
    private String txtCNPJCPF;
    private String txtNumero;
    private Integer indTipoDocumento;
    private LocalDateTime datEmissao;
    private Double vlrDocumento;
    private Double vlrGlosa;
    private Double vlrLiquido;
    private Integer numMes;
    private Integer numAno;
    private Integer numParcela;
    private String txtPassageiro;
    private String txtTrecho;
    private Integer numLote;
    private String numRessarcimento;
    private LocalDateTime datPagamentoRestituicao;
    private Double vlrRestituicao;
    private Integer nuDeputadoId;
    private Integer ideDocumento;
    private String urlDocumento;
    private String tipoDespesa;
    private String tipoDocumento;

    public static Despesa fromMap(Map<String, Object> dados) {
        return Despesa.builder()
                .txtFornecedor((String) dados.get("nomeFornecedor"))
                .txtCNPJCPF((String) dados.get("cnpjCpfFornecedor"))
                .indTipoDocumento((Integer) dados.get("codTipoDocumento"))
                .datEmissao(dados.get("dataDocumento") != null ? LocalDateTime.parse((String) dados.get("dataDocumento")) : null)
                .vlrDocumento((Double) dados.get("valorDocumento"))
                .vlrGlosa((Double) dados.get("valorGlosa"))
                .vlrLiquido((Double) dados.get("valorLiquido"))
                .numMes((Integer) dados.get("mes"))
                .numAno((Integer) dados.get("ano"))
                .numParcela((Integer) dados.get("parcela"))
                .numLote((Integer) dados.get("codLote"))
                .numRessarcimento((String) dados.get("numRessarcimento"))
                .ideDocumento((Integer) dados.get("codDocumento"))
                .urlDocumento((String) dados.get("urlDocumento"))
                .tipoDespesa((String) dados.get("tipoDespesa"))
                .tipoDocumento((String) dados.get("tipoDocumento"))
                .build();
    }

    public static Despesa fromArq(String linha) {

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

        return Despesa.builder()
                .txNomeParlamentar(dados[0].trim())
                .cpf(dados[1] != null ? dados[1] : null)
                .ideCadastro(dados[2] != null ? Integer.parseInt(dados[2]) : 0)
                .nuCarteiraParlamentar(dados[3] != null && !dados[3].isEmpty() ? Integer.parseInt(dados[3]) : 0)
                .nuLegislatura(dados[4] != null ? Integer.parseInt(dados[4]) : 0)
                .sgUF(dados[5])
                .sgPartido(dados[6])
                .codLegislatura(dados[7] != null ? Integer.parseInt(dados[7]) : 0)
                .numSubCota(dados[8] != null ? Integer.parseInt(dados[8]) : 0)
                .txtDescricao(dados[9].trim())
                .numEspecificacaoSubCota(dados[10] != null ? Integer.parseInt(dados[10]) : 0)
                .txtDescricaoEspecificacao(dados[11].trim())
                .txtFornecedor(dados[12].trim())
                .txtCNPJCPF(dados[13])
                .txtNumero(dados[14])
                .indTipoDocumento(dados[15] != null ? Integer.parseInt(dados[15]) : 0)
                .datEmissao(dados[16] != null && !dados[16].isEmpty() ? LocalDateTime.parse(dados[16]) : null)
                .vlrDocumento(dados[17] != null ? Double.parseDouble(dados[17]) : 0)
                .vlrGlosa(dados[18] != null ? Double.parseDouble(dados[18]) : 0)
                .vlrLiquido(dados[19] != null ? Double.parseDouble(dados[19]) : 0)
                .numMes(dados[20] != null ? Integer.parseInt(dados[20]) : 0)
                .numAno(dados[21] != null ? Integer.parseInt(dados[21]) : 0)
                .numParcela(dados[22] != null ? Integer.parseInt(dados[22]) : 0)
                .txtPassageiro(dados[23])
                .txtTrecho(dados[24])
                .numLote(dados[25] != null ? Integer.parseInt(dados[25]) : 0)
                .numRessarcimento(dados[26])
                .datPagamentoRestituicao(dados[27] != null && !dados[27].isEmpty() ? LocalDateTime.parse(dados[27]) : null)
                .vlrRestituicao(dados[28] != null && !dados[28].isEmpty() ? Double.parseDouble(dados[28]) : null)
                .nuDeputadoId(dados[29] != null && !dados[29].isEmpty() ? Integer.parseInt(dados[29]) : 0)
                .ideDocumento(dados[30] != null && !dados[30].isEmpty() ? Integer.parseInt(dados[30]) : 0)
                .urlDocumento(dados.length > 31 ? dados[31] : null)
                .build();
    }
}
