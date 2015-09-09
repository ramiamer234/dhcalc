package com.dawg6.web.dhcalc.shared.calculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.dawg6.web.dhcalc.shared.calculator.d3api.Const;

public enum SpecialItemType {

	Harrington(Const.HARRINGTON, "harrington-waistguard", false, new Slot[] {
			Slot.Waist, Slot.CubeArmor }, new Attribute(
			SpecialItemType.PERCENT, Const.HARRINGTON_PERCENT, 100, 135),
			new Attribute(SpecialItemType.UPTIME)),

	HuntersWrath(Const.HUNTERS_WRATH, "hunters-wrath", false, new Slot[] {
			Slot.Waist, Slot.CubeArmor }, new Attribute(
			SpecialItemType.PERCENT, Const.HUNTERS_WRATH_PERCENT, 45, 60)),

	CR(Const.CRASHING_RAIN, "crashing-rain", false, new Slot[] { Slot.Waist,
			Slot.CubeArmor }, new Attribute(SpecialItemType.PERCENT,
			Const.CRASHING_RAIN_PERCENT, 3000, 4000)),

	Calamity(Const.CALAMITY, "calamity", false, new Slot[] { Slot.MainHand,
			Slot.CubeWeapon, Slot.OffHand }),

	HellTrapper(Const.HELLTRAPPER, "helltrapper", false, new Slot[] {
			Slot.MainHand, Slot.OffHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.PERCENT, Const.HELLTRAPPER_PERCENT, 7, 10)),

	Yangs(Const.YANGS, "yangs-recurve", false, new Slot[] { Slot.MainHand,
			Slot.CubeWeapon }),

	Kridershot(Const.KRIDERSHOT, "kridershot", false, new Slot[] {
			Slot.MainHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.HATRED, Const.KRIDERSHOT_HATRED, 3, 4, 1.0)),

	Oddessy(Const.ODYSSEYS_END, "odysseys-end", false, new Slot[] {
			Slot.MainHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.PERCENT, Const.ODYSSEYS_END_PERCENT, 20, 25)),

	TnT(Const.TASKER_AND_THEO, "tasker-and-theo", false, new Slot[] {
			Slot.Hands, Slot.CubeArmor }, new Attribute(
			SpecialItemType.PERCENT, Const.TNT_IAS, 40, 50)),

	MeticulousBolts(Const.METICULOUS_BOLTS, "meticulous-bolts", false,
			new Slot[] { Slot.OffHand, Slot.CubeWeapon }, new Attribute(
					SpecialItemType.PERCENT, Const.METICULOUS_BOLTS_PERCENT,
					30, 40)),

	Bombadiers(Const.BOMBADIERS, "bombardiers-rucksack", false, new Slot[] {
			Slot.OffHand, Slot.CubeWeapon }),

	Spines(Const.SPINES, "spines-of-seething-hatred", false, new Slot[] {
			Slot.OffHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.HATRED, Const.SPINES_HATRED, 3, 4, 1.0)),

	DML(Const.DML, "dead-mans-legacy", false, new Slot[] { Slot.OffHand,
			Slot.CubeWeapon }, new Attribute(SpecialItemType.PERCENT,
			Const.DML_PERCENT, 50, 60)),

	Fulminator(Const.FULMINATOR, "fulminator", false, new Slot[] {
			Slot.MainHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.PERCENT, Const.FULMINATOR_PCT, 444, 555)),

	Thunderfury(Const.THUNDERFURY, "thunderfury-blessed-blade-of-the-windseeker", false, new Slot[] {
			Slot.MainHand, Slot.CubeWeapon }, new Attribute(
			SpecialItemType.PERCENT, Const.THUNDERFURY_PCT, 279, 372)),

	StrongArm(Const.STRONGARM, "strongarm-bracers", false, new Slot[] {
			Slot.Bracers, Slot.CubeArmor }, new Attribute(
			SpecialItemType.PERCENT, Const.STRONGARM_PERCENT, 20, 30),
			new Attribute(SpecialItemType.UPTIME)),

	Reapers(Const.REAPERS_WRAPS, "reapers-wraps", true, new Slot[] {
			Slot.Bracers, Slot.CubeArmor }, new Attribute(
			SpecialItemType.PERCENT, Const.REAPERS_WRAPS_PERCENT, 25, 30)),

	HexingPants(Const.HEXING_PANTS, "hexing-pants-of-mr-yan", false,
			new Slot[] { Slot.Legs, Slot.CubeArmor },
			new Attribute(SpecialItemType.PERCENT, Const.HEXING_PANTS_PERCENT,
					20, 25), new Attribute(SpecialItemType.PERCENT_MOVING)),

	DepthDiggers(Const.DEPTH_DIGGERS, "depth-diggers", false, new Slot[] {
			Slot.Legs, Slot.CubeArmor }, new Attribute(SpecialItemType.PERCENT,
			Const.DEPTH_DIGGERS_PCT, 80, 100)),

	Vaxo(Const.VAXO, "haunt-of-vaxo", false, new Slot[] { Slot.Necklace,
			Slot.CubeJewelry }),

	CoE(Const.COE, "convention-of-elements", false, new Slot[] { Slot.Ring1,
			Slot.Ring2, Slot.CubeJewelry }, new Attribute(
			SpecialItemType.PERCENT, Const.COE_PERCENT, 150, 200)),

	Cindercoat(Const.CINDERCOAT, "cindercoat", false, new Slot[] { Slot.Torso,
			Slot.CubeArmor }, new Attribute(SpecialItemType.PERCENT,
			Const.CINDERCOAT_RCR, 23, 30)),

	Leorics(Const.LEORICS_CROWN, "leorics-crown", false, new Slot[] {
			Slot.Head, Slot.CubeArmor }, new Attribute(SpecialItemType.PERCENT,
			Const.GEM_MULTIPLIER, 75, 100)),

	PridesFall(Const.PRIDES_FALL, "prides-fall", false, new Slot[] { Slot.Head,
			Slot.CubeArmor }),

	RoyalRing(Const.ROYAL_RING, "ring-of-royal-grandeur", false, new Slot[] {
			Slot.Ring1, Slot.Ring2, Slot.CubeJewelry }),

	;

	public static final String PERCENT = "Percent";
	public static final String PERCENT_MOVING = "Percent Moving";
	public static final String UPTIME = "Uptime";
	public static final String HATRED = "Hatred";

	private final String name;
	private final String slug;
	private final Slot[] slots;
	private final Attribute[] attributes;
	private final boolean crafted;

	private SpecialItemType(String name, String slug, boolean crafted,
			Slot[] slots, Attribute... attributes) {
		this.name = name;
		this.crafted = crafted;
		this.slug = slug;
		this.slots = slots;
		this.attributes = attributes;
	}

	public static class Attribute {

		private final String label;
		private final String slug;
		private final int min;
		private final int max;
		private final double scalar;
		private final String key;

		public Attribute(String label) {
			this(label, null);
		}

		public Attribute(String label, String slug) {
			this(label, slug, 0, 100);
		}

		public Attribute(String label, String slug, int min, int max) {
			this(label, slug, min, max, 100.0);
		}

		public Attribute(String label, String slug, int min, int max,
				double scalar) {
			this.label = label;
			this.slug = slug;
			this.min = min;
			this.max = max;
			this.scalar = scalar;
			this.key = label.replaceAll(" ", "_");
		}

		public String getLabel() {
			return label;
		}

		public String getKey() {
			return key;
		}

		@Override
		public String toString() {
			return label;
		}

		public String getSlug() {
			return slug;
		}

		public int setRawAttributeValue(double value) {
			return (int) Math.round(value * scalar);
		}

		public double getRawAttributeValue(int value) {
			return value / scalar;
		}

		public int getMin() {
			return min;
		}

		public int getMax() {
			return max;
		}

		public double getScalar() {
			return scalar;
		}
	}

	public String getName() {
		return name;
	}

	public String getSlug() {
		return slug;
	}

	public Slot[] getSlots() {
		return slots;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public String getUrl() {
		return (crafted ? "http://us.battle.net/d3/en/artisan/blacksmith/recipe/"
				: "http://us.battle.net/d3/en/item/")
				+ slug;
	}

	public static List<SpecialItemType> getItemsBySlot(Slot slot) {
		List<SpecialItemType> list = new Vector<SpecialItemType>();

		for (SpecialItemType t : values()) {
			if (Util.indexOf(t.slots, slot) >= 0)
				list.add(t);
		}

		Collections.sort(list, SORTER);

		return list;
	}

	public static final Comparator<SpecialItemType> SORTER = new Comparator<SpecialItemType>() {

		@Override
		public int compare(SpecialItemType o1, SpecialItemType o2) {
			return o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
		}
	};

	@Override
	public String toString() {
		return name;
	}
}
