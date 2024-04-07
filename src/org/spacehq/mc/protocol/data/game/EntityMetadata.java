package org.spacehq.mc.protocol.data.game;

import org.spacehq.mc.protocol.data.game.values.entity.MetadataType;

public class EntityMetadata {

	private final int id;
	private final MetadataType type;
	private final Object value;

	public EntityMetadata(int id, MetadataType type, Object value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}

	public int getId() {
		return this.id;
	}

	public MetadataType getType() {
		return this.type;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EntityMetadata metadata = (EntityMetadata) o;

		if (id != metadata.id) return false;
		if (type != metadata.type) return false;
        return value.equals(metadata.value);
    }

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + type.hashCode();
		result = 31 * result + value.hashCode();
		return result;
	}

}
