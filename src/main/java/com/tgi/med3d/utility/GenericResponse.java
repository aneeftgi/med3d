package com.tgi.med3d.utility;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/** e@Datarror code. 0 if success else unique error code value */


	@Override
	public String toString() {
		return "GenericResponse [status=" + status + ", errorDescription=" + description + ", errorCode="
				+ statusCode + ", userDisplayMesg=" + message + ", Data=" + data + "]";
	}
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	private String description;
	private int statusCode;
	
	private String message;
	private Object data;
	
//	private String message;
	
	//{"statusInfo":{"status":"S","errorDescription":"Success","errorCode":100

}
