package app.com.patricksdeliveryboy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import app.com.patricksdeliveryboy.R;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontThickness = values.getString(R.styleable.CustomTextView_customFontThickness);
        if (fontThickness == null) fontThickness = "normal";
        values.recycle();
        switch(fontThickness){
            case "bold":
                setBold(context);
                break;

            case "medium":
                setMedium(context);
                break;

            case "thin":
                setThin(context);
                break;

            case "light":
                setLight(context);
                break;

            case "regular":
                setRegular(context);
                break;

            default:
                applyCustomFont(context);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontThickness = values.getString(R.styleable.CustomTextView_customFontThickness);
        if (fontThickness == null) fontThickness = "normal";
        values.recycle();
        switch(fontThickness){
            case "bold":
                setBold(context);
                break;

            case "medium":
                setMedium(context);
                break;

            case "thin":
                setThin(context);
                break;

            case "light":
                setLight(context);
                break;

            case "regular":
                setRegular(context);
                break;

            default:
                applyCustomFont(context);
        }
    }

    private void applyCustomFont(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-regular.ttf");
        this.setTypeface(face);
    }

    public void setBold(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/roboto-bold.ttf");
        this.setTypeface(face);
    }

    public void setMedium(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-medium.ttf");
        this.setTypeface(face);
    }

    public void setThin(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/roboto-thin.ttf");
        this.setTypeface(face);
    }

    public void setLight(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-light.ttf");
        this.setTypeface(face);
    }

    private void setRegular(Context context) {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-regular.ttf");
        this.setTypeface(face);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
