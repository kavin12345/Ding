package com.goky.ding;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;


public class CircularAnimation extends SurfaceView implements Runnable {

    boolean lose = false;
    boolean corner_is_not_set = true;

    Bitmap glass1, glass2, glass3, glass4;

    Thread thread = null;
    boolean CanDraw = false;

    Paint paintbrush_fill, paintbrush_stroke;
    Path square;

    int bell_x, bell_y;
    int circle_x, circle_y;
    int width, x, y;

    Canvas canvas;
    SurfaceHolder surfaceHolder;

    boolean corner_1 = false, corner_2 = false, corner_3 = false, corner_4 = false;
    int curr_corner;

    int curr_speed = 40;
    int curr_score = 0;

    public CircularAnimation(Context context) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x/2;
        x = width-300;
        y = width+300;
        circle_x = x;
        circle_y = y;
        bell_x = y-485;
        bell_y = y+200;

        glass1 = BitmapFactory.decodeResource(getResources(), R.drawable.glass_1);
        glass2 = BitmapFactory.decodeResource(getResources(), R.drawable.glass_2);
        glass3 = BitmapFactory.decodeResource(getResources(), R.drawable.glass_3);
        glass4 = BitmapFactory.decodeResource(getResources(), R.drawable.glass_4);

        surfaceHolder = getHolder();

    }

    @Override
    public void run() {

        prepPaintBrushes();

        while(CanDraw) {
            //Carry out some drawing...
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            canvas = surfaceHolder.lockCanvas();
            motionCircle(curr_speed);
            canvas.drawARGB(255, 255, 255, 255);
            drawSqaure(x, x, y, y);
            canvas.drawCircle(circle_x, circle_y, 80, paintbrush_fill);
            drawGlass();
            drawScore();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        CanDraw = false;

        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread=null;
    }

    public void resume() {
        CanDraw = true;
        thread = new Thread(this);
        thread.start();
    }

    private void prepPaintBrushes() {

    paintbrush_fill = new Paint();
    paintbrush_fill.setColor(getResources().getColor(R.color.standard_color));
    paintbrush_fill.setStyle(Paint.Style.FILL);

    paintbrush_stroke = new Paint();
    paintbrush_stroke.setColor(getResources().getColor(R.color.standard_color));
    paintbrush_stroke.setStyle(Paint.Style.STROKE);
    paintbrush_stroke.setStrokeWidth(40);

    }

    private void drawSqaure(int x1, int y1, int x2, int y2) {
        square = new Path();
        square.moveTo(x1, y1); // 100, 100
        square.lineTo(x2, y1); // 400, 100
        square.moveTo(x2, y1); // 400, 100
        square.lineTo(x2, y2); // 400, 400
        square.moveTo(x2, y2); // 400, 400
        square.lineTo(x1, y2); // 100, 400
        square.moveTo(x1, y2); // 100, 400
        square.lineTo(x1, y1); // 100, 100

        this.canvas.drawPath(square, paintbrush_stroke);
    }

    private void motionCircle(int speed) {

        if( (circle_y == x) && (circle_x < y) ) {
            circle_x = circle_x + speed;
        }

        if( (circle_y < y) && (circle_x == y) ) {
            circle_y = circle_y + speed;
        }

        if( (circle_y == y) && (circle_x > x) ) {
            circle_x = circle_x - speed;
        }

        if( (circle_y > x) && (circle_x == x) ) {
            circle_y = circle_y - speed;
        }
    }

    private void check_match() {
        int x1, x2;
        int y1, y2;
        if (curr_corner == 1) {
            x1 = x - 100;
            x2 = x + 100;
            y1 = x - 100;
            y2 = x + 100;
        } else if (curr_corner == 2) {
            x1 = y - 100;
            x2 = y + 100;
            y1 = x - 100;
            y2 = x + 100;
        } else if (curr_corner == 3) {
            x1 = y - 100;
            x2 = y + 100;
            y1 = y - 100;
            y2 = y + 100;
        } else {
            x1 = x - 100;
            x2 = x + 100;
            y1 = y - 100;
            y2 = y + 100;
        }

        if (!((x1 <= circle_x && circle_x < x2) && (y1 <= circle_y && circle_y < y2))) {
            lose = true;
            checkHighScore();
        } else {
            curr_score = curr_score + 1;
            lose = false;
            corner_is_not_set = true;
        }
    }

    private void drawGlass() {
        if (corner_is_not_set) {
            Random random = new Random();
            int number = random.nextInt(5 - 1) + 1;

            setCorner(number);

            if (number == 1) {
                canvas.drawBitmap(glass1, x - 200, x - 200, null);
            } else if (number == 2) {
                canvas.drawBitmap(glass2, y, x - 200, null);
            } else if (number == 3) {
                canvas.drawBitmap(glass3, y, y, null);
            } else if (number == 4) {
                canvas.drawBitmap(glass4, x - 200, y, null);
            }
            corner_is_not_set = false;
        } else {
            if (curr_corner == 1) {
                canvas.drawBitmap(glass1, x - 200, x - 200, null);
            } else if (curr_corner == 2) {
                canvas.drawBitmap(glass2, y, x - 200, null);
            } else if (curr_corner == 3) {
                canvas.drawBitmap(glass3, y, y, null);
            } else if (curr_corner == 4) {
                canvas.drawBitmap(glass4, x - 200, y, null);
            }
        }
    }

    private void setCorner(int number) {
        curr_corner = number;
        if (number == 1) {
            corner_1 = true;
            corner_2 = false;
            corner_3 = false;
            corner_4 = false;
        } else if (number == 2) {
            corner_1 = false;
            corner_2 = true;
            corner_3 = false;
            corner_4 = false;
        } else if (number == 3) {
            corner_1 = false;
            corner_2 = false;
            corner_3 = true;
            corner_4 = false;
        } else  if (number == 4) {
            corner_1 = false;
            corner_2 = false;
            corner_3 = false;
            corner_4 = true;
        }
    }

    public boolean matchCorrect() {
        check_match();
        if (lose) {
            return false;
        }
        return true;
    }

    private void drawScore() {
        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/feenacasual.ttf");
        paintText.setColor(getContext().getResources().getColor(R.color.standard_color));
        paintText.setTextSize(150);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTypeface(tf);
        canvas.drawText("" + curr_score, x + 260, y - 250, paintText);
    }

    private void checkHighScore() {
        SharedPreferences sp = getContext().getSharedPreferences("ding", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int highScore = sp.getInt("high_score", 0);

        if (curr_score > highScore) {
            editor.putInt("high_score", curr_score);
            editor.commit();
        }

        editor.putInt("curr_score", curr_score);
        editor.commit();
    }

}