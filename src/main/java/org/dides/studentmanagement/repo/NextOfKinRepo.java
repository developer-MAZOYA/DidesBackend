package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionNextOfKin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NextOfKinRepo extends JpaRepository<AdmissionNextOfKin,Long> {
    List<AdmissionNextOfKin> findByNameContainingIgnoreCase(String name);

    List<AdmissionNextOfKin> findByRelationship(String relationship);

    Optional<AdmissionNextOfKin> findByMobileNumber(String mobileNumber);

}
