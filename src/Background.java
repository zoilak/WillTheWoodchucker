import processing.core.PImage;

import java.util.List;

/**
 * Represents a background for the 2D world.
 */
public final class Background {
    private String id;
    private List<PImage> images;
    private int imageIndex;
    //getter
    public int getImageIndex(){ return imageIndex;}
    public List<PImage> getImages() {return images;}

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }
}
