package com.lck;

import com.lck.model.Patient;
import com.lck.model.PatientDes;
import com.lck.repository.PatientDesRepository;
import com.lck.repository.PatientRepository;
import com.lck.util.ConvertUtil;
import com.lck.util.VariableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HosptialEntryApplicationTests {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private PatientDesRepository patientDesRepository;

    @Test
    public void contextLoads() {
        PatientDes patientDes = patientDesRepository.findByNumber("DS001");
        Map<String, Object> stringObjectMap = convertUtil.entityToMap(patientDes);
        StringBuilder sb=new StringBuilder();
        stringObjectMap.forEach((k,v)->{
            sb.append(k+","+v+";");
        });
        String[] values = sb.toString().split("\\;");
        List<Map<String,String>> ListResult=new LinkedList<>();
        for (int i = 0; i <values.length ; i++) {
            Map<String,String> result=new HashMap<>();
            String[] items = values[i].split("\\,");
            int k=0;
            for (int j = 0; j < items.length; j++) {
                result.put("V"+(k++),items[j]);
            }
            ListResult.add(result);
//            System.out.println(values[i]);
        }
        System.out.println(ListResult.size()+"------------------");
        ListResult.forEach(item->{
            item.forEach((k,v)->{
                System.out.println(k+"----------"+v);
            });
        });
    }

    List<Patient> mockData(){
        List<Patient> resultList=new ArrayList<>();
        resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
            resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
           resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
            resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setRoomId("23423423"));


        return resultList;
    }
}
