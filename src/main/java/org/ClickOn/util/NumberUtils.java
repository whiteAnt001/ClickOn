package org.ClickOn.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component("numberUtils")
public class NumberUtils {
    public String formatWithComma(Number number) {
        if(number == null){
            return "";
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }
}
