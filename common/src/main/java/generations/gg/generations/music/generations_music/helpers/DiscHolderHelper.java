package generations.gg.generations.music.generations_music.helpers;


import dev.architectury.networking.NetworkManager;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.PacketRegistry;
import generations.gg.generations.music.generations_music.inventory.DiscData;
import generations.gg.generations.music.generations_music.inventory.DiscDataProvider;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import io.netty.buffer.Unpooled;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.List;
import java.util.UUID;

public class DiscHolderHelper {

    public static final SoundEvent MISSING_EVENT = SoundEvent.createVariableRangeEvent(new ResourceLocation(GenerationsMusic.MOD_ID, "missing"));
    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static final DiscData EMPTY_DATA = new DiscData() {
        @Override
        public int getContainerSize() {
            return 0;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void set(int slot, ItemStack stack) {

        }

        @Override
        public ItemStack get(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack remove(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public List<ContainerListener> getListeners() {
            return null;
        }

        @Override
        public void setStacks(NonNullList<ItemStack> stacks) {

        }

        @Override
        public void setVolume(float vol) {

        }

        @Override
        public float getVolume() {
            return 1.0f;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public void setActive(boolean active) {

        }

        @Override
        public int getSelected() {
            return 0;
        }

        @Override
        public void setSelected(int selected) {

        }

        @Override
        public UUID getId() {
            return EMPTY_UUID;
        }

        @Override
        public void setId(UUID id) {

        }

        @Override
        public void stopSelectedDisc() {

        }
    };

    public static DiscData getInventory(ItemStack stack, Inventory inv) {


        if (!stack.getOrCreateTag().contains("discData")) {
            if (!inv.player.level().isClientSide) { // Is this ever called?
                inv.setChanged();
            }
        }

    }

    public static int getSelectedSlot(ItemStack stack) {
        return getData(stack).getSelected();
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
        DiscData discInv = getInventory(stack, inv);
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

    public static ItemStack getDiscInSlot(DiscData stack, int slot) {
        ItemStack discStack = ItemStack.EMPTY;
        if (MusicHelper.mc.player != null) {
            discStack = stack.get(slot);
        }
        return discStack;
    }

    public static UUID getUUID(ItemStack stack) {
        return getData(stack).getId();
    }

    public static boolean containsUUID(UUID uuid, Inventory inventory) {
        for (ItemStack stack : inventory.items) {
            if (getUUID(stack).equals(uuid)) {
                return true;
            }
        }
        return getUUID(inventory.offhand.get(0)).equals(uuid);
    }

    public static DiscData getStack(UUID uuid, Inventory inventory) {
        for (var stack : inventory.items) {
            var data = ((DiscDataProvider) (Object) stack).getDiscData();
            if (data.getId().equals(uuid)) {
                return data;
            }
        }
        return ((DiscDataProvider) (Object) (getUUID(inventory.offhand.get(0)).equals(uuid) ? inventory.offhand.get(0) : ItemStack.EMPTY)).getDiscData();
    }

    public static void toggleActive(ItemStack stack) {
        getData(stack).toggleActive();
    }

    public static boolean isActive(ItemStack stack) {
        return getData(stack).isActive();
    }

    public static float getVolume(ItemStack stack) {
        return getData(stack).getVolume();
    }

    public static void setRandomSelectedSlot(int slot) {
    }

    public static DiscData getData(ItemStack stack) {
        return ((DiscDataProvider) (Object) stack).getDiscData();
    }

    public static DiscData getData(Inventory inventory, int slot) {
        return ((DiscDataProvider) (Object) inventory.getItem(slot)).getDiscData();
    }
}
