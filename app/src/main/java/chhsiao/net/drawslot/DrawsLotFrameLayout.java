package chhsiao.net.drawslot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawsLotFrameLayout extends FrameLayout {
    private static final int PAPER_BALL_LENGTH = 150;

    private LotsLocation lotsLocation;
    private DrawsLotGame game;
    private TextView textViewInformation;

    public DrawsLotFrameLayout(Context context) {
        super(context);
    }

    public DrawsLotFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawsLotFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public synchronized void newGame(int lotCount, int hitCount) {
        this.removeAllViews();
        game = generateGame(lotCount, hitCount);
        generateTextViewInformation();
        generateButton();
    }

    private DrawsLotGame generateGame(int lotCount, int hitCount) {
        return new DrawsLotGame(lotCount, hitCount);
    }

    private void generateTextViewInformation() {
        textViewInformation = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.bottomMargin = 0;
        params.rightMargin = 0;
        addView(textViewInformation, params);
        refreshInformation();
    }

    private void refreshInformation() {
        textViewInformation.setText(String.format("%s: %d / %d", getContext().getString(R.string.msg_lots_information), game.getRemainHitLots(), game.getRemainLots()));
    }

    private void generateButton() {
        for (final DrawsLot drawsLot : game.generateLotResults()) {
            ImageView paperBall = new ImageView(getContext());
            paperBall.setImageResource(R.drawable.paperball);
            paperBall.setScaleType(ImageView.ScaleType.FIT_CENTER);

            paperBall.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    popUpIsHitImage(drawsLot.hit());
                    DrawsLotFrameLayout.this.removeView(view);
                    refreshInformation();
                }
            });

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.height = PAPER_BALL_LENGTH;
            params.width = PAPER_BALL_LENGTH;
            params.leftMargin = lotsLocation.nextRandomX();
            params.topMargin = lotsLocation.nextRandomY();
            addView(paperBall, params);
        }
    }

    private void popUpIsHitImage(boolean hit) {
        ImageView showIsHit = new ImageView(getContext());
        showIsHit.setScaleType(ImageView.ScaleType.FIT_CENTER);
        showIsHit.setImageResource(hit ? R.drawable.paperhit : R.drawable.paper);
        showIsHit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(v);
                if (game.isGameEnd()) {
                    game.restart();
                    generateButton();
                    refreshInformation();
                }
            }
        });
        addView(showIsHit);
    }

    public void setLotsLocation(LotsLocation lotsLocation) {
        this.lotsLocation = lotsLocation;
    }

}
