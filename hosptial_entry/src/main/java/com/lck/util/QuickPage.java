package com.lck.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuickPage {
    private String jumpPage;
    private String pageDes;
}
