package com.lck.controllor;

import com.lck.model.Patient;
import com.lck.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.persistence.Access;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientContrllor {
    private PatientRepository patientRepository;
    @Autowired
    public PatientContrllor(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }

    //患者列表页面
    @GetMapping("/patients")
    public String login(Model model
    ) {
        model.addAttribute("patients", patientRepository.findAll());
        return "/patients/list";
    }

    //删除
    @GetMapping("/delPatient/{id}")
    public String delPatient(
            @PathVariable Integer id,
            Model model
    ) {
        patientRepository.deleteById(id);
        model.addAttribute("patients", patientRepository.findAll());
        return "/patients/list";
    }

    //上传文件
    @PostMapping("/fileUpload")
    public String fileUpload(
            @RequestParam(name = "patientFile") MultipartFile patientFile,
            @RequestParam(name = "grade", required = false) String grade,//权限
            Model model
    ) {
        if (!"0".equals(grade)) {
            model.addAttribute("msg", "您暂无上传文件权限");
            return "/patients/filedata";
        }
        String originalFilename = patientFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            model.addAttribute("msg", "请选择文件后上传");
            return "/patients/filedata";
        }
        model.addAttribute("msg", originalFilename);
        return "/patients/filedata";
    }

    //上传文件
    @GetMapping("/fileDown")
    public String downloadFile(HttpServletResponse response, Model model) {
        String fileName = "template.xlsx";// 设置文件名，根据业务需要替换成要下载的文件名
        //设置文件路径
        String realPath = "D:\\part_job\\template";
        File file = new File(realPath, fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                model.addAttribute("msg", "下载成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "/patients/filedata";
        } else {
            model.addAttribute("msg", "文件路径不存在");
            return "/patients/filedata";
        }
    }
}
