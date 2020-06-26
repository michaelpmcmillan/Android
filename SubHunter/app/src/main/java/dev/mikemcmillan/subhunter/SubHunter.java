package dev.mikemcmillan.subhunter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

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

    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Debugging", "In onCreate");

        initialiseDeviceScreenResolution();
        initialiseDrawingObjects();

        newGame();
        draw();
    }

    private void initialiseDrawingObjects() {
        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();
        setContentView(gameView);
    }

    private void initialiseDeviceScreenResolution() {
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

        gameView.setImageBitmap(blankBitmap);
        drawBackground();
        drawGrid();
        drawHud();

        printDebuggingText();
    }

    private void drawBackground() {
        canvas.drawColor(Color.argb(255, 255, 255, 255));
    }

    private void drawGrid() {
        paint.setColor(Color.argb(255, 0, 0, 0));

        drawVerticalGridLines();
        drawHorizontalGridLines();
    }

    private void drawVerticalGridLines() {
        for(int i = 0; i < gridWidth; i++){
            canvas.drawLine(blockSize * i, 0,
                    blockSize * i, numberVerticalPixels,
                    paint);
        }
    }

    private void drawHorizontalGridLines() {
        for(int i = 0; i < gridHeight; i++){
            canvas.drawLine(0, blockSize * i,
                    numberHorizontalPixels, blockSize * i,
                    paint);
        }
    }

    private void drawHud() {
        paint.setTextSize(blockSize * 2);

        paint.setColor(Color.argb(255, 0, 0, 255));

        canvas.drawText(
                "Shots Taken: " + shotsTaken + "  Distance: " + distanceFromSub,
                blockSize, blockSize * 1.75f,
                paint);
    }

    private void newGame() {
        Log.d("Debugging", "In newGame");

        Random random = new Random();
        subHorizontalPosition = random.nextInt(gridWidth);
        subVerticalPosition = random.nextInt(gridHeight);
        shotsTaken = 0;
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
        paint.setTextSize(blockSize);

        drawText("numberHorizontalPixels = " + numberHorizontalPixels, blockSize * 3);
        drawText("numberVerticalPixels = " + numberVerticalPixels, blockSize * 4);
        drawText("blockSize = " + blockSize, blockSize * 5);
        drawText("gridWidth = " + gridWidth, blockSize * 6);
        drawText("gridHeight = " + gridHeight, blockSize * 7);
        drawText("horizontalTouched = " + horizontalTouched, blockSize * 8);
        drawText("verticalTouched = " + verticalTouched, blockSize * 9);

        drawText("subHorizontalPosition = " + subHorizontalPosition, blockSize * 10);
        drawText("subVerticalPosition = " + subVerticalPosition, blockSize * 11);
        drawText("hit = " + hit, blockSize * 12);
        drawText("shotsTaken = " + shotsTaken, blockSize * 13);

        drawText("debugging = " + debugging, blockSize * 14);
    }

    private void drawText(String text, int y) {
        canvas.drawText(text, 50, y, paint);
    }
}