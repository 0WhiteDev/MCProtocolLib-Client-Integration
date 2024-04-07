package org.spacehq.mc.protocol.data.game.values.entity;

public class ProjectileData implements ObjectData {

	private final int ownerId;

	public ProjectileData(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerId() {
		return this.ownerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProjectileData that = (ProjectileData) o;

        return ownerId == that.ownerId;
    }

	@Override
	public int hashCode() {
		return ownerId;
	}

}
