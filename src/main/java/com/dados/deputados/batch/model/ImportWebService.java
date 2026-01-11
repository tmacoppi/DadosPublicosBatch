package com.dados.deputados.batch.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportWebService {

    private String name;

    private int totalImport;
    private int totalErrors;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private List<String> results;

}
