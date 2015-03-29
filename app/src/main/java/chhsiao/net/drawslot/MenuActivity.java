package chhsiao.net.drawslot;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import static com.google.common.base.Preconditions.checkArgument;


public class MenuActivity extends ActionBarActivity {

    private static final GameSettings DEFAULT_GAME_SETTINGS = new GameSettings(5, 1, 10);
    private Dialog dialog;
    private Button btnStart;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bindView();
        setUpListener();
    }

    private void bindView() {
        dialog = createSettingDialog();
        btnStart = (Button) findViewById(R.id.btnStart);
        btnExit = (Button) findViewById(R.id.btnExit);
    }

    private void setUpListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });
    }

    private Dialog createSettingDialog() {
        final Dialog dialog = new Dialog(MenuActivity.this);
        dialog.setTitle(R.string.title_dialog_setting);
        dialog.setContentView(R.layout.dialog_setting);

        final NumberPicker numberPickerMaxCount = (NumberPicker) dialog.findViewById(R.id.numberPickerMaxCount);
        numberPickerMaxCount.setMinValue(DEFAULT_GAME_SETTINGS.DEFAULT_HIT_COUNT + 1);
        numberPickerMaxCount.setMaxValue(DEFAULT_GAME_SETTINGS.MAX_MAX_COUNT);
        numberPickerMaxCount.setValue(DEFAULT_GAME_SETTINGS.DEFAULT_MAX_COUNT);

        final NumberPicker numberPickerHitCount = (NumberPicker) dialog.findViewById(R.id.numberPickerHitCount);
        numberPickerHitCount.setMinValue(1);
        numberPickerHitCount.setMaxValue(DEFAULT_GAME_SETTINGS.DEFAULT_MAX_COUNT - 1);
        numberPickerHitCount.setValue(DEFAULT_GAME_SETTINGS.DEFAULT_HIT_COUNT);

        numberPickerMaxCount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerHitCount.setMaxValue(newVal - 1);
            }
        });

        numberPickerHitCount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerMaxCount.setMinValue(newVal + 1);
            }
        });

        setUpDialogOnClickListener(dialog, R.id.btnPlay, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                Bundle bundle = new Bundle();
                bundle.putInt("hitCount", numberPickerHitCount.getValue());
                bundle.putInt("maxCount", numberPickerMaxCount.getValue());
                Intent intent = new Intent(MenuActivity.this, DrawActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setUpDialogOnClickListener(dialog, R.id.btnCancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        return dialog;
    }

    static class GameSettings {
        final int DEFAULT_MAX_COUNT;
        final int DEFAULT_HIT_COUNT;
        final int MAX_MAX_COUNT;

        GameSettings(int DEFAULT_MAX_COUNT, int DEFAULT_HIT_COUNT, int MAX_MAX_COUNT) {
            checkArgument(DEFAULT_MAX_COUNT >= 2);
            checkArgument(DEFAULT_HIT_COUNT >= 1);
            checkArgument(MAX_MAX_COUNT >= 2);
            checkArgument(DEFAULT_MAX_COUNT > DEFAULT_HIT_COUNT);
            this.DEFAULT_MAX_COUNT = DEFAULT_MAX_COUNT;
            this.DEFAULT_HIT_COUNT = DEFAULT_HIT_COUNT;
            this.MAX_MAX_COUNT = MAX_MAX_COUNT;
        }
    }

    private void setUpDialogOnClickListener(Dialog dialog, int id, View.OnClickListener onClickListener) {
        View view = dialog.findViewById(id);
        view.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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

    @Override
    protected void onDestroy() {
        System.exit(0);
    }

}
