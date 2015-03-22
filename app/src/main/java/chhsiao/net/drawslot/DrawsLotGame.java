package chhsiao.net.drawslot;

import static com.google.common.base.Preconditions.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by user on 2015/2/1.
 */
public class DrawsLotGame {
    private final List<Lot> lots;
    private final Collection<Integer> hasDrawNumbers = new HashSet<>();
    private final int maxLotCount;
    private final int hitLotCount;
    private volatile int remainLots;
    private volatile int remainHitLots;

    public DrawsLotGame(int maxLotCount, int hitLotCount) {
        checkArgument(maxLotCount > hitLotCount, "maxCount must large then hitCount");
        lots = new ArrayList<>(maxLotCount);
        for (int lotId = 1; lotId <= maxLotCount; lotId++) {
            lots.add(new Lot(lotId <= hitLotCount));
        }

        this.maxLotCount = maxLotCount;
        this.hitLotCount = hitLotCount;
        remainLots = maxLotCount;
        remainHitLots = hitLotCount;
    }

    public synchronized DrawsLot draws(final int index) {
        return new DrawsLot() {
            @Override
            public boolean hit() {
                checkState(!hasDrawNumbers.contains(index), "cannot draw one lot twice");
                hasDrawNumbers.add(index);

                boolean hit = lots.get(index).isHit();
                remainLots--;
                if (hit) {
                    remainHitLots--;
                }

                return hit;
            }
        };
    }

    public synchronized void restart() {
        remainLots = maxLotCount;
        remainHitLots = hitLotCount;
        hasDrawNumbers.clear();
    }

    public Collection<DrawsLot> generateLotResults() {
        return new AbstractList<DrawsLot>() {
            @Override
            public DrawsLot get(int location) {
                return draws(location);
            }

            @Override
            public int size() {
                return lots.size();
            }
        };
    }

    public boolean isGameEnd() {
        return remainLots == 0;
    }

    public int getRemainLots() {
        return remainLots;
    }

    public int getRemainHitLots() {
        return remainHitLots;
    }
}
