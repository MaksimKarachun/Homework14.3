
public class Loader
{

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-18M.xml";

        StaxParser staxParser = new StaxParser();

        long startTime = System.currentTimeMillis();
        long usageStax = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        staxParser.parseFile(fileName);
        usageStax = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usageStax;
        System.out.println("Утилизация МЕМ при использовании SAXParser: " + (int)(usageStax/1048576.0) + "MB");
        System.out.println((System.currentTimeMillis() - startTime) / 60000);


    }

}