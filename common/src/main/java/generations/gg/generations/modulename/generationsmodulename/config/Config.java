package generations.gg.generations.modulename.generationsmodulename.config;

public class Config {

    public ExampleCategory exampleCategory = new ExampleCategory();

    public Config() {}

    public static class ExampleCategory {
        public boolean exampleBoolean = true;
        public int exampleInt = 1;
        public double exampleDouble = 1.0;
        public String exampleString = "example";
    }
}
