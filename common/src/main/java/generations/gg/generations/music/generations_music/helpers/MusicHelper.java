package generations.gg.generations.music.generations_music.helpers;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;

/**
 * Helper methods for music or walkman related functions
 * Client only.
 */
public class MusicHelper {

    static final Minecraft mc = Minecraft.getInstance();
    public static final Map<UUID, SoundInstance> playingTracks = Maps.newHashMap();

    public static void stopTrack(UUID uuid) {
        mc.getSoundManager().stop(playingTracks.get(uuid));
        playingTracks.remove(uuid);
    }

    public static void stopTrack(ItemStack stack) {
       stopTrack(DiscHolderHelper.getUUID(stack));
    }
    
    public static void playTrack(ItemStack stack, SoundInstance instance) {
        if (mc.player != null) {
            if (!mc.getSoundManager().isActive(playingTracks.get(DiscHolderHelper.getUUID(stack)))) {
                ItemStack disc = DiscHolderHelper.getDiscInSlot(stack, DiscHolderHelper.getSelectedSlot(stack));
                if (!disc.isEmpty()) {
                    mc.gui.setNowPlaying(DiscHelper.getDesc(disc));
                    mc.getSoundManager().play(instance);
                    playingTracks.put(DiscHolderHelper.getUUID(stack), instance);
                }
            }
        }
    }
}
