package com.dawg6.web.sentry.shared.calculator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExportData implements Serializable {

	private static final long serialVersionUID = -5281919948781069134L;
	
	public CharacterData data;
	public Map<ActiveSkill, Rune> skills;
	public Damage[] output;
	public Map<DamageType, DamageHolder> types;
	public Map<DamageSource, DamageHolder> skillDamages;
	public List<MultipleSummary> multiple;
	public int bp;
	public double sentryBaseDps;
	public double sentryDps;
	public double sentryEliteDps;
	public double totalDamage;
	public double totalEliteDamage;
}