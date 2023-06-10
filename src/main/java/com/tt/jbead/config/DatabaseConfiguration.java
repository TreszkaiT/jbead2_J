package com.tt.jbead.config;

import com.tt.jbead.domain.entities.*;
import com.tt.jbead.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class DatabaseConfiguration {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private MessageAppRepository messageAppRepository;

    @Autowired
    private OtherSkillRepository otherSkillRepository;

    @Autowired
    private ProofExperienceRepository proofExperienceRepository;

    @Autowired
    private StudyRepository studyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedDatabase(){

        City city = City.builder().name("Nyíregyháza").zip("4400").build();
        cityRepository.save(city);

        Language language = Language.builder().name("Magyar").code("HUN").build();
        languageRepository.save(language);

        Phone phone = Phone.builder().code(30).pnumber(1234567).build();
        phoneRepository.save(phone);

        Picture picture = Picture.builder().name("önarckép").type("jpg").uuid(UUID.randomUUID()).build();
        pictureRepository.save(picture);

        SocialMedia socialMedia = SocialMedia.builder().name("Facebook").build();
        socialMediaRepository.save(socialMedia);

        MessageApp messageApp = MessageApp.builder().name("Messenger").build();
        messageAppRepository.save(messageApp);
        MessageApp messageApp2 = MessageApp.builder().name("Skype").build();
        messageAppRepository.save(messageApp2);

        OtherSkill otherSkill = OtherSkill.builder().name("Jogosítvány").level("B").build();
        otherSkillRepository.save(otherSkill);

        ProofExperience proofExperience = ProofExperience.builder().nameWork("Programozó").from(LocalDate.of(2020, 1, 8)).to(LocalDate.of(2022, 1, 8)).comment("Bla bla bla").build();
        proofExperienceRepository.save(proofExperience);

        Study study = Study.builder().nameSchool("Nyíregyházi Főiskola").from(LocalDate.of(2020, 1, 8)).to(LocalDate.of(2022, 1, 8)).level(2).comment("Bla bla bla").build();
        studyRepository.save(study);

        User user = User.builder().id(1).email("kiss.geza@company.com").password("a").firstName("Kiss Géza").theme("kb-dark-theme").build();
        user.setMessageApps(List.of(messageApp));
//        user.setLanguages(List.of(language));
        user.setPhones(List.of(phone));
        userRepository.save(user);
    }
}
