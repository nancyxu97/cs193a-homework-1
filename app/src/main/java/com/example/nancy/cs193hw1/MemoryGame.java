package com.example.nancy.cs193hw1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class MemoryGame extends AppCompatActivity {
    public int[] colorPairs = {Color.parseColor("#f0f8ff"), Color.parseColor("#e5c1ea"), Color.parseColor("#e55ea2"),
            Color.parseColor("#97a7e3"), Color.parseColor("#ad97e3"), Color.parseColor("#e3b7cc"),
            Color.parseColor("#c3b0f9"), Color.parseColor("#baadcf")};
    public int[] gridIds = {R.id.p1,R.id.p2,R.id.p3,R.id.p4,R.id.p5,R.id.p6,R.id.p7,R.id.p8,R.id.p9,R.id.p10,R.id.p11,R.id.p12,R.id.p13,R.id.p14,R.id.p15,R.id.p16};
    // gridId -> colors in order
    public int[] colorLocations;
    // marks if one square chosen already
    public boolean oneSelected;
    public int selectedId;
    public int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colorLocations = new int[16];
        score = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        for (int i = 0; i < 16; i++) {
            colorLocations[i] = i;
        }
        shuffleArray(colorLocations);
        for (int i = 0; i < 16; i++){
            findViewById(gridIds[colorLocations[i]]).setBackgroundColor(colorPairs[(int)i/2]);
            findViewById(gridIds[colorLocations[i]]).setEnabled(false);
        }
    }

    private void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    public void startGame(View view) {
        for (int i = 0; i < 16; i++){
            findViewById(gridIds[i]).setBackgroundColor(Color.parseColor("#000000"));
            findViewById(gridIds[i]).setEnabled(true);
        }
        findViewById(R.id.startButton).setEnabled(false);
    }
    public Toast currentToast;
    public void tileSelected(View view) {
        if (currentToast != null) currentToast.cancel();
        boolean matchFound = false;
        int currentId = view.getId();
        if (oneSelected) {
            for (int i = 0; i < 8; i++) {
                if (gridIds[colorLocations[2 * i]] == currentId && gridIds[colorLocations[2 * i + 1]] == selectedId ||
                        gridIds[colorLocations[2 * i]] == selectedId && gridIds[colorLocations[2 * i + 1]] == currentId) {
                    score++;
                    TextView temp = (TextView) findViewById(R.id.scoreText);
                    temp.setText("Score: " + Integer.toString(score));
                    findViewById(currentId).setBackgroundColor(colorPairs[i]);
                    findViewById(selectedId).setBackgroundColor(colorPairs[i]);
                    findViewById(currentId).setEnabled(false);
                    findViewById(selectedId).setEnabled(false);
                    matchFound = true;
                    System.out.println(score%8);
                    if (score % 8 == 0){
                        Toast.makeText(this, "New board!", Toast.LENGTH_SHORT).show();
                        for (int j = 0; j < 16; j++) {
                            colorLocations[j] = j;
                        }
                        shuffleArray(colorLocations);
                        for (int j = 0; j < 16; j++){
                            findViewById(gridIds[colorLocations[j]]).setBackgroundColor(colorPairs[(int)j/2]);
                            findViewById(gridIds[colorLocations[j]]).setEnabled(false);
                        }
                        findViewById(R.id.startButton).setEnabled(true);
                    }
                }
            }
        }
        if (oneSelected && !matchFound) {
            currentToast = Toast.makeText(this, "Incorrect match. Try again!", Toast.LENGTH_SHORT);
            currentToast.show();
            oneSelected = false;
        }
        if (!oneSelected) {
            oneSelected = true;
            selectedId = view.getId();
        }
    }
}
