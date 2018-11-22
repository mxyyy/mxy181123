package com.bwie.mxy181123.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.mxy181123.R;



public class AddDecreaseWidget extends LinearLayout implements View.OnClickListener {
    private TextView txtDecrease;
    private TextView txtAdd;
    private TextView txtNum;
    private int num = 1;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        txtNum.setText(num + "");
    }

    public interface OnAddDecreaseClickListener {
        void onAddClick(int num);

        void onDecreaseClick(int num);
    }

    private OnAddDecreaseClickListener addDecreaseClickListener;

    public void setOnAddDecreaseClickListener(OnAddDecreaseClickListener onAddDecreaseClickListener) {
        this.addDecreaseClickListener = onAddDecreaseClickListener;
    }

    public AddDecreaseWidget(Context context) {
        this(context, null);
    }

    public AddDecreaseWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddDecreaseWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.widget_add_decrease, this);
        txtDecrease = findViewById(R.id.txt_decrease);
        txtAdd = findViewById(R.id.txt_add);
        txtNum = findViewById(R.id.txt_num);

        txtDecrease.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_decrease:
                if (num > 1) {
                    num--;
                }
                txtNum.setText(num + "");
                if (addDecreaseClickListener != null) {
                    addDecreaseClickListener.onDecreaseClick(num);
                }
                break;
            case R.id.txt_add:
                num++;
                txtNum.setText(num + "");
                if (addDecreaseClickListener != null) {
                    addDecreaseClickListener.onAddClick(num);
                }
                break;
        }
    }
}
