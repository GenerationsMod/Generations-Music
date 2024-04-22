package generations.gg.generations.music.generations_music.helpers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.Optional;

/**
 * Various helpers related to the new NBT discs, that I would rather not copy and paste repeatedly/retype constantly
 */
public class DiscHelper {

    /**
     * Get the {@link SoundEvent} this custom disc is set to
     *
     * @param stack The disc in question, needs tag data
     * @return The {@link SoundEvent} this disc is linked to, otherwise null if it doesn't exist
     */
    public static Optional<SoundEvent> getSetTrack(ItemStack stack) {
        return BuiltInRegistries.SOUND_EVENT.getOptional(getTrackID(stack));
    }

    /**
     * Get the {@link ResourceLocation} for the track this disc is set to
     *
     * @param stack The stack to check
     * @return The {@link ResourceLocation} for the set track
     */
    public static ResourceLocation getTrackID(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("track")) {
            return ResourceLocation.tryParse(tag.getString("track"));
        } else {
            return DiscHolderHelper.MISSING_EVENT.getLocation();
        }
    }

    /**
     * Set's the disc's track
     *
     * @param stack The stack to modify
     * @param id    The {@link ResourceLocation} of the {@link SoundEvent in question}
     */
    public static void setTrack(ItemStack stack, ResourceLocation id) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("track", id.toString());
        stack.setTag(tag);
    }

    /**
     * Gets the {@link SoundEvent} for a {@link RecordItem}
     * @param stack The disc stack to get the event of
     * @return The {@link SoundEvent} of the disc, or null if one doesn't exist
     */
    public static SoundEvent getEvent(ItemStack stack) {
        return stack.getItem() instanceof RecordItem recordItem ? recordItem.getSound() : DiscHolderHelper.MISSING_EVENT;
    }

    /**
     * Get's the description for a {@link RecordItem}
     * @param stack The disc stack to get the description of
     * @return The {@link Component} description of the disc, typically the song name and author
     */
    // Client only because of getDescription()
    public static Component getDesc(ItemStack stack) {
        return stack.getItem() instanceof RecordItem record ? record.getDescription() : Component.empty();
    }

}
