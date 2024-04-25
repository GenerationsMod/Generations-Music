package generations.gg.generations.music.generations_music.description;


import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.inventory.DiscData;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import it.unimi.dsi.fastutil.objects.Object2IntFunctions;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;

import java.util.UUID;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static io.github.cottonmc.cotton.gui.widget.data.Insets.ROOT_PANEL;

public class Abstract9DiscHolderDescription extends SyncedGuiDescription {

    public final ItemStack holder;
    public final UUID uuid;
    final WGridPanel root;
    final WSprite selected = new WSprite(new ResourceLocation(GenerationsMusic.MOD_ID, "textures/misc/selected.png"));
    private final DiscData discData;

    public Abstract9DiscHolderDescription(MenuType<?> type, int syncId, Inventory playerInv, InteractionHand hand) {
        super(type, syncId, playerInv);
        root = new WGridPanel();
        root.setInsets(ROOT_PANEL);
        root.setBackgroundPainter(BackgroundPainter.VANILLA);
        holder = playerInv.player.getItemInHand(hand);
        uuid = DiscHolderHelper.getUUID(holder);
        discData = DiscHolderHelper.getData(holder);

        var rows = discData.getContainerSize()/9;

        discData.addListener(sender -> {
            if (!playerInv.player.level().isClientSide) {
                    playerInv.setChanged();
            }
        });
        root.add(selected, 0, 1);
        root.add(new WItemSlot(discData, 0, 9, rows, false).setInputFilter(stack -> stack.getItem() instanceof RecordItem), 0, 1);
        root.add(new WButton(Component.literal("▶")).setOnClick(discData::playSelectedDisc), 0, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏹")).setOnClick(discData::stopSelectedDisc), 2, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏮")).setOnClick(() -> changeSelectedSlot(SelectAction.PREVIOUS)), 4, 2 + rows - 1);
        root.add(new WButton(Component.literal("⏭")).setOnClick(() -> changeSelectedSlot(SelectAction.NEXT)), 6, 2 + rows - 1);
        root.add(new WButton(Component.literal("?")).setOnClick(() -> changeSelectedSlot(SelectAction.RANDOM)), 8, 2 + rows - 1);
        var slider = new WLabeledSlider(0, 100, Axis.HORIZONTAL, Component.literal(discData.getVolume() + "%"));

        slider.setLabelUpdater(i -> {
            discData.setVolume(i/100f);
            return Component.literal(i + "%");
        });
        root.add(slider, 1, 3 + rows - 1, 7, 1);
        root.add(new WPlayerInvPanel(playerInventory) {
        }, 0, 4 + rows - 1);

        var slot = DiscHolderHelper.getSelectedSlot(holder);
        selected.setLocation((slot % 9) * 18 + 7, (1 + (slot/9)) * 18 + 7);
        setRootPanel(root);
        root.validate(this);
    }

    void changeSelectedSlot(SelectAction action) {
        var slot = action.process(discData);
        DiscHolderHelper.setSelectedSlot(slot, playerInventory.selected);
        selected.setLocation((slot % 9) * 18 + 7, (1 + (slot/9)) * 18 + 7);
    }

    public enum SelectAction {
        PREVIOUS(key -> ((DiscData) key).getSelected() + 1),
        NEXT(key -> ((DiscData) key).getSelected() - 1),
        RANDOM(key -> Minecraft.getInstance().player.getRandom().nextInt(((DiscData) key).getContainerSize()));

        private final Object2IntFunction<DiscData> function;

        SelectAction(Object2IntFunction<DiscData> function) {
            this.function = function;
        }

        public int process(DiscData data) {
            return Mth.clamp(function.applyAsInt(data), 0, data.getContainerSize()-1);
        }
    }

    public static class WPlayerInvPanel extends io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel {

        public WPlayerInvPanel(Inventory playerInventory) {
            super(playerInventory);
            hotbar.setOutputFilter(stack -> ItemStack.matches(stack, playerInventory.getSelected()));
        }
    }
}
