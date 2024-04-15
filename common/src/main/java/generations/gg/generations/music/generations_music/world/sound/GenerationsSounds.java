package generations.gg.generations.music.generations_music.world.sound;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class GenerationsSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(GenerationsMusic.MOD_ID, Registries.SOUND_EVENT);
    public static final RegistrySupplier<SoundEvent> AZALEA_TOWN = registerSound("generations_music.music.ambient.azalea_town");
    public static final RegistrySupplier<SoundEvent> CASCARRAFA_CITY = registerSound("generations_music.music.ambient.cascarrafa_city");
    public static final RegistrySupplier<SoundEvent> CERULEAN_CITY = registerSound("generations_music.music.ambient.cerulean_city");
    public static final RegistrySupplier<SoundEvent> ETERNA_CITY = registerSound("generations_music.music.ambient.eterna_city");
    public static final RegistrySupplier<SoundEvent> GOLDENROD_CITY = registerSound("generations_music.music.ambient.goldenrod_city");
    public static final RegistrySupplier<SoundEvent> ICIRRUS_CITY = registerSound("generations_music.music.ambient.icirrus_city");
    public static final RegistrySupplier<SoundEvent> JUBILIFE_VILLAGE = registerSound("generations_music.music.ambient.jubilife_villag");
    public static final RegistrySupplier<SoundEvent> LAKE_OF_RAGE = registerSound("generations_music.music.ambient.lake_of_rage");
    public static final RegistrySupplier<SoundEvent> LAVERRE_CITY = registerSound("generations_music.music.ambient.laverre_city");
    public static final RegistrySupplier<SoundEvent> LILLIE = registerSound("generations_music.music.ambient.lillie");
    public static final RegistrySupplier<SoundEvent> POKEMON_CENTER = registerSound("generations_music.music.ambient.pokemon_center");
    public static final RegistrySupplier<SoundEvent> ROUTE_228 = registerSound("generations_music.music.ambient.route_228");
    public static final RegistrySupplier<SoundEvent> SLUMBERING_WEALD = registerSound("generations_music.music.ambient.slumbering_weal");
    public static final RegistrySupplier<SoundEvent> SURF = registerSound("generations_music.music.ambient.surf");
    public static final RegistrySupplier<SoundEvent> VERMILION_CITY = registerSound("generations_music.music.ambient.vermilion_city");
    public static final RegistrySupplier<SoundEvent> CYNTHIA = registerSound("generations_music.music.battle.cynthia");
    public static final RegistrySupplier<SoundEvent> DEOXYS = registerSound("generations_music.music.battle.deoxys");
    public static final RegistrySupplier<SoundEvent> IRIS = registerSound("generations_music.music.battle.iris");
    public static final RegistrySupplier<SoundEvent> KANTO = registerSound("generations_music.music.battle.kanto");
    public static final RegistrySupplier<SoundEvent> LUSAMINE = registerSound("generations_music.music.battle.lusamine");
    public static final RegistrySupplier<SoundEvent> NEMONA = registerSound("generations_music.music.battle.nemona");
    public static final RegistrySupplier<SoundEvent> NESSA = registerSound("generations_music.music.battle.nessa");
    public static final RegistrySupplier<SoundEvent> PENNY = registerSound("generations_music.music.battle.penny");
    public static final RegistrySupplier<SoundEvent> RIVAL = registerSound("generations_music.music.battle.rival");
    public static final RegistrySupplier<SoundEvent> SADA_AND_TURO = registerSound("generations_music.music.battle.sada_and_turo");
    public static final RegistrySupplier<SoundEvent> SOUTH_PROVINCE = registerSound("generations_music.music.battle.south_province");
    public static final RegistrySupplier<SoundEvent> TEAM_ROCKET = registerSound("generations_music.music.battle.team_rocket");
    public static final RegistrySupplier<SoundEvent> ULTRA_NECROZMA = registerSound("generations_music.music.battle.ultra_necrozma");
    public static final RegistrySupplier<SoundEvent> XY_LEGENDARY = registerSound("generations_music.music.battle.xy_legendary");
    public static final RegistrySupplier<SoundEvent> ZINNIA = registerSound("generations_music.music.battle.zinnia");
    public static final RegistrySupplier<SoundEvent> LAVENDER_TOWN = registerSound("generations_music.music.special.lavender_town");
    public static final RegistrySupplier<SoundEvent> LUGIA = registerSound("generations_music.music.special.lugia");
    public static final RegistrySupplier<SoundEvent> MT_PYRE = registerSound("generations_music.music.special.mt_pyre");

    public GenerationsSounds() {
    }

    private static RegistrySupplier<SoundEvent> registerSound(String id) {
        return SOUNDS.register(id, () -> {
            return SoundEvent.createFixedRangeEvent(GenerationsMusic.id(id), 16.00f);
        });
    }

    public static void init() {
        SOUNDS.register();
    }
}
