package com.intellect.user.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
	private String resMsg;
	private String userId = "";
	private List<ErrorResponse> varErrors = new ArrayList<>();
	
	
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<ErrorResponse> getVarErrors() {
		return varErrors;
	}
	public void addVarErrors(ErrorResponse err) {
		this.varErrors.add(err);
	}
	public void setVarErrors(List<ErrorResponse> varErrors) {
		this.varErrors = varErrors;
	}
	
	
}
