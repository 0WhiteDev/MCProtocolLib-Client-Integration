package org.spacehq.mc.protocol.data.game.values.world.map;

public class MapPlayer {

	private final int centerX;
	private final int centerZ;
	private final int iconSize;
	private final int iconRotation;

	public MapPlayer(int centerX, int centerZ, int iconSize, int iconRotation) {
		this.centerX = centerX;
		this.centerZ = centerZ;
		this.iconSize = iconSize;
		this.iconRotation = iconRotation;
	}

	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterZ() {
		return this.centerZ;
	}

	public int getIconSize() {
		return this.iconSize;
	}

	public int getIconRotation() {
		return this.iconRotation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MapPlayer mapPlayer = (MapPlayer) o;

		if (centerX != mapPlayer.centerX) return false;
		if (centerZ != mapPlayer.centerZ) return false;
		if (iconRotation != mapPlayer.iconRotation) return false;
        return iconSize == mapPlayer.iconSize;
    }

	@Override
	public int hashCode() {
		int result = centerX;
		result = 31 * result + centerZ;
		result = 31 * result + iconSize;
		result = 31 * result + iconRotation;
		return result;
	}

}
