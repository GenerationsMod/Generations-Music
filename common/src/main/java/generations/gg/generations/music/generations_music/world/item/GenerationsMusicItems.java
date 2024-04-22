package generations.gg.generations.music.generations_music.world.item;

import dev.architectury.core.item.ArchitecturyRecordItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.world.sound.GenerationsSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;


public class GenerationsMusicItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(GenerationsMusic.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> POKE_WALKMON = register("poke_walkmon", properties -> new WalkmanItem(properties, 1, "poke_walkmon"));
    public static final RegistrySupplier<Item> GREAT_WALKMON = register("great_walkmon", properties -> new WalkmanItem(properties, 2, "great_walkmon"));
    public static final RegistrySupplier<Item> ULTRA_WALKMON = register("ultra_walkmon", properties -> new WalkmanItem(properties, 3, "ultra_walkmon"));
    public static final RegistrySupplier<Item> MASTER_WALKMON = register("master_walkmon", properties -> new WalkmanItem(properties, 4, "master_walkmon"));
    public static final RegistrySupplier<Item> HI_TECH_EARBUDS = register("hi_tech_earbuds", properties -> new WalkmanItem(properties, 4, "hi_tech_earbuds"));
    public static final RegistrySupplier<Item> GB_SOUNDS = register("gb_sounds", properties -> new WalkmanItem(properties, 4, "hi_tech_earbuds"));


    public static final RegistrySupplier<Item> AZALEA_TOWN_DISC = createMusicDisc("azalea_town_disc", GenerationsSounds.AZALEA_TOWN, 200);
    public static final RegistrySupplier<Item> CASCARRAFA_CITY_DISC = createMusicDisc("cascarrafa_city_disc", GenerationsSounds.CASCARRAFA_CITY, 169);
    public static final RegistrySupplier<Item> CERULEAN_CITY_DISC = createMusicDisc("cerulean_city_disc", GenerationsSounds.CERULEAN_CITY, 184);
    public static final RegistrySupplier<Item> ETERNA_CITY_DISC = createMusicDisc("eterna_city_disc", GenerationsSounds.ETERNA_CITY, 136);
    public static final RegistrySupplier<Item> GOLDENROD_CITY_DISC = createMusicDisc("goldenrod_city_disc", GenerationsSounds.GOLDENROD_CITY, 182);
    public static final RegistrySupplier<Item> ICIRRUS_CITY_DISC = createMusicDisc("icirrus_city_disc", GenerationsSounds.ICIRRUS_CITY, 148);
    public static final RegistrySupplier<Item> JUBILIFE_VILLAGE_DISC = createMusicDisc("jubilife_village_disc", GenerationsSounds.JUBILIFE_VILLAGE, 202);
    public static final RegistrySupplier<Item> LAKE_OF_RAGE_DISC = createMusicDisc("lake_of_rage_disc", GenerationsSounds.LAKE_OF_RAGE, 139);
    public static final RegistrySupplier<Item> LAVERRE_CITY_DISC = createMusicDisc("laverre_city_disc", GenerationsSounds.LAVERRE_CITY, 281);
    public static final RegistrySupplier<Item> LILLIE_DISC = createMusicDisc("lillie_disc", GenerationsSounds.LILLIE, 312);
    public static final RegistrySupplier<Item> POKEMON_CENTER_DISC = createMusicDisc("pokemon_center_disc", GenerationsSounds.POKEMON_CENTER, 240);
    public static final RegistrySupplier<Item> ROUTE_228_DISC = createMusicDisc("route_228_disc", GenerationsSounds.ROUTE_228, 418);
    public static final RegistrySupplier<Item> SLUMBERING_WEALD_DISC = createMusicDisc("slumbering_weald_disc", GenerationsSounds.SLUMBERING_WEALD, 262);
    public static final RegistrySupplier<Item> SURF_DISC = createMusicDisc("surf_disc", GenerationsSounds.SURF, 261);
    public static final RegistrySupplier<Item> VERMILION_CITY_DISC = createMusicDisc("vermilion_city_disc", GenerationsSounds.VERMILION_CITY, 216);
    public static final RegistrySupplier<Item> CYNTHIA_DISC = createMusicDisc("cynthia_disc", GenerationsSounds.CYNTHIA, 385);
    public static final RegistrySupplier<Item> DEOXYS_DISC = createMusicDisc("deoxys_disc", GenerationsSounds.DEOXYS, 414);
    public static final RegistrySupplier<Item> IRIS_DISC = createMusicDisc("iris_disc", GenerationsSounds.IRIS, 291);
    public static final RegistrySupplier<Item> KANTO_DISC = createMusicDisc("kanto_disc", GenerationsSounds.KANTO, 337);
    public static final RegistrySupplier<Item> LUSAMINE_DISC = createMusicDisc("lusamine_disc", GenerationsSounds.LUSAMINE, 337);
    public static final RegistrySupplier<Item> NEMONA_DISC = createMusicDisc("nemona_disc", GenerationsSounds.NEMONA, 158);
    public static final RegistrySupplier<Item> NESSA_DISC = createMusicDisc("nessa_disc", GenerationsSounds.NESSA, 263);
    public static final RegistrySupplier<Item> PENNY_DISC = createMusicDisc("penny_disc", GenerationsSounds.PENNY, 267);
    public static final RegistrySupplier<Item> RIVAL_DISC = createMusicDisc("rival_disc", GenerationsSounds.RIVAL, 221);
    public static final RegistrySupplier<Item> SADA_AND_TURO_DISC = createMusicDisc("sada_and_turo_disc", GenerationsSounds.SADA_AND_TURO, 349);
    public static final RegistrySupplier<Item> SOUTH_PROVINCE_DISC = createMusicDisc("south_province_disc", GenerationsSounds.SOUTH_PROVINCE, 214);
    public static final RegistrySupplier<Item> TEAM_ROCKET_DISC = createMusicDisc("team_rocket_disc", GenerationsSounds.TEAM_ROCKET, 186);
    public static final RegistrySupplier<Item> ULTRA_NECROZMA_DISC = createMusicDisc("ultra_necrozma_disc", GenerationsSounds.ULTRA_NECROZMA, 296);
    public static final RegistrySupplier<Item> XY_LEGENDARY_DISC = createMusicDisc("xy_legendary_disc", GenerationsSounds.XY_LEGENDARY, 261);
    public static final RegistrySupplier<Item> ZINNIA_DISC = createMusicDisc("zinnia_disc", GenerationsSounds.ZINNIA, 320);
    public static final RegistrySupplier<Item> LAVENDER_TOWN_DISC = createMusicDisc("lavender_town_disc", GenerationsSounds.LAVENDER_TOWN, 369);
    public static final RegistrySupplier<Item> LUGIA_DISC = createMusicDisc("lugia_disc", GenerationsSounds.LUGIA, 341);
    public static final RegistrySupplier<Item> MT_PYRE_DISC = createMusicDisc("mt_pyre_disc", GenerationsSounds.MT_PYRE, 219);

    private static RegistrySupplier<Item> createMusicDisc(String name, RegistrySupplier<SoundEvent> sound, int ticks) {
        return ITEMS.register(name, () -> new ArchitecturyRecordItem(0, sound, new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES), ticks));
    }

    public static <T extends Item> RegistrySupplier<T> register(String name, Function<Item.Properties, T> itemSupplier) {
        return ITEMS.register(name, () -> itemSupplier.apply(new Item.Properties()));
    }

    public static void init() {
        ITEMS.register();
    }
}
