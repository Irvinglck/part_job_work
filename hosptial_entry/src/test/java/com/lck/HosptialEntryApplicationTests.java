package com.lck;

import com.lck.util.VariableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HosptialEntryApplicationTests {
    @Autowired
    private VariableUtil variableUtil;

    @Test
    public void contextLoads() {
        String sqlitePath = variableUtil.getSqlitePath();
        System.out.println(sqlitePath);
    }

}
