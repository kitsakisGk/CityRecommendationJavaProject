public class Main {
    static final String appId = "a434c79e93cfeee860190e0489ff6715";

    public static void main(String[] args) throws Exception {
        System.out.printf("Hello World!");
        City athens = new City("Athens");
        double[] a = athens.getFeaturesVector(appId);
        City sydney = new City("Sydney");
        double[] b = sydney.getFeaturesVector(appId);
        City budapest = new City("Budapest");
        double[] c = budapest.getFeaturesVector(appId);
        City newYork = new City("New York");
        double[] d = newYork.getFeaturesVector(appId);
        System.out.printf("Done");
    }
}
