package org.spacehq.mc.protocol.data.game.values.world.block;

import org.spacehq.mc.protocol.data.game.Position;

public class BlockChangeRecord {

	private final Position position;
	private final int block;

	public BlockChangeRecord(Position position, int block) {
		this.position = position;
		this.block = block;
	}

	public Position getPosition() {
		return this.position;
	}

	public int getBlock() {
		return this.block;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BlockChangeRecord record = (BlockChangeRecord) o;

		if (block != record.block) return false;
        return position.equals(record.position);
    }

	@Override
	public int hashCode() {
		int result = position.hashCode();
		result = 31 * result + block;
		return result;
	}

}
