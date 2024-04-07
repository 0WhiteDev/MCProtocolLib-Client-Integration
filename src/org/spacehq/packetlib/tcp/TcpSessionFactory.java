package org.spacehq.packetlib.tcp;

import org.spacehq.packetlib.*;

/**
 * A session factory used to create TCP sessions.
 */
public class TcpSessionFactory implements SessionFactory {
	private ProxyInfo clientProxy;

	public TcpSessionFactory() {
	}

	public TcpSessionFactory(ProxyInfo clientProxy) {
		this.clientProxy = clientProxy;
	}

	@Override
	public Session createClientSession(final Client client) {
		return new TcpClientSession(client.getHost(), client.getPort(), client.getPacketProtocol(), client, this.clientProxy);
	}

	@Override
	public ConnectionListener createServerListener(final Server server) {
		return new TcpConnectionListener(server.getHost(), server.getPort(), server);
	}
}
