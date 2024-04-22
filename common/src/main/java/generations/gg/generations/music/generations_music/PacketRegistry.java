package generations.gg.generations.music.generations_music;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.GameInstance;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PacketRegistry {

    // Packet Identifiers
    public static final ResourceLocation CHANGE_SLOT_PACKET = new ResourceLocation(GenerationsMusic.MOD_ID, "change_slot");
    public static final ResourceLocation CREATE_RECORD = new ResourceLocation(GenerationsMusic.MOD_ID, "create_record");
    public static final ResourceLocation ALL_RECORDS = new ResourceLocation(GenerationsMusic.MOD_ID, "all_records");
    public static final ResourceLocation PLAY_JUKEBOX_TRACK = new ResourceLocation(GenerationsMusic.MOD_ID, "play_track");
    public static final ResourceLocation SYNC_EVENTS = new ResourceLocation(GenerationsMusic.MOD_ID, "sync_events");
    public static final ResourceLocation ALL_PLAYERS_SERVER = new ResourceLocation(GenerationsMusic.MOD_ID, "all_players_server");
    public static final ResourceLocation ALL_PLAYERS_CLIENT = new ResourceLocation(GenerationsMusic.MOD_ID, "all_players_client");
    public static final ResourceLocation SET_VOLUME = new ResourceLocation(GenerationsMusic.MOD_ID, "set_volume");

    public static void registerServerPackets() {
        // Change slot on disc holder
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, CHANGE_SLOT_PACKET, (buf, context) -> {
            int slot = buf.readInt();
            int invSlot = buf.readInt();
            var player = context.getPlayer();
            context.queue(() -> {
                if (player.getInventory().getItem(invSlot).hasTag()) {
                    player.getInventory().getItem(invSlot).getOrCreateTag().putInt("selected", slot);
                    player.getInventory().setChanged();
                }
            });
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ALL_PLAYERS_SERVER, (buf, context) -> {
            Player player = context.getPlayer();
            String name = buf.readUtf(32767);
            FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer());
            data.writeUtf(name);
            switch (name) {
                case "set_volume", "stop_track" -> data.writeItem(buf.readItem());
                case "play_track" -> {
                    data.writeItem(buf.readItem());
                    data.writeUUID(player.getUUID());
                }
                default -> {
                    return;
                }
            }
            context.queue(() -> NetworkManager.sendToPlayers(GameInstance.getServer().getPlayerList().getPlayers(), ALL_PLAYERS_CLIENT, data));
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SET_VOLUME, (buf, context) -> {
            float volume = buf.readFloat();
            int invSlot = buf.readInt();
            Player player = context.getPlayer();
            context.queue(() -> {
                if (player.getInventory().getItem(invSlot).hasTag()) {
                    player.getInventory().getItem(invSlot).getOrCreateTag().putFloat("volume", volume);
                    player.getInventory().setChanged();
                }
            });
        });
    }

}
