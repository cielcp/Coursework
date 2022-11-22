/*
 * Ciel Park ccp7gcp HW5
 */
public class Meme {

    /**
     * instance variables
     */
    private User creator;
    private BackgroundImage backgroundImage;
    private Rating[] ratings;
    private String caption;
    private String captionVerticalAlign;
    private boolean shared;

    /**
     * meme default constructor
     */
    public Meme() {
        this.creator = new User();
        this.backgroundImage = new BackgroundImage();
        this.caption = "";
        this.ratings = new Rating[10];
        this.captionVerticalAlign = "bottom";
        this.shared = false;
    }

    /**
     * meme constructor
     */
    public Meme(BackgroundImage backgroundImage, String caption, User creator) {
        this.backgroundImage = backgroundImage;
        this.caption = caption;
        this.creator = creator;

        ratings = new Rating[10];
        captionVerticalAlign = "bottom";
        shared = false;
    }

    /**
     * getters and setters
     */
    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public BackgroundImage getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(BackgroundImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Rating[] getRatings() {
        return this.ratings;
    }

    public void setRatings(Rating[] ratings) {
        this.ratings = ratings;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionVerticalAlign() {
        return this.captionVerticalAlign;
    }

    public boolean setCaptionVerticalAlign(String captionVerticalAlign) {
        if (captionVerticalAlign == "top" || captionVerticalAlign == "middle" || captionVerticalAlign == "bottom") {
            this.captionVerticalAlign = captionVerticalAlign;
            return true;
        }
        return false;
    }

    public boolean getShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    /**
     * allows users to add a rating to a meme
     */
    public boolean addRating(Rating r) {
        if (r.getScore() == 1 || r.getScore() == -1 || r.getScore() == 0) { // if r is a valid score input

            if (this.ratings[9] != null) { // if ratings is full
                for (int i = 0; i < 9; i++) {
                    this.ratings[i] = this.ratings[i + 1];
                }
                this.ratings[9] = r;
            }

            else { // if the 10th spot in ratings is open
                for (int i = 0; i < 10; i++) {
                    if (this.ratings[i] == null) { // if the ith spot in ratings is open
                        this.ratings[i] = r;
                        break;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * allows users to calculate the overall rating of a meme
     */
    public double calculateOverallRating() {
        double overallRating = 0.0;
        for (int i = 0; i < 10; i++) {
            if (this.ratings[i] != null) {
                overallRating = overallRating + this.ratings[i].getScore();
            }
        }
        return overallRating;
    }

    // counts the number of positive ratings in Ratings[]
    private int plusCount() {
        int plusCount = 0;
        for (int i = 0; i < 10; i++) {
            if (this.ratings[i] != null) {
                if (this.ratings[i].getScore() == 1) {
                    plusCount = plusCount + 1;
                }
            }
        }
        return plusCount;
    }

    // counts the number of negative ratings in Ratings[]
    private int minusCount() {
        int minusCount = 0;
        for (int i = 0; i < 10; i++) {
            if (this.ratings[i] != null) {
                if (this.ratings[i].getScore() == -1) {
                    minusCount = minusCount + 1;
                }
            }
        }
        return minusCount;
    }

    /**
     * returns a string
     */
    @Override
    public String toString() {
        String bgImage = backgroundImage.toString();
        String caption = this.getCaption();
        double overallRating = this.calculateOverallRating();
        String uName = (this.getCreator()).getUserName();
        return bgImage + " '" + caption + "' " + overallRating + " [+1: " + this.plusCount() + ", -1: " + this.minusCount()
                + "] - created by " + uName;
    }

    /**
     * returns true if object o is equal to this Meme
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Meme) || o == null) {
            return false;
        }

        Meme x = (Meme) o;

        if (this.getBackgroundImage() != x.getBackgroundImage()) {
            return false;
        }
        if (!(this.getCaption().equals(x.getCaption()))) {
            return false;
        }
        if (this.getCreator() != x.getCreator()) {
            return false;
        }
        return true;
    }
    /**
     * compares this meme to meme m
     */
    public int compareTo(Meme m) {
        int nComparison = this.getCaption().compareTo(m.getCaption());
        if (nComparison != 0) {
            return nComparison;
        }
        int tComparison = this.getBackgroundImage().compareTo(m.getBackgroundImage());
        if (tComparison != 0) {
            return tComparison;
        }
        int dComparison = (int) (m.calculateOverallRating() - this.calculateOverallRating());
        if (dComparison != 0) {
            return dComparison;
        }
        if (this.getShared() == true) {
            return -1;
        }
        if (m.getShared() == true) {
            return 1;
        }
        return 0;
    }
}
