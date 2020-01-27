package ds4300_twitter_relational;

import java.util.List;

public interface TwitterDatabaseAPI {

  /**
   * Inserts a tweet into the database
   * @param t the tweet object to insert
   */
  public void postTweet(Tweet t);

  /**
   * Inserts a follower tuple into the database containing the user and who they follow
   * @param f the follower object to insert
   */
  public void insertFollower(Follower f);

  /**
   * Returns hometimeline for given user
   * @param user_id the id of the user
   * @return the ten most recent tweets posted by users followed by this user
   */
  public List<Tweet> getHomeTimeline(int user_id);

  /**
   * Set connection settings
   * @param user username to connection
   * @param password password to connection
   */
  public void authenticate(String user, String password);

  /**
   * Close connection when application finishes
   */
  public void closeConnection();
}
