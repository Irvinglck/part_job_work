package com.lck.util;

import com.csvreader.CsvReader;
import com.lck.model.AdviceDrug;
import com.lck.model.Patient;
import com.lck.model.PatientDes;
import com.lck.repository.AdviceDrugRepository;
import com.lck.repository.PatientDesRepository;
import com.lck.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FileUtils {
    private PatientRepository patientRepository;
    private PatientDesRepository patientDesRepository;
    private AdviceDrugRepository adviceDrugRepository;
    @Autowired
    public FileUtils(PatientRepository patientRepository,PatientDesRepository patientDesRepository,AdviceDrugRepository adviceDrugRepository) {
        this.patientRepository = patientRepository;
        this.patientDesRepository=patientDesRepository;
        this.adviceDrugRepository=adviceDrugRepository;
    }

    public String uploadFileCsv(MultipartFile template) {
        BufferedReader br;
        PatientDes save1 = null;
        try {
            br = new BufferedReader(new InputStreamReader(template.getInputStream(), "GBK"));
            CsvReader csvReader = new CsvReader(br, ',');
            csvReader.readHeaders();

            while (csvReader.readRecord()) {
                String number = csvReader.get("编号");
                String name = csvReader.get("姓名");
                String gender = csvReader.get("性别");
                String age = csvReader.get("年龄");
                String isDiabetes = csvReader.get("糖尿病");
                String diaAge = csvReader.get("糖尿病病程");
                String highBlood = csvReader.get("高血压");
                String boolFat = csvReader.get("血脂异常");
                String opraType = csvReader.get("手术方式");
                String reverseOpra = csvReader.get("修正手术");
                String isFirstOpra = csvReader.get("首次手术");
                String opraTime = csvReader.get("手术日期");
                String roomId = csvReader.get("住院号");


                Patient pat = patientRepository.findByPatientNumber(number);
                if(pat!=null){

                    continue;
                }

                //添加患者基本信息
                Patient patient = new Patient().setNumber(number).setUsername(name).setGender(gender)
                        .setAge(age).setIsDiabetes(isDiabetes).setDiaAge(diaAge).setHighBlood(highBlood)
                        .setBoolFat(boolFat).setOpraType(opraType).setReverseOpra(reverseOpra).setIsFirstOpra(isFirstOpra)
                        .setOpraTime(opraTime).setRoomId(roomId);
                Patient save = patientRepository.save(patient);

                String afterTime = csvReader.get("随访时间V");
                String PV = csvReader.get("PV");
                String CaV = csvReader.get("CaV");
                String MgV = csvReader.get("MgV");
                String NaV = csvReader.get("NaV");
                String KV = csvReader.get("KV");
                String PTHV = csvReader.get("PTHV");
                String OH25DV = csvReader.get("OH25DV");
                String CRPV = csvReader.get("CRPV");
                String FT3V = csvReader.get("FT3V");
                String FT4V = csvReader.get("FT4V");
                String TSHV = csvReader.get("TSHV");
                String FFAV = csvReader.get("FFAV");
                String ALPV = csvReader.get("ALPV");
                String ALTV = csvReader.get("ALTV");
                String ASTV = csvReader.get("ASTV");
                String BUNV = csvReader.get("BUNV");
                String CP0V = csvReader.get("CP0V");
                String CP120V = csvReader.get("CP120V");
                String INS0V = csvReader.get("INS0V");
                String INS120V = csvReader.get("INS120V");
                String ChV = csvReader.get("ChV");
                String CrV = csvReader.get("CrV");
                String FBGV = csvReader.get("FBGV");
                String HDLV = csvReader.get("HDLV");
                String HOMABV = csvReader.get("HOMABV");
                String HOMAIRV = csvReader.get("HOMAIRV");
                String HbV = csvReader.get("HbV");
                String HbA1cV = csvReader.get("HbA1cV");
                String LDLV = csvReader.get("LDLV");
                String PBGV = csvReader.get("PBGV");
                String TGV = csvReader.get("TGV");
                String WBCV = csvReader.get("WBCV");
                String rGTV = csvReader.get("rGTV");
                String GAV = csvReader.get("GAV");
                String weightV = csvReader.get("体重V");
                String fatV = csvReader.get("腹内脂肪V");
                String acidV = csvReader.get("尿酸V");
                String skinFatV = csvReader.get("皮下脂肪V");
                String pressureV = csvReader.get("收缩压V");
                String disPressureV = csvReader.get("舒张压V");
                String bodyIndexV = csvReader.get("体重指数V");
                String hiplineV = csvReader.get("臀围V");
                String hipRatioV = csvReader.get("腰臀比V");
                String waistV = csvReader.get("腰围V");
                //添加患者描述信息
                PatientDes patientDes = new PatientDes();
                patientDes.setNumber(number).setFollowUpTimeV(afterTime).setPV(PV).setCaV(CaV).
                        setMgV(MgV).setNaV(NaV).setKV(KV).setPTHV(PTHV).setOH25DV(OH25DV).
                        setCRPV(CRPV).setFT3V(FT3V).setFT4V(FT4V).setTSHV(TSHV).setFFAV(FFAV).
                        setALPV(ALPV).setALTV(ALTV).setASTV(ASTV).setBUNV(BUNV).setCP0V(CP0V).
                        setCP120V(CP120V).setINSOV(INS0V).setINS120V(INS120V).setChV(ChV).
                        setCrV(CrV).setFBGV(FBGV).setHDLV(HDLV).setHOMABV(HOMABV).setHOMAIRV(HOMAIRV).
                        setHbV(HbV).setHbA1cV(HbA1cV).setLDLV(LDLV).setPBGV(PBGV).setTGV(TGV).
                        setWBCV(WBCV).setRGTV(rGTV).setGAV(GAV).setWeightV(weightV).setFatV(fatV).
                        setAcidV(acidV).setSkinFatV(skinFatV).setPressureV(pressureV).setDisPressureV(disPressureV).
                        setBodyIndexV(bodyIndexV).setHiplineV(hiplineV).setHipRatioV(hipRatioV).setWaistV(waistV);

                save1= patientDesRepository.save(patientDes);
            }
            csvReader.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return save1!=null?"入库成功":"入库失败";
    }
    public String uploadAdviceCsv(MultipartFile template) {
        BufferedReader br;
        AdviceDrug save=null;
        try {
            br = new BufferedReader(new InputStreamReader(template.getInputStream(), "GBK"));
            CsvReader csvReader = new CsvReader(br, ',');
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String number = csvReader.get("编号");
                String name = csvReader.get("姓名");
                String method = csvReader.get("用药方案V");
                String InsulinQuaType = csvReader.get("胰岛素类");
                String mouthDrugQuaType = csvReader.get("口服降糖药类");
                String downBloodQuaType = csvReader.get("降压药");
                String regulatingDrugsQuaType = csvReader.get("调脂类药");
                String elseDrugs = csvReader.get("其它药物");
                String des = csvReader.get("备注说明");

                AdviceDrug adviceDrug=new AdviceDrug().setNumber(number)
                        .setName(name).setMethod(method)
                        .setInsulinQuaType(InsulinQuaType)
                        .setMouthDrugQuaType(mouthDrugQuaType)
                        .setDownBloodQuaType(downBloodQuaType)
                        .setRegulatingDrugsQuaType(regulatingDrugsQuaType)
                        .setElseDrugs(elseDrugs).setDes(des);

               save = adviceDrugRepository.save(adviceDrug);
            }
            csvReader.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return save!=null?"入库成功":"入库失败";
    }
}
