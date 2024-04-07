package org.spacehq.packetlib;

/**
 * Listens for new sessions to connect.
 */
public interface ConnectionListener {
	/**
	 * Gets the host the session is listening on.
	 *
	 * @return The listening host.
	 */
    String getHost();

	/**
	 * Gets the port the session is listening on.
	 *
	 * @return The listening port.
	 */
    int getPort();

	/**
	 * Returns true if the listener is listening.
	 *
	 * @return True if the listener is listening.
	 */
    boolean isListening();

	/**
	 * Binds the listener to its host and port.
	 */
    void bind();

	/**
	 * Binds the listener to its host and port.
	 *
	 * @param wait Whether to wait for the listener to finish binding.
	 */
    void bind(boolean wait);

	/**
	 * Binds the listener to its host and port.
	 *
	 * @param wait     Whether to wait for the listener to finish binding.
	 * @param callback Callback to call when the listener has finished binding.
	 */
    void bind(boolean wait, Runnable callback);

	/**
	 * Closes the listener.
	 */
    void close();

	/**
	 * Closes the listener.
	 *
	 * @param wait Whether to wait for the listener to finish closing.
	 */
    void close(boolean wait);

	/**
	 * Closes the listener.
	 *
	 * @param wait     Whether to wait for the listener to finish closing.
	 * @param callback Callback to call when the listener has finished closing.
	 */
    void close(boolean wait, Runnable callback);
}
