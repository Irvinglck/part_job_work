package com.lck.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class VariableUtil {
    @Value("${sqlite.path.url}")
    private String sqlitePath;
}
