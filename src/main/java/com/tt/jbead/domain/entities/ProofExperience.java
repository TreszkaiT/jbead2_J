package com.tt.jbead.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JNB_PROOFEXP")
@Audited
public class ProofExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "PROF_NAMEWORK")
    private String nameWork;

    @NotNull
    @Column(name = "PROF_FROM")
    private LocalDate from;

    @NotNull
    @Column(name = "PROF_TO")
    private LocalDate to;

    @Column(name = "PROF_COMMENT", columnDefinition = "TEXT")
    private String comment;
}
