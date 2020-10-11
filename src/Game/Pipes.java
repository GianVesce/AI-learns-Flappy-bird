package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pipes extends GameObject{
    Rectangle bottomPipeBounds;
    Rectangle topPipeBounds;

    private RenderingPanel renderingPanel;

    private String spritePath;
    private Image pipeSprite;

    private int gapSize;
    private int gapHeight;

    private double xSpeed;
    //Using the currentX as a double for greater control over pipe's speed
    private double currentX;

    //Has already been passed by the bird?
    private boolean hasBeenPassed;

    private Dimension panelSize;
    private Dimension spriteDimension;

    private int pipeRenderedHeight;

    public Pipes(Rectangle bounds, String spritePath, RenderingPanel renderingPanel) {
        super(bounds);
        this.currentX = bounds.x;
        xSpeed = 0;
        this.spritePath = spritePath;
        hasBeenPassed = false;
        this.renderingPanel = renderingPanel;
        this.panelSize = renderingPanel.getPanelSize();
    }

    @Override
    void initialize() {
        try {
            pipeSprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        spriteDimension = new Dimension(pipeSprite.getWidth(null), pipeSprite.getHeight(null));
    }

    @Override
    void update() {
        //Makes the pipe move
        if(isOutOfLeftMap()) {
           renderingPanel.pipeExited(this);
        }else
            currentX -= xSpeed;

        bounds.x = (int)currentX;
        topPipeBounds.x = (int)currentX;
        bottomPipeBounds.x = (int)currentX;
    }

    @Override
    void render(Graphics g) {
        double scale = (double)spriteDimension.width/(double)bounds.width;
        double renderedHeight = spriteDimension.height*scale*2;

        g.drawImage(pipeSprite, topPipeBounds.x, topPipeBounds.height, topPipeBounds.width, (int)(-renderedHeight), null);
        g.drawImage(pipeSprite, bottomPipeBounds.x, bottomPipeBounds.y, bottomPipeBounds.width, (int)(renderedHeight), null);
        //g.drawLine(bounds.x, gapHeight, bounds.x + bounds.width, gapHeight);
    }

    void generateRandomGapHeight() {
        int offset = gapSize/2;

        //Minumum space between the borders
        int minSpace = bounds.height/100;
        int minLow = minSpace + gapSize/2;
        int minHigh = bounds.height - (minSpace + gapSize/2);
        gapHeight = (int)(Math.random() * (minHigh - minLow) + minLow);
        //System.out.println(" gapHeight: " + gapHeight + " minSpace: " + minSpace);

        this.topPipeBounds = new Rectangle(bounds.x, 0, bounds.width, gapHeight - offset);
        this.bottomPipeBounds = new Rectangle(bounds.x, topPipeBounds.height + gapSize, bounds.width, bounds.height - gapHeight - offset);
    }

    public String toString() {
        return
                "Gap size:   " + gapSize   +
                "\nBounds:     " + bounds.toString() +
                "\nTopPipe:    " + topPipeBounds.toString() +
                "\nBottomPipe: " + bottomPipeBounds.toString();
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    //Collides only with the pipes and not the gap between them
    public boolean collides(GameObject g) {
        return topPipeBounds.intersects(g.bounds) || bottomPipeBounds.intersects(g.bounds);
    }

    public boolean isOutOfLeftMap() {
        return (bounds.x + bounds.width) <= 0;
    }

    //Has been passed by the bird?
    public boolean passed(Bird b) {
        if(b.bounds.x > (bounds.x + bounds.y) && !hasBeenPassed) {
            hasBeenPassed = true;
            return true;
        }
        return false;
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    void reset(int x) {
        currentX = x;
        hasBeenPassed = false;
        generateRandomGapHeight();
    }
}
