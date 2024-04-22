package generations.gg.generations.music.generations_music.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LevelRenderer.class)
public interface WorldRendererAccessor {

   @Accessor("playingRecords")
   Map<BlockPos, SimpleSoundInstance> getPlayingSongs();
}
