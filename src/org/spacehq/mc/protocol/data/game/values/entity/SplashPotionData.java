package org.spacehq.mc.protocol.data.game.values.entity;

public class SplashPotionData implements ObjectData {

	private final int potionData;

	public SplashPotionData(int potionData) {
		this.potionData = potionData;
	}

	public int getPotionData() {
		return this.potionData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SplashPotionData that = (SplashPotionData) o;

        return potionData == that.potionData;
    }

	@Override
	public int hashCode() {
		return potionData;
	}

}
