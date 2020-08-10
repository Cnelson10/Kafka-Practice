# Kafka-Practice

## Set up Twitter API for Kafka Producer
Create a Twitter Developer account. When your aacount is approved go to 'Apps' in the dropdown section and click the 'Create an app' button. Once you've gone through the set up process go to the 'Keys and tokens' section of your newly created app. In this section you'll find your API key, API secret key, Access token and Access token secret. You will go into your project folder and create a .env file where you will stash these keys and tokens. Set them equal to the variable names CONSUMER_KEY, CONSUMER_SECRET, TOKEN, SECRET respectively with your keys and tokens in quotes. 

## Set up Elastic Search for Kafka Consumer
Set up a free node cluster on https://bonsai.io/ by clicking on the 'Sign Up' button and going throught the sign up process. Once set up go to the 'Credentials' for your cluster under the 'Access' section. There you will find a URL structured like this: https://username:password@hostname - Take these credentials and store them in your .env file like this: USERNAME="username" PASSWORD="password" and HOSTNAME="hostname"

## Create a Kafka Topic
Before running the Kafka producer we need to create our topic to prevent the producer from creating the topic with default values for the number of partitions and replication factor. e.g. Run this command in your terminal first: 
kafka-topics --zookeeper 127.0.0.1:2181 --create --topic twitter_tweets --partitions 6 --replication-factor 1

This command will create a new Kafka topic with the name twitter_tweets, six partitions and a replication factor of one (for this example I am only working with a single broker)
