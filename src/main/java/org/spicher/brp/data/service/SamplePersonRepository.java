package org.spicher.brp.data.service;

import java.util.UUID;
import org.spicher.brp.data.entity.SamplePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}