package com.lck.repository;


import com.lck.model.PatientDes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface PatientDesRepository extends JpaSpecificationExecutor<PatientDes>, JpaRepository<PatientDes,Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from t_patient_des where `number` =?1",nativeQuery = true)
    void delPatientDes(String number);

    @Query(value = "select *  from t_patient_des where `number` =?1",nativeQuery = true)
    PatientDes findByNumber(String number);

}
