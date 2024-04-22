package generations.gg.generations.music.generations_music.world.menu;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.description.WalkmanDescription;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

import static dev.architectury.registry.menu.MenuRegistry.ofExtended;

public class GenerationsMusicMenus {
    public static DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(GenerationsMusic.MOD_ID, Registries.MENU);

    public static RegistrySupplier<MenuType<WalkmanDescription>> WALKMAN_HANDLER_TYPE = registerHandler("walkmon", (integer, inventory, packetByteBuf) -> {
        return new WalkmanDescription(integer, inventory, packetByteBuf.readEnum(InteractionHand.class));
    });
    private static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> registerHandler(String name, MenuRegistry.ExtendedMenuTypeFactory<T> func) {
        return MENU_TYPES.register(name, () -> ofExtended(func));
    }

    public static void init() {
        MENU_TYPES.register();
    }
}
