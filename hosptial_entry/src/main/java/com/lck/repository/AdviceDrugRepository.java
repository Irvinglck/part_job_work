package com.lck.repository;

import com.lck.model.AdviceDrug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AdviceDrugRepository extends CrudRepository<AdviceDrug,Integer> {
}
