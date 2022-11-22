/*
 * Ciel Park ccp7gcp HW3
 */
public class Rating {

    /**
     * instance variables
     */
    private int score;
    private User user;

    /**
     * rating default constructor
     */
    public Rating() {
        this.score = 0;
        this.user = new User();
    }

    /**
     * rating constructor
     */
    public Rating(User user, int score) {
        this.score = score;
        this.user = user;
    }

    /**
     * getters and setters
     */
    public int getScore() {
        return score;
    }

    public boolean setScore(int score) {
        if (score == +1 || score == -1 || score == 0) {
            this.score = score;
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * returns a string
     */
    String type_of_rating;

    @Override
    public String toString() {

        if (this.getScore() == +1) {
            type_of_rating = " an upvote";
        } else if (this.getScore() == -1) {
            type_of_rating = " a downvote";
        } else if (this.getScore() == 0) {
            type_of_rating = " a pass";
        }
        String uName = this.getUser().getUserName();
        return (uName + " rated as" + type_of_rating);
    }

    /**
     * returns true if object o is equal to this Rating
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Rating) || o == null) {
            return false;
        }

        Rating x = (Rating) o;

        if (this.getUser() != x.getUser()) {
            return false;
        }
        if (this.getScore() != x.getScore()) {
            return false;
        }

        return true;
    }
}
