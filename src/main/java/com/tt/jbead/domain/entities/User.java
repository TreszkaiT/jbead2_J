package com.tt.jbead.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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

    @Column(name = "USR_FNAME")
    private String firstName;

    @Column(name = "USR_LNAME")
    private String lastName;

    @NotNull
    @Column(name = "USR_EMAIL")
    private String email;

    @NotEmpty
    @Column(name = "USR_PASSWOR")
    private String password;

}
