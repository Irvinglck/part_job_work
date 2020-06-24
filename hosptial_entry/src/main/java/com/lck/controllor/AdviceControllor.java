package com.lck.controllor;

import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
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
            model.addAttribute("adviceDrug",resutl.get(0));
            model.addAttribute("number", number);
            return "/suggestions/advice";
        } else {
            model.addAttribute("adviceDrugs", adviceDrugs);
            model.addAttribute("adviceDrug",adviceDrugs.get(0));
            model.addAttribute("number", number);
            return "/suggestions/advice";
        }
    }

    //跳转添加用药方案页面
    @GetMapping("/toAddAdvicePage")
    public String toAddAdviceDrug(
            @RequestParam(name = "number") String number,
            @RequestParam(name = "name") String name,
            Model model
    ) {
        AdviceDrug one=new AdviceDrug().setName(name).setNumber(number);
        model.addAttribute("addviceDrug",one);
        return "/suggestions/addAdvice";
    }
    //添加用药方案
    @PostMapping("/addAdvice")
    public String addAdvice(
            AdviceDrug adviceDrug,
            Model model
    ) {

        AdviceDrug one = adviceDrugRepository.save(adviceDrug);
        if(one!=null){
            model.addAttribute("number",one.getNumber());
            List<AdviceDrug> byNumber = adviceDrugRepository.findByNumber(one.getNumber());
            model.addAttribute("adviceDrugs",byNumber);
            return "/suggestions/advice";
        }else{
            model.addAttribute("msg","添加用药方案失败,检查后重新添加");
            return "/suggestions/addAdvice";
        }
    }


    //删除用药方案
    @GetMapping("/delAdvice/{id}")
    public String delAdvice(
            @PathVariable Integer id,
            Model model
    ) {
        AdviceDrug one = adviceDrugRepository.getOne(id);
        adviceDrugRepository.deleteById(id);
        model.addAttribute("adviceDrugs",adviceDrugRepository.findByNumber(one.getNumber()));
        model.addAttribute("adviceDrug",one);
        return "/suggestions/advice";
    }

    //跳转添加用药方案页面
    @GetMapping("/toEditAdvicePage/{id}")
    public String toAddAdviceDrug(
           @PathVariable Integer id,
            Model model
    ) {
        AdviceDrug one = adviceDrugRepository.getOne(id);
        model.addAttribute("adviceDrug",one);
        return "/suggestions/editAdvice";
    }

    //添加用药方案
    @PostMapping("/editAdvice")
    public String editAdvice(
            AdviceDrug adviceDrug,
            Model model
    ) {

        AdviceDrug one = adviceDrugRepository.save(adviceDrug);
        if(one!=null){
            model.addAttribute("number",one.getNumber());
            List<AdviceDrug> byNumber = adviceDrugRepository.findByNumber(one.getNumber());
            model.addAttribute("adviceDrugs",byNumber);
            return "/suggestions/advice";
        }else{
            model.addAttribute("msg","添加用药方案失败,检查后重新添加");
            return "/suggestions/addAdvice";
        }
    }

}
