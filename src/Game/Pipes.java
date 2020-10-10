package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pipes extends GameObject{
    Rectangle bottomPipeBounds;
    Rectangle topPipeBounds;

    private String spritePath;
    private Image pipeSprite;

    private int gapHeight;

    public Pipes(Rectangle bounds, String spritePath) {
        super(bounds);

        this.spritePath = spritePath;
    }

    @Override
    void initialize() {
        try {
            pipeSprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateGapHeight(bounds.width/2, 15);
    }

    @Override
    void update() {
    }

    @Override
    void render(Graphics g) {
        g.drawImage(pipeSprite, topPipeBounds.x, topPipeBounds.y + bounds.width, topPipeBounds.width, topPipeBounds.height, null);
        g.drawImage(pipeSprite, bottomPipeBounds.x, bottomPipeBounds.y + bounds.width, bottomPipeBounds.width, bottomPipeBounds.height, null);
        g.drawImage(pipeSprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    void generateGapHeight(int gapHeight, int gapSize) {
        this.gapHeight = gapHeight;
        int offset = gapSize/2;
        System.out.println(gapSize);
        this.topPipeBounds = new Rectangle(bounds.x, 0, bounds.width, gapHeight - offset);
        this.bottomPipeBounds = new Rectangle(bounds.x, gapHeight + offset, bounds.width, bounds.height - gapHeight - offset);
    }
}
