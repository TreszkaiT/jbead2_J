package com.tt.jbead.repositories;

import com.tt.jbead.domain.entities.MessageApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageAppRepository extends JpaRepository<MessageApp, Integer> {
}
