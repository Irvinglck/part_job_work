package com.lck;

import com.csvreader.CsvWriter;
import com.lck.model.PatientDes;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainTest {
    public static void main1(String[] args) {
        List<Map<String,Object>> demoList = createDemoList();
        String filepath = "D:/WL2HC/";
        File dirfile = new File(filepath);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        String date = new SimpleDateFormat("yyyyMMdd").format(time);
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }
        String fileName = "CLUEDATA" + date + ".csv"; //  DATACLUE20190821.csv
        String localFileName = filepath + fileName;

        writeCSV(demoList, localFileName, csvHeaders);
    }

    public static void main(String[] args) {

        PatientDes model=new PatientDes();

        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,String.class);
                        m.invoke(model, "");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(model, 0);
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Boolean.class);
                        m.invoke(model, false);
                    }
                }
                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Date.class);
                        m.invoke(model, new Date());
                    }
                }// 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


   String[] str= {"编号", "姓名", "性别", "年龄", "糖尿病", "糖尿病病程", "高血压", "血脂异常", "手术方式", "修正手术", "手术日期", "住院号", "随访时间V", "PV", "CaV", "MgV", "NaV", "KV", "PTHV", "OH25DV", "CRPV", "FT3V", "FT4V", "TSHV", "FFAV", "ALPV", "ALTV", "ASTV", "BUNV", "CP0V", "CP120V", "INS0V", "INS120V", "ChV", "CrV", "FBGV", "HDLV", "HOMABV", "HOMAIRV", "HbV", "HbA1cV", "LDLV", "PBGV", "TGV", "WBCV", "rGTV", "GAV", "体重V", "腹内脂肪V", "尿酸V", "皮下脂肪V", "收缩压V", "舒张压V", "体重指数V", "臀围V", "腰臀比V", "腰围V"};

    static String[] csvHeaders = {"ID", "DATE", "姓名"};

    //    private static List<Demo> createDemoList() {
//        List<Demo> result = new ArrayList<>();
//        result.add(new Demo().setId(1).setDate("javacsv").setName("大师兄"));
//        result.add(new Demo().setId(2).setDate("javacsv1").setName("大师兄2"));
//        result.add(new Demo().setId(3).setDate("javacsv2").setName("大师兄3"));
//        result.add(new Demo().setId(4).setDate("javacsv3").setName("大师兄4"));
//        result.add(new Demo().setId(5).setDate("javacsv4").setName("大师兄5"));
//        return result;
//
//    }
    private static List<Map<String,Object>> createDemoList() {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        map1.put("id",1);
        map1.put("daaa","javacsv");
        map1.put("username","詹姆斯");
        Map<String,Object> map2=new HashMap<>();
        map2.put("id",1);
        map2.put("daaa","javacsv");
        map2.put("username","詹姆斯");
        Map<String,Object> map3=new HashMap<>();
        map3.put("id",1);
        map3.put("daaa","javacsv");
        map3.put("username","詹姆斯");
        Map<String,Object> map4=new HashMap<>();
        map4.put("id",1);
        map4.put("daaa","javacsv");
        map4.put("username","詹姆斯");
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        return result;

    }

    @Data
    @Accessors(chain = true)
    private static class Demo {
        private Integer id;
        private String date;
        private String name;
    }

    public static <T> void writeCSV(Collection<T> list, String csvFilePath, String[] csvHeaders) {
        try {
            // 定义路径，分隔符，编码
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("gbk"));
            // 写表头
            csvWriter.writeRecord(csvHeaders);
            // 写内容
            //遍历集合
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                T t = (T) it.next();
                //获取类属性
                Field[] fields = t.getClass().getDeclaredFields();
                String[] csvContent = new String[fields.length];
                for (short i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    try {
                        Class tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});
                        if (value == null) {
                            continue;
                        }
                        //取值并赋给数组
                        String textvalue = value.toString();
                        csvContent[i] = textvalue;
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                //迭代插入记录
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            System.out.println("<--------CSV文件写入成功-------->");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
