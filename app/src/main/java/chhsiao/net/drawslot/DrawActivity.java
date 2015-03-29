package chhsiao.net.drawslot;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class DrawActivity extends ActionBarActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private volatile int maxLotCount;
    private volatile int hitLotCount;
    private DrawsLotFrameLayout drawsLotFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        bindView();
        initView();
        setUpListener();
        init(getIntent().getExtras());

    }

    private void bindView() {
        drawsLotFrameLayout = (DrawsLotFrameLayout) findViewById(R.id.viewDrawGame);
    }

    private void initView() {
        drawsLotFrameLayout.setLotsLocation(getLotsLocation());
    }

    private void setUpListener() {
    }

    private void init(Bundle bundle) {
        maxLotCount = bundle.getInt("maxCount");
        hitLotCount = bundle.getInt("hitCount");
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
            drawsLotFrameLayout.newGame(maxLotCount, hitLotCount);
        }
    }
}
