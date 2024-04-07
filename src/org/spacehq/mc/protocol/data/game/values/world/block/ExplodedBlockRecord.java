package org.spacehq.mc.protocol.data.game.values.world.block;

public class ExplodedBlockRecord {

	private final int x;
	private final int y;
	private final int z;

	public ExplodedBlockRecord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExplodedBlockRecord that = (ExplodedBlockRecord) o;

		if (x != that.x) return false;
		if (y != that.y) return false;
        return z == that.z;
    }

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}

}
