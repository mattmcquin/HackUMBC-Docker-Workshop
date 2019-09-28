package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Twitter twitter() throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("Insert your consumer key here")
				.setOAuthConsumerSecret("Insert your consumer secret here")
				.setOAuthAccessToken("Insert your access token here")
				.setOAuthAccessTokenSecret("Insert your access secret here");
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
