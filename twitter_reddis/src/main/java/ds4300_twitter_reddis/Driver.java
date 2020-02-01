package ds4300_twitter_reddis;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Driver {

  private static TwitterDatabaseAPI api = new TwitterDatabaseReddis();

  public static void main(String[] args) throws Exception {
    api.reset();
    Boolean strategy = true;
    insertAllFollowers();
    program_post_tweets(strategy);
    getRandomTimelines(strategy);




  }

  /**
   * Randomly gets 20 home timelines
   * @param strategy is the strategy for retreiving the home timeline
   */
  private static void getRandomTimelines(boolean strategy) {
    Random rand = new Random();
    List<Integer> random_numbers = new ArrayList<Integer>();
    for (int i = 0; i < 20; i++) {
      random_numbers.add(rand.nextInt(6000));
    }

    Date start = new Date();
    for (Integer j: random_numbers) {
      api.getHomeTimeline(j, strategy);
    }
    Date end = new Date();
    //how long it took to get all home timelines
    System.out.println((end.getTime() - start.getTime()) / 1000);
  }

  /**
   * determines the percent of activity of a twitter user with this user_id
   * @param user_id is the user_id of the twitter user
   * @return the percent of activity of a twitter user with this user_id
   */
  private static float getPercent(int user_id) {
    return (float) (3.003542 - (-0.0005111736/-0.0007832683)*(1 - Math.exp (0.0007832683*(user_id)))) / 100;
  }

  /**
   * creates all the follower objects
   */
  private static List<Follower> createFollowers() {
    List<Follower> list_of_followers = new ArrayList<Follower>();
    for (int i = 0; i < 6000; i++) {
      float percent_followers = getPercent(i);
      int num_followers = Math.round(percent_followers * 6000);
      List<Integer> sample_space = new ArrayList<Integer>();
      for (int j = 0; j < 6000; j++) {
        if (j != i) {
          sample_space.add(j);
        }
      }
      for (int x = 0; x < num_followers; x++) {
        Collections.shuffle(sample_space);
        int followee = sample_space.remove(0);
        Follower new_follower = new Follower(i, followee);
        list_of_followers.add(new_follower);
      }
    }

    return list_of_followers;
  }

  /**
   * inserts all the followers and followees into the redis database
   */
  private static void insertAllFollowers() {
    List<Follower> followers = createFollowers();

    Date start = new Date();
    for (Follower f: followers) {
      api.insertFollower(f);
    }

    Date end = new Date();
    System.out.println((end.getTime() - start.getTime()) / 1000);
  }

  /**
   * Program to run the posttweet method from the api to post all tweets from file
   * @throws Exception if problem reading in tweets from file
   */
  private static void program_post_tweets(boolean stategy) throws Exception {
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
    //looping through prepared list of tweet objects, one by one calling the postTweet method and timing total duration
    for (Tweet t : result) {
      api.postTweet(t, stategy);
    }
    Date end = new Date();
    //how long it took to post all tweets
    System.out.println((end.getTime() - start.getTime()) / 1000);
  }
}
