package com.tt.jbead.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JNB_LANGUAGE")
@Audited
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "LANG_NAME")
    private String name;

    @Column(name = "LANG_CODE")
    private String code;

    // Kapocsolótábla
    @ManyToMany
    @JoinTable(name = "LANGUAGE_PERSON", joinColumns = {@JoinColumn(name = "language_id")}, inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<User> personList;
}
