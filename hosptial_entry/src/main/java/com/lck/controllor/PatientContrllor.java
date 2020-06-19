package com.lck.controllor;

import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.model.PatientDes;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientDesRepository;
import com.lck.repository.PatientRepository;
import com.lck.util.ConvertUtil;
import com.lck.util.FileUtils;
import com.lck.util.PageInfo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
    public PatientContrllor(PatientRepository patientRepository, FileUtils fileUtils, PatientDesRepository patientDesRepository,
                            ConvertUtil convertUtil, AdviceDrugRepository adviceDrugRepository) {
        this.patientRepository = patientRepository;
        this.fileUtils = fileUtils;
        this.patientDesRepository = patientDesRepository;
        this.convertUtil = convertUtil;
        this.adviceDrugRepository = adviceDrugRepository;
    }

    //患者列表页面
    @GetMapping("/patients")
    public String login(
            @RequestParam(name = "username" ,required = false) String name,
            @RequestParam(name = "pageCurrent", required = false, defaultValue = "1") Integer pageCurrent,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            Model model
    ) {
        Page<Patient> patientList = findByPageAndParams(name,pageCurrent,pageSize);
        model.addAttribute("patients", patientList.getContent());
        model.addAttribute("pageInfo",
                new PageInfo<>().setPageCurrent(pageCurrent).setPageSize(pageSize)
                        .setTotalCount((int) patientList.getTotalElements()).setTotalPage(patientList.getTotalPages()));
        return "/patients/list";
    }

    private Page<Patient> findByPageAndParams(String name, Integer pageCurrent, Integer pageSize) {
        Specification<Patient> specification=null;
        if(!StringUtils.isEmpty(name)){
            //查询条件存在这个对象中
            specification = new Specification<Patient>() {
                //重新Specification的toPredicate方法
                @Override
                public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    //我要模糊查询的字段是PatientName
                    Path username = root.get("username");
                    //criteriaBuilder.like模糊查询，第一个参数是上一行的返回值，第二个参数是like('%xxx%')中，xxx的值
                    Predicate predicate = criteriaBuilder.like(username, "%" + name + "%");
                    return predicate;
                }
            };
        }
        //分页条件存在这个对象中
        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize);

        //进行查询操作，第一个参数是查询条件对象，第二个参数是分页对象
        Page<Patient> page;
        page = patientRepository.findAll(specification, pageRequest);

        //返回的数据都封装在了Page<Patient>对象中

        return page;
    }


    //根据名字查询
    @GetMapping("/findByName")
    public String findByName(
            @RequestParam(name = "username") String name,
            @RequestParam(name = "pageCurrent",required = false,defaultValue = "1") Integer pageCurrent,
            @RequestParam(name = "pageSize",required = false,defaultValue = "3") Integer pageSize,
            Model model
    ) {
        Page<Patient> patientList = findByPageAndParams(name,pageCurrent,pageSize);
        model.addAttribute("patients", patientList.getContent());
        model.addAttribute("pageInfo",
                new PageInfo<>().setPageCurrent(pageCurrent).setPageSize(pageSize)
                        .setTotalCount((int) patientList.getTotalElements()).setTotalPage(patientList.getTotalPages()));
        return "/patients/list";
    }

    //删除
    @GetMapping("/delPatient/{id}")
    public String delPatient(
            @PathVariable Integer id,
            Model model
    ) {
        Patient patient = patientRepository.findById(id).get();
        patientRepository.deleteById(id);
        patientDesRepository.delPatientDes(patient.getNumber());
//        model.addAttribute("patients", patientRepository.findAll());
        return "/patients/list";
    }

    //添加患者基础信息
    @PostMapping("/addPatient")
    public String addPatient(
            Patient patient,
            Model model
    ) {
        Patient save = patientRepository.save(patient);
        if (save != null) {
            model.addAttribute("patients", patientRepository.findAll());
            return "redirect:/list.html";
        } else {
            return "/patients/add";
        }
    }

    //添加患者基础信息
    @PostMapping("/addPatientDes")
    public String addPatientDes(
            PatientDes patientDes,
            Model model
    ) {
        PatientDes des = patientDesRepository.findByNumber(patientDes.getNumber());
        if (des != null) {
            PatientDes combineBean = combineSydwCore(des, patientDes);
            patientDesRepository.save(combineBean.setNumber(des.getNumber()).setId(des.getId()));
            model.addAttribute("msg", "追加录入跟踪数据成功");
            return "/patients/add";
        } else {
            PatientDes save = patientDesRepository.save(patientDes);
            if (save != null) {
                model.addAttribute("msg", "添加录入跟踪数据成功");
                return "/patients/add";
            } else {
                model.addAttribute("msg", "录入跟踪数据失败,请检查患者编号");
                return "/patients/add";
            }
        }
    }


    private PatientDes combineSydwCore(PatientDes source, PatientDes target) {
        Class sourceBeanClass = source.getClass();
        Class targetBeanClass = target.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            if (Modifier.isStatic(sourceField.getModifiers())) {
                continue;
            }
            Field targetField = targetFields[i];
            if (Modifier.isStatic(targetField.getModifiers())) {
                continue;
            }
            if (targetField.getName().equals("id"))
                continue;
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(source) == null) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                    targetField.set(target, sourceField.get(source) + "" + targetField.get(target));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return target;
    }


    //详情信息
    @GetMapping("/toDetail/{number}")
    private String toDetail(
            @PathVariable String number,//编号
            Model model
    ) {
        PatientDes patientDes = patientDesRepository.findByNumber(number);
        Patient patient = patientRepository.findByPatientNumber(patientDes.getNumber());
        if(patientDes==null){
            model.addAttribute("msg", "暂无跟踪信息");
            model.addAttribute("maps", new HashMap<>());
            model.addAttribute("user", patient);
            return "/patients/detail";
        }
        List<Map<String, String>> maps = convertToMap(patientDes);

        model.addAttribute("maps", maps);
        model.addAttribute("user", patient);
        return "/patients/detail";
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
            return "/patients/advice";
        } else {
            model.addAttribute("adviceDrugs", adviceDrugs);
            return "/patients/advice";
        }
    }

    private List<Map<String, String>> convertToMap(PatientDes patientDes) {
        Map<String, Object> stringObjectMap = convertUtil.entityToMap(patientDes);
        StringBuilder sb = new StringBuilder();
        stringObjectMap.forEach((k, v) -> {
            sb.append(k + "," + v + ";");
        });
        String[] values = sb.toString().split("\\;");
        List<Map<String, String>> ListResult = new LinkedList<>();
        for (int i = 0; i < values.length; i++) {
            Map<String, String> result = new TreeMap<>();
            String[] items = values[i].split("\\,");
            int k = 0;
            for (int j = 0; j < items.length; j++) {
                result.put("V" + (k++), getMappingField(items[j]) != null ? getMappingField(items[j]) : items[j]);
            }
            ListResult.add(result);
        }
        return ListResult;
    }

    private String getMappingField(String key) {
        Map<String, String> mapTable = new HashMap<>();
        mapTable.put("weightV", "体重");
        mapTable.put("fatV", "腹内脂肪");
        mapTable.put("acidV", "尿酸");
        mapTable.put("skinFatV", "皮下脂肪");
        mapTable.put("pressureV", "收缩压");
        mapTable.put("disPressureV", "舒张压");
        mapTable.put("bodyIndexV", "体重指数");
        mapTable.put("hiplineV", "臀围");
        mapTable.put("hipRatioV", "腰臀比");
        mapTable.put("waistV", "腰围");
        mapTable.put("followUpTimeV", "随访时间");
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


        if (StringUtils.isEmpty(fileName)) {
            model.addAttribute("msg", "不能上传空文件");
            return "/patients/filedata";
        }
        String[] split = fileName.split("\\.");
        if (!"csv".equals(split[1])) {
            model.addAttribute("msg", "请上传CSV格式文件");
            return "/patients/filedata";
        }
        String result;
        //上传文件
        if ("template.csv".equals(fileName)) {
            result = fileUtils.uploadFileCsv(patientFile);
        } else if ("advice.csv".equals(fileName)) {
            result = fileUtils.uploadAdviceCsv(patientFile);
        } else {
            model.addAttribute("msg", "请上传文件名称为template.csv或者advice.csv的文件");
            return "/patients/filedata";
        }

        model.addAttribute("msg", result);
        return "/patients/filedata";
    }

    //下载模板文件
    @GetMapping("/fileTemp")
    public void downloadFile(
            @RequestParam(name = "fileType") String type,
            HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException {
        //得到要下载的文件名
        String fileName = null;
        if ("advice".equals(type)) {
            fileName = "advice.csv";
        } else {
            fileName = "template.csv";
        }
        //上传的文件都是保存在D:\fileupload\目录下的子目录当中
        String fileSaveRootPath = "D:\\fileupload\\";
        //得到要下载的文件
        File file = new File(fileSaveRootPath + "\\" + fileName);
        //如果文件不存在
        if (!file.exists()) {
            model.addAttribute("msg", "您要下载的资源已被删除！！");
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_") + 1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();

    }

}
