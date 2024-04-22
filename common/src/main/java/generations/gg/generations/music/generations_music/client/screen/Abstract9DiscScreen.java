package generations.gg.generations.music.generations_music.client.screen;

import generations.gg.generations.music.generations_music.description.Abstract9DiscHolderDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Abstract9DiscScreen<B extends Abstract9DiscHolderDescription> extends CottonInventoryScreen<B> {
    public Abstract9DiscScreen(B description, Inventory playerInventory, Component title) {
        super(description, playerInventory.player, title);
    }
}
