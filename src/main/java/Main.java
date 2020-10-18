import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Map;
import java.util.HashMap;

public class Main {

    final static String FILE_DIR = "files/";

    public static void main(String[] args){


        try {
            System.out.println("enter page address as \"abc.de\"");

            BufferedReader addressReader = new BufferedReader(new InputStreamReader(System.in));
            String address = addressReader.readLine();

            hostIsReachableCheck(address);

            String fullAddress = "https://" + address;

            File file = prepareFile(address);

            FileWriter fileWriter = new FileWriter(file);

            URL url = new URL(fullAddress);

            BufferedReader pageReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            while (true) {
                String line = pageReader.readLine();
                if (line == null){
                    break;
                }
                fileWriter.write(line);
            }

            Document doc = Jsoup.parse(file, "UTF-8");

 //           Document doc = getDocumentFromUrl(fullAddress);

            String text = doc.text().toLowerCase();

            String[] arrayOfWords = text.split("[\\s-,.!?\";:«»\\[\\]()\n\r\t]+");

            Map<String, Integer> map = new HashMap<>();

            for (String word : arrayOfWords) {
                if (map.containsKey(word)) {
                    int count = map.get(word);
                    map.replace(word, count, ++count);
                } else {
                    map.put(word, 1);
                }
            }

            for (Map.Entry pair : map.entrySet()) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }

            System.out.println(map.size());

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

    public static File prepareFile(String fileName) throws IOException {
        File file = new File(FILE_DIR + fileName + ".txt");

        if (file.exists()){
            file.delete();
        }

        file.createNewFile();

        return file;
    }

    public static Document getDocumentFromUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        doc.select("noscript").remove();
        return doc;
    }
}