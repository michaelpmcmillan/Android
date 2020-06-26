package dev.mikemcmillan.subhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class SubHunter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Debugging", "In onCreate");
        newGame();
        draw();
    }

    private void draw() {
        Log.d("Debugging", "In draw");
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

    }
}