package com.ito.app.beans;

import java.util.List;

import com.snail.core.util.StringUtil;

public class SegmentPaxFare {
	private List<PaxFare> listAvFare;

	public List<PaxFare> getListAvFare() {
		return listAvFare;
	}

	public void setListAvFare(List<PaxFare> listAvFare) {
		this.listAvFare = listAvFare;
	}

	// GET ADULT FARE
	public double getAdultFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "ADT")) {
				return avFare.getTotalAmount();
			}
		}

		return 0;
	}

	// GET ADULT BASE FARE
	public double getAdultBaseFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "ADT")) {
				return avFare.getBaseAmount();
			}
		}

		return 0;
	}

	// GET CHILD FARE
	public double getChildFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "CNN" ) || avFare.getPaxType().startsWith("C")) {
				return avFare.getTotalAmount();
			}
		}
		return 0;
	}

	// GET CHILD FARE
	public double getChildBaseFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "CNN")) {
				return avFare.getBaseAmount();
			}
		}
		return 0;
	}
	
	// GET INFANT FARE
	public double getInfantFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "INF")) {
				return avFare.getTotalAmount();
			}
		}
		return 0;
	}

	// GET INFANT FARE
	public double getInfantBaseFare() {
		for (PaxFare avFare : listAvFare) {
			if (StringUtil.isSame(avFare.getPaxType(), "INF")) {
				return avFare.getBaseAmount();
			}
		}
		return 0;
	}
}