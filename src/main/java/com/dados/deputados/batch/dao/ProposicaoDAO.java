package com.dados.deputados.batch.dao;

import com.dados.deputados.batch.model.Proposicao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;

public class ProposicaoDAO {

    public void upsertBatch(Connection c, List<Proposicao> proposicoes) throws SQLException {
        String sql = getInsert();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Proposicao proposicao : proposicoes) {
                psInsertProposicao(ps, proposicao);

                ps.addBatch();   // acumula no batch
            }
            ps.executeBatch();   // executa todos de uma vez
        }
    }

    private String getInsert() {
        return "INSERT IGNORE INTO dados_publicos.proposicao (id, uri, siglaTipo, numero, ano, codTipo, descricaoTipo, " +
                "ementa, ementaDetalhada, keywords, dataApresentacao, uriOrgaoNumerador, uriPropAnterior, " +
                "uriPropPrincipal, uriPropPosterior, urlInteiroTeor, urnFinal, ultimoStatus_dataHora, " +
                "ultimoStatus_sequencia, ultimoStatus_ideRelator, ultimoStatus_idOrgao, ultimoStatus_siglaOrgao, " +
                "ultimoStatus_uriOrgao, ultimoStatus_regime, ultimoStatus_descricaoTramitacao, " +
                "ultimoStatus_idTipoTramitacao, ultimoStatus_descricaoSituacao, ultimoStatus_idSituacao, " +
                "ultimoStatus_despacho, ultimoStatus_apreciacao, ultimoStatus_url) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    private void psInsertProposicao(PreparedStatement ps, Proposicao proposicao) throws SQLException {
        ps.setInt(1, proposicao.getId());
        ps.setString(2, proposicao.getUri());
        ps.setString(3, proposicao.getSiglaTipo());
        ps.setInt(4, proposicao.getNumero());
        ps.setInt(5, proposicao.getAno());
        ps.setInt(6, proposicao.getCodTipo());
        ps.setString(7, proposicao.getDescricaoTipo());
        ps.setString(8, proposicao.getEmenta());
        ps.setString(9, proposicao.getEmentaDetalhada());
        ps.setString(10, proposicao.getKeywords());
        ps.setDate(11, proposicao.getDataApresentacao() != null ? Date.valueOf(proposicao.getDataApresentacao().toLocalDate()) : null);
        ps.setString(12, proposicao.getUriOrgaoNumerador());
        ps.setString(13, proposicao.getUriPropAnterior());
        ps.setString(14, proposicao.getUriPropPrincipal());
        ps.setString(15, proposicao.getUriPropPosterior());
        ps.setString(16, proposicao.getUrlInteiroTeor());
        ps.setString(17, proposicao.getUrnFinal());
        ps.setDate(18, proposicao.getUltimoStatus_dataHora() != null ? Date.valueOf(proposicao.getUltimoStatus_dataHora().toLocalDate()) : null);
        ps.setInt(19, proposicao.getUltimoStatus_sequencia());
        ps.setInt(20, proposicao.getUltimoStatus_ideRelator());
        ps.setInt(21, proposicao.getUltimoStatus_idOrgao());
        ps.setString(22, proposicao.getUltimoStatus_siglaOrgao());
        ps.setString(23, proposicao.getUltimoStatus_uriOrgao());
        ps.setString(24, proposicao.getUltimoStatus_regime());
        ps.setString(25, proposicao.getUltimoStatus_descricaoTramitacao());
        ps.setInt(26, proposicao.getUltimoStatus_idTipoTramitacao());
        ps.setString(27, proposicao.getUltimoStatus_descricaoSituacao());
        ps.setInt(28, proposicao.getUltimoStatus_idSituacao());
        ps.setString(29, proposicao.getUltimoStatus_despacho());
        ps.setString(30, proposicao.getUltimoStatus_apreciacao());
        ps.setString(31, proposicao.getUltimoStatus_url());

    }
}
