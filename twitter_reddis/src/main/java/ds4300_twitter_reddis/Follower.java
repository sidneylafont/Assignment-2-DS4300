package ds4300_twitter_reddis;

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

  /**
   * Determines the user id of the user who is a follower of the user_id
   * @return the user id of the user who is a follower of the user_id
   */
  public int getFollows_id() {
    return follows_id;
  }

  /**
   * Determines the user id of the user who is being followed
   * @return the user id of the user who is being followed
   */
  public int getUser_id() {
    return user_id;
  }

  /**
   * Converts this follower to a string representation of this follower
   * @return a string representation of this follower
   */
  public String toString() {
    return "ds4300_twitter_reddis.Follower{" +
        "user_id=" + this.user_id + '\'' +
        "follows_id=" + this.follows_id +
        "}" + '\'';
  }
}
