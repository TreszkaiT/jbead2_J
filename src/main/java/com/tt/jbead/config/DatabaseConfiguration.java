package com.tt.jbead.config;

import com.tt.jbead.domain.entities.City;
import com.tt.jbead.domain.entities.Language;
import com.tt.jbead.domain.entities.User;
import com.tt.jbead.repositories.UserRepository;
import com.tt.jbead.repositories.CityRepository;
import com.tt.jbead.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfiguration {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedDatabase(){

        City city = City.builder().name("Nyíregyháza").zip("4400").build();
        cityRepository.save(city);

        Language language = Language.builder().name("Magyar").code("HUN").build();
        languageRepository.save(language);

        User user = User.builder().id(1).email("kiss.geza@company.com").password("a").firstName("Kiss Géza").theme("kb-dark-theme").build();
        userRepository.save(user);
    }
}
