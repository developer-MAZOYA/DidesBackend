package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionNextOfKin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NextOfKinRepo extends JpaRepository<AdmissionNextOfKin,Long> {

}
