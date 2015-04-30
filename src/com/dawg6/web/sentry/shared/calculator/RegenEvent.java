package com.dawg6.web.sentry.shared.calculator;

import java.util.List;

public class RegenEvent extends Event {

	private double regen;
	private double venRegenTick;

	public RegenEvent(CharacterData data) {
		this.time = 1.0;
		
		Rune companionRune = data.getCompanionRune();
		
		regen = 5.0
				+ data.getHatredPerSecond()
				+ (data.isInspire() ? 1.0 : 0.0)
				+ (((companionRune == Rune.Bat) || ((companionRune != null) && data
						.getNumMarauders() >= 2)) ? 1.0 : 0.0)
				+ ((data.isArchery()
						&& (data.getWeaponType() == WeaponType.HandCrossbow) && (data
						.getOffHand_weaponType() == WeaponType.HandCrossbow)) ? 1.0
						: 0.0);
		venRegenTick = 10.0;

		if (data.isHexingPants()) {
			venRegenTick = venRegenTick
					+ (venRegenTick * data.getHexingPantsUptime() * .25)
					- (venRegenTick * (1.0 - data.getHexingPantsUptime()) * data
							.getHexingPantsPercent());
			regen = regen
					+ (regen * data.getHexingPantsUptime() * .25)
					- (regen * (1.0 - data.getHexingPantsUptime()) * data
							.getHexingPantsPercent());
		}
	}
	
	@Override
	public void execute(EventQueue queue, List<Damage> log,
			SimulationState state) {
		
		if (regen > 0.0)
		{
			double h = regen;
		
			double actual = state.addHatred(h);
			
			if (actual > 0) {
				Damage d = new Damage();
				d.time = state.getTime();
				d.shooter = "Player";
				d.hatred = actual;
				d.currentHatred = state.getHatred();
				d.currentDisc = state.getDisc();
				d.note = "Hatred Regen";
				log.add(d);
			}
		}

		if (state.getBuffs().isActive(Buff.Seethe)) 
		{
			double h = venRegenTick;
		
			double actual = state.addHatred(h);
			
			if (actual > 0) {
				Damage d = new Damage();
				d.time = state.getTime();
				d.shooter = "Player";
				d.source = new DamageSource(ActiveSkill.Vengeance, Rune.Seethe);
				d.hatred = actual;
				d.currentHatred = state.getHatred();
				d.currentDisc = state.getDisc();
				d.note = "Seethe Hatred";
				log.add(d);
			}
		}
		
		this.time += 1.0;
		
		queue.push(this);
	}

}
