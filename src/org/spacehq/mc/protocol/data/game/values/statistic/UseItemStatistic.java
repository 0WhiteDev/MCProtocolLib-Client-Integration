package org.spacehq.mc.protocol.data.game.values.statistic;

public class UseItemStatistic implements Statistic {

	private final int id;

	public UseItemStatistic(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UseItemStatistic that = (UseItemStatistic) o;

        return id == that.id;
    }

	@Override
	public int hashCode() {
		return id;
	}

}
