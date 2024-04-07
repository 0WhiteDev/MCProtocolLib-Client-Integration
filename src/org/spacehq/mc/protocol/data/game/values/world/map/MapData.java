package org.spacehq.mc.protocol.data.game.values.world.map;

import java.util.Arrays;

public class MapData {
	private final int columns;
	private final int rows;
	private final int x;
	private final int y;
	private final byte[] data;

	public MapData(int columns, int rows, int x, int y, byte[] data) {
		this.columns = columns;
		this.rows = rows;
		this.x = x;
		this.y = y;
		this.data = data;
	}

	public int getColumns() {
		return this.columns;
	}

	public int getRows() {
		return this.rows;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public byte[] getData() {
		return this.data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MapData mapData = (MapData) o;

		if (columns != mapData.columns) return false;
		if (rows != mapData.rows) return false;
		if (x != mapData.x) return false;
		if (y != mapData.y) return false;
        return Arrays.equals(data, mapData.data);
    }

	@Override
	public int hashCode() {
		int result = columns;
		result = 31 * result + rows;
		result = 31 * result + x;
		result = 31 * result + y;
		result = 31 * result + Arrays.hashCode(data);
		return result;
	}

}
