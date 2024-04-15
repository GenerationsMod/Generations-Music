package generations.gg.generations.music.generations_music.fabric;

import generations.gg.generations.music.generations_music.GenerationsMusic;
import net.fabricmc.api.ModInitializer;

public class GenerationsMusicFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GenerationsMusic.init();
    }
}
