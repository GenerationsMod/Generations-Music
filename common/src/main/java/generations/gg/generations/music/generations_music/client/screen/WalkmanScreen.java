package generations.gg.generations.music.generations_music.client.screen;

import generations.gg.generations.music.generations_music.description.WalkmanDescription;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WalkmanScreen extends Abstract9DiscScreen<WalkmanDescription> {

    public WalkmanScreen(WalkmanDescription description, Inventory playerInventory, Component title) {
        super(description, playerInventory, title);
    }
}
