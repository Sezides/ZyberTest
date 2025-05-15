package com.example.zybertest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

public class BottomButtonBar extends LinearLayout {

    private ImageButton button1, button2, button3, button4;
    private OnButtonClickListener listener;

    // Интерфейс для обработки кликов
    public interface OnButtonClickListener {
        void onButton1Click();
        void onButton2Click();
        void onButton3Click();
        void onButton4Click();
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    // Конструкторы
    public BottomButtonBar(Context context) {
        super(context);
        init(context);
    }

    public BottomButtonBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_button_bar, this, true);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(v -> {
            if (listener != null) listener.onButton1Click();
        });

        button2.setOnClickListener(v -> {
            if (listener != null) listener.onButton2Click();
        });

        button3.setOnClickListener(v -> {
            if (listener != null) listener.onButton3Click();
        });

        button4.setOnClickListener(v -> {
            if (listener != null) listener.onButton4Click();
        });
    }
}
