package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.model.*;

public class ImportFileThreadFactory {

    public ImportFileThread createTask(ImportFile importFileDTO) {
        String fileName = importFileDTO.getFile().getName();
        if (fileName.contains("deputados.csv")) {
            return new ImportDeputadoFileThread(importFileDTO);
        } else if (fileName.contains("Ano-")) {
            return new ImportDespesaFileThread(importFileDTO);
        } else if (fileName.contains("proposicoes-")){
            return new ImportProposicaoFileThread(importFileDTO);
        } else if (fileName.contains("legislaturas.csv")){
            return new ImportLegislaturaFileThread(importFileDTO);
        }

        throw new IllegalArgumentException("Nenhuma estratégia de import encontrada para: " + fileName);
    }

}