package com.dados.deputados.batch.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportFile {

    private String extractDir;
    private String fileName;
    private Long fileSize;

    private int totalImport;
    private int totalErrors;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private List<String> results;

}
