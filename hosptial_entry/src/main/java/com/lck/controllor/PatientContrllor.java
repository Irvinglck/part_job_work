package com.lck.controllor;

import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientContrllor {

    //患者列表页面
    @GetMapping("/patients")
    public String login( Model model
    ) {
        model.addAttribute("AdviceDrug",mockData());
        return "/patients/list";
    }

    List<AdviceDrug> mockData(){
        List<AdviceDrug> resultList=new ArrayList<>();
        resultList.add(new AdviceDrug().setId(1).setInsulinQuaType("2/百泌达+诺和灵N"));
        resultList.add(new AdviceDrug().setId(2).setInsulinQuaType("2/百泌达+诺和灵N"));
        resultList.add(new AdviceDrug().setId(3).setInsulinQuaType("2/百泌达+诺和灵N"));
        resultList.add(new AdviceDrug().setId(4).setInsulinQuaType("2/百泌达+诺和灵N"));
        resultList.add(new AdviceDrug().setId(5).setInsulinQuaType("2/百泌达+诺和灵N"));
        return resultList;
    }
}
