package com.lck;

import com.lck.model.PatientDes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        PatientDes p1=new PatientDes().setNumber("123");
        PatientDes p2=new PatientDes().setNumber("678");
        PatientDes patientDes = combineSydwCore(p1, p2);
        System.out.println(patientDes);
    }


    private static PatientDes combineSydwCore(PatientDes patientDes, PatientDes des) {
        Class sourceBeanClass = patientDes.getClass();
        Class targetBeanClass = des.getClass();

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
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(patientDes) == null) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                    targetField.set(des, sourceField.get(patientDes)+""+targetField.get(des));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return des;
    }
}
