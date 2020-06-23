package com.lck.controllor;

import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/advice")
public class AdviceControllor {

    private AdviceDrugRepository adviceDrugRepository;
    private PatientRepository patientRepository;

    @Autowired
    private AdviceControllor(AdviceDrugRepository adviceDrugRepository,PatientRepository patientRepository){
        this.adviceDrugRepository=adviceDrugRepository;
        this.patientRepository=patientRepository;
    }

    //查看用药方案
    @GetMapping("/advieDrug/{number}")
    public String adviceDrug(@PathVariable String number,
                             Model model) {
        List<AdviceDrug> adviceDrugs = adviceDrugRepository.findByNumber(number);
        if (CollectionUtils.isEmpty(adviceDrugs)) {
            Patient patient = patientRepository.findByPatientNumber(number);
            List<AdviceDrug> resutl = new ArrayList<>();
            AdviceDrug adviceDrug = new AdviceDrug().setName(patient.getUsername());
            resutl.add(adviceDrug);
            model.addAttribute("adviceDrugs", resutl);
            model.addAttribute("number", number);
            return "/suggestions/advice";
        } else {
            model.addAttribute("adviceDrugs", adviceDrugs);
            model.addAttribute("number", number);
            return "/suggestions/advice";
        }
    }


}
