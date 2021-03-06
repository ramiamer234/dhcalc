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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SimulationState implements Serializable {

	private static final long serialVersionUID = -6201606085704392742L;

	private CharacterData data;
	private TargetList targets;
	private final BuffList buffs;
	private final DotList dots;
	private double time;
	private double hatred;
	private double maxHatred;
	private double lastSpenderTime;
	private Hand hand;
	private double disc;
	private double maxDisc;
	private final Map<DamageProc, Double> procAvail = new TreeMap<DamageProc, Double>();
	private SkillAndRune lastAttack;
	private double lastAps;
	private double lastFoK;
	private final Map<Integer, SpikeTrapEvent> spikeTraps = new TreeMap<Integer, SpikeTrapEvent>();
	
	public SimulationState() {
		this.time = 0.0;
		this.buffs = new BuffList();
		this.dots = new DotList();
		this.lastSpenderTime = 0.0;
		this.lastFoK = 0.0;
		
		for (DamageProc dp : DamageProc.values()) {
			procAvail.put(dp, 0.0);
		}
	}

	public SimulationState(CharacterData data, TargetList targets) {
		this();

		this.data = data;
		this.targets = targets;
		this.hatred = data.getMaxHatred();
		this.maxHatred = data.getMaxHatred();
		this.disc = data.getMaxDiscipline();
		this.maxDisc = data.getMaxDiscipline();
	}

	public CharacterData getData() {
		return data;
	}

	public void setData(CharacterData data) {
		this.data = data;
	}

	public TargetList getTargets() {
		return targets;
	}

	public void setTargets(TargetList targets) {
		this.targets = targets;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
		
		buffs.expire(time);
	}

	public BuffList getBuffs() {
		return buffs;
	}

	public double getHatred() {
		return hatred;
	}

	public void setHatred(double hatred) {
		this.hatred = hatred;
	}

	public double getMaxHatred() {
		return maxHatred;
	}

	public void setMaxHatred(double maxHatred) {
		this.maxHatred = maxHatred;
	}

	public double addHatred(double h) {
		
		double actual = Math.min(maxHatred - hatred, h);
		this.hatred += actual;
		
		return actual;
	}

	public double addDisc(double h) {
		
		double actual = Math.min(maxDisc - disc, h);
		this.disc += actual;
		
		return actual;
	}

	public double getLastSpenderTime() {
		return lastSpenderTime;
	}

	public void setLastSpenderTime(double lastSpenderTime) {
		this.lastSpenderTime = lastSpenderTime;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public double getDisc() {
		return disc;
	}

	public void setDisc(double disc) {
		this.disc = disc;
	}

	public double getMaxDisc() {
		return maxDisc;
	}

	public void setMaxDisc(double maxDisc) {
		this.maxDisc = maxDisc;
	}

	public DotList getDots() {
		return dots;
	}

	public int getNumSpikeTraps() {
		return spikeTraps.size();
	}

	public int addSpikeTrap() {
		
		int max = data.getMaxTraps();
		
		for (Integer i = 1; i <= max; i++) {
			if (!spikeTraps.containsKey(i)) {
				SpikeTrapEvent e = new SpikeTrapEvent(this.getData(), i);
				spikeTraps.put(i, e);
				return i;
			}
		}

		return 0;
	}

	public Map<DamageProc, Double> getProcAvail() {
		return procAvail;
	}

	public SkillAndRune getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(SkillAndRune lastAttack) {
		this.lastAttack = lastAttack;
	}

	public double getLastAps() {
		return lastAps;
	}

	public void setLastAps(double lastAps) {
		this.lastAps = lastAps;
	}

	public void setLastFoK(double time) {
		this.lastFoK = time;
	}
	
	public int getLGFStacks() {
		
		if (this.lastFoK <= 0.0)
			return 30;
		
		return Math.min((int)Math.floor(this.time - this.lastFoK), 30);
	}
	
	public double getLastFoK() {
		return this.lastFoK;
	}

	public boolean hasSpikeTraps() {
		return !spikeTraps.isEmpty();
	}

	public void detonateTraps(EventQueue queue, List<Damage> log) {
		for (SpikeTrapEvent e : spikeTraps.values()) {
			e.execute(queue, log, this);
		}
		
		spikeTraps.clear();
	}
}
