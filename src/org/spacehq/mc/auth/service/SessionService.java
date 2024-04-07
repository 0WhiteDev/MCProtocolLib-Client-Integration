package org.spacehq.mc.auth.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.auth.exception.profile.ProfileException;
import org.spacehq.mc.auth.exception.profile.ProfileLookupException;
import org.spacehq.mc.auth.exception.profile.ProfileNotFoundException;
import org.spacehq.mc.auth.exception.property.ProfileTextureException;
import org.spacehq.mc.auth.exception.property.PropertyException;
import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.auth.util.Base64;
import org.spacehq.mc.auth.util.HTTP;
import org.spacehq.mc.auth.util.UUIDSerializer;
import us.whitedev.proxy.function.SessionAction;

import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * Service used for session-related queries.
 */
public class SessionService {
	private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
	private static final String JOIN_URL = BASE_URL + "join";
	private static final String HAS_JOINED_URL = BASE_URL + "hasJoined";
	private static final String PROFILE_URL = BASE_URL + "profile";

	private static final PublicKey SIGNATURE_KEY;
	private static final Gson GSON;

	static {
		InputStream in = null;
		try {
			in = SessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int length = -1;
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();

			SIGNATURE_KEY = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(out.toByteArray()));
		} catch (Exception e) {
			throw new ExceptionInInitializerError("Missing/invalid yggdrasil public key.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDSerializer()).create();
	}

	private final Proxy proxy;

	/**
	 * Creates a new SessionService instance.
	 */
	public SessionService() {
		this(Proxy.NO_PROXY);
	}

	/**
	 * Creates a new SessionService instance.
	 *
	 * @param proxy Proxy to use when making HTTP requests.
	 */
	public SessionService(Proxy proxy) {
		if (proxy == null) {
			throw new IllegalArgumentException("Proxy cannot be null.");
		}

		this.proxy = proxy;
	}

	/**
	 * Calculates the server ID from a base string, public key, and secret key.
	 *
	 * @param base      Base server ID to use.
	 * @param publicKey Public key to use.
	 * @param secretKey Secret key to use.
	 * @return The calculated server ID.
	 * @throws IllegalStateException If the server ID hash algorithm is unavailable.
	 */
	public String getServerId(String base, PublicKey publicKey, SecretKey secretKey) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(base.getBytes(StandardCharsets.ISO_8859_1));
			digest.update(secretKey.getEncoded());
			digest.update(publicKey.getEncoded());
			return new BigInteger(digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Server ID hash algorithm unavailable.", e);
		}
	}

	/**
	 * Joins a server.
	 *
	 * @param profile             Profile to join the server with.
	 * @param authenticationToken Authentication token to join the server with.
	 * @param serverId            ID of the server to join.
	 * @throws RequestException If an error occurs while making the request.
	 */
	public void joinServer(GameProfile profile, String authenticationToken, String serverId) throws RequestException {
		JoinServerRequest request = new JoinServerRequest(authenticationToken, profile.getId(), serverId);
		HTTP.makeRequest(this.proxy, SessionAction.JOINAUTH, request, null);
	}

	/**
	 * Gets the profile of the given user if they are currently logged in to the given server.
	 *
	 * @param name     Name of the user to get the profile of.
	 * @param serverId ID of the server to check if they're logged in to.
	 * @return The profile of the given user, or null if they are not logged in to the given server.
	 * @throws RequestException If an error occurs while making the request.
	 */
	public GameProfile getProfileByServer(String name, String serverId) throws RequestException {
		HasJoinedResponse response = HTTP.makeRequest(this.proxy, HAS_JOINED_URL + "?username=" + name + "&serverId=" + serverId, null, HasJoinedResponse.class);
		if (response != null && response.id != null) {
			GameProfile result = new GameProfile(response.id, name);
			if (response.properties != null) {
				result.getProperties().addAll(response.properties);
			}

			return result;
		} else {
			return null;
		}
	}

	/**
	 * Fills in the properties of a profile.
	 *
	 * @param profile Profile to fill in the properties of.
	 * @return The given profile, after filling in its properties.
	 * @throws ProfileException If the property lookup fails.
	 */
	public GameProfile fillProfileProperties(GameProfile profile) throws ProfileException {
		if (profile.getId() == null) {
			return profile;
		}

		try {
			MinecraftProfileResponse response = HTTP.makeRequest(this.proxy, PROFILE_URL + "/" + UUIDSerializer.fromUUID(profile.getId()) + "?unsigned=false", null, MinecraftProfileResponse.class);
			if (response == null) {
				throw new ProfileNotFoundException("Couldn't fetch profile properties for " + profile + " as the profile does not exist.");
			}

			if (response.properties != null) {
				profile.getProperties().addAll(response.properties);
			}

			return profile;
		} catch (RequestException e) {
			throw new ProfileLookupException("Couldn't look up profile properties for " + profile + ".", e);
		}
	}

	/**
	 * Fills in the textures of a profile.
	 *
	 * @param profile       Profile to fill in the textures of.
	 * @param requireSecure Whether to require the textures to be securely signed.
	 * @return The given profile, after filling in its textures.
	 * @throws PropertyException If an error occurs while retrieving the profile's textures.
	 */
	public GameProfile fillProfileTextures(GameProfile profile, boolean requireSecure) throws PropertyException {
		GameProfile.Property textures = profile.getProperty("textures");
		if (textures != null) {
			if (!textures.hasSignature()) {
				throw new ProfileTextureException("Signature is missing from textures payload.");
			}

			if (!textures.isSignatureValid(SIGNATURE_KEY)) {
				throw new ProfileTextureException("Textures payload has been tampered with. (signature invalid)");
			}

			MinecraftTexturesPayload result;
			try {
				String json = new String(Base64.decode(textures.getValue().getBytes(StandardCharsets.UTF_8)));
				result = GSON.fromJson(json, MinecraftTexturesPayload.class);
			} catch (Exception e) {
				throw new ProfileTextureException("Could not decode texture payload.", e);
			}

			if (result.profileId == null || !result.profileId.equals(profile.getId())) {
				throw new ProfileTextureException("Decrypted textures payload was for another user. (expected id " + profile.getId() + " but was for " + result.profileId + ")");
			}

			if (result.profileName == null || !result.profileName.equals(profile.getName())) {
				throw new ProfileTextureException("Decrypted textures payload was for another user. (expected name " + profile.getName() + " but was for " + result.profileName + ")");
			}

			if (requireSecure) {
				if (result.isPublic) {
					throw new ProfileTextureException("Decrypted textures payload was public when secure data is required.");
				}

				Calendar limit = Calendar.getInstance();
				limit.add(Calendar.DATE, -1);
				Date validFrom = new Date(result.timestamp);
				if (validFrom.before(limit.getTime())) {
					throw new ProfileTextureException("Decrypted textures payload is too old. (" + validFrom + ", needs to be at least " + limit + ")");
				}
			}

			if (result.textures != null) {
				profile.getTextures().putAll(result.textures);
			}
		}

		return profile;
	}

	@Override
	public String toString() {
		return "SessionService{}";
	}

	private static class JoinServerRequest {
		private final String accessToken;
		private final UUID selectedProfile;
		private final String serverId;

		protected JoinServerRequest(String accessToken, UUID selectedProfile, String serverId) {
			this.accessToken = accessToken;
			this.selectedProfile = selectedProfile;
			this.serverId = serverId;
		}
	}

	private static class HasJoinedResponse {
		public UUID id;
		public List<GameProfile.Property> properties;
	}

	private static class MinecraftProfileResponse {
		public UUID id;
		public String name;
		public List<GameProfile.Property> properties;
	}

	private static class MinecraftTexturesPayload {
		public long timestamp;
		public UUID profileId;
		public String profileName;
		public boolean isPublic;
		public Map<GameProfile.TextureType, GameProfile.Texture> textures;
	}
}
