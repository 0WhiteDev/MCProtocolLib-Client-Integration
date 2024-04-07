package client.whitedev.mods.botter.handler;

import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.Arrays;

public class PacketsHandler {

    private static PacketsHandler packetsHandler;
    private final ArrayList<PacketHandler> packetsList = new ArrayList<>();

    public static PacketsHandler getInstance(){
        if(packetsHandler == null){
            packetsHandler = new PacketsHandler();
        }
        return packetsHandler;
    }

    public void handlePacket(Packet packet){
        packetsList.forEach(p -> {
            if(p.getPacket().getName().equals(packet.getClass().getName())){
                p.handlePacket(packet);
            }
        });
    }

    public void registerPackets(PacketHandler... packets){
        this.packetsList.addAll(Arrays.asList(packets));
    }

    public ArrayList<PacketHandler> getPacketsList() {
        return packetsList;
    }
}
