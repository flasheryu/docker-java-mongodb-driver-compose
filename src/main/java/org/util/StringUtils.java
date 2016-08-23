package org.util;

import java.util.List;


public class StringUtils {
  
  public static final String EMPTY_STRING = "";
  
  public static boolean isNullOrWhiteSpaces(String s) {
    return s == null || s.trim().isEmpty();
  }

  public static String getNonEmptyString(String value, String defaultValue) {
    return isNullOrWhiteSpaces(value) ? defaultValue : value;
  }
  
  public static String parseNullToEmpty(String stringObj){
    if(stringObj == null){
      return EMPTY_STRING;
    }else{
      return stringObj;
    }
  }
  
    public static String joinString(String delimitor, List<String> strs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.size(); i++) {
            sb.append(strs.get(i));
            if (i != strs.size() - 1)
                sb.append(delimitor);
        }

        return sb.toString();
    }
}