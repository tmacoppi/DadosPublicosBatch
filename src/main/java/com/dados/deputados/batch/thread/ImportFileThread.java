package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.model.ImportFile;

import java.util.concurrent.Callable;

public interface ImportFileThread extends Callable<ImportFile> {
    // Cada implementação recebe seu próprio ImportDTO e repositórios específicos

    int bulkSize = 5000;
}

