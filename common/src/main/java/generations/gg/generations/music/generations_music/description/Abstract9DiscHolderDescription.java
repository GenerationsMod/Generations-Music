package generations.gg.generations.music.generations_music.description;


import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.inventory.Generic9DiscInventory;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.UUID;

import static io.github.cottonmc.cotton.gui.widget.data.Insets.ROOT_PANEL;

public class Abstract9DiscHolderDescription extends SyncedGuiDescription {

    public final ItemStack holder;
    public final UUID uuid;
    final WGridPanel root;
    final WSprite selected = new WSprite(new ResourceLocation(GenerationsMusic.MOD_ID, "textures/misc/selected.png"));

    public Abstract9DiscHolderDescription(MenuType<?> type, int syncId, Inventory playerInv, InteractionHand hand) {
        super(type, syncId, playerInv);
        root = new WGridPanel();
        root.setInsets(ROOT_PANEL);
        root.setBackgroundPainter(BackgroundPainter.VANILLA);
        holder = playerInv.player.getItemInHand(hand);
        var rows = holder.getItem() instanceof Abstract9DiscItem disc ? disc.rows() : 1;
        uuid = DiscHolderHelper.getUUID(holder);
        Generic9DiscInventory discHolderInv = DiscHolderHelper.getInventory(holder, playerInv);
        discHolderInv.addListener(sender -> {
            if (!playerInv.player.level().isClientSide) {
//              Set the items tag in inventory, by getting the tag and setting the "Items" tag to the resulting ListTag from Inventories.toTag() using the stacks from the current inventory
                CompoundTag invTag = ContainerHelper.saveAllItems(holder.getTag(), discHolderInv.getStacks());
                if (invTag != null) {
                    holder.getOrCreateTag().put("Items", invTag.getList("Items", 10));
                    playerInv.setChanged();
                }
            }
        });
        root.add(selected, 0, 1);
        root.add(new WItemSlot(discHolderInv, 0, 9, rows, false).setInputFilter(stack -> stack.getItem() instanceof RecordItem), 0, 1);
        root.add(new WButton(Component.literal("▶")).setOnClick(() -> {
            if(holder.getItem() instanceof Abstract9DiscItem discItem) discItem.playSelectedDisc(playerInv.getItem(Mth.clamp(DiscHolderHelper.getSlotFromUUID(playerInv, uuid), 0, 8)));
        }), 0, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏹")).setOnClick(() -> {
            if(holder.getItem() instanceof Abstract9DiscItem disc) disc.stopSelectedDisc(playerInv.getItem(Mth.clamp(DiscHolderHelper.getSlotFromUUID(playerInv, uuid), 0, 8)));
        }), 2, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏮")).setOnClick(() -> {
            changeSelectedSlot(Math.max(0, DiscHolderHelper.getSelectedSlot(playerInv.getItem(DiscHolderHelper.getSlotFromUUID(playerInv, uuid))) - 1));
        }), 4, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏭")).setOnClick(() -> {
            changeSelectedSlot(Math.min((rows * 9) - 1, DiscHolderHelper.getSelectedSlot(playerInv.getItem(DiscHolderHelper.getSlotFromUUID(playerInv, uuid))) + 1));
        }), 6, 2 + rows - 1);
        root.add(new WButton(Component.literal("?")).setOnClick(() -> changeSelectedSlot(playerInv.player.getRandom().nextInt(rows * 9))), 8, 2 + rows - 1);
        var slider = new WLabeledSlider(0, 100, Axis.HORIZONTAL, Component.translatable("text.generations_music.volume"));

        slider.setLabelUpdater(i -> {
            if(holder.getItem() instanceof Abstract9DiscItem discItem) discItem.setVolume(playerInv.getItem(Mth.clamp(DiscHolderHelper.getSlotFromUUID(playerInv, uuid), 0, 8)), i);
            return Component.literal(i + "%");
        });
        root.add(slider, 1, 3 + rows - 1, 7, 1);
        root.add(new WPlayerInvPanel(playerInventory, false), 0, 4 + rows - 1);

        var slot = DiscHolderHelper.getSelectedSlot(holder);
        selected.setLocation((slot % 9) * 18, (1 + (slot/9)) * 18);
        setRootPanel(root);
        root.validate(this);
    }

    void changeSelectedSlot(int slot) {
        DiscHolderHelper.setSelectedSlot(slot, DiscHolderHelper.getSlotFromUUID(playerInventory, uuid));
        selected.setLocation((slot % 9) * 18, (1 + (slot/9)) * 18);
    }
}
