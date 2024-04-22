package generations.gg.generations.music.generations_music.forge;

import generations.gg.generations.music.generations_music.client.GenerationsMusicClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GenerationsMusicClientForge {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        GenerationsMusicClient.init();
    }
}
