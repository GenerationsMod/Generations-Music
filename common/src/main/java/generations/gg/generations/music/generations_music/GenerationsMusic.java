package generations.gg.generations.music.generations_music;

import generations.gg.generations.music.generations_music.world.item.GenerationsMusicItems;
import generations.gg.generations.music.generations_music.world.sound.GenerationsSounds;
import net.minecraft.resources.ResourceLocation;

public class GenerationsMusic {

    /** The mod id for the Generations-Music mod. */
    public static final String MOD_ID = "generations_music";

    /** The config for the Generations-Music mod. */
//    public static Config CONFIG;

    public static void init() {
        GenerationsSounds.init();
        GenerationsMusicItems.init();
//        CONFIG = ConfigLoader.loadConfig(Config.class, "name", "config");
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}
