package org.spacehq.mc.protocol.data.game.values.statistic;

public class BreakItemStatistic implements Statistic {

	private final int id;

	public BreakItemStatistic(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BreakItemStatistic that = (BreakItemStatistic) o;

        return id == that.id;
    }

	@Override
	public int hashCode() {
		return id;
	}

}
