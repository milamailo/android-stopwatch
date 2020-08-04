package ca.i3th.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    ImageButton startBtn, stopBtn;

    private boolean isActive;
    Handler handler;
    long timeMilliSecond, timeStart, timeStop, timeBuffer, timeUpdate =0L;
    int minute, second, millisecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        startBtn = findViewById(R.id.btnStart);
        stopBtn = findViewById(R.id.btnStop);

        handler = new Handler();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isActive){
                    timeStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isActive = true;
                    stopBtn.setVisibility(View.GONE);
//                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                    startBtn.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_baseline_pause_24, null));
                }
                else  {
                    timeBuffer += timeMilliSecond;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isActive = false;
                    stopBtn.setVisibility(View.VISIBLE);
                    startBtn.setImageDrawable(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.ic_baseline_play_arrow_24, null));
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isActive) {
                    startBtn.setImageDrawable(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.ic_baseline_play_arrow_24, null));
                    timeBuffer = timeMilliSecond = timeStart = timeUpdate = 0L;
                    minute = second = millisecond = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeMilliSecond = SystemClock.uptimeMillis() - timeStart;
            timeUpdate = timeBuffer + timeMilliSecond;
            second = (int) (timeUpdate / 1000);
            minute = second / 60;
            second = second % 60;
            millisecond = (int) (timeUpdate % 100);
            chronometer.setText(String.format("%02d", minute) + ":" + String.format("%02d", second)
                    + ":" + String.format("%02d", millisecond));
            handler.postDelayed(this, 60);
        }
    };
}