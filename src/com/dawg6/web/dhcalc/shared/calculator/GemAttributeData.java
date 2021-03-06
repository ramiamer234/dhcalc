/*******************************************************************************
 * Copyright (c) 2014, 2015 Scott Clarke (scott@dawg6.com).
 *
 * This file is part of Dawg6's Demon Hunter DPS Calculator.
 *
 * Dawg6's Demon Hunter DPS Calculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dawg6's Demon Hunter DPS Calculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.dawg6.web.dhcalc.shared.calculator;


public class GemAttributeData extends AttributeData {

	private static final long serialVersionUID = 7974733464392181532L;

	
	public int level;
	
	public GemAttributeData() { 
		
	}
	
	public GemAttributeData(GemAttributeData o) {
		super(o);
		this.level = o.level;
	}

	public GemAttributeData copy() {
		return new GemAttributeData(this);
	}
	
	@Override
	public String toString() {
		return "{level=" + level + ",attributes=" + super.toString() + "}";
	}
	
}
