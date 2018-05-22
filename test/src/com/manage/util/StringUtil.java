package com.manage.util;

public class StringUtil {
	
	public static boolean isBlank(String str){
		if(str != null && !str.trim().isEmpty()&& !"null".equals(str)&&!"".equals(str))
			return false;
		return true;
	}
	
	public static boolean isBlank(Object obj){
		String value = String.valueOf(obj);
		return isBlank(value);
	}
	
	/** 
     * 对象属性转换为字段  例如：userName to user_name 
     * @param property 字段名 
     * @return 
     */  
    public static String propertyToField(String property) {  
        if (null == property) {  
            return "";  
        }  
        char[] chars = property.toCharArray();  
        StringBuffer sb = new StringBuffer();  
        for (char c : chars) {  
            if (Character.isUpperCase(c)) {  
                sb.append("_" + Character.toLowerCase(c));  
            } else {  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 字段转换成对象属性 例如：user_name to userName 
     * @param field 
     * @return 
     */  
    public static String fieldToProperty(String field) {  
        if (null == field) {  
            return "";  
        }  
        char[] chars = field.toCharArray();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < chars.length; i++) {  
            char c = chars[i];  
            if (c == '_') {  
                int j = i + 1;  
                if (j < chars.length) {  
                    sb.append(Character.toUpperCase(chars[j]));  
                    i++;  
                }  
            } else {  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    } 
}
