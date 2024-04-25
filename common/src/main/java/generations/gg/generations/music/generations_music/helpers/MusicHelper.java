package generations.gg.generations.music.generations_music.helpers;

import com.google.common.collect.Maps;
import generations.gg.generations.music.generations_music.inventory.DiscData;
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

    public static void stopTrack(DiscData stack) {
       stopTrack(stack.getId());
    }
    
    public static void playTrack(DiscData stack, SoundInstance instance) {
        if (mc.player != null) {
            if (!mc.getSoundManager().isActive(playingTracks.get(stack.getId()))) {
                ItemStack disc = DiscHolderHelper.getDiscInSlot(stack, stack.getSelected());
                if (!disc.isEmpty()) {
                    mc.gui.setNowPlaying(DiscHelper.getDesc(disc));
                    mc.getSoundManager().play(instance);
                    playingTracks.put(stack.getId(), instance);
                }
            }
        }
    }
}
