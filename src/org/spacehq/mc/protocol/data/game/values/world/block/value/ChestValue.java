package org.spacehq.mc.protocol.data.game.values.world.block.value;

public class ChestValue implements BlockValue {

	private final int viewers;

	public ChestValue(int viewers) {
		this.viewers = viewers;
	}

	public int getViewers() {
		return this.viewers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChestValue that = (ChestValue) o;

        return viewers == that.viewers;
    }

	@Override
	public int hashCode() {
		return viewers;
	}

}
