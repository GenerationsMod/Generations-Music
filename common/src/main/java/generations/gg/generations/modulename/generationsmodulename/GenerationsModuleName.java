package generations.gg.generations.modulename.generationsmodulename;

import generations.gg.generations.core.generationscore.config.ConfigLoader;
import generations.gg.generations.modulename.generationsmodulename.config.Config;

public class GenerationsModuleName {

    /** The mod id for the Generations-ModuleName mod. */
    public static final String MOD_ID = "generationsmodulename";

    /** The config for the Generations-ModuleName mod. */
    public static Config CONFIG;

    public static void init() {
        CONFIG = ConfigLoader.loadConfig(Config.class, "name", "config");
    }
}
