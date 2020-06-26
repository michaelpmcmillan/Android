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
    boolean debugging = false;

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
        drawLastShot();
        drawHud();

        if (debugging) {
            printDebuggingText();
        }
    }

    private void drawLastShot() {
        canvas.drawRect(horizontalTouched * blockSize,
                verticalTouched * blockSize,
                (horizontalTouched * blockSize) + blockSize,
                (verticalTouched * blockSize)+ blockSize,
                paint );
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

        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            takeShot(event.getX(), event.getY());
        }
        return true;
    }

    private void takeShot(float touchX, float touchY) {
        Log.d("Debugging", "In onTouchEvent");

        shotsTaken++;
        Point gridCoordinates = getGridCoordinatesFromTouchCoordinates(touchX, touchY);

        if (getSubmarineHit(gridCoordinates)) {
            boom();
        } else {
            setDistanceFromSubmarine(gridCoordinates);
            draw();
        }
    }

    private void setDistanceFromSubmarine(Point gridCoordinates) {
        int horizontalGap = (int)horizontalTouched -
                subHorizontalPosition;
        int verticalGap = (int)verticalTouched -
                subVerticalPosition;

        distanceFromSub = (int)Math.sqrt(
                ((horizontalGap * horizontalGap) +
                        (verticalGap * verticalGap)));
    }

    private boolean getSubmarineHit(Point touchedGridCoordinates) {
        return touchedGridCoordinates.x == subHorizontalPosition
            && touchedGridCoordinates.y == subVerticalPosition;
    }

    private Point getGridCoordinatesFromTouchCoordinates(float touchX, float touchY) {
        Point gridCoordinates = new Point();
        int gridX = (int)touchX/ blockSize;
        int gridY = (int)touchY/ blockSize;
        gridCoordinates.set(gridX, gridY);

        horizontalTouched = gridCoordinates.x;
        verticalTouched = gridCoordinates.y;

        return gridCoordinates;
    }

    private void boom() {
        gameView.setImageBitmap(blankBitmap);

        setScreenRed();
        drawBoomText();
        drawStartAgainText();
        newGame();
    }

    private void drawStartAgainText() {
        paint.setTextSize(blockSize * 2);
        canvas.drawText("Take a shot to start again",
                blockSize * 8,
                blockSize * 18, paint);
    }

    private void drawBoomText() {
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(blockSize * 10);

        canvas.drawText("BOOM!", blockSize * 4,
                blockSize * 14, paint);
    }

    private void setScreenRed() {
        canvas.drawColor(Color.argb(255, 255, 0, 0));
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