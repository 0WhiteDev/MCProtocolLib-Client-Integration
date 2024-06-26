package org.spacehq.mc.protocol.data.game;

public class Rotation {
	private final float pitch;
	private final float yaw;
	private final float roll;

	public Rotation() {
		this(0, 0, 0);
	}

	public Rotation(float pitch, float yaw, float roll) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public float getPitch() {
		return this.pitch;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getRoll() {
		return this.roll;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Rotation rotation = (Rotation) o;

		if (Float.compare(rotation.pitch, pitch) != 0) return false;
		if (Float.compare(rotation.roll, roll) != 0) return false;
        return Float.compare(rotation.yaw, yaw) == 0;
    }

	@Override
	public int hashCode() {
		int result = (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
		result = 31 * result + (yaw != +0.0f ? Float.floatToIntBits(yaw) : 0);
		result = 31 * result + (roll != +0.0f ? Float.floatToIntBits(roll) : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Rotation{" +
			"pitch=" + pitch +
			", yaw=" + yaw +
			", roll=" + roll +
			'}';
	}
}
