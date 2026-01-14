package com.dados.deputados.batch.dao;

import com.dados.deputados.batch.model.Despesa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DespesaDAO {

    public void upsertBatch(Connection c, List<Despesa> despesas) throws SQLException {
        String sql = getInsert();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Despesa despesa : despesas) {
                psInsertDespesa(ps, despesa);

                ps.addBatch();   // acumula no batch
            }
            ps.executeBatch();   // executa todos de uma vez
        }
    }

    private String getInsert() {
        return "INSERT IGNORE INTO dados_publicos.despesa (ideDocumento, txNomeParlamentar, cpf, ideCadastro, " +
                "nuCarteiraParlamentar, nuLegislatura, sgUF, sgPartido, codLegislatura, numSubCota, txtDescricao, " +
                "numEspecificacaoSubCota, txtDescricaoEspecificacao, txtFornecedor, txtCNPJCPF, txtNumero, " +
                "indTipoDocumento, datEmissao, vlrDocumento, vlrGlosa, vlrLiquido, numMes, numAno, numParcela, " +
                "txtPassageiro, txtTrecho, numLote, numRessarcimento, datPagamentoRestituicao, vlrRestituicao, " +
                "nuDeputadoId, urlDocumento, tipoDespesa, tipoDocumento) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    private void psInsertDespesa(PreparedStatement ps, Despesa despesa) throws SQLException {
        ps.setInt(1, despesa.getIdeDocumento());
        ps.setString(2, despesa.getTxNomeParlamentar());
        ps.setString(3, despesa.getCpf());
        ps.setInt(4, despesa.getIdeCadastro());
        ps.setInt(5, despesa.getNuCarteiraParlamentar());
        ps.setInt(6, despesa.getNuLegislatura());
        ps.setString(7, despesa.getSgUF());
        ps.setString(8, despesa.getSgPartido());
        ps.setInt(9, despesa.getCodLegislatura());
        ps.setInt(10, despesa.getNumSubCota());
        ps.setString(11, despesa.getTxtDescricao());
        ps.setInt(12, despesa.getNumEspecificacaoSubCota());
        ps.setString(13, despesa.getTxtDescricaoEspecificacao());
        ps.setString(14, despesa.getTxtFornecedor());
        ps.setString(15, despesa.getTxtCNPJCPF());
        ps.setString(16, despesa.getTxtNumero());
        ps.setInt(17, despesa.getIndTipoDocumento());
        ps.setDate(18, despesa.getDatEmissao() != null ? Date.valueOf(despesa.getDatEmissao()) : null);
        ps.setDouble(19, despesa.getVlrDocumento());
        ps.setDouble(20, despesa.getVlrGlosa());
        ps.setDouble(21, despesa.getVlrLiquido());
        ps.setInt(22, despesa.getNumMes());
        ps.setInt(23, despesa.getNumAno());
        ps.setInt(24, despesa.getNumParcela());
        ps.setString(25, despesa.getTxtPassageiro());
        ps.setString(26, despesa.getTxtTrecho());
        ps.setBigDecimal(27, despesa.getNumLote());
        ps.setString(28, despesa.getNumRessarcimento());
        ps.setDate(29, despesa.getDatPagamentoRestituicao() != null ? Date.valueOf(despesa.getDatPagamentoRestituicao()) : null);
        ps.setDouble(30, despesa.getVlrRestituicao() != null ? despesa.getVlrRestituicao() : 0.0);
        ps.setInt(31, despesa.getNuDeputadoId());
        ps.setString(32, despesa.getUrlDocumento());
        ps.setString(33, despesa.getTipoDespesa());
        ps.setString(34, despesa.getTipoDocumento());

    }
}
