package com.tgi.med3d.utility;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.log4j.Log4j2;

@Log4j2
public class Library {
	

	public static Float getFloatValue(String strValue) {
		if (strValue != null && !strValue.equals("")) {
			try {
				return Float.parseFloat(strValue.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return 0f;
	}
	public static Long getLongValue(String strValue) {
		if (strValue != null && !strValue.equals("")) {
			try {
				return Long.parseLong(strValue.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return 0l;
	}

	public static int getIntValue(String strValue) {
		if (strValue != null && !strValue.equals("")) {
			try {
				return Integer.parseInt(strValue.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return 0;
	}

	public static String trimAndRemoveSpecialCharacter(String str) throws Exception {
		if (str != null && !str.equals("")) {
			str = str.trim();
		}
		if (str != null) {
			str = str.replaceAll("'", "");
		}
		return str;
	}

	public static String toLowerCase(String str) throws Exception {
		if (str != null && !str.equals("")) {
			str = str.toLowerCase().trim();
		}
		return str;
	}

	public static GenericResponse getSuccessfulResponse(Object obj,int erorrCode,String msg)  {
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(obj);
		GenericResponse.setStatusCode(erorrCode);
		GenericResponse.setStatus("Success");
		GenericResponse.setMessage("Success");
		GenericResponse.setMessage(msg);
		return GenericResponse;
	}
	public static GenericResponse noRecordFoundResponse(String msg)  {
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(null);
		GenericResponse.setStatusCode(0);
		GenericResponse.setStatus("Success");
		GenericResponse.setMessage("Success");
		GenericResponse.setMessage(msg);
		return GenericResponse;
	}
	public static GenericResponse getFailResponseCode(int erorrCode,String strMsg){
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(null);
		GenericResponse.setStatusCode(erorrCode);
		GenericResponse.setStatus("Failed");
		GenericResponse.setMessage("Failed");
		GenericResponse.setMessage(strMsg);
		return GenericResponse;
	}
	public static String getObjectToJson(Object object) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = Obj.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	public static HttpHeaders getHeader(Map<String, String> headers) {
		HttpHeaders headersObj = new HttpHeaders();
		Iterator <String> it = headers.keySet().iterator();     
		while(it.hasNext())  
		{  
			String key=(String)it.next();
			String value=(String)headers.get(key);
			headersObj.set(key, value);
		} 
		return headersObj;
	}
	
	public static boolean isNullOrEmpty(String str) { 
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	
}
