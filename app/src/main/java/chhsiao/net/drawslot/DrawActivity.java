package chhsiao.net.drawslot;

import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class DrawActivity extends ActionBarActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int DEFAULT_MAX_LOT_COUNT = 5;
    private static final int DEFAULT_HIT_LOT_COUNT = 1;

    private volatile int maxLotCount;
    private volatile  int hitLotCount;
    private DrawsLotFrameLayout drawsLotFrameLayout;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        bindView();
        initView();
        setUpListener();
        init();

    }

    private void bindView() {
        drawsLotFrameLayout = (DrawsLotFrameLayout) findViewById(R.id.draw_game_view);
    }

    private void initView() {
        drawsLotFrameLayout.setLotsLocation(getLotsLocation());
        gestureDetector = new GestureDetector(this, new GameGestureDetector());
    }

    private void setUpListener() {
        drawsLotFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void init() {
        maxLotCount = DEFAULT_MAX_LOT_COUNT;
        hitLotCount = DEFAULT_HIT_LOT_COUNT;
        drawsLotFrameLayout.newGame(maxLotCount, hitLotCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private LotsLocation getLotsLocation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        return new LotsLocation(point.x, point.y);
    }

    class GameGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    setUpGame(maxLotCount + 1, hitLotCount);
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    setUpGame(maxLotCount - 1, hitLotCount);
                } else if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    setUpGame(maxLotCount, hitLotCount - 1);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    setUpGame(maxLotCount, hitLotCount + 1);
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    private synchronized void setUpGame(int newMaxLotCount, int newHitLotCount) {
        if (newMaxLotCount <= 0 || newHitLotCount <= 0 || newHitLotCount >= newMaxLotCount) {
            // illegal argument to start a draw lot game
        } else {
            maxLotCount = newMaxLotCount;
            hitLotCount = newHitLotCount;
            drawsLotFrameLayout.newGame(maxLotCount, hitLotCount);;
        }
    }
}
