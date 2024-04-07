package org.spacehq.mc.protocol.data.status.handler;

import org.spacehq.mc.protocol.data.status.ServerStatusInfo;
import org.spacehq.packetlib.Session;


public interface ServerInfoHandler {

	void handle(Session session, ServerStatusInfo info);

}
