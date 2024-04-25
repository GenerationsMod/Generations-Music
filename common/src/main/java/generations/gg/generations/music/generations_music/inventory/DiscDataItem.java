package generations.gg.generations.music.generations_music.inventory;

import generations.gg.generations.music.generations_music.client.WalkmanMovingSound;
import generations.gg.generations.music.generations_music.helpers.DiscHelper;
import generations.gg.generations.music.generations_music.helpers.MusicHelper;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DiscDataItem implements DiscData {
    //    TODO: Use in 1.20.5 for ItemStackComponents
//    public static Codec<NonNullList<ItemStack>> STACK_CODEC = CompoundTag.CODEC.xmap(compoundTag -> {
//        NonNullList<ItemStack> list = NonNullList.withSize(compoundTag.getInt("Size"), ItemStack.EMPTY);
//        ContainerHelper.loadAllItems(compoundTag, list);
//
//        return list;
//    }, list -> {
//        var tag = new CompoundTag();
//        ContainerHelper.saveAllItems(tag, list);
//
//        return tag;
//    });
//    public static Codec<DiscData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            STACK_CODEC.fieldOf("stacks").forGetter(discData -> discData.stacks),
//            Codec.FLOAT.fieldOf("volume").forGetter(discData -> discData.volume),
//            Codec.INT.fieldOf("slot").forGetter(discData -> discData.slot),
//            UUIDUtil.CODEC.fieldOf("id").forGetter(discData -> discData.id)
//    ).apply(instance, DiscData::new));

    private final List<ContainerListener> listeners = new ArrayList<>();
    private final ItemStack stack;
    private CompoundTag rootTag;


    public DiscDataItem(ItemStack stack) {
        this.stack = stack;
//        this(stack, NonNullList.withSize((stack.getItem() instanceof Abstract9DiscItem disc ? disc.rows() : 1) * 9, ItemStack.EMPTY), 1.0f, 0, UUID.randomUUID());

        if(hasData(stack)) {
            this.rootTag = stack.getTag();
        } else {
            setId(UUID.randomUUID());
            setStacks(NonNullList.withSize((stack.getItem() instanceof Abstract9DiscItem disc ? disc.rows() : 1) * 9, ItemStack.EMPTY));
        }
    }

    private boolean hasData(ItemStack stack) {
        if (!stack.hasTag()) return false;
        if (!stack.getTag().contains("discData")) return false;
        var discData = stack.getTagElement("discData");
        if (!discData.contains("uuid") || !discData.contains("stacks")) return false;
        var stacks = discData.getCompound("stacks");
        return stacks.contains("Size") && stacks.contains("Items");
    }

    private CompoundTag getRootTag() {
        return rootTag;
    }

    private CompoundTag getOrCreateRootTag() {
        if (this.rootTag != null) return this.rootTag;
        return this.rootTag = this.stack.getOrCreateTagElement("discData");
    }

    @Override
    public int getContainerSize() {
        return rootTag != null ? rootTag.getInt("Size") : 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isEmpty() {
        return getStacksTag() != null && getStacksTag().isEmpty();
    }

    @Override
    public void set(int slot, ItemStack stack) {
        var inventoryTag = getStacksTag();

        var key = Integer.toString(slot);

        if(inventoryTag != null) {
            inventoryTag.remove(key);
        }
    }

    @Override
    public ItemStack get(int slot) {
        var inventoryTag = getStacksTag();

        var key = Integer.toString(slot);

        if(inventoryTag != null && inventoryTag.contains(key)) {
            var stack = inventoryTag.getCompound(key);
            return ItemStack.of(stack);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack remove(int slot) {
        var inventoryTag = getStacksTag();

        var key = Integer.toString(slot);

        if(inventoryTag != null && inventoryTag.contains(key)) {
            var stack = inventoryTag.getCompound(key);
            inventoryTag.remove(key);

            return ItemStack.of(stack);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public List<ContainerListener> getListeners() {
        return listeners;
    }

    private CompoundTag getStacksTag() {
        return rootTag != null && rootTag.contains("stacks") ? rootTag.getCompound("stacks") : null;
    }

    @Override
    public void setStacks(NonNullList<ItemStack> stacks) {
        var root = getOrCreateRootTag();

        var stacksTag = new CompoundTag();

        stacksTag.putInt("Size", stacks.size());

        var items = new CompoundTag();

        for (int i = 0; i < stacks.size(); i++) {
            var stack = stacks.get(i);

            if(!stack.isEmpty()) {
                var stackTag = stack.save(new CompoundTag());
                items.put(Integer.toString(i), stackTag);
            }
        }

        stacksTag.put("Items", items);
        root.put("stacks", stacksTag);
    }

    @Override
    public void setVolume(float vol) {
        getOrCreateRootTag().putFloat("volume", 1.0f);
    }

    @Override
    public float getVolume() {
        return rootTag != null && rootTag.contains("volume") ? rootTag.getFloat("volume"): 1.0f;
    }

    @Override
    public boolean isActive() {
        return rootTag != null && rootTag.getBoolean("active");
    }

    @Override
    public void setActive(boolean active) {
        getOrCreateRootTag().putBoolean("active", active);
    }

    @Override
    public int getSelected() {
        return rootTag != null && rootTag.contains("Selected") ? rootTag.getInt("Selected") : -1;
    }

    @Override
    public void setSelected(int selected) {
        getOrCreateRootTag().putInt("Selected", selected);
    }

    @Override
    public UUID getId() {
        return rootTag != null && rootTag.contains("id") ? rootTag.getUUID("id") : null;
    }

    @Override
    public void setId(UUID id) {
        getOrCreateRootTag().putUUID("id", id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void playSelectedDisc() {
        MusicHelper.playTrack(this, new WalkmanMovingSound(DiscHelper.getEvent(get(getSelected())), getId()));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void stopSelectedDisc() {
        MusicHelper.stopTrack(this);
    }
}
