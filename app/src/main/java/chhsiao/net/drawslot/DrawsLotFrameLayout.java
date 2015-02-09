package chhsiao.net.drawslot;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by user on 2015/2/1.
 */
public class DrawsLotFrameLayout extends FrameLayout {
    private static final int DEFAULT_MAX_COUNT = 5;
    private static final int DEFAULT_HIT_COUNT = 2;
    private static final int PAPER_BALL_LENGTH = 150;

    private LotsLocation lotsLocation;

    public DrawsLotFrameLayout(Context context) {
        super(context);
    }

    public DrawsLotFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawsLotFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void newGame() {
        this.removeAllViews();
        generateButton(generateGame());
    }

    private DrawsLotGame generateGame() {
        return new DrawsLotGame(DEFAULT_MAX_COUNT, DEFAULT_HIT_COUNT);
    }

    private void generateButton(DrawsLotGame drawsLotGame) {
        for (final DrawsLot drawsLot : drawsLotGame.generateLotResults()) {
            ImageView paperBall = new ImageView(getContext());
            paperBall.setImageResource(R.drawable.paperball);
            paperBall.setScaleType(ImageView.ScaleType.FIT_CENTER);

            paperBall.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawsLotFrameLayout.this.removeView(view);
                }
            });

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.height = PAPER_BALL_LENGTH;
            params.width = PAPER_BALL_LENGTH;
            params.leftMargin = lotsLocation.nextRandomX();
            params.topMargin = lotsLocation.nextRandomY();
            this.addView(paperBall, params);
        }
    }

    private void popUpIsHitImage(boolean hit) {
        ImageView showIsHit = new ImageView(getContext());
    }

    public void setLotsLocation(LotsLocation lotsLocation) {
        this.lotsLocation = lotsLocation;
    }
}
