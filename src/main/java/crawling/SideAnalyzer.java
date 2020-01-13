package crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SideAnalyzer {

    private List<String> stopWords;

    public SideAnalyzer() {
        try {
            this.stopWords = Files.readAllLines(Paths.get("src/main/resources/stopwords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String findJobDescription(String link) {
        try {
            Document doc = Jsoup.connect(link).get();
            return findDescription(doc);
        } catch (IOException e) {
            return e.getLocalizedMessage() + link;
        }
    }

    public String findDescription(Document document) {

        return document.select("div.description__text")
                .eachText()
                .stream()
                .collect(Collectors.joining(" "));


    }

    /**
     * Method to remove stopwords from the string
     *
     * @param description job description
     * @return text without stop words
     */
    public List<String> removeStopWords(String description) { //return list of strings

        description = description.toLowerCase().trim();
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(description.split(" ")));
        wordList.removeAll(stopWords);

        return wordList;
    }

    /**
     * This method is to merge the duplicated words and count how many times the are shown
     *
     * @param words
     * @return
     */
    public Map<String, Integer> mergeAndCount(List<String> words) {


        Map<String, Integer> map = new HashMap<>();
        for (String singleWord : words) {
            singleWord = singleWord.replace(",", "")
                    .replace(".", "")
                    .replace(")", "")
                    .replace("'", "")
                    .replace("|", "");
            if (map.get(singleWord) != null) {
                map.put(singleWord, map.get(singleWord) + 1);
            } else {
                map.put(singleWord, 1);
            }
        }
        return map;
    }

    /**
     *
     * @param jobResult
     * @param result
     * @return
     */
    public Map<String, Integer> countWords(Map<String, Integer> jobResult, Map<String, Integer> result) {

        for (Map.Entry<String, Integer> entry : jobResult.entrySet()) {
            if (result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), result.get(entry.getKey()) + entry.getValue());
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
            if(entry.getValue()<2){
               result.remove(entry.getKey());
            }
        }
        return jobResult;
    }

    /**
     * Method sort is to sort the words with their appearance
     */
    public List<Map.Entry<String, Integer>> sort(Map<String, Integer> result) {

        return result.entrySet().stream().sorted((o1, o2) -> {
            if (o1.getValue() > o2.getValue()) return -1;
            else if (o1.getValue() < o2.getValue()) return 1;
            return 0;
        }).collect(Collectors.toList());
    }

    public Map<String, Integer> call(Map<String,Integer>resultWithoutSort,String jobs) {

            List<String> removed = removeStopWords(jobs);
            Map<String, Integer> result = mergeAndCount(removed);
            countWords(result, resultWithoutSort);

        return resultWithoutSort;
    }

}





