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
package com.dawg6.web.dhcalc.client;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import com.dawg6.gwt.client.widgets.SimpleCaptionPanel;
import com.dawg6.web.dhcalc.shared.calculator.AttributeData;
import com.dawg6.web.dhcalc.shared.calculator.ItemHolder;
import com.dawg6.web.dhcalc.shared.calculator.ItemSet;
import com.dawg6.web.dhcalc.shared.calculator.Slot;
import com.dawg6.web.dhcalc.shared.calculator.SpecialItemType;
import com.dawg6.web.dhcalc.shared.calculator.SpecialItemType.Attribute;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;

public class ItemPanel extends Composite {
	private final NumberSpinner areaDamageEquipment;
	private final NumberSpinner eliteDamagePercent;
	private final FlexTable table1;
	private final Map<Slot, Anchor> anchors = new TreeMap<Slot, Anchor>();
	private final Map<Slot, ListBox> listBoxes = new TreeMap<Slot, ListBox>();
	private boolean disableListeners;
	private final List<ChangeHandler> handlers = new Vector<ChangeHandler>();
	private final Map <ItemSet, NumberSpinner> setCounts = new TreeMap<ItemSet, NumberSpinner>();
	private final Map <ItemSet, SimpleCheckBox> setToggles = new TreeMap<ItemSet, SimpleCheckBox>();
	private final Map <Slot, Integer> rows = new TreeMap<Slot, Integer>();
	private final Map <Slot, SpecialItemType> selected = new TreeMap<Slot, SpecialItemType>();
	private final Map <Slot, Map<String, BaseSpinner<?>>> attributeSpinners = new TreeMap<Slot, Map<String, BaseSpinner<?>>>();
	private final NumberSpinner numAncients;
	private final SimpleCheckBox otherSets;
	private final Set<ItemsChangedListener> listeners = new TreeSet<ItemsChangedListener>();
	private final FlexTable table2;
	private final FlexTable table3;
	
	public interface ItemsChangedListener {
		void itemsChanged(Map<Slot, ItemHolder> items);
		void setCountsChanged(Map<String, Integer> sets);
	}
	
	public ItemPanel() {

		disableListeners = false;
		
		SimpleCaptionPanel cptnpnlSpecialItems = new SimpleCaptionPanel("Items");
		initWidget(cptnpnlSpecialItems);

		FlexTable table = new FlexTable();
		cptnpnlSpecialItems.setContentWidget(table);
		
		SimpleCaptionPanel c1 = new SimpleCaptionPanel("General");
		table.setWidget(0, 0, c1);
		table.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		table1 = new FlexTable();
		c1.setContentWidget(table1);

		SimpleCaptionPanel c3 = new SimpleCaptionPanel("Set Bonuses");
		table.setWidget(1, 0, c3);
		table.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		table3 = new FlexTable();
		c3.setContentWidget(table3);

		SimpleCaptionPanel c2 = new SimpleCaptionPanel("Special Item Bonuses");
		table.setWidget(0, 1, c2);
		table.getFlexCellFormatter().setRowSpan(0, 1, 2);
		table.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		table2 = new FlexTable();
		c2.setContentWidget(table2);

		int row = 0;
		
		Label label_4 = new Label("# Ancient Items:", false);
		table1.setWidget(row, 0, label_4);
		
		numAncients = new NumberSpinner();
		numAncients.setVisibleLength(4);
		numAncients.setTitle("# Ancient items worn");
		table1.setWidget(row, 1, numAncients);

		row++;
		
		Label label_2 = new Label("Area Damage +%:", false);
		table1.setWidget(row, 0, label_2);
		
		areaDamageEquipment = new NumberSpinner();
		areaDamageEquipment.setMax(100);
		areaDamageEquipment.setVisibleLength(4);
		areaDamageEquipment.setTitle("Added Area Damage from Equipment only");
		table1.setWidget(row, 1, areaDamageEquipment);
		
		row++;
		
		Label label_3 = new Label("Elite Damage +%:", false);
		table1.setWidget(row, 0, label_3);
		
		eliteDamagePercent = new NumberSpinner();
		eliteDamagePercent.setVisibleLength(4);
		eliteDamagePercent.setTitle("Added Elite Damage from Armor and Weapons only (do not include Bane of the Powerful bonus)");
		table1.setWidget(row, 1, eliteDamagePercent);

		
		row++;
		
		Label label_3a = new Label("Note: subtract 15% from the value displayed in-game if Bane of the Powerful is rank 25 or more.", true);
		label_3a.addStyleName("boldText");
		label_3a.setWidth("350px");
		table1.setWidget(row, 0, label_3a);
		table1.getFlexCellFormatter().setColSpan(row, 0, 2);

		row = 0;
		
		Label label_4b = new Label("Other Set Bonuses?", false);
		table3.setWidget(row, 0, label_4b);

		this.otherSets = new SimpleCheckBox();
		this.otherSets.setTitle("Select if any set bonuses not listed below are in effect.");
		table3.setWidget(row, 1, this.otherSets);
		
		row++;
		
		for (ItemSet set : ItemSet.values()) {
			Anchor anchor = new Anchor(set.getName());
			anchor.setWordWrap(false);
			anchor.setTarget("_blank");
			anchor.setHref(set.getUrl());
			table3.setWidget(row, 0, anchor);
			
			final ItemSet thisSet = set;
			
			if (set.getMaxPieces() > 2) {
				
				NumberSpinner number = new NumberSpinner();
				number.setMax(set.getMaxPieces());
				number.setVisibleLength(2);
				number.setTitle("# of pieces of this set worn (add 1 if using RoRG)");
				table3.setWidget(row, 1, number);
				
				setCounts.put(thisSet, number);
				
				number.addChangeHandler(new ChangeHandler(){

					@Override
					public void onChange(ChangeEvent event) {
						setChanged(thisSet);
						
					}});
				
			} else {
				SimpleCheckBox checkBox = new SimpleCheckBox();
				table3.setWidget(row, 1, checkBox);
				
				setToggles.put(thisSet, checkBox);
				
				checkBox.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						setChanged(thisSet);
					}});
			}
				
			row++;
		}
		
		Label label_4a = new Label("Note: Add 1 to Set Item count if using RROG", true);
		label_4a.addStyleName("boldText");
		label_4a.setWidth("350px");
		table3.setWidget(row, 0, label_4a);
		table3.getFlexCellFormatter().setColSpan(row, 0, 2);

		row = 0;

		for (Slot slot : Slot.values()) {
			List<SpecialItemType> types = SpecialItemType.getItemsBySlot(slot);
			
			if (!types.isEmpty()) {
				
				rows.put(slot, row);
				
				Anchor anchor = new Anchor(slot.getName());
				anchor.setWordWrap(false);
				anchor.setHref("javascript:void(0)");
				anchor.setTarget("_blank");
				table2.setWidget(row, 0, anchor);
				anchors.put(slot, anchor);

				ListBox list = new ListBox();
				list.setWidth("100%");
				list.addItem("Other/None", "");
				
				for (SpecialItemType t : types) {
					list.addItem(t.getName(), t.name());
				}
				
				list.setSelectedIndex(0);
				
				final Slot thisSlot = slot;
				list.addChangeHandler(new ChangeHandler(){

					@Override
					public void onChange(ChangeEvent event) {
						itemChanged(thisSlot);
					}});
				
				listBoxes.put(slot,  list);
				table2.setWidget(row, 1, list);
				
				attributeSpinners.put(slot, new TreeMap<String, BaseSpinner<?>>());
				
				row += 2;
			}
			
			
		}
		
		
	}

	public void addItemsChangedListener(ItemsChangedListener l) {
		listeners.add(l);
	}
	
	public void removeItemsChangedListener(ItemsChangedListener l) {
		listeners.remove(l);
	}
	
	protected void setChanged(ItemSet set) {
		itemsChanged(null);
		
		if (!disableListeners) {
			Map<String, Integer> sets = this.getSetCounts();
			
			for (ItemsChangedListener l : listeners)
				l.setCountsChanged(sets);
		}
	}

	protected void itemChanged(Slot slot) {

		SpecialItemType type = getSelectedItem(slot);
		
		boolean changed = setItem(slot, type, null);

		if ((type != null) && !slot.isCube()) {
		
			for (Slot s : type.getSlots()) {
				
				if ((s != slot) && !s.isCube()) {
					
					SpecialItemType other = getSelectedItem(s);
					
					if (other == type) {
						changed |= setItem(s, null, null);
					}
				}
			}
		}

		if (changed);
			itemsChanged(null);
	}

	private SpecialItemType getSelectedItem(Slot slot) {
		
		ListBox list = listBoxes.get(slot);
		
		int i = list.getSelectedIndex();
		
		if (i <= 0)
			return null;
		else
			return SpecialItemType.valueOf(list.getValue(i));
	}

	public NumberSpinner getEliteDamagePercent() {
		return eliteDamagePercent;
	}

	public NumberSpinner getAreaDamageEquipment() {
		return areaDamageEquipment;
	}
	
	public Map<Slot, ItemHolder> getItems() {
		Map<Slot, ItemHolder> map = new TreeMap<Slot, ItemHolder>();
		
		for (Slot slot : Slot.values()) {
			if (this.listBoxes.containsKey(slot)) {
				SpecialItemType type = this.getSelectedItem(slot);
				
				if (type != null) {
					ItemHolder item = new ItemHolder();
					AttributeData data = new AttributeData();

					item.setType(type);
					item.setAttributes(data);
					map.put(slot, item);
					
					for (SpecialItemType.Attribute a : type.getAttributes()) {
						
						BaseSpinner<?> spinner = this.attributeSpinners.get(slot).get(a.getLabel());
						
						if (spinner != null) {
							
							if (spinner instanceof NumberSpinner) {
								int n = ((NumberSpinner)spinner).getValue();
								data.put(a.getLabel(), n);
							} else {
								double n = ((DoubleSpinner)spinner).getValue();
								data.put(a.getLabel(), (int)(n * 10.0));
							}
						}
					}
				}
			}
		}
		
		return map;
	}

	
	public void setItems(Map<Slot, ItemHolder> items) {
	
		this.disableListeners = true;
		
		boolean changed = false;
		Set<Slot> all = new TreeSet<Slot>();
		
		for (Slot slot : Slot.values()) {
			if (this.listBoxes.containsKey(slot))
				all.add(slot);
		}
		
		for (Map.Entry<Slot, ItemHolder> e : items.entrySet()) {
			Slot slot = e.getKey();
			all.remove(slot);
			
			ItemHolder item = e.getValue();
			changed |= setItem(slot, item.getType(), item.getAttributes());
		}

		for (Slot slot : all) {
			changed |= setItem(slot, null, null);
		}
		
		this.disableListeners = false;
		
		if (changed)
			itemsChanged(null);
	}

	private boolean setItem(final Slot slot, SpecialItemType type, AttributeData data) {

		boolean changed = false;

		SpecialItemType prev = selected.get(slot);
		selected.put(slot, type);
		
		int row = rows.get(slot);
		
		if ((prev != null) && (type != prev)) {
			table2.removeCell(row+1, 1);
			attributeSpinners.get(slot).clear();
			
			changed |= true;
		}

		if (type != prev)
			setItem(slot, type);

		if ((type != null) && (type != prev)) {

			changed |= true;

			FlexTable aTable = new FlexTable();
			table2.setWidget(row+1, 1, aTable);
			
			SpecialItemType.Attribute[] aList = type.getAttributes();
			
			int n = 0;
			
			for (SpecialItemType.Attribute a : aList) {
				String name = a.getLabel();
				Label label = new Label(name + ":", false);
				aTable.setWidget(n, 0, label);
				
				double s = a.getScalar();
				
				if (s > 100) {
					DoubleSpinner spinner = new DoubleSpinner();
					spinner.setIncrement(0.1);
					spinner.setMin(a.getMin() / 10.0);
					spinner.setMax(a.getMax() / 10.0);
					spinner.setVisibleLength(6);
					aTable.setWidget(n, 1, spinner);
					attributeSpinners.get(slot).put(name, spinner);
					
					final SpecialItemType.Attribute thisAttribute = a;
					
					spinner.addChangeHandler(new ChangeHandler(){
	
						@Override
						public void onChange(ChangeEvent event) {
							attributeValueChanged(slot, thisAttribute);
						}});
				} else {
					NumberSpinner spinner = new NumberSpinner();
					spinner.setMin(a.getMin());
					spinner.setMax(a.getMax());
					spinner.setVisibleLength(6);
					aTable.setWidget(n, 1, spinner);
					attributeSpinners.get(slot).put(name, spinner);
					
					final SpecialItemType.Attribute thisAttribute = a;
					
					spinner.addChangeHandler(new ChangeHandler(){
	
						@Override
						public void onChange(ChangeEvent event) {
							attributeValueChanged(slot, thisAttribute);
						}});
				}
				
				n++;
			}
			
		}
		
		if (type != null) {
			SpecialItemType.Attribute[] aList = type.getAttributes();
			
			for (SpecialItemType.Attribute a : aList) {
				BaseSpinner<?> spinner = attributeSpinners.get(slot).get(a.getLabel());
				Integer value = (data != null) ? data.get(a.getLabel()) : null;
				
				if (value == null) {
					if (slot.isCube()) {
						// TODO Get Min value for others that need min value as default for cube
						if (type == SpecialItemType.MeticulousBolts)
							value = a.getMin();
						else
							value = a.getMax(); 
					} else {
						// TODO Get Min value for others that need max value as default 
						if (type == SpecialItemType.MeticulousBolts)
							value = a.getMax(); 
						else
							value = a.getMin();
					}
				}

				if (spinner instanceof NumberSpinner) {
					if (value != ((NumberSpinner)spinner).getValue()) {
						((NumberSpinner)spinner).setValue(value);
						changed = true;
					} 
				} else {
					if (value != (int)(((DoubleSpinner)spinner).getValue() * 10.0)) {
						((DoubleSpinner)spinner).setValue(value / 10.0);
						changed = true;
					} 
				}
			}
		}

		
		return changed;
	}

	protected void attributeValueChanged(Slot slot, Attribute thisAttribute) {
		itemsChanged(null);
	}

	private void setItem(Slot slot, SpecialItemType type) {
		
		ListBox list = listBoxes.get(slot);
		
		if (list != null) {
			if (type == null) {
				list.setSelectedIndex(0);
				
				anchors.get(slot).setHref("javascript:void(0)");
			} else {
				int n = list.getItemCount();
				anchors.get(slot).setHref(type.getUrl());
				
				for (int i = 0; i < n; i++) {
					if (list.getValue(i).equals(type.name())) {
						list.setSelectedIndex(i);
						return;
					}
				}
			}
		}
	}

	protected void itemsChanged(ChangeEvent event) {

		if (!disableListeners) {
			for (ChangeHandler h : handlers)
				h.onChange(event);
			
			Map<Slot, ItemHolder> items = this.getItems();
			
			for (ItemsChangedListener l : listeners)
				l.itemsChanged(items);
		}

	}

	public void addChangeHandler(ChangeHandler handler) {
		this.handlers.add(handler);
	}

	public int getNumNats() {
		return getSetCount(ItemSet.Nats);
	}

	private int getSetCount(ItemSet set) {
		
		if (set.getMaxPieces() > 2) {
			return this.setCounts.get(set).getValue();
		} else {
			return this.setToggles.get(set).getValue() ? 2 : 0;
		}
	}

	public boolean isTnt() {
		return (this.getSelectedItem(Slot.Hands) == SpecialItemType.TnT) ||
				(this.getSelectedItem(Slot.CubeArmor) == SpecialItemType.TnT);
	}

	public double getTntPercent() {
		
		if (!isTnt()) {
			return 0;
		}

		if (this.getSelectedItem(Slot.Hands) == SpecialItemType.TnT)
			return getItemAttributeValue(Slot.Hands, SpecialItemType.TnT.getAttributes()[0]);
		else
			return getItemAttributeValue(Slot.CubeArmor, SpecialItemType.TnT.getAttributes()[0]);
	}

	private double getItemAttributeValue(Slot slot, Attribute attribute) {
		
		BaseSpinner<?> spinner = this.attributeSpinners.get(slot).get(attribute.getLabel());
		
		if (spinner == null) {
			if (attribute.getScalar() > 100.0) {
				return attribute.getMin() / 10.0;
			} else {
				return attribute.getMin();
			}
		}
		
		if (spinner instanceof NumberSpinner) {
			return attribute.getRawAttributeValue(((NumberSpinner)spinner).getValue());
		} else {
			return attribute.getRawAttributeValue((int)(((DoubleSpinner)spinner).getValue() * 10.0));
		}
	}

	public boolean isPridesFall() {
		return (this.getSelectedItem(Slot.Head) == SpecialItemType.PridesFall)
				|| (this.getSelectedItem(Slot.CubeArmor) == SpecialItemType.PridesFall);
	}

	public int getNumCrimson() {
		return getSetCount(ItemSet.Crimson);
	}

	public int getNumBorns() {
		return getSetCount(ItemSet.Borns);
	}

	public boolean isLeorics() {
		return (this.getSelectedItem(Slot.Head) == SpecialItemType.Leorics) ||
				(this.getSelectedItem(Slot.CubeArmor) == SpecialItemType.Leorics);
	}

	public double getLeoricsPercent() {
		if (!isLeorics()) {
			return 0;
		}

		if (this.getSelectedItem(Slot.Head) == SpecialItemType.Leorics)
			return getItemAttributeValue(Slot.Head, SpecialItemType.Leorics.getAttributes()[0]);
		else
			return getItemAttributeValue(Slot.CubeArmor, SpecialItemType.Leorics.getAttributes()[0]);
		
	}

	public Map<String, Integer> getSetCounts() {
		
		Map<String, Integer> map = new TreeMap<String, Integer>();
		
		for (ItemSet set : ItemSet.values()) {
			int n = getSetCount(set);
			
			if (n > 0) 
				map.put(set.getSlug(), n);
		}

		return map;
	}

	public void setSetCounts(Map<String, Integer> map) {

		this.disableListeners = true;
		boolean changed = false;
		
		for (ItemSet set : ItemSet.values()) {
			Integer n = map.get(set.getSlug());
			
			if (n == null)
				n = 0;

			n = Math.min(set.getMaxPieces(), n);
			
			int prev = getSetCount(set);
			
			if (prev != n) {
				setSetCount(set, n);
				changed = true;
			}
		}
		
		this.disableListeners = false;
		
		if (changed)
			this.itemsChanged(null);
	}

	private void setSetCount(ItemSet set, Integer n) {
		
		if (n == null)
			n = 0;
		
		if (set.getMaxPieces() > 2) {
			this.setCounts.get(set).setValue(n);
		} else {
			this.setToggles.get(set).setValue(n >= 2);
		}
	}

	public boolean isHuntersWrath() {
		return (this.getSelectedItem(Slot.Waist) == SpecialItemType.HuntersWrath) ||
				(this.getSelectedItem(Slot.CubeArmor) == SpecialItemType.HuntersWrath);
	}

	public boolean isYangs() {
		return (this.getSelectedItem(Slot.MainHand) == SpecialItemType.Yangs) ||
				(this.getSelectedItem(Slot.CubeWeapon) == SpecialItemType.Yangs);
	}
	
	public NumberSpinner getNumAncients() {
		return numAncients;
	}

	public SimpleCheckBox getOtherSets() {
		return otherSets;
	}

}
