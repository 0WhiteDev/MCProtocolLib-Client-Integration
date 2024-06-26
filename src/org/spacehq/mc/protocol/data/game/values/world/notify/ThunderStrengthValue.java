package org.spacehq.mc.protocol.data.game.values.world.notify;

public class ThunderStrengthValue implements ClientNotificationValue {

	private final float strength;

	public ThunderStrengthValue(float strength) {
		if (strength > 1) {
			strength = 1;
		}

		if (strength < 0) {
			strength = 0;
		}

		this.strength = strength;
	}

	public float getStrength() {
		return this.strength;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ThunderStrengthValue that = (ThunderStrengthValue) o;

        return Float.compare(that.strength, strength) == 0;
    }

	@Override
	public int hashCode() {
		return (strength != +0.0f ? Float.floatToIntBits(strength) : 0);
	}

}
