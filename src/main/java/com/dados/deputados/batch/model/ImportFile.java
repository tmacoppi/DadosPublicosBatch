package com.dados.deputados.batch.model;

import lombok.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportFile {

    private File file;
    private Long fileSize;

    private int totalImport;
    private int totalErrors;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private List<String> results;

}
