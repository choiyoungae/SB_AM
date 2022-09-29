package com.cya.exam.demo.util;

public class Ut {

	public static boolean isEmpty(Object obj) {
		
		if(obj == null) {
			return true;
		}
		if(obj instanceof String == false) {
			return true;
		}
		
		String str = (String)obj;
		
		return str.trim().length() == 0;
	}

}
