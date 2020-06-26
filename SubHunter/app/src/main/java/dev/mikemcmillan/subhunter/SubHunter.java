package dev.mikemcmillan.subhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

public class SubHunter extends Activity {

    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = -100;
    int subHorizontalPosition;
    int subVerticalPosition;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;
    boolean debugging = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Debugging", "In onCreate");

        getDeviceScreenResolution();

        newGame();
        draw();
    }

    private void getDeviceScreenResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;
    }

    private void draw() {
        Log.d("Debugging", "In draw");
        printDebuggingText();
    }

    private void newGame() {
        Log.d("Debugging", "In newGame");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Debugging", "In onTouchEvent");
        takeShot();
        return true;
    }

    private void takeShot() {
        Log.d("Debugging", "In onTouchEvent");
        draw();
    }

    private void boom() {

    }

    private void printDebuggingText() {
        Log.d("numberHorizontalPixels", "" + numberHorizontalPixels);
        Log.d("numberVerticalPixels", "" + numberVerticalPixels);

        Log.d("blockSize", "" + blockSize);
        Log.d("gridWidth", "" + gridWidth);
        Log.d("gridHeight", "" + gridHeight);

        Log.d("horizontalTouched", "" + horizontalTouched);
        Log.d("verticalTouched", "" + verticalTouched);
        Log.d("subHorizontalPosition", "" + subHorizontalPosition);
        Log.d("subVerticalPosition", "" + subVerticalPosition);
    }
}