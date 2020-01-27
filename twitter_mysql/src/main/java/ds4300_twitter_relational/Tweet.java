package ds4300_twitter_relational;

import java.util.Random;
import org.joda.time.DateTime;
import org.joda.time.IllegalInstantException;
import org.joda.time.format.DateTimeFormat;

public class Tweet {

  private int tweet_id;
  private int user_id;
  private DateTime tweet_ts;
  private String tweet_text;

  /**
   * General constructor for a Tweet object given all parameters
   * @param tweet_id unique id of tweet
   * @param user_id unique id of user
   * @param tweet_ts timestamp of tweet
   * @param tweet_text text of tweet
   */
  public Tweet(int tweet_id, int user_id, DateTime tweet_ts, String tweet_text) {
    this.tweet_id = tweet_id;
    this.user_id = user_id;
    this.tweet_ts = tweet_ts;
    this.tweet_text = tweet_text;
  }

  /**
   * Constructor given all parameters except datetime for timestamp
   * Calls upon a helper method to generate datetime
   * @param tweet_id the unique id of the tweet
   * @param user_id the unique id of the user
   * @param tweet_text the text of the tweet
   */
  public Tweet(int tweet_id, int user_id, String tweet_text) {
    this.tweet_id = tweet_id;
    this.user_id = user_id;
    this.tweet_text = tweet_text;
    this.tweet_ts = make_datetime();
  }

  /**
   * Helper method for generating a random datetime between 2006 (when twitter was founded) and 2020
   * If generates a invalid time, recurs on itself
   * @return a datetime for a tweet object
   */
  private DateTime make_datetime() {
    Random rand = new Random();
    int year = rand.nextInt(15) + 2006;
    int monthOfYear = rand.nextInt(12) + 1;
    int dayOfMonth = rand.nextInt(28) + 1;
    int hourOfDay = rand.nextInt(24);
    int minuteOfHour = rand.nextInt(60);
    int secondOfMinute = rand.nextInt(60);
    DateTime d;
    try {
      d = new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute);
    } catch(IllegalInstantException i) {
      d = make_datetime();
    }
    return d;
  }

  public int getTweet_id() {
    return tweet_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public String getTweet_ts_text() {
    return tweet_ts.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public String getTweet_text() {
    return tweet_text;
  }

  public String toString() {
    return "Tweet{" +
        "tweet_id=" + this.tweet_id + '\'' +
        "user_id=" + this.user_id + '\'' +
        "tweet_ts=" + this.tweet_ts + '\'' +
        "tweet_text=" + this.tweet_text +
        "}" + '\'';
  }

}
