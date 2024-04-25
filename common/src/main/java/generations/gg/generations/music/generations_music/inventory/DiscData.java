package generations.gg.generations.music.generations_music.inventory;

import generations.gg.generations.music.generations_music.client.WalkmanMovingSound;
import generations.gg.generations.music.generations_music.helpers.MusicHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface DiscData extends Container, ContainerListener, ControllableVolume {

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

//    private static NonNullList<ItemStack> getStacks(CompoundTag compoundTag) {
//        NonNullList<ItemStack> list = NonNullList.withSize(compoundTag.getInt("Size"), ItemStack.EMPTY);
//        ContainerHelper.loadAllItems(compoundTag, list);
//
//        return list;
//    }


    @Override
    int getContainerSize();
    @Override
    default void clearContent() {
        setChanged();
        clear();
    }

    void clear();

    @Override
    boolean isEmpty();
    @Override
    default ItemStack getItem(int slot) {
        return get(slot);
    }

    @Override
    default ItemStack removeItem(int slot, int amount) {
        var item = get(slot);
        ItemStack split = item.split(amount);
        set(slot, item);
        setChanged();

        return split;
    }

    void set(int slot, ItemStack stack);

    ItemStack get(int slot);

    ItemStack remove(int slot);

    @Override
    default ItemStack removeItemNoUpdate(int slot) {
        ItemStack remove = remove(slot);
        setChanged();
        return remove;
    }


    @Override
    default void setItem(int slot, ItemStack stack) {
        set(slot, stack);
        setChanged();
    }

    @Override
    default void setChanged() {
        containerChanged(this);
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.getItem() instanceof RecordItem;
    }

    @Override
    default void containerChanged(Container sender) {
        getListeners().forEach(inventoryListener -> inventoryListener.containerChanged(sender));
    }

    default void addListener(ContainerListener... listeners) {
        getListeners().addAll(Arrays.asList(listeners));
    }

    @SuppressWarnings("unused") // Keeping for potential use later
    default void removeListener(ContainerListener... listeners) {
        getListeners().removeAll(Arrays.asList(listeners));
    }

    List<ContainerListener> getListeners();

    public void setStacks(NonNullList<ItemStack> stacks);

    public void setVolume(float vol);

    public float getVolume();

    public boolean isActive();

    public void setActive(boolean active);

    public int getSelected();

    public void setSelected(int selected);

    public UUID getId();

    public void setId(UUID id);

    @Environment(EnvType.CLIENT)
    default void playSelectedDisc() {
        MusicHelper.playTrack(this, new WalkmanMovingSound());
    }

    @Environment(EnvType.CLIENT)
    void stopSelectedDisc();

    default void toggleActive() {
        this.setActive(!isActive());
    }
}
