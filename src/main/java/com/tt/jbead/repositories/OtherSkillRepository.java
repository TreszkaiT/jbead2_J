package com.tt.jbead.repositories;

import com.tt.jbead.domain.entities.OtherSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherSkillRepository extends JpaRepository<OtherSkill, Integer> {
}
