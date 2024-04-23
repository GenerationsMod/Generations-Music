package generations.gg.generations.music.generations_music.helpers;


import dev.architectury.networking.NetworkManager;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.PacketRegistry;
import generations.gg.generations.music.generations_music.inventory.Generic9DiscInventory;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import io.netty.buffer.Unpooled;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.UUID;

public class DiscHolderHelper {

    public static final SoundEvent MISSING_EVENT = SoundEvent.createVariableRangeEvent(new ResourceLocation(GenerationsMusic.MOD_ID, "missing"));
    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static Generic9DiscInventory getInventory(ItemStack stack, Inventory inv) {
        var rows = stack.getItem() instanceof Abstract9DiscItem disc ? disc.rows() : 1;
        if (!stack.getOrCreateTag().contains("Items")) {
            
            if (!inv.player.level().isClientSide) { // Is this ever called?
                setupInitialTags(stack, rows);
                inv.setChanged();
            }
        }
        NonNullList<ItemStack> stacks = NonNullList.withSize(9 * rows, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(stack.getOrCreateTag(), stacks);
        return new Generic9DiscInventory(stacks, rows);
    }

    public static int getSelectedSlot(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("selected")) {
            return Mth.clamp(tag.getInt("selected"), 0, ((stack.getItem() instanceof Abstract9DiscItem disc ? disc.rows() : 1) * 9) - 1);
        } else {
            tag.putInt("selected", 0);
            stack.setTag(tag);
            return 0;
        }
    }

    public static void setSelectedSlot(int slot, int invSlot) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(slot);
        buf.writeInt(invSlot);
        NetworkManager.sendToServer(PacketRegistry.CHANGE_SLOT_PACKET, buf);
    }

    /**
     * Finds the last specified disc holder in a players inventory
     * @param inventory The {@link Inventory} to search
     * @return The slot number of the walkman if found, otherwise -1
     */
    public static int getActiveDiscHolderSlot(Inventory inventory) {
        int slot = -1;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (isActive(stack)) {
                slot = i;
            }
        }
        return slot;
    }


    /**
     * Check to see if the disc holder in the given inv has the specified event
     * @param event The event to check
     * @param inv The player inventory to check
     * @return true if the walkman has a disc that can play the sound event, false if not
     */
    public static boolean discHolderContainsSound(SoundEvent event, Inventory inv, UUID uuid) {
        ItemStack stack = inv.getItem(getSlotFromUUID(inv, uuid));
        Generic9DiscInventory discInv = getInventory(stack, inv);
        for (int i = 0; i < discInv.getContainerSize(); i++) {
            ItemStack disc = discInv.getItem(i);
            if (disc.getItem() instanceof RecordItem record) {
                if (record.getSound().getLocation().equals(event.getLocation())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getSlotFromUUID(Inventory inv, UUID uuid) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (getUUID(inv.getItem(i)).equals(uuid)) {
               return i;
            }
        }
        return -1;
    }

    public static ItemStack getDiscInSlot(ItemStack stack, int slot) {
        ItemStack discStack = ItemStack.EMPTY;
        if (MusicHelper.mc.player != null && stack.getTag() != null && stack.getTag().contains("Items")) {
            discStack = getInventory(stack, MusicHelper.mc.player.getInventory()).getItem(slot);
        }
        return discStack;
    }

    public static UUID getUUID(ItemStack stack) {
        UUID uuid = EMPTY_UUID;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("uuid")) {
            uuid = UUID.fromString(tag.getString("uuid"));
        }
        return uuid;
    }

    public static void setupInitialTags(ItemStack stack, int rows) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("uuid")) {
            tag.putString("uuid", UUID.randomUUID().toString());
        }
        if (!tag.contains("selected")) {
            tag.putInt("selected", 0);
        }
        if (!tag.contains("active")) {
            tag.putBoolean("active", false);
        }
        if (!tag.contains("Items")) {
            CompoundTag invTag = ContainerHelper.saveAllItems(tag, new Generic9DiscInventory(rows).getStacks());
            tag.put("Items", invTag.getList("Items", 10));
        }
        if (!tag.contains("volume")) {
            tag.putFloat("volume", 100.0F);
        }
        stack.setTag(tag);
    }

    public static boolean containsUUID(UUID uuid, Inventory inventory) {
        for (ItemStack stack : inventory.items) {
            if (getUUID(stack).equals(uuid)) {
                return true;
            }
        }
        return getUUID(inventory.offhand.get(0)).equals(uuid);
    }

    public static ItemStack getStack(UUID uuid, Inventory inventory) {
        for (ItemStack stack : inventory.items) {
            if (getUUID(stack).equals(uuid)) {
                return stack;
            }
        }
        return getUUID(inventory.offhand.get(0)).equals(uuid) ? inventory.offhand.get(0) : ItemStack.EMPTY;
    }

//    public float getVolume() {
//
//    }

    public static void toggleActive(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean("active", !tag.getBoolean("active"));
        stack.setTag(tag);
    }

    public static boolean isActive(ItemStack stack) {
        boolean active = false;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("active")) {
            active = tag.getBoolean("active");
        }
        return active;
    }

    public static float getVolume(ItemStack stack) {
        return stack.getOrCreateTag().getFloat("volume");
    }
}
