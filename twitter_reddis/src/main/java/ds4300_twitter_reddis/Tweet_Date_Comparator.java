package ds4300_twitter_reddis;

import org.joda.time.DateTime;

public class Tweet_Date_Comparator implements Comparable<Tweet> {

  private Tweet t1;

  /**
   * creates a Tweet_Date_Comparator with this tweet
   * @param t is the tweet stored in this Tweet_Date_Comparator
   */
  public Tweet_Date_Comparator(Tweet t) {
    this.t1 = t;
  }

  /**
   * Determines the earlier tweet
   * @param t2 is the second
   * @return a number representing if t2 is before the tweet stored in this Tweet_Date_Comparator
   * or not
   */
  public int compareTo(Tweet t2) {
    DateTime dt1 = t1.getTweet_ts();
    DateTime dt2 = t2.getTweet_ts();
    return dt1.compareTo(dt2);
  }
}
