
/*
 * Ciel Park ccp7gcp HW5
 */
import java.util.ArrayList;

public class Feed {

    private ArrayList<Meme> memes;

    /**
     * feed default constructor
     */
    public Feed() {
        this.memes = new ArrayList<Meme>();
    }

    /**
     * getters and setters
     */
    public ArrayList<Meme> getMemes() {
        return this.memes;
    }

    public void setMemes(ArrayList<Meme> memes) {
        this.memes = memes;
    }

    /**
     * allows users to get a new meme
     */
    public Meme getNewMeme(User user) {
        if (this.memes != null) {
            for (Meme m : this.memes) {
                if (!user.getMemesViewed().contains(m) && !user.getMemesCreated().contains(m)) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * returns a string
     */
    @Override
    public String toString() {
        String s = "";
        for (Meme m : this.memes) {
            s += (m.toString() + "\n");
        }
        return s;
    }

}
