package org.spacehq.mc.protocol.data.message;

public class ClickEvent implements Cloneable {

	private final ClickAction action;
	private final String value;

	public ClickEvent(ClickAction action, String value) {
		this.action = action;
		this.value = value;
	}

	public ClickAction getAction() {
		return this.action;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public ClickEvent clone() {
		return new ClickEvent(this.action, this.value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClickEvent that = (ClickEvent) o;

		if (action != that.action) return false;
        return value.equals(that.value);
    }

	@Override
	public int hashCode() {
		int result = action.hashCode();
		result = 31 * result + value.hashCode();
		return result;
	}

}
