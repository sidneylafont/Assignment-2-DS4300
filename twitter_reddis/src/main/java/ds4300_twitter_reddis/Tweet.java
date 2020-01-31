package ds4300_twitter_reddis;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.IllegalInstantException;
import org.joda.time.format.DateTimeFormat;

public class Tweet {

  private int tweet_id;
  private int user_id;
  private DateTime tweet_ts;
  private String tweet_text;

  /**
   * General constructor for a ds4300_twitter_reddis.Tweet object given all parameters
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
   * converts a string representation of a tweet into a tweet object
   * @param s is a string representation of a tweet
   */
  public Tweet(String s) {
    String val = s.substring(37, s.length() - 2);
    String[] split_by = val.split("'");
    this.tweet_id = Integer.parseInt(split_by[0]);
    String user_id_str = split_by[1].substring(8);
    this.user_id = Integer.parseInt(user_id_str);
    this.tweet_text = split_by[3].substring(11);
    String date_str = split_by[2].substring(9);
    this.tweet_ts = new DateTime(date_str);
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

  /**
   * Determines the tweet id
   * @return the tweet id
   */
  public int getTweet_id() {
    return tweet_id;
  }

  /**
   * Determines the user id of the user who posted this tweet
   * @return the user id of the user who posted this tweet
   */
  public int getUser_id() {
    return user_id;
  }

  /**
   * Determines the post date of this tweet and converts it to a string
   * @return the string representation of the date this tweet was posted
   */
  public String getTweet_ts_text() {
    return tweet_ts.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
  }

  /**
   * Determines the post date of this tweet
   * @return the post date of this tweet
   */
  public DateTime getTweet_ts() {
    return tweet_ts;
  }

  /**
   * Determines the tweet text of this tweet
   * @return the tweet text of this tweet
   */
  public String getTweet_text() {
    return tweet_text;
  }

  /**
   * Converts this tweet to a string representation of this tweet
   * @return a string representation of this tweet
   */
  public String toString() {
    return "ds4300_twitter_reddis.Tweet{" +
        "tweet_id=" + this.tweet_id + '\'' +
        "user_id=" + this.user_id + '\'' +
        "tweet_ts=" + this.tweet_ts + '\'' +
        "tweet_text=" + this.tweet_text +
        "}" + '\'';
  }

}
