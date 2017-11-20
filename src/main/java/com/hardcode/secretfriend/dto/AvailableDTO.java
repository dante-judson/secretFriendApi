package com.hardcode.secretfriend.dto;

public class AvailableDTO {
	
	private boolean available;
	
	private String msg;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AvailableDTO(boolean available, String msg) {
		super();
		this.available = available;
		this.msg = msg;
	}
	
	
}
