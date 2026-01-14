package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.model.ImportFile;
import com.dados.deputados.batch.model.Proposicao;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImportProposicaoFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

    public ImportProposicaoFileThread(ImportFile anImportFile) {
        this.anImportFile = anImportFile;
    }

    @Override
    public ImportFile call() {
        /*

        anImportFile.setInicio(LocalDateTime.now());

        Path target = Path.of(anImportFile.getExtractDir(), anImportFile.getFileName());

        int[] counters = new int[2]; // [0] imports, [1] errors

        try (BufferedReader br = Files.newBufferedReader(target)) {
            anImportFile.setFileSize(Files.size(target));

            StringBuilder sb = new StringBuilder();
            String line;
            boolean insideRecord = false;

            line = br.readLine();

            List<Proposicao> lista = new ArrayList<>();

            long lineNumber = 1; // header = 1
            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (!insideRecord) {
                    sb.setLength(0); // limpa buffer
                }
                sb.append(line).append("\n");

                // regra: se número de aspas é par, registro terminou
                long quotes = sb.chars().filter(ch -> ch == '"').count();
                if (quotes % 2 == 0) {
                    String registroCompleto = sb.toString();
                    try {
                        lista.add(Proposicao.fromArq(registroCompleto));
                        counters[0]++;

                        if (lista.size() >= bulkSize) {
                            collection.insertMany(lista);
                            lista.clear();
                        }
                    } catch (Exception e) {
                        counters[1]++;
                        log.error("[Error : " + lineNumber + "][" + e.getMessage() + "][" + line + "]", e);
                    }
                    insideRecord = false;
                } else {
                    insideRecord = true; // continua acumulando
                }
            }

            if (!lista.isEmpty()) {
                collection.insertMany(lista);
            }
        } catch (IOException e) {
            counters[1]++;
            anImportFile.getResults().add("[Error: " + e.getMessage() + "][" + target + "]");
        }

        anImportFile.setTotalImport(counters[0]);
        anImportFile.setTotalErrors(counters[1]);
        anImportFile.setFim(LocalDateTime.now());


         */
        return anImportFile;
    }
}