package com.tt.jbead.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofExperienceDTO {

    private Integer id;

    private String nameWork;

    private LocalDate from;

    private LocalDate to;

    private String comment;
}
