package chhsiao.net.drawslot;

import java.util.Random;

/**
 * Created by user on 2015/2/1.
 */
public class LotsLocation {
    private static final float PADDING_TOP = 0.1f;
    private static final float PADDING_LEFT = 0.1f;
    private static final float PADDING_RIGHT = 0.2f;
    private static final float PADDING_BOTTOM = 0.3f;
    private final int startX;
    private final int startY;
    private final int width;
    private final int height;
    private Random random = new Random();

    public LotsLocation(int x, int y) {
        this.width = (int) ((1 - PADDING_LEFT - PADDING_RIGHT) * x);
        this.height = (int) ((1 - PADDING_TOP - PADDING_BOTTOM) * y);
        this.startX = (int) (PADDING_LEFT * x);
        this.startY = (int) (PADDING_TOP * y);
    }

    public int nextRandomX() {
        return startX + random.nextInt(width);
    }

    public int nextRandomY() {
        return startY + random.nextInt(height);
    }
}
