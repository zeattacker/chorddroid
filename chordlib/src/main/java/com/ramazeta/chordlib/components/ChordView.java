package com.ramazeta.chordlib.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ramazeta.chordlib.R;

/**
 * Created by rama on 13/01/18.
 */

public class ChordView extends View {
    private Shape chordshape;
    private Paint fretPaint;
    private Paint dotPaint;
    private Paint stringPaint;
    private Paint nutPaint;
    private Paint textPaint;
    private Paint textFretPaint;
    private int stringColor,fretColor,dotColor,circleTextColor;



    public ChordView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public ChordView(Context context) {
        this(context, null);
    }

    public ChordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chordshape = null;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChordDroid,
                defStyleAttr, 0);
        stringColor = a.getColor(R.styleable.ChordDroid_fretTextColor, Color.BLACK);
        fretColor = a.getColor(R.styleable.ChordDroid_fretTextColor, Color.BLACK);
        dotColor = a.getColor(R.styleable.ChordDroid_cirlceColor, Color.BLACK);
        circleTextColor = a.getColor(R.styleable.ChordDroid_circleTextColor, Color.WHITE);

        stringPaint = new Paint();
        stringPaint.setColor(fretColor);
        stringPaint.setAntiAlias(true);

        fretPaint = new Paint();
        fretPaint.setColor(fretColor);
        fretPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(circleTextColor);
        textPaint.setAntiAlias(true);

        nutPaint = new Paint();
        nutPaint.setColor(fretColor);
        nutPaint.setAntiAlias(true);

        textFretPaint = new Paint();
        textFretPaint.setColor(stringColor);
        textFretPaint.setAntiAlias(true);

        dotPaint = new Paint();
        dotPaint.setColor(dotColor);
        dotPaint.setAntiAlias(true);
    }

    public void setStringColor(int stringColor) {
        this.stringColor = stringColor;
    }

    public void setFretColor(int fretColor) {
        this.fretColor = fretColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }

    public void setCircleTextColor(int circleTextColor) {
        this.circleTextColor = circleTextColor;
    }

    public void setShape(Shape chordshape) {
        this.chordshape = chordshape;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        // we use a square playing field
        int size = Math.min(width, height);

        float penWidth = size / 100;
        stringPaint.setStrokeWidth(penWidth);
        nutPaint.setStrokeWidth(penWidth * 2);
        fretPaint.setStrokeWidth(penWidth / 2);
        fretPaint.setTextSize(12 * penWidth);
        textFretPaint.setTextSize(5 * penWidth);

        if (chordshape != null) {
            // draw the chord shape
            drawLines(canvas);
        }
    }

    private void drawLines(Canvas c) {
        int[] pos = chordshape.getPositions();
        int n = pos.length;

        // the last fret used by this shape: A shape consisting only of muted
        // and open strings will display at least one fret
        int last_fret = Math.max(1, chordshape.getMaxPos());
        // the first fret used by this shape, excluding open strings
        int first_fret = Math.max(1, chordshape.getMinPos());

        // we will display at least 4 frets, so we expand the range
        while (1 + (last_fret - first_fret) < 4) {
            if (first_fret > 1) {
                first_fret--;
            } else {
                last_fret++;
            }
        }

        int frets = 1 + (last_fret - first_fret);

        float d_w = (getHeight() / (frets));
        float d_h = (getWidth() / (n + 1));

        for (int i = n; i > 0; i--) {
            c.drawLine(d_h * i, 0, d_h * i, getHeight(), stringPaint);
        }
        for (int i = frets; i > 0; i--) {
            c.drawText(i + " Fr",d_h * (pos.length + 0.25f), d_w * ((float)(i - first_fret + 1) - 0.5f), textFretPaint);
            c.drawLine(d_h * 0.75f, d_w * i, d_h * (pos.length + 0.25f), d_w * i, stringPaint);
        }
        if (first_fret == 1) {
            c.drawLine(d_h * 0.75f, d_w * 0 + nutPaint.getStrokeWidth() / 2, d_h * (pos.length + 0.25f), d_w * 0 + nutPaint.getStrokeWidth() / 2, nutPaint);
        }

        int countFret = 1;
        for(int string = pos.length; string > 0; string--){
            int fret = pos[string - 1];
            // fret position relative to the fretboard position
            int rel_fret = fret - first_fret;
            float r = d_w / 4f;
            if (fret > 0) {
                float c_y = d_w * ((float) rel_fret + 0.5f);
                float c_x = d_h * (0f + (float) string);
                c.drawCircle(c_x, c_y, r, fretPaint);
                textPaint.setTextSize(r);
                c.drawText(String.valueOf(countFret++),c_x - (r / 3f),c_y + (r / 3f), textPaint);
            } else if (fret < 0) {
                // paint markers on muted strings
                float c_y = d_w * 0.5f;
                float c_x = d_h * (0f + (float) string);
                c.drawLine(c_x - r, c_y - r, c_x + r, c_y + r, stringPaint);
                c.drawLine(c_x - r, c_y + r, c_x + r, c_y - r, stringPaint);
            }
        }

    }
}
