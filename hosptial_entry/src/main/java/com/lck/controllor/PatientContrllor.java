package com.lck.controllor;

import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.model.PatientDes;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientDesRepository;
import com.lck.repository.PatientRepository;
import com.lck.util.ConvertUtil;
import com.lck.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/patient")
public class PatientContrllor {
    private PatientRepository patientRepository;
    private FileUtils fileUtils;
    private PatientDesRepository patientDesRepository;
    private ConvertUtil convertUtil;
    private AdviceDrugRepository adviceDrugRepository;

    @Autowired
    public PatientContrllor(PatientRepository patientRepository,FileUtils fileUtils,PatientDesRepository patientDesRepository,
                            ConvertUtil convertUtil,AdviceDrugRepository adviceDrugRepository) {
        this.patientRepository = patientRepository;
        this.fileUtils=fileUtils;
        this.patientDesRepository=patientDesRepository;
        this.convertUtil=convertUtil;
        this.adviceDrugRepository=adviceDrugRepository;
    }

    //患者列表页面
    @GetMapping("/patients")
    public String login(Model model
    ) {
//        Iterable<Patient> all = patientRepository.findAll();
        model.addAttribute("patients", patientRepository.findAll());
        return "/patients/list";
    }

    //根据名字查询
    @GetMapping("/findByName")
    public String findByName(
            @RequestParam(name="username") String name,
            Model model
    ) {
        List<Patient> patients = patientRepository.findByName(name);
        model.addAttribute("patients", patients);
        return "/patients/list";
    }

    //删除
    @GetMapping("/delPatient/{id}")
    @ResponseBody
    public String delPatient(
            @PathVariable Integer id,
            Model model
    ) {
        Patient patient = patientRepository.findById(id).get();
        patientRepository.deleteById(id);
        patientDesRepository.delPatientDes(patient.getNumber());
        model.addAttribute("patients", patientRepository.findAll());
        return "SUCCESS";
    }
    //详情信息
    @GetMapping("/toDetail/{number}")
    private String toDetail(
            @PathVariable String number,//编号
            Model model
    ){
        PatientDes patientDes = patientDesRepository.findByNumber(number);
        List<Map<String, String>> maps = convertToMap(patientDes);
        Patient patient = patientRepository.findByPatientNumber(patientDes.getNumber());
        model.addAttribute("maps",maps);
        model.addAttribute("user",patient);
        return "/patients/detail" ;
    }
    //查看用药方案
    @GetMapping("/advieDrug/{number}")
    public String adviceDrug(@PathVariable String number,
                             Model model){
        List<AdviceDrug> adviceDrugs = adviceDrugRepository.findByNumber(number);
        model.addAttribute("adviceDrugs",adviceDrugs);
        return "/patients/advice";
    }
    private List<Map<String,String>> convertToMap(PatientDes patientDes) {
        Map<String, Object> stringObjectMap = convertUtil.entityToMap(patientDes);
        StringBuilder sb=new StringBuilder();
        stringObjectMap.forEach((k,v)->{
            sb.append(k+","+v+";");
        });
        String[] values = sb.toString().split("\\;");
        List<Map<String,String>> ListResult=new LinkedList<>();
        for (int i = 0; i <values.length ; i++) {
            Map<String,String> result=new TreeMap<>();
            String[] items = values[i].split("\\,");
            int k=0;
            for (int j = 0; j < items.length; j++) {
                result.put("V"+(k++),getMappingField(items[j])!=null?getMappingField(items[j]):items[j]);
            }
            ListResult.add(result);
        }
        return ListResult;
    }

    private String getMappingField(String key){
        Map<String,String> mapTable=new HashMap<>();
        mapTable.put("weightV","体重");
        mapTable.put("fatV","腹内脂肪");
        mapTable.put("acidV","尿酸");
        mapTable.put("skinFatV","皮下脂肪");
        mapTable.put("pressureV","收缩压");
        mapTable.put("disPressureV","舒张压");
        mapTable.put("bodyIndexV","体重指数");
        mapTable.put("hiplineV","臀围");
        mapTable.put("hipRatioV","腰臀比");
        mapTable.put("waistV","腰围");
        mapTable.put("followUpTimeV","随访时间");
        return mapTable.get(key);
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
        String fileName = patientFile.getOriginalFilename();
        assert fileName != null;
        String[] split = fileName.split("\\.");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        if (StringUtils.isEmpty(fileName)) {
            model.addAttribute("msg", "请选择csv文件上传");
            return "/patients/filedata";
        }
        String result;
        //上传文件
        if("template .csv".equals(fileName)){
            result=fileUtils.uploadFileCsv(patientFile);
        }else{
            result=fileUtils.uploadAdviceCsv(patientFile);
        }

        model.addAttribute("msg", result);
        return "/patients/filedata";
    }

    //下载模板文件
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
