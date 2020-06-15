package com.lck.repository;

import com.lck.model.Patient;
import com.lck.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    @Query(value = "select * from t_patient u where u.number =?1",nativeQuery=true)
    Patient findByPatientNumber(String number);

}
