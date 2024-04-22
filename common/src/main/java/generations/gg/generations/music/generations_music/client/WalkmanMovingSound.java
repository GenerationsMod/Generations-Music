package generations.gg.generations.music.generations_music.client;

import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class WalkmanMovingSound extends AbstractTickableSoundInstance implements ControllableVolume {

    private final SoundEvent soundEvent;
    private final Player player;
    private final UUID discUUID;
    private float backupVolume = volume;

    public WalkmanMovingSound(SoundEvent soundEvent, UUID discUUID) {
        super(soundEvent, SoundSource.RECORDS, RandomSource.create());
        this.soundEvent = soundEvent;
        this.player = Minecraft.getInstance().player;
        this.discUUID = discUUID;
        this.looping = false;
        this.delay = 0;
    }

    @Override
    public void tick() { // https://stackoverflow.com/questions/57277755/music-discs-have-do-not-get-quieter-by-distance-in-my-minecraft-1-14-4-mod
        if (!DiscHolderHelper.containsUUID(discUUID, player.getInventory())) {
            if (volume > 0.0F) {
                backupVolume = volume;
            }
            volume = 0.0F;
            return;
        }
        if (!this.player.isAlive() || !DiscHolderHelper.discHolderContainsSound(soundEvent, player.getInventory(), discUUID)) {
            stop();
        } else {
            this.x = (float) this.player.getX();
            this.y = (float) this.player.getY();
            this.z = (float) this.player.getZ();
            this.volume = backupVolume;
        }
    }

    @Override
    public void setVolume(float vol) {
        volume = vol;
        backupVolume = vol;
    }
}
