package com.indramakers.example.measuresms.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Measure implements Serializable {

	private int id;
	private int deviceId;
	private Double value;
	private Date dateTime;

	public Measure() {
	}

	public Measure(int deviceId, Double value) {
		super();
		this.deviceId = deviceId;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
