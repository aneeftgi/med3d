package com.tgi.med3d.constant;


public interface ErrorCode {

	public static class Error {
		private Integer errorCode;

		public Integer getErrorCode() {
			return errorCode;
		}

		public Integer getCode() {
			return errorCode;
		}

		public Error(Integer errorCode) {
			this.errorCode = errorCode;
		}
	}
	
	public static final Error SUCCESS_RESPONSE = new Error(200);
	public static final Error CREATED = new Error(201); //failure
	public static final Error UNAUTHORIZED = new Error(401);
	public static final Error EXCEPTION = new Error(-1);
	public static final Error INVALID_DATA = new Error(5);
	public static final Error FAILURE_RESPONSE=new Error(417);

	

}
