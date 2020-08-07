package kafka.practice;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterProducer {

    Dotenv dotenv = Dotenv.load();

    // constructor
    public TwitterProducer() {

    }

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run() {
        // create Twitter client
        Client client = createTwitterClient();
        // Attempt to establish a connection
        client.connect();

        // TODO create Kafka produce

        // TODO create loop to send tweets to Kafka
    }

    String consumerKey = dotenv.get("CONSUMER_KEY");
    String consumerSecret = dotenv.get("CONSUMER_SECRET");
    String token = dotenv.get("TOKEN");
    String secret = dotenv.get("SECRET");

    public Client createTwitterClient() {

        /* Declaring the connection */
        // Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

        // Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        List<String> terms = Lists.newArrayList("kafka");
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        /* Creating a client */
        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }
}
