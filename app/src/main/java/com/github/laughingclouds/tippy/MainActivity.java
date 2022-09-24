package com.github.laughingclouds.tippy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int INITIAL_TIP_PERCENT = 15;
    private EditText etBaseAmount;
    private SeekBar seekBarTip;
    private TextView tvTipPercentLabel;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;

    /**
     *
     */
    protected void recalculateTip() {
        // 1. get value of base amount and tip percentage
        Double baseAmount;
        try {
            baseAmount = Double.valueOf(etBaseAmount.getText().toString());
        } catch (Exception e) {
            baseAmount = (double) 0;
        }
        int tipPercent = seekBarTip.getProgress();
        // 2. compute the tip and total
        Double tipAmount = baseAmount * tipPercent / 100;
        Double totalAmount = baseAmount - tipAmount;
        // 3. update the ui
        tvTipAmount.setText(tipAmount.toString());
        tvTotalAmount.setText(totalAmount.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseAmount = findViewById(R.id.etBaseAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        // initialize values
        seekBarTip.setProgress(INITIAL_TIP_PERCENT);
        tvTipPercentLabel.setText(String.format("%d%%", INITIAL_TIP_PERCENT));

        // add event listeners
        etBaseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                recalculateTip();
            }
        });

        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvTipPercentLabel.setText(String.format("%d%%", progress));
                recalculateTip();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}