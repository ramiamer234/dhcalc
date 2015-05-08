package com.dawg6.web.dhcalc.shared.calculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.dawg6.web.dhcalc.shared.calculator.d3api.Const;

public enum SpecialItemType {

	Harrington(Const.HARRINGTON, "harrington-waistguard",
			new Slot[] { Slot.Waist }, new Attribute(SpecialItemType.PERCENT,
					Const.HARRINGTON_PERCENT, 100, 135),
			new Attribute(SpecialItemType.UPTIME)),

	CR(Const.CRASHING_RAIN, "crashing-rain", new Slot[] { Slot.Waist },
			new Attribute(SpecialItemType.PERCENT, Const.CRASHING_RAIN_PERCENT,
					3000, 4000)),

	Calamity(Const.CALAMITY, "calamity", new Slot[] { Slot.MainHand,
			Slot.OffHand }, new Attribute(SpecialItemType.UPTIME)),

	HellTrapper(Const.HELLTRAPPER, "helltrapper-3tfdaj", new Slot[] {
			Slot.MainHand, Slot.OffHand }, new Attribute(
			SpecialItemType.PERCENT, Const.HELLTRAPPER_PERCENT, 7, 10)),

	Kridershot(Const.KRIDERSHOT, "kridershot", new Slot[] { Slot.MainHand },
			new Attribute(SpecialItemType.HATRED, Const.KRIDERSHOT_HATRED, 3, 4)),

	Oddessy(Const.ODYSSEYS_END, "odysseys-end", new Slot[] { Slot.MainHand },
			new Attribute(SpecialItemType.PERCENT, Const.ODYSSEYS_END_PERCENT,
					20, 25), new Attribute(SpecialItemType.UPTIME)),

	TnT(Const.TASKER_AND_THEO, "tasker-and-theo", new Slot[] { Slot.Hands },
			new Attribute(SpecialItemType.PERCENT, Const.TNT_IAS, 40, 50)),

	MeticulousBolts(Const.METICULOUS_BOLTS, "meticulous-bolts",
			new Slot[] { Slot.OffHand }, new Attribute(SpecialItemType.PERCENT,
					Const.METICULOUS_BOLTS_PERCENT, 40, 50)),

	Bombadiers(Const.BOMBADIERS, "bombardiers-rucksack",
			new Slot[] { Slot.OffHand }),

	Spines(Const.SPINES, "spines-of-seething-hatred",
			new Slot[] { Slot.OffHand }, new Attribute(SpecialItemType.HATRED,
					Const.SPINES_HATRED, 3, 4)),

	DML(Const.DML, "dead-mans-legacy", new Slot[] { Slot.OffHand },
			new Attribute(SpecialItemType.PERCENT, Const.DML_PERCENT, 50, 60)),

	StrongArm(Const.STRONGARM, "strongarm-bracers",
			new Slot[] { Slot.Bracers }, new Attribute(SpecialItemType.PERCENT,
					Const.STRONGARM_PERCENT, 20, 30), new Attribute(SpecialItemType.UPTIME)),

	Reapers(Const.REAPERS_WRAPS, "reapers-wraps", new Slot[] { Slot.Bracers },
			new Attribute(SpecialItemType.PERCENT, Const.REAPERS_WRAPS_PERCENT,
					25, 30)),

	HexingPants(Const.HEXING_PANTS, "hexing-pants-of-mr-yan",
			new Slot[] { Slot.Legs }, new Attribute(SpecialItemType.PERCENT,
					Const.HEXING_PANTS_PERCENT, 20, 25), new Attribute(
					"% Moving")),

	Vaxo(Const.VAXO, "haunt-of-vaxo", new Slot[] { Slot.Necklace },
			new Attribute(SpecialItemType.UPTIME)),

	CoE(Const.COE, "convention-of-elements", new Slot[] { Slot.Ring1,
			Slot.Ring2 }, new Attribute(SpecialItemType.PERCENT,
			Const.COE_PERCENT, 150, 200)),

	Cindercoat(Const.CINDERCOAT, "cindercoat", new Slot[] { Slot.Torso },
			new Attribute(SpecialItemType.PERCENT, Const.CINDERCOAT_RCR, 23, 30)),

	Leorics(Const.LEORICS_CROWN, "leorics-crown", new Slot[] { Slot.Head },
			new Attribute(SpecialItemType.PERCENT, Const.GEM_MULTIPLIER, 75, 100)),

	PridesFall(Const.PRIDES_FALL, "prides-fall-pgClp", new Slot[] { Slot.Head }),

	RoyalRing(Const.ROYAL_RING, "ring-of-royal-grandeur-3qRFop", new Slot[] { Slot.Ring1, Slot.Ring2 }),
	
	;

	public static final String PERCENT = "Percent";
	public static final String UPTIME = "Uptime";
	public static final String HATRED = "Hatred";

	private final String name;
	private final String slug;
	private final Slot[] slots;
	private final Attribute[] attributes;

	private SpecialItemType(String name, String slug, Slot[] slots,
			Attribute... attributes) {
		this.name = name;
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
		}

		public String getLabel() {
			return label;
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
		return "http://us.battle.net/d3/en/item/" + slug;
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
