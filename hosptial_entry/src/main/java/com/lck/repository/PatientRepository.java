package com.lck.repository;

import com.lck.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientRepository extends JpaSpecificationExecutor<Patient>, JpaRepository<Patient, Integer> {
    @Query(value = "select * from t_patient u where u.number =?1",nativeQuery=true)
    Patient findByPatientNumber(String number);

    @Query(value = "select * from t_patient u where u.username like %?1% ",nativeQuery=true)
    List<Patient> findByName(String username);
//
//    @Query("select s from ssh s where s.userId = :userId")
//    Page<Patient> selectAllByUserId(@Param("userId") Integer userId, Pageable pageable);

}
