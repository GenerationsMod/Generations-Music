package generations.gg.generations.music.generations_music.description;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;

import static generations.gg.generations.music.generations_music.world.menu.GenerationsMusicMenus.WALKMAN_HANDLER_TYPE;

public class WalkmanDescription extends Abstract9DiscHolderDescription {

    public WalkmanDescription(int syncId, Inventory inv, InteractionHand hand) {
        super(WALKMAN_HANDLER_TYPE.get(), syncId, inv, hand);
    }
}
