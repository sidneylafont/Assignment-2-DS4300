package ds4300_twitter_reddis;

import java.util.List;

public interface TwitterDatabaseAPI {

  /**
   * Inserts a tweet into the database
   * @param t the tweet object to insert
   * @param strategy is the strategy being used, strategy 1 is false, strategy 2 is true
   */
  public void postTweet(Tweet t, boolean strategy);

  /**
   * Inserts a follower tuple into the database containing the user and who they follow
   * @param f the follower object to insert
   */
  public void insertFollower(Follower f);

  /**
   * Returns hometimeline for given user
   * @param user_id the id of the user
   * @param strategy is the strategy being used, strategy 1 is false, strategy 2 is true
   * @return the ten most recent tweets posted by users followed by this user
   */
  public List<Tweet> getHomeTimeline(int user_id, boolean strategy);

  /**
   * Determines the list of user_id's of followers of this user
   * @param user_id is the user_id of the twitter user
   * @return the list user_id's of followers of this user
   */
  public List<Integer> getFollowers(int user_id);

  /**
   * Determines the list of user_id's representing the people this user follows
   * @param user_id is the user_id of the twitter user
   * @return the list of user_id's representing the people this user follows
   */
  public List<Integer> getFollowees(int user_id);

  /**
   * Determines the tweets made by this user
   * @param user_id is the user_id of the twitter user
   * @return the tweets made by this user
   */
  public List<Tweet> getTweets(int user_id);

  /**
   * resets the jeddis twitter database
   */
  public void reset();
}
