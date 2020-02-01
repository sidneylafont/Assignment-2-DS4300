package ds4300_twitter_reddis;

import java.util.List;
import java.util.*;
import redis.clients.jedis.*;

public class TwitterDatabaseReddis implements TwitterDatabaseAPI {

  Jedis jedis = new Jedis("localhost");

  public void postTweet(Tweet t, boolean strategy) {
    String key = "tweet:"+t.getUser_id()+":"+ t.getTweet_id();
    String value = t.toString();
    jedis.set(key,value);

    if (strategy)
    {
      Set<String> followers = jedis.smembers("followers:"+t.getUser_id());
      for (String f : followers)
        addToTimeline(t, f);
    }
  }

  private void addToTimeline(Tweet t, String userID)
  {
    String key = "timeline:"+userID;
    String value = t.toString();
    jedis.lpush(key, value);
  }

  public void insertFollower(Follower f) {
    String key = "followers:"+f.getUser_id();
    jedis.sadd(key, Integer.toString(f.getFollows_id()));

    //added a followees set to increase the speed of getHomeTimeLine
    String key2 = "followees:"+f.getFollows_id();
    jedis.sadd(key2, Integer.toString(f.getUser_id()));
  }

  public List<Tweet> getHomeTimeline(int user_id, boolean strategy) {
    List<Tweet> tweets = new ArrayList<Tweet>();

    if (strategy) {
      List<String> followers = jedis.lrange("timeline:"+user_id, 0, -1);
      for (String s: followers) {
        Tweet t = new Tweet(s);
        tweets.add(t);
      }
    } else {
      List<Integer> followees = getFollowees(user_id);
      for (Integer i : followees) {
        tweets.addAll(getTweets(i));
      }
    }

    Collections.sort(tweets, new Comparator<Tweet>() {
        public int compare(Tweet event1, Tweet event2) {
          return event2.getTweet_ts().compareTo(event1.getTweet_ts());
        }
    });

    List<Tweet> final_10 = new ArrayList<Tweet>();
    for (int i = 0; i < 10; i++) {
      final_10.add(tweets.get(i));
    }
    return final_10;
  }



  public List<Integer> getFollowers(int user_id) {
    Set<String> followers = jedis.smembers("followers:"+user_id);

    List<Integer> follower_ids = new ArrayList<Integer>();
    for (String s: followers) {
      follower_ids.add(Integer.parseInt(s));
    }

    return follower_ids;
  }

  public List<Integer> getFollowees(int user_id) {
    Set<String> followers = jedis.smembers("followees:"+user_id);

    List<Integer> follower_ids = new ArrayList<Integer>();
    for (String s: followers) {
      follower_ids.add(Integer.parseInt(s));
    }

    return follower_ids;
  }

  public List<Tweet> getTweets(int user_id) {
    Set<String> tweet_keys = jedis.keys("tweet:"+user_id+":*");

    List<Tweet> tweets = new ArrayList<Tweet>();
    for (String s: tweet_keys) {
      String value = jedis.get(s);
      Tweet new_tweet = new Tweet(value);
      tweets.add(new_tweet);
    }

    return tweets;
  }

  public void reset() {
    jedis.flushAll();
  }
}
