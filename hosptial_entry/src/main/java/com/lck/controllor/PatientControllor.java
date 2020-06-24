package com.lck.controllor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.model.PatientDes;
import com.lck.model.VModel;
import com.lck.model.vo.ResponseVo;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientDesRepository;
import com.lck.repository.PatientRepository;
import com.lck.util.ConvertUtil;
import com.lck.util.FileUtils;
import com.lck.util.PageInfo;
import com.lck.util.QuickPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/patient")
public class PatientControllor {
    private PatientRepository patientRepository;
    private FileUtils fileUtils;
    private PatientDesRepository patientDesRepository;
    private ConvertUtil convertUtil;
    private AdviceDrugRepository adviceDrugRepository;

    private static final String[] CSV_HEADERS = {"编号", "姓名", "性别", "年龄", "糖尿病", "糖尿病病程", "高血压", "血脂异常", "手术方式", "修正手术", "首次手术", "手术日期", "住院号", "随访时间V", "PV", "CaV", "MgV", "NaV", "KV", "PTHV", "OH25DV", "CRPV", "FT3V", "FT4V", "TSHV", "FFAV", "ALPV", "ALTV", "ASTV", "BUNV", "CP0V", "CP120V", "INS0V", "INS120V", "ChV", "CrV", "FBGV", "HDLV", "HOMABV", "HOMAIRV", "HbV", "HbA1cV", "LDLV", "PBGV", "TGV", "WBCV", "rGTV", "GAV", "体重V", "腹内脂肪V", "尿酸V", "皮下脂肪V", "收缩压V", "舒张压V", "体重指数V", "臀围V", "腰臀比V", "腰围V"};


    @Autowired
    public PatientControllor(PatientRepository patientRepository, FileUtils fileUtils, PatientDesRepository patientDesRepository,
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
            @RequestParam(name = "username", required = false) String name,
            @RequestParam(name = "pageCurrent", required = false, defaultValue = "1") Integer pageCurrent,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            Model model
    ) {
        Page<Patient> patientList = findByPageAndParams(name, pageCurrent, pageSize);
        model.addAttribute("patients", patientList.getContent());
        model.addAttribute("pageInfo",
                new PageInfo<>().setPageCurrent(pageCurrent).setPageSize(pageSize)
                        .setTotalCount((int) patientList.getTotalElements())
                        .setTotalPage(patientList.getTotalPages())
                        .setQuickMap(createMap(patientList.getTotalPages())));
        return "/patients/list";
    }

    private Page<Patient> findByPageAndParams(String name, Integer pageCurrent, Integer pageSize) {
        Specification<Patient> specification = null;
        if (!StringUtils.isEmpty(name)) {
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
            @RequestParam(name = "pageCurrent", required = false, defaultValue = "1") Integer pageCurrent,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            Model model
    ) {
        Page<Patient> patientList = findByPageAndParams(name, pageCurrent, pageSize);
        model.addAttribute("patients", patientList.getContent());
        model.addAttribute("pageInfo",
                new PageInfo<>().setPageCurrent(pageCurrent).setPageSize(pageSize)
                        .setTotalCount((int) patientList.getTotalElements())
                        .setTotalPage(patientList.getTotalPages())
                        .setQuickMap(createMap(patientList.getTotalPages())));

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
        Page<Patient> patientList = findByPageAndParams(null, 1, 3);
        model.addAttribute("patients", patientList.getContent());
        model.addAttribute("pageInfo",
                new PageInfo<>().setPageCurrent(1).setPageSize(3)
                        .setTotalCount((int) patientList.getTotalElements())
                        .setTotalPage(patientList.getTotalPages())
                        .setQuickMap(createMap(patientList.getTotalPages())));

        return "/patients/list";
    }

    private List<QuickPage> createMap(int totalPage) {
        List<QuickPage> result = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            result.add(new QuickPage().setPageDes("第" + (i + 1) + "页").setJumpPage(String.valueOf(i + 1)));
        }
        return result;
    }

    //添加患者基础信息
    @PostMapping("/addPatient")
    public String addPatient(
            Patient patient,
            Model model
    ) {
        Patient save = patientRepository.save(patient);
        if (save != null) {
            Page<Patient> patientList = findByPageAndParams(null, 1, 3);
            model.addAttribute("patients", patientList.getContent());
            model.addAttribute("pageInfo",
                    new PageInfo<>().setPageCurrent(1).setPageSize(3)
                            .setTotalCount((int) patientList.getTotalElements())
                            .setTotalPage(patientList.getTotalPages())
                            .setQuickMap(createMap(patientList.getTotalPages())));

            return "/patients/list";
        } else {
            return "/patients/add";
        }
    }

    //    //添加患者跟踪信息
//    @PostMapping("/addPatientDes")
//    public String addPatientDes(
//            PatientDes patientDes,
//            Model model
//    ) {
//        PatientDes des = patientDesRepository.findByNumber(patientDes.getNumber());
//        //已经有跟踪信息的追加
//        if (des != null) {
//            PatientDes combineBean = combineSydwCore(des, patientDes);
//            patientDesRepository.save(combineBean.setNumber(des.getNumber()).setId(des.getId()));
//            model.addAttribute("msg", "追加录入跟踪数据成功");
//            return "/patients/addDes";
//        }//没有追踪信息的直接添加
//        else {
//            PatientDes save = patientDesRepository.save(patientDes);
//            if (save != null) {
//                model.addAttribute("msg", "添加录入跟踪数据成功");
//                return "/patients/addDes";
//            } else {
//                model.addAttribute("msg", "录入跟踪数据失败,请检查患者编号");
//                return "/patients/addDes";
//            }
//        }
//    }
    //添加患者跟踪信息
    @PostMapping("/addPatientDes")
    public String addPatientDes(
            PatientDes patientDes,
            Model model
    ) {
        PatientDes des = patientDesRepository.findByNumber(patientDes.getNumber());
        //追加
        if (des != null) {
            PatientDes patientUpdate = patientDesRepository.save(patientDes);
            model.addAttribute("msg", "添加录入跟踪数据成功");
            return "/patients/addDes";
        }//第一次添加
        else {
            assert false;
            Field[] declaredFields = des.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();//属性名字
                if("id".equals(name)||"number".equals(name))
                    continue;
                name = name.substring(0, 1).toUpperCase() + name.substring(1);//属性首字母大写
                Method m = null;
                try {
                    m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model);
                    m = model.getClass().getMethod("set"+name,String.class);
                    //获取第一个值 然后拼接
                    m.invoke(model, value+",,,,,,,,,,,,,,");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            patientDesRepository.save(patientDes);
            model.addAttribute("msg", "添加录入跟踪数据成功");
            return "/patients/addDes";
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
        Patient patient = patientRepository.findByPatientNumber(number);
        if (patientDes == null) {
            model.addAttribute("msg", "暂无跟踪信息");
            model.addAttribute("maps", null);
            model.addAttribute("user", patient);
            return "/patients/detail";
        }
        List<Map<String, String>> maps = convertToMap(patientDes);

        model.addAttribute("maps", maps);
        model.addAttribute("user", patient);
        return "/patients/detail";
    }

    //跳转修改页面
    @GetMapping("/toEdit")
    public String editItem(HttpServletRequest request,
                           Model model) {
        String vMode = request.getParameter("params");
        String substring = vMode.substring(1, vMode.length() - 1);
        String[] split = substring.split(",");
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split("=");
            String value = split1.length < 2 ? "" : split1[1];
            result.put(split1[0].trim(), value.trim());
        }
        String number = request.getParameter("number");
        result.put("number", number);
        VModel vModel = JSON.parseObject(JSON.toJSONString(result), VModel.class);
        model.addAttribute("vModel", vModel);
        model.addAttribute("patient", patientRepository.findByPatientNumber(number));
        return "/patients/delEdit";
    }

    //数据V值修改
    @PostMapping("/editPatient")
    private String editPatient(VModel vModel, Model model) {
        if (vModel == null) {
            model.addAttribute("msg", "无修改V值");
            return "/patient/delEdit";
        }
        PatientDes patientDes = patientDesRepository.findByNumber(vModel.getNumber());
        StringBuilder sb = new StringBuilder();
        sb.append(vModel.getV1())
                .append(",").append(vModel.getV2())
                .append(",").append(vModel.getV3())
                .append(",").append(vModel.getV4())
                .append(",").append(vModel.getV5())
                .append(",").append(vModel.getV6())
                .append(",").append(vModel.getV7())
                .append(",").append(vModel.getV8())
                .append(",").append(vModel.getV9())
                .append(",").append(vModel.getV10())
                .append(",").append(vModel.getV11())
                .append(",").append(vModel.getV12())
                .append(",").append(vModel.getV13())
                .append(",").append(vModel.getV14())
                .append(",").append(vModel.getV15());

        try {
            //属性
            String field = getMappingField1(vModel.getV0()) != null ? getMappingField1(vModel.getV0()) : vModel.getV0();

            Field fieldDec = patientDes.getClass().getDeclaredField(field);
            fieldDec.setAccessible(true);
            fieldDec.set(patientDes, sb.toString());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        patientDesRepository.save(patientDes);


        model.addAttribute("user", patientRepository.findByPatientNumber(vModel.getNumber()));

        List<Map<String, String>> maps = convertToMap(patientDes);

        model.addAttribute("maps", maps);

        return "/patients/detail";
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
        File file = new File(fileSaveRootPath);
        //如果文件不存在
        if (!file.exists()) {
            model.addAttribute("msg", "您要下载的资源已被删除！！");
        }
        //处理文件名
//        String realname = fileName.substring(fileName.indexOf("_") + 1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
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


    //导出数据
    @GetMapping("/exportRecFile")
    @ResponseBody
    public String exportRecFile(HttpServletResponse response) {
        List<Patient> PatientList = patientRepository.findAll();
        List<ResponseVo> result = new LinkedList<>();
        for (Patient patient : PatientList) {
            ResponseVo vo = new ResponseVo();
            PatientDes patientDes = patientDesRepository.findByNumber(patient.getNumber());
            if (patientDes == null)
                continue;
            BeanUtils.copyProperties(patient, vo);
            BeanUtils.copyProperties(patientDes, vo);
            result.add(vo);
        }
        String path = createPath();
        FileUtils.writeCSV(result, path, CSV_HEADERS);

        try {
            //设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("csv_export.csv", "UTF-8"));

            //读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(path);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/patients/filedata";
    }

    //跳转添加V值页面
    @GetMapping("/addDes/{number}")
    private String toAddDes(
            @PathVariable String number,
            Model model
    ) {
        Patient patient = patientRepository.findByPatientNumber(number);
        PatientDes patientDes = patientDesRepository.findByNumber(number);
        model.addAttribute("patient", patient);
        model.addAttribute("patientDes", patientDes);
        return "/patients/addDes";
    }

    private String createPath() {
        String filepath = "D:\\csv_export\\";
        File dirfile = new File(filepath);
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }
        String fileName = "csv_export.csv"; //  csv_export.csv
        return filepath + fileName;
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

            Map<String, String> result = new TreeMap<>(Comparator.comparingInt(a -> Integer.parseInt(a.substring(1))));
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

    private String getMappingField1(String key) {
        Map<String, String> mapTable = new HashMap<>();
        mapTable.put("体重", "weightV");
        mapTable.put("腹内脂肪", "fatV");
        mapTable.put("尿酸", "acidV");
        mapTable.put("皮下脂肪", "skinFatV");
        mapTable.put("收缩压", "pressureV");
        mapTable.put("舒张压", "disPressureV");
        mapTable.put("体重指数", "bodyIndexV");
        mapTable.put("臀围", "hiplineV");
        mapTable.put("腰臀比", "hipRatioV");
        mapTable.put("腰围", "waistV");
        mapTable.put("随访时间", "followUpTimeV");
        return mapTable.get(key);
    }
}
