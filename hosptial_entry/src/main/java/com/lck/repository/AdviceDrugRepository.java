package com.lck.repository;

import com.lck.model.AdviceDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdviceDrugRepository extends JpaSpecificationExecutor<AdviceDrug>, JpaRepository<AdviceDrug, Integer> {
    @Query(value = "select * from t_advice_drug u where u.number =?1",nativeQuery=true)
    List<AdviceDrug> findByNumber(String number);
}
