package com.cheezhi.bean;

import java.io.Serializable;

public class MenuItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int imgId;
	private String bigName;

	public MenuItem(int imgId, String bigName) {
		super();
		this.imgId = imgId;
		this.bigName = bigName;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getBigName() {
		return bigName;
	}

	public void setBigName(String bigName) {
		this.bigName = bigName;
	}



	@Override
	public String toString() {
		return "MenuItem [imgId=" + imgId + ", bigName=" + bigName + "]";
	}

}
