package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorShipRepo extends JpaRepository<AdmissionSponsor, Long> {

}
