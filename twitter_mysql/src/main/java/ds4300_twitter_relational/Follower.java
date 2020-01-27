package ds4300_twitter_relational;

public class Follower {

  private int user_id;
  private int follows_id;

  /**
   * General constructor taking in all parameters
   * @param user_id the unique id representing a user
   * @param follows_id the unique id representing the user being followed by the user with id user_id
   */
  public Follower(int user_id, int follows_id) {
    this.user_id = user_id;
    this.follows_id = follows_id;
  }

  public int getFollows_id() {
    return follows_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public String toString() {
    return "Follower{" +
        "user_id=" + this.user_id + '\'' +
        "follows_id=" + this.follows_id +
        "}" + '\'';
  }
}
