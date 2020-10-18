package support;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class TextParser {
    private static final String REG_EXP = "[\\s-,.!?\";:«»\\[\\]()\n\r\t]+";

    private Document doc;
    private String text;
    private String[] arrayOfWords;
    private HashMap<String, Integer> wordsCount;

    public TextParser(FileWorks fileWorks) throws IOException {
        this.doc = Jsoup.parse(fileWorks.getFile(), "UTF-8");
        this.text = this.doc.text().toLowerCase();
        this.arrayOfWords = this.text.split(REG_EXP);

        HashMap<String, Integer> map = new HashMap<>();
        for (String word : arrayOfWords) {
            if (map.containsKey(word)) {
                int count = map.get(word);
                map.replace(word, count, ++count);
            } else {
                map.put(word, 1);
            }
        }

        this.wordsCount = map;
    }

    public HashMap getWordsCount(){
        return this.wordsCount;
    }
}
