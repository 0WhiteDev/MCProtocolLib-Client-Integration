package org.spacehq.mc.protocol;

import org.spacehq.packetlib.Session;

public interface ServerLoginHandler {

	void loggedIn(Session session);

}
