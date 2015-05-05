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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.dawg6.web.dhcalc.shared.calculator;

public enum DamageType {
	Cold(DamageMultiplier.Cold),
	Fire(DamageMultiplier.Fire),
	Lightning(DamageMultiplier.Lightning),
	Physical(DamageMultiplier.Physical),
	Poison(DamageMultiplier.Poison);

	private DamageMultiplier multiplier;
	
	private DamageType(DamageMultiplier multiplier) {
		this.multiplier = multiplier;
	}

	public DamageMultiplier getMultiplier() {
		return multiplier;
	}
	
	public String getLongName() {
		return name();
	}
	
	public String getSlug() {
		return name();
	}
	
	@Override
	public String toString() {
		return name();
	}
}
