package com.lck.controllor;

import com.lck.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientContrllor {

    //患者列表页面
    @GetMapping("/patients")
    public String login( Model model
    ) {
        model.addAttribute("patients",mockData());
        return "/patients/list";
    }
    //上传文件
    @PostMapping("/fileUpload")
    public String fileUpload(
            @RequestParam(name="patientFile") MultipartFile patientFile,
            Model model
            ) {
        String originalFilename = patientFile.getOriginalFilename();
        model.addAttribute("fileName",originalFilename);
        return "/patients/filedata";
    }
    List<Patient> mockData(){
        List<Patient> resultList=new ArrayList<>();
        resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        return resultList;
    }
}
