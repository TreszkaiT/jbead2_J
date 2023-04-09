package com.tt.jbead.domain.dtos;

import com.tt.jbead.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate bornDate;

    private String phone = "";

    private String address;

    private String webSite;

    private String coverLetter;


    // Enums
    private Gender gender;


    // Kapocsolótábla
    private List<LanguageDTO> languages;
    private List<SocialMediaDTO> socialMedia;
    private List<MessageAppDTO> messageApps;
    private List<StudyDTO> studies;
    private List<ProofExperienceDTO> proofExperiences;
    private List<OtherSkillDTO> otherSkills;

    // Másik táblával való összekötés idegen kulccsal (Foreign Key) = JoinColumn
    // OneToMany
    // Phone
    private List<PhoneDTO> phones;


    // @manyToOne
    // City
    private CityDTO city;

    //OneToOne
    private PictureDTO picture;

}