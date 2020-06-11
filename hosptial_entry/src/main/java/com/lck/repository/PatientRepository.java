package com.lck.repository;

import com.lck.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PatientRepository extends CrudRepository<Patient, Integer> {

}
