package com.ito.app.beans;

import java.util.ArrayList;
import java.util.List;

public class Paxes {
	private boolean baggaged;
	private List<Pax> adults;
	private List<Pax> children;
	private List<Pax> infants;
	private int childCount;
	private int adultCount;
	private int infantCount;

	public Paxes() {
		adults = new ArrayList<Pax>();
		children = new ArrayList<Pax>();
		infants = new ArrayList<Pax>();
		childCount = 0;
		adultCount = 0;
		infantCount = 0;
	}

	// ADD
	public void add(Pax pax) {
		
		if( pax.isAdult() )
		{
			baggaged = baggaged || pax.hasBaggage();
			adults.add(pax);
			adultCount++;
		}
		else if( pax.isChild() )
		{
			baggaged = baggaged || pax.hasBaggage();
			children.add(pax);
			childCount++;
		}
		else
		{		
			infants.add(pax);
			infantCount++;
		}					
	}
	
	public String getAdultNameNumberForInfant(String ref)
	{				
		String result = "";
		for(Pax p : adults)
		{
			if( p.getNameNumber().startsWith(ref) ){
				result = p.getNameNumber();
				break;
			}
		}
		
		for(Pax p : infants)
		{
			if( p.getRefNameNumber().equals(result) )
				return "";
		}
		
		return result;
	}

	public boolean hasBaggage() {
		return baggaged;
	}

	// GET CHILD COUNT
	public int getChildCount() {
		return childCount;
	}

	// GET ADULT COUNT
	public int getAdultCount() {
		return adultCount;
	}
	
	// GET INFANT COUNT
	public int getInfantCount() {
		return infantCount;
	}

	// GET ADULT LIST
	public List<Pax> getAdultList() {
		return adults;
	}
	
	// GET CHILD LIST
	public List<Pax> getChildList() {
		return children;
	}
	
	// GET INFANT LIST
	public List<Pax> getInfantList() {
		return infants;
	}
}
