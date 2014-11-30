package com.dawg6.web.sentry.client;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.dawg6.gwt.client.ApplicationPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class BasePanel extends ApplicationPanel {

	protected boolean disableListeners = false;

	protected void loadForm() {

		if (!loadStorage())
			loadCookies();
	}

	private boolean loadStorage() {

		boolean result = false;

		if (Storage.isLocalStorageSupported()) {
			Storage storage = Storage.getLocalStorageIfSupported();

			Collection<String> cookies = null;

			if (Cookies.isCookieEnabled()) {
				cookies = Cookies.getCookieNames();
			}

			for (Field f : getFields()) {
				String value = storage.getItem(f.name);

				if (value != null) {
					// do nothing
				} else if ((cookies != null) && (cookies.contains(f.name))) {
					value = getCookie(f.name, f.defaultValue);
					storage.setItem(f.name, value);
					Cookies.removeCookie(f.name);
				} else {
					value = f.defaultValue;
					storage.setItem(f.name, value);
				}

				setFieldValue(f, value);
			}

			result = true;
		}

		return result;
	}

	protected boolean loadCookies() {
		boolean result = false;

		if (Cookies.isCookieEnabled()) {
			result = true;

			for (Field f : getFields()) {
				loadCookie(f);
			}
		}

		return result;
	}

	protected void saveForm() {
		saveFields(getFields());
	}

	protected void saveFields(Field... fields) {
		if (Storage.isLocalStorageSupported()) {
			Storage storage = Storage.getLocalStorageIfSupported();

			for (Field f : fields) {
				String value = getFieldValue(f);
				storage.setItem(f.name, value);
			}
			
		} else {
			for (Field f : fields) {
				saveCookie(f);
			}
		}
	}
	
	protected Field getField(Widget w) {
		
		for (Field f : getFields()) {
			if (f.field == w)
				return f;
		}
		
		return null;
	}
	
	protected class Field {
		public Widget field;
		public String name;
		public String defaultValue;

		public Field(Widget field, String name, String defaultValue) {
			this.field = field;
			this.name = name;
			this.defaultValue = defaultValue;
		}
	}

	protected Field[] getFields() {
		return new Field[0];
	}

	protected void saveCookie(Field field) {
		String value = getFieldValue(field);
		saveCookie(value, field.name);
	}

	protected void loadCookie(Field field) {
		String value = getCookie(field.name, field.defaultValue);
		setFieldValue(field, value);
	}

	protected static final long ONE_YEAR = 1000 * 60 * 60 * 24 * 365;

	protected void saveCookie(String value, String name) {

		Date date = new Date();

		Cookies.setCookie(name, value, new Date(date.getTime() + ONE_YEAR));

	}

	protected String getCookie(String name, String defaultValue) {
		String value = Cookies.getCookie(name);

		if (value == null)
			value = defaultValue;

		return value;
	}

	protected int getValue(NumberSpinner spinner) {
		Integer value = spinner.getValue();

		if (value == null) {
			spinner.setValue(0);
			value = 0;
		}

		return value;
	}

	protected double getValue(DoubleSpinner spinner) {
		Double value = spinner.getValue();

		if (value == null) {
			spinner.setValue(0.0);
			value = 0.0;
		}

		return value;
	}

	protected double getValue(TextBox textBox) {

		try {
			String text = textBox.getText();
			return Double.valueOf(text);
		} catch (Exception e) {
			textBox.setValue("0");
			return 0.0;
		}
	}

	protected int getValue(IntegerBox box) {

		try {
			Integer value = box.getValue();

			if (value == null) {
				box.setValue(0);
				value = 0;
			}

			return value;
		} catch (Exception e) {
			box.setValue(0);
			return 0;
		}
	}

	protected double getValue(DoubleBox box) {

		try {
			Double value = box.getValue();

			if (value == null) {
				box.setValue(0.0);
				value = 0.0;
			}

			return value;
		} catch (Exception e) {
			box.setValue(0.0);
			return 0.0;
		}
	}

	@Override
	protected void onLoad() {

		Field[] fields = getFields();

		for (Field f : fields) {
			if (f.field instanceof ValueBoxBase) {
				final ValueBoxBase<?> tf = (ValueBoxBase<?>) f.field;
				tf.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						tf.selectAll();
					}
				});
			}
		}
	}

	protected String getFieldValue(Field f) {
		Widget field = f.field;
		String defaultValue = f.defaultValue;

		if (field instanceof TextBox)
			return getFieldValue((TextBox) field, defaultValue);
		else if (field instanceof IntegerBox)
			return getFieldValue((IntegerBox) field, defaultValue);
		else if (field instanceof NumberSpinner)
			return getFieldValue((NumberSpinner) field, defaultValue);
		else if (field instanceof DoubleSpinner)
			return getFieldValue((DoubleSpinner) field, defaultValue);
		else if (field instanceof DoubleBox)
			return getFieldValue((DoubleBox) field, defaultValue);
		else if (field instanceof SimpleCheckBox)
			return getFieldValue((SimpleCheckBox) field, defaultValue);
		else if (field instanceof ListBox)
			return getFieldValue((ListBox) field, defaultValue);
		else
			return defaultValue;
	}

	protected String getFieldValue(ListBox field, String defaultValue) {
		int i = field.getSelectedIndex();

		if (i < 0)
			return defaultValue;

		return field.getValue(i);
	}

	protected String getFieldValue(SimpleCheckBox field, String defaultValue) {
		return String.valueOf(field.getValue());
	}

	protected String getFieldValue(DoubleBox field, String defaultValue) {
		try {
			return String.valueOf(field.getValue());
		} catch (Exception e) {
			field.setText(defaultValue);
			return defaultValue;
		}
	}

	protected String getFieldValue(DoubleSpinner field, String defaultValue) {
		try {
			return String.valueOf(field.getValue());
		} catch (Exception e) {
			field.setText(defaultValue);
			return defaultValue;
		}
	}

	protected String getFieldValue(NumberSpinner field, String defaultValue) {
		try {
			return String.valueOf(field.getValue());
		} catch (Exception e) {
			field.setText(defaultValue);
			return defaultValue;
		}
	}

	protected String getFieldValue(IntegerBox field, String defaultValue) {
		try {
			return String.valueOf(field.getValue());
		} catch (Exception e) {
			field.setText(defaultValue);
			return defaultValue;
		}
	}

	protected String getFieldValue(TextBox field, String defaultValue) {
		String value = field.getValue();

		if ((value != null) && (value.length() > 0))
			return field.getValue();

		field.setText(defaultValue);
		return defaultValue;
	}

	public void populateFormData(Map<String, String> data) {
		for (Field f : getFields()) {
			String name = f.name;
			String value = getFieldValue(f);
			data.put(name, value);
		}

	}

	public void restoreFormData(Map<String, String> data) {

		for (Field f : getFields()) {
			String name = f.name;
			String value = data != null ? data.get(name) : null;
			setFieldValue(f, value);
		}
	}

	protected void setFieldValue(Field f, String value) {

		Widget field = f.field;

		if (value == null)
			value = f.defaultValue;

		if (field instanceof TextBox)
			setFieldValue((TextBox) field, value);
		else if (field instanceof IntegerBox)
			setFieldValue((IntegerBox) field, value);
		else if (field instanceof NumberSpinner)
			setFieldValue((NumberSpinner) field, value);
		else if (field instanceof DoubleSpinner)
			setFieldValue((DoubleSpinner) field, value);
		else if (field instanceof DoubleBox)
			setFieldValue((DoubleBox) field, value);
		else if (field instanceof SimpleCheckBox)
			setFieldValue((SimpleCheckBox) field, value);
		else if (field instanceof ListBox)
			setFieldValue((ListBox) field, value);
	}

	protected void setFieldValue(ListBox field, String value) {
		for (int i = 0; i < field.getItemCount(); i++) {
			String v = field.getValue(i);

			if (v.equals(value)) {
				field.setSelectedIndex(i);
				return;
			}
		}
	}

	protected void setFieldValue(SimpleCheckBox field, String value) {
		field.setValue(Boolean.valueOf(value));
	}

	protected void setFieldValue(DoubleBox field, String value) {
		try {
			field.setValue(Double.valueOf(value));
		} catch (Exception e) {
			field.setValue(0.0);
		}
	}

	protected void setFieldValue(DoubleSpinner field, String value) {
		try {
			field.setValue(Double.valueOf(value));
		} catch (Exception e) {
			field.setValue(0.0);
		}
	}

	protected void setFieldValue(NumberSpinner field, String value) {
		try {
			field.setValue(Integer.valueOf(value));
		} catch (Exception e) {
			field.setValue(0);
		}
	}

	protected void setFieldValue(IntegerBox field, String value) {
		try {
			field.setValue(Integer.valueOf(value));
		} catch (Exception e) {
			field.setValue(0);
		}
	}

	protected void setFieldValue(TextBox field, String value) {
		field.setValue(value);
	}

}
