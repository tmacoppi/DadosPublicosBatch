package com.dados.deputados.batch.dao;

import com.dados.deputados.batch.model.Deputado;

import java.sql.*;
import java.util.List;

public class DeputadoDAO {

    public void upsert(Connection c, Deputado deputado) throws SQLException {
        String sql = getInsert();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            psInsertDeputado(ps, deputado);

            ps.executeUpdate();
        }
    }

    public void upsertBatch(Connection c, List<Deputado> deputados) throws SQLException {
        String sql = getInsert();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Deputado deputado : deputados) {
                psInsertDeputado(ps, deputado);

                ps.addBatch();   // acumula no batch
            }
            ps.executeBatch();   // executa todos de uma vez
        }
    }

    public Integer findIdByDocumento(Connection c, String doc) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("SELECT id FROM clientes WHERE documento=?")) {
            ps.setString(1, doc);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }

    private String getInsert() {
        return "INSERT IGNORE INTO dados_publicos.deputado " +
                "(id, uri, nome, siglaPartido, uriPartido, siglaUf, idLegislatura, idLegislaturaInicial, " +
                "idLegislaturaFinal, urlFoto, email, nomeCivil, cpf, siglaSexo, urlWebsite, redeSocial, " +
                "dataNascimento, dataFalecimento, ufNascimento, municipioNascimento, escolaridade) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    private void psInsertDeputado(PreparedStatement ps, Deputado deputado) throws SQLException {
        ps.setInt(1, deputado.getId());
        ps.setString(2, deputado.getUri());
        ps.setString(3, deputado.getNome());
        ps.setString(4, deputado.getSiglaPartido());
        ps.setString(5, deputado.getUriPartido());
        ps.setString(6, deputado.getSiglaUf());
        ps.setInt(7, deputado.getIdLegislatura());
        ps.setInt(8, deputado.getIdLegislaturaInicial());
        ps.setInt(9, deputado.getIdLegislaturaFinal());
        ps.setString(10, deputado.getUrlFoto());
        ps.setString(11, deputado.getEmail());
        ps.setString(12, deputado.getNomeCivil());
        ps.setString(13, deputado.getCpf());
        ps.setString(14, deputado.getSiglaSexo());
        ps.setString(15, deputado.getUrlWebsite());
        ps.setString(16, deputado.getRedeSocial() != null ? deputado.getRedeSocial().toString() : null);
        ps.setDate(17, deputado.getDataNascimento() != null ? Date.valueOf(deputado.getDataNascimento()) : null);
        ps.setDate(18, deputado.getDataFalecimento() != null ? Date.valueOf(deputado.getDataFalecimento()) : null);
        ps.setString(19, deputado.getUfNascimento());
        ps.setString(20, deputado.getMunicipioNascimento());
        ps.setString(21, deputado.getEscolaridade());
    }

}
