package chhsiao.net.drawslot;

import static com.google.common.base.Preconditions.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 2015/2/1.
 */
public class DrawsLotGame {
    private final List<Lot> lots;

    public DrawsLotGame(int maxCount, int hitCount) {
        checkArgument(maxCount > hitCount, "maxCount must large then hitCount");
        lots = new ArrayList<>(maxCount);
        for (int lotId = 1; lotId <= maxCount; lotId++) {
            lots.add(new Lot(lotId <= hitCount));
        }
    }

    public DrawsLot draws(final int index) {
        return new DrawsLot() {
            @Override
            public boolean hit() {
                return lots.get(index).isHit();
            }
        };
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
}
