package ds4300_twitter_relational;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Driver {
  private static TwitterDatabaseAPI api = new TwitterDatabaseMySQL();

  //running two programs and printing out details for timing each
  public static void main(String[] args) throws Exception{
    api.authenticate("twitter_user","password"); //authenticates with database

    //we made two private methods in this driver class so it would be easier to separate out
    // functionality between the two programs

    program_post_tweets();
    //program_get_home_timeline();
  }

  /**
   * Program to run the posttweet method from the api to post all tweets from file
   * @throws Exception if problem reading in tweets from file
   */
  private static void program_post_tweets() throws Exception {
    //read in file
    //put into a list of strings
    //loop through list of strings, creating a tweet, and putting in a list of tweets
    //loop through list of tweets, calling postTweet on each while keeping track of total time

    List<Tweet> result = new ArrayList<Tweet>();
    BufferedReader br = null;
    int counterTweet = 0;

    try {

      br = new BufferedReader(new FileReader("tweets.txt"));

      String line;
      //read every line of the tweets in
      while ((line = br.readLine()) != null) {
        //randomly picking the user who is posting the tweet
        Random r = new Random();
        int user_id_random = r.nextInt(6000);
        //create a tweet object with the incrementing counter, random user, and line of tweet
        Tweet t = new Tweet(counterTweet, user_id_random, line);
        result.add(t); //add to list of tweets
        counterTweet += 1;
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        br.close();
      }
    }

    Date start = new Date();
    System.out.println(start);
    int counter = 0;
    //looping through prepared list of tweet objects, one by one calling the postTweet method and timing total duration
    for (Tweet t : result) {
      api.postTweet(t);
      counter++;
      System.out.println(counter+"\t"+new Date().getTime()); // to show how many tweets inserted so far and at what time
    }
    Date end = new Date();
    System.out.println(end);
    //how long it took to post all tweets
    System.out.println((end.getTime() - start.getTime()) / 1000);
  }

  /**
   * Program to run the getHomeTimeLine request using the api
   */
  private static void program_get_home_timeline() {
    Date start = new Date();
    System.out.println(start);
    //running 10 getHomeTimeLine requests in order to generate an average rate per request
    for (int i = 1; i < 11; i++){
      Random r = new Random();
      int user_id_random = r.nextInt(6000);
      List<Tweet> ht = api.getHomeTimeline(user_id_random);
      for (Tweet t: ht) {
        System.out.println(t.toString());
      }
    }
    Date end = new Date();
    System.out.println(end);
    //how long it took for 10 getHometimeLine requests
    System.out.println((end.getTime() - start.getTime()) / 1000);
  }
}
