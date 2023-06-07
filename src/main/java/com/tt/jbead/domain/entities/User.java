package com.tt.jbead.domain.entities;

import com.tt.jbead.domain.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JNB_USER")
@Audited
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "USR_FNAME", length = 20)
    private String firstName;

    @Column(name = "USR_LNAME", columnDefinition = "varchar(30) default 'Smith'")
    private String lastName;

    @NotNull
    @Column(name = "USR_EMAIL", length = 30)
    private String email;

    //@NotEmpty
    @Column(name = "USR_PASSWOR")
    private String password;

    @Column(name = "PERS_BORN_DATE")
    private LocalDate bornDate;

    //@NotEmpty
//    @Column(name = "PERS_PHONE")
//    private String phone = "";

    //@NotEmpty
    @Column(name = "PERS_ADDRESS")
    private String address;

    @Column(name = "PERS_WEBSITE")
    private String webSite;

    //@NotNull
    @Column(name = "PERS_COVERLETTER", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "PERS_CONFIG_UI")
    private String theme;


    // Enums
    @Enumerated(value = EnumType.ORDINAL)
    private Gender gender;


    // Kapocsolótábla
    @ManyToMany
    @JoinTable(name = "LANGUAGE_PERSON", joinColumns = {@JoinColumn(name = "person_id")}, inverseJoinColumns = {@JoinColumn(name = "language_id")})
    private List<Language> languages;
    @ManyToMany
    private List<SocialMedia> socialMedias;
    @ManyToMany
    private List<MessageApp> messageApps;
    @ManyToMany
    private List<Study> studies;
    @ManyToMany
    private List<ProofExperience> proofExperiences;
    @ManyToMany
    private List<OtherSkill> otherSkills;

    // Másik táblával való összekötés idegen kulccsal (Foreign Key) = JoinColumn
    // OneToMany
    // Phone
    @OneToMany
    private List<Phone> phones;


    // @manyToOne
    // City
    @ManyToOne
    @JoinColumn(name = "city_id")       // Foreign Key; name = másik tábla oszlopának(rekord) neve
    private City city;

    //OneToOne
    @OneToOne
    private Picture picture;
}
