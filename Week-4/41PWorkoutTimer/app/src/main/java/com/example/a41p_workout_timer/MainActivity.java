package com.example.a41p_workout_timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    private EditText workoutDuration;
    private EditText restPeriodDuration;
    private EditText numberOfSets;
    private Button startStopWorkoutDurationBtn;
    private ProgressBar workoutProgressBar;
    private CountDownTimer workoutCountdownTimer;
    private CountDownTimer restPeriodCountdownTimer;
    private int timeLeft;
    private long setsLeft;
    private int restPeriod;
    private boolean timerRunning = false;
    private boolean isFirstRun = true;
    private int progressBarToEmpty;
    private int progressBarCounter;
    private int progressBarTicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startStopWorkoutDurationBtn = findViewById(R.id.idStartWorkoutTimerBtn);
        startStopWorkoutDurationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseWorkoutTimer();
                } else {
                    startWorkoutTimer();
                }
            }

            private void pauseWorkoutTimer() {
                timerRunning = false;
                workoutCountdownTimer.cancel();
                startStopWorkoutDurationBtn.setText("START");
            }

            private void startWorkoutTimer() {
                if (isFirstRun) {
                    // Initialise workout variables from user input
                    numberOfSetsParseLong();
                    restPeriodDurationParseLong();
                    workoutDurationParseLong();

                    isFirstRun = false;
                }

                workoutCountdownTimer = new CountDownTimer(timeLeft, 1000) {

                        int ticksTotal = timeLeft / 1000;
                        int progressBarDecrement = (100 / ticksTotal) ; // = 10% to decrement each on a 10 second timer
                        int progressBarPercentage = 100;

                        @Override
                        public void onTick(long l) {
                            timeLeft -= 1000;
                            progressBarPercentage -= progressBarDecrement;
                            updateWorkoutProgressBar(progressBarPercentage);
                        }

                        @Override
                        public void onFinish() {
                            setsLeft--;

                            if (setsLeft > 0) {
                                timeLeft = ticksTotal;
                                startRestPeriodTimer();
                            }
                        }
                    }.start();
                    timerRunning = true;
                    startStopWorkoutDurationBtn.setText("PAUSE");
                }
                private void startRestPeriodTimer() {
                int tempRestTime = restPeriod;
                if (isFirstRun) {
                    restPeriodDurationParseLong();
                }
                restPeriodCountdownTimer = new CountDownTimer(restPeriod, 1000) {
                    int ticksTotal = restPeriod / 1000;
                    int progressBarPercentage = 0;
                    @Override

                    public void onTick(long l) {
                        restPeriod -= 1000;
                        progressBarPercentage += ticksTotal;
                        updateWorkoutProgressBar(progressBarPercentage);
                    }

                    @Override
                    public void onFinish() {
                        restPeriod = tempRestTime;
                        startWorkoutTimer();
                    }
                }.start();
            }

            private void workoutDurationParseLong() {
                workoutDuration = findViewById(R.id.idWorkoutDurationEditText);
                String workoutDurationStr = workoutDuration.getText().toString();

                if (workoutDurationStr.isEmpty()) {
                    workoutDurationStr = "30";
                }

                int workoutDurationNum = Integer.parseInt(workoutDurationStr);
                timeLeft = workoutDurationNum * 1000;
            }

            private void restPeriodDurationParseLong() {
                restPeriodDuration = findViewById(R.id.idRestPeriodDurationEditText);
                String restPeriodDurationStr = restPeriodDuration.getText().toString();

                if (restPeriodDurationStr.isEmpty()) {
                    restPeriodDurationStr = "30";
                }

                int restPeriodDurationNum = Integer.parseInt(restPeriodDurationStr);

                restPeriod = restPeriodDurationNum * 1000;
            }

            private void numberOfSetsParseLong() {
                numberOfSets = findViewById(R.id.idSetSelector);
                String numberOfSetsStr = numberOfSets.getText().toString();

                if (numberOfSetsStr.isEmpty()) {
                    numberOfSetsStr = "3";
                }

                long numberOfSetsNum = Long.parseLong(numberOfSetsStr);
                setsLeft = numberOfSetsNum;
            }

            private int updateWorkoutProgressBar(int workoutProgress) {
                workoutProgressBar = findViewById(R.id.idWorkoutProgressBar);
                workoutProgressBar.setProgress(workoutProgress);
                return workoutProgress;
            }
        });
    }}