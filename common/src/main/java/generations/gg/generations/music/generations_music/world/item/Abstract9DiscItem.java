package generations.gg.generations.music.generations_music.world.item;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import generations.gg.generations.music.generations_music.description.WalkmanDescription;
import generations.gg.generations.music.generations_music.helpers.DiscHelper;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Abstract9DiscItem extends Item {

    private int rows;

    public Abstract9DiscItem(Item.Properties properties, int rows) {
        super(properties.stacksTo(1).arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES));
        this.rows = rows;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(getDescription());
        tooltip.add(Component.translatable("desc.musicexpansion.active_keybinds").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("text.musicexpansion.active_status").append(WordUtils.capitalize(String.valueOf(DiscHolderHelper.isActive(stack)))).withStyle(ChatFormatting.GRAY));
        if (Minecraft.getInstance().player != null) {
            ItemStack disc = DiscHolderHelper.getDiscInSlot(stack, DiscHolderHelper.getSelectedSlot(stack));
            if (!disc.isEmpty()) {
                tooltip.add(Component.translatable("text.musicexpansion.current_track").append(DiscHelper.getDesc(disc)).withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("text.musicexpansion.current_track.nothing").withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, world, tooltip, context);
    }

    public abstract Component getDescription();

    @Environment(EnvType.CLIENT)
    public abstract void playSelectedDisc(ItemStack stack);

    @Environment(EnvType.CLIENT)
    public abstract void stopSelectedDisc(ItemStack stack);

    public abstract void setVolume(ItemStack stack, float volume);

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        DiscHolderHelper.setupInitialTags(player.getItemInHand(hand), rows);
        player.getInventory().setChanged();
        if (!world.isClientSide()) {

            if (player.isShiftKeyDown()) {
                DiscHolderHelper.toggleActive(player.getItemInHand(hand));
                player.getInventory().setChanged();
                return InteractionResultHolder.success(player.getItemInHand(hand));
            } else {
                MenuRegistry.openExtendedMenu((ServerPlayer) player, new ExtendedMenuProvider() {
                    @Override
                    public void saveExtraData(FriendlyByteBuf buf) {
                        buf.writeEnum(hand);
                    }

                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(getDescriptionId());
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                        return new WalkmanDescription(syncId, inv, hand);
                    }
                });
            }

        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        DiscHolderHelper.setupInitialTags(stack, rows);
    }

    public CompoundTag toTag(CompoundTag tag, NonNullList<ItemStack> stacks) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.get(i);
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putByte("Slot", (byte) i);
            itemStack.save(compoundTag);
            listTag.add(compoundTag);
        }

        if (!listTag.isEmpty()) {
            tag.put("Items", listTag);
        }

        return tag;
    }

    public int rows() {
        return rows;
    }
}
