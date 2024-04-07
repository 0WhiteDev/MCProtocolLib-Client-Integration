package org.spacehq.mc.protocol.packet.ingame.server.world;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerChunkDataPacket implements Packet {

	private byte[] packetData;

	@Override
	public void read(NetInput in) throws IOException {
		this.packetData = in.readBytes(in.available());
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeBytes(this.packetData);
	}

	@Override
	public boolean isPriority() {
		return false;
	}

}
