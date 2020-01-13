
package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import crawling.Response;
import crawling.Scraper;
import crawling.SideAnalyzer;
import database.Word;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.*;
import java.util.stream.Collectors;


class Starter {

    public static void main(String[] args) throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();
        SideAnalyzer sideAnalyzer = new SideAnalyzer();
        List<String> jobDescriptions = new LinkedList<>();
        String searchUrl = "https://www.linkedin.com/jobs/search/?keywords=software%20engineering&locationId=OTHERS.worldwide&pageNum=";

        for (int index = 1; index < 2; index++) {
            String[] params = {"-u", searchUrl + index};

            Scraper scraperTest = new Scraper(params);
            Response response = scraperTest.start();
            List<String> localDescriptions = response.externalLinks.stream()
                    .filter(x -> x.contains("/jobs/view"))
                    .map(sideAnalyzer::findJobDescription)
                    .collect(Collectors.toList());
            jobDescriptions.addAll(localDescriptions);
        }

        SideAnalyzer analyzer = new SideAnalyzer();
        Map<String, Integer> completeResult = new HashMap<>();
        for (String job : jobDescriptions) {
            analyzer.call(completeResult, job);
        }
        List<Map.Entry<String, Integer>> sortedResult = analyzer.sort(completeResult);
        // sortedResult.stream().forEach(System.out::println);

        String json = objectMapper.writeValueAsString(sortedResult);
        System.out.println("json = " + json);


        final EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("my-persistence-unit");

        // create session and save TestEntity to database
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Word testWord = new Word();
        testWord.setTime(Calendar.getInstance());
        testWord.setCounts(json);
        /**
         * "{" +
         *                 "   \"title\": \"High-Performance Java Persistence\"," +
         *                 "   \"author\": \"Vlad Mihalcea\"," +
         *                 "   \"publisher\": \"Amazon\"," +
         *                 "   \"price\": 44.99" +
         *                 "}"
         */
        entityManager.persist(testWord);
        entityManager.flush();
        tx.commit();

    }
}









