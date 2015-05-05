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
package com.dawg6.web.dhcalc.shared.calculator.d3api;

public class Const {

	public static final String CLASS_WIZARD = "wizard";
	public static final String CLASS_WITCHDOCTOR = "witch-doctor";
	public static final String CLASS_CRUSADER = "crusader";
	public static final String CLASS_BARBARIAN = "barbarian";
	public static final String CLASS_DEMONHUNTER = "demon-hunter";
	public static final String CLASS_MONK = "monk";

	public static final String DEXTERITY = "Dexterity_Item";
	
	public static final int GENDER_MALE = 0;
	public static final int GENDER_FEMALE = 1;

	public static final String ITEMTYPE_ORB = "Orb";

	public static final String AFFIXTYPE_DEFAULT = "default";
	public static final String AFFIXTYPE_ENCHANT = "enchant";
	public static final String AFFIXTYPE_UTILITY = "utility";

	public static final String SERVER_US = "http://us.battle.net";
	public static final String SERVER_EU = "http://eu.battle.net";

	public static final String COLD = "Cold";
	public static final String FIRE = "Fire";
	public static final String LIGHTNING = "Lightning";
	public static final String ARCANE = "Arcane";
	public static final String PHYSICAL = "Physical";
	public static final String HOLY = "Holy";
	public static final String Poison = "Poison";

	public static final String ELEMENTAL_SKILL_DAMAGE = " skills deal ";
	public static final String INCREASES = "Increases ";
	public static final String DAMAGE_BY = " Damage by ";
	public static final String ATTACK_SPEED = "Attack Speed Increased by ";
	public static final String BALLISTICS = "Ballistics";
	public static final String CULL_THE_WEEK = "Cull the Weak";
	public static final String BANE_OF_THE_TRAPPED = "Bane of the Trapped";
	public static final String JEWEL_RANK = "Jewel_Rank";
	public static final String ENFORCER = "Enforcer";
	public static final String ICEBLINK = "Iceblink";
	public static final String PET_ATTACK_SPEED = "Increase attack speed of your pets by ";
	public static final String BOTP = "Bane of the Powerful";
	public static final String GRENADIER = "Grenadier";
	public static final String CALAMITY = "Calamity";
	public static final String MARKED_FOR_DEATH = "Marked for Death";
	public static final String MAIN_HAND = "mainHand";
	public static final String HANDXBOW = "HandXbow";
	public static final String CROSSBOW = "Crossbow";
	public static final String BOW = "Bow";
	public static final String DAMAGE_WEAPON_MIN = "Damage_Weapon_Min";
	public static final String DAMAGE_WEAPON_BONUS_MIN = "Damage_Weapon_Bonus_Min";
	public static final String DAMAGE_WEAPON_BONUS_DELTA = "Damage_Weapon_Bonus_Delta";
	public static final String DAMAGE_WEAPON_DELTA = "Damage_Weapon_Delta";
	public static final String DAMAGE_WEAPON_PERCENT = "Damage_Weapon_Percent_All";
	public static final String ARCHERY = "Archery";
	public static final String WEAPON_IAS = "Attacks_Per_Second_Item_Percent";
	public static final String STEADY_AIM = "Steady Aim";
	public static final String ZEI = "Zei's Stone of Vengeance";
	public static final String TAEGUK = "Taeguk";
	public static final String ROYAL_RING = "Attribute_Set_Item_Discount";
	public static final String ELITE_DAMAGE_RAW = "Damage_Percent_Bonus_Vs_Elites";
	public static final String CRIT_CHANCE_RAW = "Crit_Percent_Bonus_Capped";
	public static final String CRIT_DAMAGE_RAW = "Crit_Damage_Percent";
	public static final String AMBUSH = "Ambush";
	public static final String SINGLE_OUT = "Single Out";
	public static final String HELLFIRE_PASSIVE = "Gain the ";
	public static final String PASSIVE = " passive.";
	public static final String JEWELY_MIN_DAMAGE = "Damage_Min#Physical";
	public static final String JEWELY_DELTA_DAMAGE = "Damage_Delta#Physical";
	public static final String IAS = "Attacks_Per_Second_Percent";
	public static final String TNT_IAS = "";
	public static final String CDR = "Power_Cooldown_Reduction_Percent_All";
	public static final String LEORICS_CROWN = "Leoric's Crown";
	public static final String GEM_MULTIPLIER = "Gem_Attributes_Multiplier";
	public static final String CAPTAIN_CRIMSON = "captain-crimsons-trimmings";
	public static final String BORNS = "borns-command";
	public static final String HEAD = "head";
	public static final String SHOULDERS = "shoulders";
	public static final String GLOVES = "hands";
	public static final String RING1 = "rightFinger";
	public static final String RING2 = "leftFinger";
	public static final String BELT = "waist";
	public static final String WEAPON = "mainHand";
	public static final String QUIVER = "offHand";
	public static final String AMULET = "neck";
	public static final String GOGOK = "Gogok of Swiftness";
	public static final String TOXIN = "Gem of Efficacious Toxin";
	public static final String PAIN_ENHANCER = "Pain Enhancer";
	public static final String NOT_FOUND = "NOTFOUND";
	public static final String COMPANION = "Companion";
	public static final String METICULOUS_BOLTS = "Meticulous Bolts";
	public static final String METICULOUS_BOLTS_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_749_x1";
	public static final String HARRINGTON = "Harrington Waistguard";
	public static final String HARRINGTON_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_685_x1";
	public static final String STRONGARM = "Strongarm Bracers";
	public static final String STRONGARM_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_590_x1";
	public static final String HEXING_PANTS = "Hexing Pants of Mr. Yan";
	public static final String HEXING_PANTS_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_635_x1";
	public static final String CALTROPS = "Caltrops";
	public static final String BOMBADIERS = "Bombardier's Rucksack";
	public static final String CUSTOM_ENGINEERING = "Custom Engineering";
	public static final String MARAUDERS = "embodiment-of-the-marauder";
	public static final String UE = "unhallowed-essence";
	public static final String NATS = "natalyas-vengeance";
	public static final String SPINES = "Spines of Seething Hatred";
	public static final String SPINES_HATRED = "Item_Power_Passive#ItemPassive_Unique_Ring_750_x1";
	public static final String KRIDERSHOT = "Kridershot";
	public static final String KRIDERSHOT_HATRED = "Item_Power_Passive#ItemPassive_Unique_Ring_517_x1";
	public static final String HATRED_PER_SECOND = "Resource_Regen_Per_Second#Hatred";
	public static final String PRIDES_FALL = "Pride's Fall";
	public static final String RESOURCE_REDUCTION = "Resource_Cost_Reduction_Percent_All";
	public static final String BLOOD_VENGEANCE = "Blood Vengeance";
	public static final String NIGHT_STALKER = "Night Stalker";
	public static final String REAPERS_WRAPS = "Reaper's Wraps";
	public static final String REAPERS_WRAPS_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_636_x1";
	public static final String CINDERCOAT = "Cindercoat";
	public static final String CINDERCOAT_RCR = "Item_Power_Passive#ItemPassive_Unique_Ring_608_x1";
	public static final String ODYSSEYS_END = "Odyssey's End";
	public static final String ODYSSEYS_END_PERCENT = "Item_Power_Passive#P2_ItemPassive_Unique_Ring_023";
	public static final String PREPARATION = "Preparation";
	public static final String PUNISHMENT = "Punishment";
	public static final String HELLTRAPPER = "Helltrapper";
	public static final String HELLTRAPPER_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_646_x1";
	public static final String SPIKE_TRAP = "Spike Trap";
	public static final String VAXO = "Haunt of Vaxo";
	public static final String AREA_DAMAGE = "Splash_Damage_Effect_Percent";
	public static final String RAIN_OF_VENGEANCE = "Rain of Vengeance";
	public static final String MAX_DISCIPLINE = "Resource_Max_Bonus#Discipline";
	public static final String BASTIONS_OF_WILL = "bastions-of-will";
	public static final String CRASHING_RAIN = "Crashing Rain";
	public static final String ELEMENTAL_DAMAGE_BONUS = "Damage_Dealt_Percent_Bonus#";
	public static final String SKILL_DAMAGE_BONUS = "Power_Damage_Percent_Bonus#DemonHunter_";
	public static final String CRASHING_RAIN_PERCENT = "Item_Power_Passive#ItemPassive_Unique_Ring_709_x1";
	public static final String DML = "Dead Man's Legacy";
	public static final String DML_PERCENT = "Item_Power_Passive#P2_ItemPassive_Unique_Ring_017";
	public static final String COE = "Convention of Elements";
	public static final String COE_PERCENT = "Item_Power_Passive#P2_ItemPassive_Unique_Ring_038";
}