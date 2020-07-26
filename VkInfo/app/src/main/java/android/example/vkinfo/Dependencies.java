package android.example.vkinfo;

public class Dependencies {

    static private Dependencies INSTANCE = new Dependencies();

    private Dependencies() {
    }

    // Singleton, create object once and returns instance
    static public Dependencies getINSTANCE() {
        return INSTANCE;
    }


    // Factory, each time creates and returns new object

    public Utils provideUtils() {
        return new Utils(provideUtilsSecond());
    }

    public UtilsSecond provideUtilsSecond() {
        return new UtilsSecond();
    }
}
