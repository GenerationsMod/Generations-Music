package generations.gg.generations.music.generations_music.world.item;

import generations.gg.generations.music.generations_music.client.WalkmanMovingSound;
import generations.gg.generations.music.generations_music.helpers.DiscHelper;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.helpers.MusicHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WalkmanItem extends Abstract9DiscItem {

    public WalkmanItem(Item.Properties properties, int rows, String name) {
        super(properties, rows);
    }

    @Override
    public Component getDescription() {
        return Component.translatable("desc.generations_music.walkmon").withStyle(ChatFormatting.GRAY);
    }
}
