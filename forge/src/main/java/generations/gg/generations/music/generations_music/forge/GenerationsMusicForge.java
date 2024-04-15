package generations.gg.generations.music.generations_music.forge;

import dev.architectury.platform.forge.EventBuses;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GenerationsMusic.MOD_ID)
public class GenerationsMusicForge {
    public GenerationsMusicForge() {
        EventBuses.registerModEventBus(GenerationsMusic.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        GenerationsMusic.init();
    }
}
