package com.snail.core.beans;

import java.util.ArrayList;
import java.util.List;

public class FareDetail implements Deliverable {

	private List<Detail> listDetail;
	private double unitFare;
	private double sum;
	private int count;

	// SET BY UNIT FARE
	public void setByUnit(double unitFare, int count) {
		this.unitFare = unitFare;
		this.count = count;
		this.sum = unitFare * count;

	}

	// SET BY SUM
	public void setBySum(double sum, int count) {
		this.sum = sum;
		this.count = count;
		this.unitFare = sum / count;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUnitFare(double unitFare) {
		this.unitFare = unitFare;
	}

	// GET SUM
	public double getSum() {
		return sum;
	}

	// GET UNIT FARE
	public double getUnitFare() {
		return unitFare;
	}

	// GET COUNT
	public int getCount() {
		return count;
	}

	// ADD DETAIL
	public void addDetail(String type, String value) {
		if (listDetail == null) {
			listDetail = new ArrayList<FareDetail.Detail>();
		}

		Detail detail = new Detail(type, value);
		listDetail.add(detail);
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("fare", sum);
		map.put("detail", listDetail);

		return map;
	}

	// **

	// CREATE BY UNIT
	public static FareDetail createByUnitFare(double unitFare, int count) {
		FareDetail detail = new FareDetail();
		detail.setByUnit(unitFare, count);
		return detail;
	}

	// CREATE BY SUM
	public static FareDetail createBySum(double sum, int count) {
		FareDetail detail = new FareDetail();
		detail.setBySum(sum, count);
		return detail;
	}

	// == DETAIL ==

	public static class Detail implements Deliverable {
		private String type;
		private String value;

		// CONSTRUCTOR
		public Detail(String type, String value) {
			this.type = type;
			this.value = value;
		}

		@Override
		public DeliveryMap deliver() {
			DeliveryMap map = new DeliveryMap();
			map.put("type", type);
			map.put("value", value);
			return map;
		}

	}

}
