package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    int minutes;
    int seconds;
    int totalTime;
    MediaPlayer mediaPlayer;
    boolean playing = false;
    CountDownTimer timer;


    public void clickedButton(View view) {

        if(playing == false){
            startTimer();
        }
        else{
            reset();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.timeLeft);
        button = (Button) findViewById(R.id.startTimer);
        mediaPlayer = MediaPlayer.create(this, R.raw.rooster);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(0);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minutes = progress / 60;
                seconds = progress % 60;
                totalTime = progress;
                if(progress == 0){
                    seekBar.setProgress(1);
                }

                if (seconds < 10) {
                    textView.setText(Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
                } else {
                    textView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTimer(){
        playing = true;
        seekBar.setEnabled(false);
        button.setText("Stop");
        timer = new CountDownTimer(totalTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                minutes = totalTime / 60;
                seconds = totalTime % 60;

                if (seconds < 10) {
                    textView.setText(Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
                } else {
                    textView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
                }
                totalTime -= 1;

            }

            @Override
            public void onFinish() {
                mediaPlayer.start();
                reset();

            }
        }.start();

        if (totalTime == 0) {
            reset();
        }
    }

    public void reset(){
        button.setText("Start");
        textView.setText("0:00");
        playing = false;
        timer.cancel();
        seekBar.setEnabled(true);
    }
}
