package org.spacehq.mc.protocol.data.game.values.world.effect;

public class RecordEffectData implements WorldEffectData {

	private final int recordId;

	public RecordEffectData(int recordId) {
		this.recordId = recordId;
	}

	public int getRecordId() {
		return this.recordId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RecordEffectData that = (RecordEffectData) o;

        return recordId == that.recordId;
    }

	@Override
	public int hashCode() {
		return recordId;
	}

}
