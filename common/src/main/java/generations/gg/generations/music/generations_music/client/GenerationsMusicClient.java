package generations.gg.generations.music.generations_music.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import generations.gg.generations.music.generations_music.PacketRegistry;
import generations.gg.generations.music.generations_music.client.screen.WalkmanScreen;
import generations.gg.generations.music.generations_music.helpers.DiscHelper;
import generations.gg.generations.music.generations_music.helpers.DiscHolderHelper;
import generations.gg.generations.music.generations_music.helpers.MusicHelper;
import generations.gg.generations.music.generations_music.mixin.WorldRendererAccessor;
import generations.gg.generations.music.generations_music.world.item.Abstract9DiscItem;
import generations.gg.generations.music.generations_music.world.menu.GenerationsMusicMenus;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class GenerationsMusicClient {
    private static KeyMapping playDisc;
    private static KeyMapping stopDisc;
    private static KeyMapping nextDisc;
    private static KeyMapping prevDisc;
    private static KeyMapping randDisc;

    public static void init() {
        MenuRegistry.registerScreenFactory(GenerationsMusicMenus.WALKMAN_HANDLER_TYPE.get(), WalkmanScreen::new);
//        MenuRegistry.registerScreenFactory(MusicExpansion.BOOMBOX_HANDLER_TYPE.get(), BoomboxScreen::new);



        playDisc = registerKeybind("play", GLFW.GLFW_KEY_UP);
        stopDisc = registerKeybind("stop", GLFW.GLFW_KEY_DOWN);
        nextDisc = registerKeybind("next", GLFW.GLFW_KEY_RIGHT);
        prevDisc = registerKeybind("back", GLFW.GLFW_KEY_LEFT);
        randDisc = registerKeybind("random", GLFW.GLFW_KEY_RIGHT_ALT);
        ClientTickEvent.CLIENT_POST.register(GenerationsMusicClient::tick);
        registerClientPackets();
    }

    public static void registerClientPackets() {
        // Get whether or not to allow all configs
//        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.ALL_RECORDS, (client, handler, buf, sender) -> RecordJsonParser.setAllRecords(buf.readBoolean()));
        // Play track
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, PacketRegistry.PLAY_JUKEBOX_TRACK, (NetworkManager.NetworkReceiver) (buf, context) -> {
            ItemStack disc = buf.readItem();
            BlockPos songPosition = buf.readBlockPos();
            Minecraft mc = Minecraft.getInstance();

            context.queue(() -> {
                if (mc.player != null) {
                    if (!disc.isEmpty()) {
                        SoundEvent event = DiscHelper.getEvent(disc);

                        if (event != null) {
                            mc.gui.setNowPlaying(DiscHelper.getDesc(disc));
                            SimpleSoundInstance soundInstance = SimpleSoundInstance.forRecord(event, Vec3.atCenterOf(songPosition));
                            ((WorldRendererAccessor) mc.level).getPlayingSongs().put(songPosition, soundInstance);
                            mc.getSoundManager().play(soundInstance);
                        }
                    }
                }
            });
        });
        // Sync sound events
//        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SYNC_EVENTS, (client, handler, buf, sender) -> {
//            int size = buf.readInt();
//            for (int i = 0; i < size; i++) {
//                ResourceLocation id = buf.readResourceLocation();
//                if (!BuiltInRegistries.SOUND_EVENT.containsKey(id)) {
//                    Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
//                }
//                if (!MusicExpansion.tracks.contains(id)) {
//                    MusicExpansion.tracks.add(id);
//                }
//            }
//        });

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, PacketRegistry.ALL_PLAYERS_CLIENT, (buf, context) -> {
            String name = buf.readUtf();
            switch (name) {
                case "play_track" -> {
                    ItemStack playStack = buf.readItem();
                    UUID uuid = buf.readUUID();

                    if (Minecraft.getInstance().level != null) {
//                        context.queue(() -> MusicHelper.playTrack(playStack, new BoomboxMovingSound(DiscHelper.getEvent(DiscHolderHelper.getDiscInSlot(playStack, DiscHolderHelper.getSelectedSlot(playStack))), DiscHolderHelper.getUUID(playStack), uuid)));
                    }
                }

                case "stop_track" -> {
                    ItemStack stopStack = buf.readItem();
                    if (Minecraft.getInstance().level != null) {
                        context.queue(() -> MusicHelper.stopTrack(stopStack));
                    }
                }

                case "set_volume" -> {
                    ItemStack volumeStack = buf.readItem();
                    if (Minecraft.getInstance().level != null) {
                        context.queue(() -> ((ControllableVolume) MusicHelper.playingTracks.get(DiscHolderHelper.getUUID(volumeStack))).setVolume(DiscHolderHelper.getVolume(volumeStack)));
                    }
                }
                default -> {}
            }
        });
    }


    // client.player should never be null
    private static void tick(Minecraft client) {
        if (playDisc.isDown()) {
            playDisc(client);
        }
        if (stopDisc.isDown()) {
            stopDisc(client);
        }
        if (nextDisc.isDown()) {
            nextDisc(client);
        }
        if (prevDisc.isDown()) {
            prevDisc(client);
        }
        if (randDisc.isDown()) {
            randomDisc(client);
        }
    }

    private static void playDisc(Minecraft client) {
        if (client.player != null) {
            int iSlot = DiscHolderHelper.getActiveDiscHolderSlot(client.player.getInventory());
            if (iSlot > -1) {
                ItemStack stack = client.player.getInventory().getItem(iSlot);
                ((Abstract9DiscItem) stack.getItem()).playSelectedDisc(stack);
            }
        }
    }

    private static void stopDisc(Minecraft client) {
        if (client.player != null) {
            int iSlot = DiscHolderHelper.getActiveDiscHolderSlot(client.player.getInventory());
            if (iSlot > -1) {
                ItemStack stack = client.player.getInventory().getItem(iSlot);
                ((Abstract9DiscItem) stack.getItem()).stopSelectedDisc(stack);
            }
        }
    }


    private static void randomDisc(Minecraft client) {
        if (client.player != null) {
            int iSlot = DiscHolderHelper.getActiveDiscHolderSlot(client.player.getInventory());
            if (iSlot > -1) {
                int slot = client.player.getRandom().nextInt(9);
                DiscHolderHelper.setSelectedSlot(slot, iSlot);
                sendCurrentDiscMessage(client, iSlot, slot);
            }
        }
    }

    private static void prevDisc(Minecraft client) {
        if (client.player != null) {
            int iSlot = DiscHolderHelper.getActiveDiscHolderSlot(client.player.getInventory());
            if (iSlot > -1) {
                int slot = Math.max(0, DiscHolderHelper.getSelectedSlot(client.player.getInventory().getItem(iSlot)) - 1);
                DiscHolderHelper.setSelectedSlot(slot, iSlot);
                sendCurrentDiscMessage(client, iSlot, slot);
            }
        }
    }

    private static void nextDisc(Minecraft client) {
        if (client.player != null) {
            int iSlot = DiscHolderHelper.getActiveDiscHolderSlot(client.player.getInventory());
            if (iSlot > -1) {
                int slot = Math.min(8, DiscHolderHelper.getSelectedSlot(client.player.getInventory().getItem(iSlot)) + 1);
                DiscHolderHelper.setSelectedSlot(slot, iSlot);
                sendCurrentDiscMessage(client, iSlot, slot);
            }
        }
    }

    private static void sendCurrentDiscMessage(Minecraft client, int iSlot, int slot) {
        if (client.player != null) {
            Component desc = DiscHelper.getDesc(DiscHolderHelper.getDiscInSlot(client.player.getInventory().getItem(iSlot), slot));
            if (!desc.equals(Component.literal(""))) {
                client.player.displayClientMessage(Component.translatable("text.generations_music.current_track").append(desc), false);
            } else {
                client.player.displayClientMessage(Component.translatable("text.generations_music.current_track.nothing"), false);
            }
        }
    }


    public static KeyMapping registerKeybind(String name, int key) {
        var mappping = new KeyMapping("key.generations_music." + name, InputConstants.Type.KEYSYM, key, "category.generations_music.binds");
        KeyMappingRegistry.register(mappping);
        return mappping;
    }
}
