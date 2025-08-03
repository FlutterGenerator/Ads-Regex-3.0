package adsreg.gaze;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.mursaat.extendedtextview.AnimatedGradientTextView;
import android.graphics.Typeface;

public class MainActivity extends AppCompatActivity {

    private LinearLayout[] bases = new LinearLayout[9]; // base1-base9
    private AnimatedGradientTextView[] regexTextViews = new AnimatedGradientTextView[45]; // t1-t45
    private ImageView imageMenu;
    private TextView textAds, textRegex;
    private Typeface customFont; // Кастомный шрифт

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // основной layout

        // Загружаем кастомный шрифт из res/font через ResourcesCompat (НЕ устаревший способ)
        try {
            customFont = ResourcesCompat.getFont(this, R.font.en_light); // Укажите имя шрифта в res/font
        } catch (Exception e) {
            e.printStackTrace();
            customFont = Typeface.DEFAULT;
        }

        initViews();
        setupClickListeners();
        applyCustomFonts(); // Применяем кастомные шрифты
    }

    private void initViews() {
        for (int i = 0; i < bases.length; i++) {
            int resId = getResources().getIdentifier("base" + (i + 1), "id", getPackageName());
            bases[i] = findViewById(resId);
        }

        for (int i = 0; i < regexTextViews.length; i++) {
            int resId = getResources().getIdentifier("t" + (i + 1), "id", getPackageName());
            regexTextViews[i] = findViewById(resId);
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
            textView.setOnLongClickListener(copyRegexListener);
        }

        imageMenu.setOnClickListener(v -> {
            // Логика при нажатии на меню
            // showCustomToast("Меню нажато");
        });
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Regex", text);
        clipboard.setPrimaryClip(clip);
    }

    @SuppressLint("SetTextI18n")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}