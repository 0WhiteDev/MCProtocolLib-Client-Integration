package us.whitedev.proxy.repository;

import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import org.spacehq.packetlib.Client;

public class ClientRep {
    private static ClientRep clientRep;
    private Client client;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public static ClientRep getInstance(){
        if(clientRep == null){
            clientRep = new ClientRep();
        }
        return clientRep;
    }

    public Client getClient() {
        return client;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch, boolean onGround){
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        client.getSession().send(new ClientPlayerPositionRotationPacket(onGround, x, y, z, yaw, pitch));
    }

    public void setRotation(float yaw, float pitch, boolean onGround){
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        client.getSession().send(new ClientPlayerRotationPacket(onGround, yaw, pitch));
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
