package ds4300_twitter_relational;

import java.sql.*;

import java.util.*;
import database.dbUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class TwitterDatabaseMySQL implements TwitterDatabaseAPI {

  private dbUtils dbu;

  public void postTweet(Tweet t) {

    String sqlStmt = "INSERT INTO Tweets (tweet_id, user_id, tweet_ts, tweet_text) VALUES"
        + "(%s, %s, '%s', '%s');";
    String sql = String.format(sqlStmt, t.getTweet_id(), t.getUser_id(), t.getTweet_ts_text(), t.getTweet_text());

    try {
      Connection con = dbu.getConnection();
      Statement smt = con.createStatement();
      smt.executeUpdate(sql);
      smt.close();
    } catch (SQLException e){
      System.err.println("Error: Could not insert record: "+ sql);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public void insertFollower(Follower f) {
    String sqlStmt = "INSERT INTO Followers (user_id, follows_id) VALUES"
        + "(%s, %s);";
    String sql = String.format(sqlStmt, f.getUser_id(), f.getFollows_id());

    try {
      Connection con = dbu.getConnection();
      Statement smt = con.createStatement();
      smt.executeUpdate(sql);
      smt.close();
    } catch (SQLException e){
      System.err.println("Error: Could not insert record: "+ sql);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public List<Tweet> getHomeTimeline(int user_id) {
    String query_followers = "CREATE VIEW follows_ids AS (SELECT follows_id FROM followers WHERE user_id =" + Integer.toString(user_id) + ");";
    String query_all_tweets = "CREATE VIEW all_tweets AS (SELECT * FROM tweets WHERE user_id in (SELECT * FROM follows_ids));";
    String query_timeline = "SELECT * FROM (SELECT * FROM all_tweets) as a_t ORDER BY tweet_ts DESC LIMIT 10;";
    String query_drop_followers = "DROP VIEW IF EXISTS follows_ids;";
    String query_drop_all_tweets = "DROP VIEW IF EXISTS all_tweets;";

    List<Tweet> timeline = new ArrayList<Tweet>();

    try {
      Connection con = dbu.getConnection();
      Statement smt = con.createStatement();

      smt.executeUpdate(query_followers);
      smt.executeUpdate(query_all_tweets);
      ResultSet rs = smt.executeQuery(query_timeline);

      while (rs.next()) {
        DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(rs.getString("tweet_ts"));
        timeline.add(new Tweet(rs.getInt("tweet_id"),
            rs.getInt("user_id"), dt,
            rs.getString("tweet_text")));
      }

      smt.executeUpdate(query_drop_followers);
      smt.executeUpdate(query_drop_all_tweets);
      rs.close();
      smt.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    return timeline;
  }

  public void authenticate(String user, String password) {
    dbu = new dbUtils("jdbc:mysql://127.0.0.1:3306/twitter_db?prop1",user, password);
  }

  public void closeConnection() {
    dbu.closeConnection();
  }
}
