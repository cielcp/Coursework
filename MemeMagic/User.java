
/*
 * Ciel Park ccp7gcp HW5
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class User {

    /**
     * instance variables! the user's username, memes created, and memes viewed
     */
    private String userName;
    private ArrayList<Meme> memesCreated;
    private TreeSet<Meme> memesViewed;

    /**
     * user default constructor
     */
    public User() {
        this.userName = "";
        this.memesCreated = new ArrayList<Meme>();
        this.memesViewed = new TreeSet<Meme>();
    }

    /**
     * user constructor
     */
    public User(String userName) {
        this.userName = userName;
        this.memesCreated = new ArrayList<Meme>();
        this.memesViewed = new TreeSet<Meme>();
    }

    /**
     * getters and setters
     */
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Meme> getMemesCreated() {
        return this.memesCreated;
    }

    public void setMemesCreated(ArrayList<Meme> memesCreated) {
        this.memesCreated = memesCreated;
    }

    public ArrayList<Meme> getMemesViewed() {
        ArrayList<Meme> arr = new ArrayList<Meme>();
        arr.addAll(this.memesViewed);
        return arr;
    }

    public void setMemesViewed(ArrayList<Meme> memesViewed) {
        this.memesViewed = new TreeSet<>(memesViewed);
    }

    /**
     * allows users to rate specific memes
     */
    public void rateMeme(Meme meme, int rating) {
        if (this.getMemesViewed() != null) {
            this.memesViewed.add(meme);// records the meme as having been viewed by user
        } else {
            this.memesViewed = new TreeSet<>();
            this.memesViewed.add(meme);
        }
        Rating nr = new Rating(this, rating);
        meme.addRating(nr); // adds rating to the meme
    }

    /**
     * allows users to create memes with specific backgroundimage and caption
     */
    public Meme createMeme(BackgroundImage backgroundImage, String caption) {
        Meme x = new Meme(backgroundImage, caption, this);
        if (this.getMemesCreated() != null) {
            this.memesCreated.add(x);
        } else {
            this.memesCreated = new ArrayList<Meme>(1);
            this.memesCreated.add(x);
        }
        return x;
    }

    /**
     * allows users to delete memes
     */
    public boolean deleteMeme(Meme meme) {
        if (!meme.getShared()) {
            if (memesCreated.contains(meme)) {
                memesCreated.remove(meme);
                return true;
            }
        }
        return false;
    }

    /**
     * allows users to share memes with other feeds
     */
    public void shareMeme(Meme meme, Feed feed) {
        meme.setShared(true);
        (feed.getMemes()).add(meme);
    }

    /**
     * allows users to rate the memes on their feed
     */
    public boolean rateNextMemeFromFeed(Feed feed, int ratingScore) {
        Meme m = feed.getNewMeme(this);
        Rating rs = new Rating(this, ratingScore);

        if (m != null && this.getMemesViewed() != null) {
            if (!memesViewed.contains(m)) {
                this.memesViewed.add(m);
                m.addRating(rs);
                return true;
            }
        }
        return false;
    }

    /**
     * allows users to calculate their reputation
     */

    public double calculateReputation() {
        if (this.memesCreated.size() == 0) {
            return 0.0;
        }
        int repCount = 0;
        double repTotal = 0.0;
        for (int i = 0; i < this.memesCreated.size(); i++) {
            repTotal += this.memesCreated.get(i).calculateOverallRating();
            repCount++;
        }
        return repTotal / repCount;
    }

    // counts number of memes viewed
    int count = 0;

    public int memesViewedCount() {
        if (this.getMemesViewed() != null) {
            for (Meme m : this.getMemesViewed()) {
                if (m != null) {
                    count = count + 1;
                }
            }
            return count;
        }
        return count;
    }

    /**
     * returns a string
     */
    @Override
    public String toString() {
        String uName = this.getUserName();
        int viewed = this.memesViewedCount();
        double reputation = this.calculateReputation();
        return (uName + " has rated (" + viewed + ") memes, (" + reputation + ")");
    }

    /**
     * accepts an object, returns a boolean
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof User) || o == null) {
            return false;
        }

        User x = (User) o;

        if (this.getUserName() != x.getUserName()) {
            return false;
        }
        return true;
    }

    /**
     * compares this user to user u
     */
    public int compareTo(User u) {
        int nComparison = this.getUserName().compareTo(u.getUserName());
        if (nComparison != 0) {
            return nComparison;
        }
        int mComparison = (u.getMemesCreated().size()) - (this.getMemesCreated().size());
        if (mComparison != 0) {
            return mComparison;
        }
        return 0;
    }
}
