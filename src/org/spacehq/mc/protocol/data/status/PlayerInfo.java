package org.spacehq.mc.protocol.data.status;

import org.spacehq.mc.auth.data.GameProfile;

import java.util.Arrays;

public class PlayerInfo {

	private final int max;
	private final int online;
	private final GameProfile[] players;

	public PlayerInfo(int max, int online, GameProfile[] players) {
		this.max = max;
		this.online = online;
		this.players = players;
	}

	public int getMaxPlayers() {
		return this.max;
	}

	public int getOnlinePlayers() {
		return this.online;
	}

	public GameProfile[] getPlayers() {
		return this.players;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlayerInfo that = (PlayerInfo) o;

		if (max != that.max) return false;
		if (online != that.online) return false;
        return Arrays.equals(players, that.players);
    }

	@Override
	public int hashCode() {
		int result = max;
		result = 31 * result + online;
		result = 31 * result + Arrays.hashCode(players);
		return result;
	}

}
