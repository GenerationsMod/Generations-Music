package generations.gg.generations.music.generations_music.forge.datagen.generators.tags;

import generations.gg.generations.music.generations_music.GenerationsMusic;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static generations.gg.generations.music.generations_music.world.item.GenerationsMusicItems.*;

public class TagsDatagen {
    public static void init(DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        generator.addProvider(true, new GenerationsItemTagsProvider(output, lookupProvider, helper));
    }


    private static class GenerationsItemTagsProvider extends ItemTagsProvider {

        public GenerationsItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, CompletableFuture.completedFuture(TagLookup.empty()), GenerationsMusic.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider arg) {
            //Discs
            tag(ItemTags.MUSIC_DISCS).add(
                    AZALEA_TOWN_DISC.get(),
                    CASCARRAFA_CITY_DISC.get(),
                    CERULEAN_CITY_DISC.get(),
                    ETERNA_CITY_DISC.get(),
                    GOLDENROD_CITY_DISC.get(),
                    ICIRRUS_CITY_DISC.get(),
                    JUBILIFE_VILLAGE_DISC.get(),
                    LAKE_OF_RAGE_DISC.get(),
                    LAVERRE_CITY_DISC.get(),
                    LILLIE_DISC.get(),
                    POKEMON_CENTER_DISC.get(),
                    ROUTE_228_DISC.get(),
                    SLUMBERING_WEALD_DISC.get(),
                    SURF_DISC.get(),
                    VERMILION_CITY_DISC.get(),
                    CYNTHIA_DISC.get(),
                    DEOXYS_DISC.get(),
                    IRIS_DISC.get(),
                    KANTO_DISC.get(),
                    LUSAMINE_DISC.get(),
                    NEMONA_DISC.get(),
                    NESSA_DISC.get(),
                    PENNY_DISC.get(),
                    RIVAL_DISC.get(),
                    SADA_AND_TURO_DISC.get(),
                    SOUTH_PROVINCE_DISC.get(),
                    TEAM_ROCKET_DISC.get(),
                    ULTRA_NECROZMA_DISC.get(),
                    XY_LEGENDARY_DISC.get(),
                    ZINNIA_DISC.get(),
                    LAVENDER_TOWN_DISC.get(),
                    LUGIA_DISC.get(),
                    MT_PYRE_DISC.get()
            );
        }
    }
}