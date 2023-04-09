package com.tt.jbead.repositories;

import com.tt.jbead.domain.entities.ProofExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProofExperienceRepository extends JpaRepository<ProofExperience, Integer> {
}
