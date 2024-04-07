package org.spacehq.mc.protocol.data.game.values.world.effect;

public class HardLandingEffectData implements WorldEffectData {

	private final int damagingDistance;

	public HardLandingEffectData(int damagingDistance) {
		this.damagingDistance = damagingDistance;
	}

	public int getDamagingDistance() {
		return this.damagingDistance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HardLandingEffectData that = (HardLandingEffectData) o;

        return damagingDistance == that.damagingDistance;
    }

	@Override
	public int hashCode() {
		return damagingDistance;
	}

}
