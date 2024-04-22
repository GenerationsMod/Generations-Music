package generations.gg.generations.music.generations_music.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generic9DiscInventory implements Container, ContainerListener {

    final NonNullList<ItemStack> stacks;
    final List<ContainerListener> listeners = new ArrayList<>();
    private final int containerSize;

    public Generic9DiscInventory(int rows) {
        this.containerSize = rows * 9;
        this.stacks = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    public Generic9DiscInventory(NonNullList<ItemStack> stacks, int rows) {
        this(rows);
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            this.stacks.set(i, stack);
        }
    }

    @Override
    public int getContainerSize() {
        return containerSize;
    }

    @Override
    public void clearContent() {
        setChanged();
        stacks.clear();
    }

    @Override
    public boolean isEmpty() {
        return stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return stacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack split = stacks.get(slot).split(amount);
        setChanged();
        return split;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack remove = stacks.remove(slot);
        setChanged();
        return remove;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        stacks.set(slot, stack);
        setChanged();
    }

    @Override
    public void setChanged() {
        containerChanged(this);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.getItem() instanceof RecordItem;
    }

    @Override
    public void containerChanged(Container sender) {
        listeners.forEach(inventoryListener -> inventoryListener.containerChanged(sender));
    }

    public void addListener(ContainerListener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    @SuppressWarnings("unused") // Keeping for potential use later
    public void removeListener(ContainerListener... listeners) {
        this.listeners.removeAll(Arrays.asList(listeners));
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
}
