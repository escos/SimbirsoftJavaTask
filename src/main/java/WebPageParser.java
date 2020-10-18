import support.FileWorks;
import support.TextParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

public class WebPageParser {

    public static void main(String[] args){

        try {
            System.out.println("enter page address as \"abc.de\"");

            BufferedReader addressReader = new BufferedReader(new InputStreamReader(System.in));
            String address = addressReader.readLine();

            hostIsReachableCheck(address);

            String fullAddress = "https://" + address;

            FileWorks fileWorks = new FileWorks(address);

            URL url = new URL(fullAddress);

            BufferedReader pageReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            while (true) {
                String line = pageReader.readLine();
                if (line == null){
                    break;
                }

                fileWorks.writeLineToFile(line);
            }

            TextParser textParser = new TextParser(fileWorks);

            HashMap<String, Integer> map = textParser.getWordsCount();
            for (HashMap.Entry pair : map.entrySet()) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }
            System.out.println(textParser.getWordsCount().size());


        } catch (UnknownHostException e) {
            System.out.println("host is not reachable");

        } catch (IOException e) {
            System.out.println("hz");
        }


    }

    public static boolean hostIsReachableCheck(String url) throws UnknownHostException, IOException{
        InetAddress inetAddress = InetAddress.getByName(url);
        return inetAddress.isReachable(1000);
    }

//    public static Document getDocumentFromUrl(String url) throws IOException {
//        Document doc = Jsoup.connect(url).getFile();
//        doc.select("noscript").remove();
//        return doc;
//    }
}