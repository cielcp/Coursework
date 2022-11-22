/*
 * Ciel Park ccp7gcp HW5
 */
public class BackgroundImage {

    /**
     * instance variables
     */
    private String imageFileName;
    private String title;
    private String description;

    /**
     * backgroundImage default constructor
     */
    public BackgroundImage() {
        this.imageFileName = "";
        this.title = "";
        this.description = "";
    }

    /**
     * backgroundImage constructor
     */
    public BackgroundImage(String imageFileName, String title, String description) {
        this.imageFileName = imageFileName;
        this.title = title;
        this.description = description;
    }

    /**
     * getters and setters
     */
    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns a string
     */
    @Override
    public String toString() {
        return getTitle() + " <" + getDescription() + ">";
    }

    /**
     * returns true if object o is equal to this BackgroundImage
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof BackgroundImage) || o == null) {
            return false;
        }

        BackgroundImage x = (BackgroundImage) o;

        if (!(this.getImageFileName().equals(x.getImageFileName()))) {
            return false;
        }
        if (!(this.getTitle().equals(x.getTitle()))) {
            return false;
        }
        if (!(this.getDescription().equals(x.getDescription()))) {
            return false;
        }
        return true;
    }
    
    /**
     * compares this background image to background image b
     */
    public int compareTo(BackgroundImage b) {
        int nComparison = this.getImageFileName().compareTo(b.getImageFileName());
        if (nComparison != 0) {
            return nComparison;
        }
        int tComparison = this.getTitle().compareTo(b.getTitle());
        if (tComparison != 0) {
            return tComparison;
        }
        int dComparison = this.getDescription().compareTo(b.getDescription());
        if (dComparison != 0) {
            return dComparison;
        }
        return 0;
    }
}
