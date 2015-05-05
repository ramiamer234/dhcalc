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

public enum MonsterType {

	RiftGuardian("Rift Guardian", 44766142, true),
	Elite("Elite", 10533210, true),
	NonElite("Non-Elite", 1053321, false);
	
	private long health;
	private String name;
	private boolean elite;
	
	private MonsterType(String name, long health, boolean elite) {
		this.health = health;
		this.name = name;
		this.elite = elite;
	}
	
	public long getHealth() {
		return health;
	}
	
	public boolean isElite() {
		return elite;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
