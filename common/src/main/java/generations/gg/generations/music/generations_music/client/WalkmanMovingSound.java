package generations.gg.generations.music.generations_music.client;

import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class WalkmanMovingSound extends AbstractTickableSoundInstance {

    private final SoundEvent soundEvent;
    private final Player player;
    private final UUID discUUID;

    public WalkmanMovingSound(SoundEvent soundEvent, UUID discUUID) {
        super(soundEvent, SoundSource.RECORDS, RandomSource.create());
        this.soundEvent = soundEvent;
        this.player = Minecraft.getInstance().player;
        this.discUUID = discUUID;
        this.looping = false;
        this.delay = 0;
    }

    @Override
    public void tick() {
        if (!DiscHolderHelper.containsUUID(discUUID, player.getInventory())) {
            volume = 0.0F;
            return;
        }
        if (!this.player.isAlive() || !DiscHolderHelper.discHolderContainsSound(soundEvent, player.getInventory(), discUUID)) {
            stop();
        } else {
            this.x = (float) this.player.getX();
            this.y = (float) this.player.getY();
            this.z = (float) this.player.getZ();
            this.volume = DiscHolderHelper.getStack(discUUID, player.getInventory()).getVolume();
        }
    }
}
