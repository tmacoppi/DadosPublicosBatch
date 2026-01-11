package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.model.*;
import com.mongodb.client.MongoDatabase;

public class ImportFileThreadFactory {

    public ImportFileThread createTask(ImportFile importFileDTO, MongoDatabase database) {
        String fileName = importFileDTO.getFileName();
        if (fileName.contains("deputados.csv")) {
            return new ImportDeputadoFileThread(importFileDTO, database.getCollection("deputado", Deputado.class));
        } else if (fileName.contains("Ano-")) {
            return new ImportDespesaFileThread(importFileDTO, database.getCollection("despesa", Despesa.class));
        } else if (fileName.contains("proposicoes-")){
            return new ImportProposicaoFileThread(importFileDTO, database.getCollection("proposicao", Proposicao.class));
        } else if (fileName.contains("legislaturas.csv")){
            return new ImportLegislaturaFileThread(importFileDTO, database.getCollection("legislatura", Legislatura.class));
        }

        throw new IllegalArgumentException("Nenhuma estratégia de import encontrada para: " + fileName);
    }

}