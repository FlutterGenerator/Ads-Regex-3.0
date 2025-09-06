package adsreg.gaze;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.mursaat.extendedtextview.AnimatedGradientTextView;

public class MainActivity extends AppCompatActivity {

    private final AnimatedGradientTextView[] regexTextViews = new AnimatedGradientTextView[45]; // t1-t45
    private ImageView imageMenu;
    private TextView textAds, textRegex;
    private Typeface customFont; // Кастомный шрифт

    // массивы ID для regexTextViews
    private final int[] regexTextViewIds = {
            R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5,
            R.id.t6, R.id.t7, R.id.t8, R.id.t9, R.id.t10,
            R.id.t11, R.id.t12, R.id.t13, R.id.t14, R.id.t15,
            R.id.t16, R.id.t17, R.id.t18, R.id.t19, R.id.t20,
            R.id.t21, R.id.t22, R.id.t23, R.id.t24, R.id.t25,
            R.id.t26, R.id.t27, R.id.t28, R.id.t29, R.id.t30,
            R.id.t31, R.id.t32, R.id.t33, R.id.t34, R.id.t35,
            R.id.t36, R.id.t37, R.id.t38, R.id.t39, R.id.t40,
            R.id.t41, R.id.t42, R.id.t43, R.id.t44, R.id.t45
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // основной layout

        // Загружаем кастомный шрифт из res/font через ResourcesCompat (НЕ устаревший способ)
        try {
            customFont = ResourcesCompat.getFont(this, R.font.en_light);
        } catch (Exception ignored) {
            customFont = Typeface.DEFAULT;
        }

        initViews();
        setupClickListeners();
        applyCustomFonts(); // Применяем кастомные шрифты
    }

    private void initViews() {
        for (int i = 0; i < regexTextViewIds.length; i++) {
            regexTextViews[i] = findViewById(regexTextViewIds[i]);
        }

        imageMenu = findViewById(R.id.imageview1);
        textAds = findViewById(R.id.textview3);
        textRegex = findViewById(R.id.textview4);
    }

    private void applyCustomFonts() {
        for (AnimatedGradientTextView textView : regexTextViews) {
            if (textView != null) {
                textView.setTypeface(customFont);
            }
        }

        if (textAds != null) {
            textAds.setTypeface(customFont);
        }

        if (textRegex != null) {
            textRegex.setTypeface(customFont);
        }
    }

    private void setupClickListeners() {
        View.OnLongClickListener copyRegexListener = v -> {
            if (v instanceof AnimatedGradientTextView) {
                String textToCopy = ((AnimatedGradientTextView) v).getText().toString();
                copyToClipboard(textToCopy);
                showCustomToast();
                return true;
            }
            return false;
        };

        for (AnimatedGradientTextView textView : regexTextViews) {
            if (textView != null) {
                textView.setOnLongClickListener(copyRegexListener);
            }
        }

        if (imageMenu != null) {
            imageMenu.setOnClickListener(v -> {
                // Логика при нажатии на меню
                // showCustomToast("Меню нажато");
            });
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Regex", text);
        clipboard.setPrimaryClip(clip);
    }

    @SuppressLint("InflateParams")
    private void showCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView title = layout.findViewById(R.id.textview1);
        title.setText(R.string.title);
        title.setTypeface(customFont);

        TextView text = layout.findViewById(R.id.textview2);
        text.setText(R.string.textview);
        text.setTypeface(customFont);

        ImageView icon = layout.findViewById(R.id.imageview1);
        icon.setImageResource(R.drawable.success);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}