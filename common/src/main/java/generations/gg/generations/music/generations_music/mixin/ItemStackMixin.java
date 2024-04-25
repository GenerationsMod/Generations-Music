package generations.gg.generations.music.generations_music.mixin;

import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.inventory.DiscData;
import generations.gg.generations.music.generations_music.inventory.DiscDataItem;
import generations.gg.generations.music.generations_music.inventory.DiscDataProvider;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DiscDataProvider {
    @Shadow public abstract boolean isEmpty();

    @Shadow public abstract Item getItem();

    @Unique private @Nullable DiscDataItem data;

    @Inject(method = "setTag", at = @At("RETURN"))
    private void invalidateData(CompoundTag tag, CallbackInfo ci) {
        if(tag.contains("discData")) {
            data = new DiscDataItem((ItemStack) (Object) this);
        }
    }

    @Override
    public DiscData getDiscData() {
        if(this.isEmpty() || !(getItem() instanceof Abstract9DiscItem)) return DiscHolderHelper.EMPTY_DATA;
        if(data == null) {
            this.data = new DiscDataItem((ItemStack) (Object) this);
        }

        return data;
    }


}
