package com.sonnysappbakery.honesttipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

//enum to keep track of which question app is asking user

enum Step {
    BILL, FRIENDLINESS, SPEED, QUALITY, GENEROSITY, AFFORDABILITY, TOTAL
}

public class MainActivity extends AppCompatActivity {

    Step step;
    Button button;
    TextView question;
    EditText answer;

    double bill;
    int friendliness;
    int speed;
    int quality;
    int generosity;
    int affordability;
    double tip;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Step needs to start at Step.FRIENDLINESS, or else first button click will not do anything!
        step = Step.FRIENDLINESS;
        button = findViewById(R.id.button);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
    }

    public void onClick(View view) {
        //Clicking button depends which question app is currently asking
        switch(step) {
            case BILL:
                button.setText(R.string.enter);
                question.setText(R.string.ask_bill);
                answer.setEnabled(true);
                answer.setText(null);
                answer.setHint(R.string.bill_hint);
                step = Step.FRIENDLINESS;
                break;
            case FRIENDLINESS:
                question.setText(R.string.ask_friendliness);
                answer.setText(null);
                answer.setHint(R.string.number_hint);
                step = Step.SPEED;
                break;
            case SPEED:
                question.setText(R.string.ask_speed);
                answer.setText(null);
                step = Step.QUALITY;
                break;
            case QUALITY:
                question.setText(R.string.ask_quality);
                answer.setText(null);
                step = Step.GENEROSITY;
                break;
            case GENEROSITY:
                question.setText(R.string.ask_generosity);
                answer.setText(null);
                step = Step.AFFORDABILITY;
                break;
            case AFFORDABILITY:
                question.setText(R.string.ask_affordability);
                answer.setText(null);
                step = Step.TOTAL;
                break;
            case TOTAL:
                button.setText(R.string.start_again);
                String totalDisplay = getString(R.string.recommend_tip) + " " + String.format(Locale.getDefault(), "%, .2f", tip) + "\n" + getString(R.string.recommend_total) + " " + String.format(Locale.getDefault(), "%, .2f", total);
                question.setText(totalDisplay);
                answer.setHint(null);
                answer.setEnabled(false);
                step = Step.BILL;
                break;
            default:
                break;
        }
    }
}