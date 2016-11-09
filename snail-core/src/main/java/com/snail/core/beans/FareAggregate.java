package com.snail.core.beans;

/**
 * 
 * @author iman
 * 
 */
public class FareAggregate {

	private double sum;
	private int count;
	private double unitFare;

	// ADD
	public void add(double fare) {
		sum += fare;
		count++;
	}

	// SET SUM
	public void setSum(double sum) {
		this.sum = sum;
	}

	// SET COUNT
	public void setCount(int count) {
		this.count = count;
	}

	// SET MEAN
	public void setUnitFare(double unitFare) {
		this.unitFare = unitFare;
	}

	// UPDATE SUM
	public void updateSum() {
		sum = count * unitFare;
	}

	// GET SUM
	public double getSum() {
		return sum;
	}

	// GET COUNT
	public int getCount() {
		return count;
	}

	// GET MEAN
	public double getUnitPrice() {
		if (unitFare > 0) {
			return unitFare;
		} else {
			if (unitFare == 0 && sum != 0 && count != 0) {
				unitFare = sum / count;
			}
			return unitFare;
		}
	}

	// ** FACTORY **

	// BY UNIT FARE
	public static FareAggregate createByUnitFare(double unitFare, int count) {
		FareAggregate aggregate = new FareAggregate();
		aggregate.setUnitFare(unitFare);
		aggregate.setCount(count);
		aggregate.setSum(unitFare * count);

		return aggregate;
	}

	public static FareAggregate createBySum(double sum, int count) {
		FareAggregate aggregate = new FareAggregate();
		aggregate.setSum(sum);
		aggregate.setCount(count);
		return aggregate;
	}

}
