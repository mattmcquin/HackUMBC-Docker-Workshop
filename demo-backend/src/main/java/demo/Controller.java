package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@RestController
public class Controller {

    private final Twitter twitter;

    @Autowired
    public Controller(Twitter twitter) {
        this.twitter = twitter;
    }

    @GetMapping("/{word}")
    @CrossOrigin
    public Map<String, Long> getRequest(@PathVariable String word) throws TwitterException {
        Query query = new Query(word);
        query.setCount(100);
        QueryResult result = twitter.search(query);

        List<Status> tweets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tweets.addAll(result.getTweets());
            query = result.nextQuery();
            result = twitter.search(query);
        }

        Map<String, Long> wordMap = tweets.stream()
                .flatMap(status -> Stream.of(status.getText().replaceAll("[^a-zA-Z @#]", "")
                        .toLowerCase().split("\\s+")))
                .map(String::toLowerCase)
                .filter(wrd -> wrd.length() > 2)
                .filter(this::filterWord)
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(75)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        wordMap.remove(word.toLowerCase());

        long maxCount = wordMap.values().stream().max(Comparator.comparingLong(Long::longValue)).orElse(0L);
        return wordMap.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> (long) ((entry.getValue().doubleValue() / maxCount) * 120)));
    }


    private String stripPunctuation(String word) {
        return word.replaceAll("\\p{Punct}", "");
    }


    private boolean filterWord(String word) {
        return !EXCLUDE_WORDS.contains(word);
    }

    private static List<String> EXCLUDE_WORDS = List.of(
            "the", "they", "a", "an", "he", "she", "i", "you", "and", "that", "there", "was", "for", "you", "his", "this", "not", "from", "are",
            "with", "about", "who", "has", "how", "get", "why", "what", "now", "have", "can", "but", "are", "just", "were", "had", "then", "did", "when",
            "does", "all", "its", "your", "their", "our", "want"
    );
}
