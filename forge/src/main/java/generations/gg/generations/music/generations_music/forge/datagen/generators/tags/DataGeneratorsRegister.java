package generations.gg.generations.music.generations_music.forge.datagen.generators.tags;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import generations.gg.generations.music.generations_music.GenerationsMusic;
import generations.gg.generations.music.generations_music.world.item.GenerationsMusicItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * This class is used to register the data generators for the mod.
 * @see GatherDataEvent
 * @author Joseph T. McQuigg (JT122406)
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GenerationsMusic.MOD_ID)
public class DataGeneratorsRegister {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        TagsDatagen.init(generator, output, lookupProvider, existingFileHelper);
        generator.addProvider(true, new GeneralLang(output, "en_us"));
//        generator.addProvider(true, new GenerationsBlockStateProvider(output, existingFileHelper, BlockDatagen::new, UltraBlockModelDataGen::new));
        generator.addProvider(true, new ItemModelProvider(output, GenerationsMusic.MOD_ID, existingFileHelper) {
            @Override
            protected void registerModels() {
                this.registerDisc(GenerationsMusicItems.AZALEA_TOWN_DISC.getId());
                this.registerDisc(GenerationsMusicItems.CASCARRAFA_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.CERULEAN_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.ETERNA_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.GOLDENROD_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.ICIRRUS_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.JUBILIFE_VILLAGE_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LAKE_OF_RAGE_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LAVERRE_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LILLIE_DISC.getId());
                this.registerDisc(GenerationsMusicItems.POKEMON_CENTER_DISC.getId());
                this.registerDisc(GenerationsMusicItems.ROUTE_228_DISC.getId());
                this.registerDisc(GenerationsMusicItems.SLUMBERING_WEALD_DISC.getId());
                this.registerDisc(GenerationsMusicItems.SURF_DISC.getId());
                this.registerDisc(GenerationsMusicItems.VERMILION_CITY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.CYNTHIA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.DEOXYS_DISC.getId());
                this.registerDisc(GenerationsMusicItems.IRIS_DISC.getId());
                this.registerDisc(GenerationsMusicItems.KANTO_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LUSAMINE_DISC.getId());
                this.registerDisc(GenerationsMusicItems.NEMONA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.NESSA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.PENNY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.RIVAL_DISC.getId());
                this.registerDisc(GenerationsMusicItems.SADA_AND_TURO_DISC.getId());
                this.registerDisc(GenerationsMusicItems.SOUTH_PROVINCE_DISC.getId());
                this.registerDisc(GenerationsMusicItems.TEAM_ROCKET_DISC.getId());
                this.registerDisc(GenerationsMusicItems.ULTRA_NECROZMA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.XY_LEGENDARY_DISC.getId());
                this.registerDisc(GenerationsMusicItems.ZINNIA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LAVENDER_TOWN_DISC.getId());
                this.registerDisc(GenerationsMusicItems.LUGIA_DISC.getId());
                this.registerDisc(GenerationsMusicItems.MT_PYRE_DISC.getId());
            }

            private void registerDisc(ResourceLocation id) {
                this.singleTexture(id.getPath(), new ResourceLocation("item/generated"), "layer0", GenerationsMusic.id("item/discs/" + id.getPath()));
            }
        });

//        generator.addProvider(true, new GenerationsRecipeProvider(output,
//                BuildingBlockRecipeDatagen::new,
//                ItemRecipeDatagen::new,
//                GenerationsArmorToolRecipeDatagen::new,
//                MachineDecorationsRecipeDatagen::new,
//                WoodRecipes::new,
//                PokeBallRecipeDatagen::new,
//                FurnaceRecipeProvider::new,
//                RksRecipeProvider::new));
//        generator.addProvider(true, new LootTableDatagen(output));
//
//        generator.addProvider(true, new GenerationsPokemonModelsProvider(output));
//
//        generator.addProvider(true, new DialogueDataGen(event.getGenerator().getPackOutput()));
//
//        generator.addProvider(true, new WorldGenProvider(output, lookupProvider));
//    }
    }
}

class GeneralLang extends LanguageProvider {

    public GeneralLang(PackOutput output, String locale) {
        super(output, GenerationsMusic.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        addItemEntries(GenerationsMusicItems.ITEMS, this::getNameGens, (item, function) -> {
            var item1 = item.get();

            if(item1 instanceof RecordItem) {
                add(item.get().asItem().getDescriptionId() + ".desc", "GlitchxCity - " + function.apply(item, item.getId().toString().replace("_disc", "")));
            }
        });
    }

    public void addItemEntries(DeferredRegister<Item> entries, BiFunction<RegistrySupplier<? extends ItemLike>, String, String> function, BiConsumer<RegistrySupplier<? extends ItemLike>, BiFunction<RegistrySupplier<? extends ItemLike>, String, String>> additionalActions) {
        entries.forEach(item -> {
            add(item.get(), function.apply(item, item.getId().toString()));
            additionalActions.accept(item, function);
        });
    }

    protected String getNameGens(RegistrySupplier<? extends ItemLike> item, String name) {
        name = name.substring(name.indexOf(":") + 1);  //Removes Mod Tag from front of name
        name = name.replace('_', ' ');
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        for (int i = 0; i < name.length(); i++)
            if (name.charAt(i) == ' ')
                name = name.substring(0, i + 1) + name.substring(i + 1, i + 2).toUpperCase() + name.substring(i + 2);
        return name;
    }
}