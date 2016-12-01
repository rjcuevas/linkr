package com.linkr.message;

/**
 * Common Result class
 * 
 */
public class Result {
	
	private boolean result;
	private String resultMessage;
	
	
	public boolean isResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
