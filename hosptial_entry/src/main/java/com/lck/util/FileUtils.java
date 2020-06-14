package com.lck.util;

import com.csvreader.CsvReader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FileUtils {

    public  void uploadFileCsv(MultipartFile template) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(template.getInputStream(), "GBK"));
            CsvReader csvReader = new CsvReader(br, ',');
            csvReader.readHeaders();
            int i=1;
            while (csvReader.readRecord()) {
                String name = csvReader.get("姓名");
                String time = csvReader.get("随访时间V"+i++);
                System.out.println(name);
                System.out.println(time+i);
            }
            csvReader.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
