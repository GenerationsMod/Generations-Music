package generations.gg.generations.music.generations_music.world.item;

import generations.gg.generations.music.generations_music.client.ControllableVolume;
import generations.gg.generations.music.generations_music.client.WalkmanMovingSound;
import generations.gg.generations.music.generations_music.description.WalkmanDescription;
import generations.gg.generations.music.generations_music.helpers.DiscHelper;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.helpers.MusicHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WalkmanItem extends Abstract9DiscItem {

    public WalkmanItem(Item.Properties properties, int rows, String name) {
        super(properties, rows);
    }

    @Override
    public Component getDescription() {
        return Component.translatable("desc.musicexpansion.walkman").withStyle(ChatFormatting.GRAY);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void playSelectedDisc(ItemStack walkman) {
        MusicHelper.playTrack(walkman, new WalkmanMovingSound(DiscHelper.getEvent(DiscHolderHelper.getDiscInSlot(walkman, DiscHolderHelper.getSelectedSlot(walkman))), DiscHolderHelper.getUUID(walkman)));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void stopSelectedDisc(ItemStack stack) {
        MusicHelper.stopTrack(stack);
    }

    @Override
    public void setVolume(ItemStack stack, float volume) {
        if (MusicHelper.playingTracks.containsKey(DiscHolderHelper.getUUID(stack))) {
            ((ControllableVolume) MusicHelper.playingTracks.get(DiscHolderHelper.getUUID(stack))).setVolume(volume / 100f);
        }
    }
}
