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
@Table(name = "JNB_STUDY")
@Audited
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "STDY_SCHOOLNAME")
    private String nameSchool;

    @NotNull
    @Column(name = "STDY_FROM")
    private LocalDate from;

    @NotNull
    @Column(name = "STDY_TO")
    private LocalDate to;

    @Column(name = "STDY_COMMENT", columnDefinition = "TEXT")
    private String comment;

    @NotNull
    @Column(name = "STDY_LEVEL")
    private Integer level;
}
