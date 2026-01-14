package com.dados.deputados.batch.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportExecution {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<ImportFile> importedFiles;
    private List<ImportWebService> importedWebServices;
    private int totalFileLines;
    private int totalWebServiceLines;

}
