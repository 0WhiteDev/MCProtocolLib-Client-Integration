package org.spacehq.mc.protocol.data.game;

import org.spacehq.opennbt.tag.builtin.CompoundTag;

public class ItemStack {

	private final int id;
	private final int amount;
	private final int data;
	private final CompoundTag nbt;

	public ItemStack(int id) {
		this(id, 1);
	}

	public ItemStack(int id, int amount) {
		this(id, amount, 0);
	}

	public ItemStack(int id, int amount, int data) {
		this(id, amount, data, null);
	}

	public ItemStack(int id, int amount, int data, CompoundTag nbt) {
		this.id = id;
		this.amount = amount;
		this.data = data;
		this.nbt = nbt;
	}

	public int getId() {
		return this.id;
	}

	public int getAmount() {
		return this.amount;
	}

	public int getData() {
		return this.data;
	}

	public CompoundTag getNBT() {
		return this.nbt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ItemStack itemStack = (ItemStack) o;

		if (amount != itemStack.amount) return false;
		if (data != itemStack.data) return false;
		if (id != itemStack.id) return false;
        return nbt != null ? nbt.equals(itemStack.nbt) : itemStack.nbt == null;
    }

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + amount;
		result = 31 * result + data;
		result = 31 * result + (nbt != null ? nbt.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ItemStack{" +
			"id=" + id +
			", amount=" + amount +
			", data=" + data +
			", nbt=" + nbt +
			'}';
	}
}
