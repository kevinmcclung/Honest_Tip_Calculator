package com.sonnysappbakery.honesttipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    double rate;
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
        switch (step) {
            case BILL:
                button.setText(R.string.enter);
                question.setText(R.string.ask_bill);
                answer.setEnabled(true);
                answer.setText(null);
                answer.setHint(R.string.bill_hint);
                step = Step.FRIENDLINESS;
                break;
            case FRIENDLINESS:
                try {
                    bill = Double.parseDouble(answer.getText().toString());
                    System.out.println("Bill: " + bill);
                    question.setText(R.string.ask_friendliness);
                    answer.setText(null);
                    answer.setHint(R.string.number_hint);
                    step = Step.SPEED;
                } catch (Exception e) {
                    displayError();
                }
                //NOTE: At first I put the break at the end of the try block, causing a nasty bug!!!
                break;
            case SPEED:
                try {
                    friendliness = getInt();
                    System.out.println("Friendliness: " + friendliness);
                    question.setText(R.string.ask_speed);
                    answer.setText(null);
                    step = Step.QUALITY;
                } catch (Exception e) {
                    displayError();
                }
                break;
            case QUALITY:
                try {
                    speed = getInt();
                    System.out.println("Speed: " + speed);
                    question.setText(R.string.ask_quality);
                    answer.setText(null);
                    step = Step.GENEROSITY;
                } catch (Exception e) {
                    displayError();
                }
                break;
            case GENEROSITY:
                try {
                    quality = getInt();
                    System.out.println("Quality: " + quality);
                    question.setText(R.string.ask_generosity);
                    answer.setText(null);
                    step = Step.AFFORDABILITY;
                } catch (Exception e) {
                    displayError();
                }
                break;
            case AFFORDABILITY:
                try {
                    generosity = getInt();
                    System.out.println("Generosity: " + generosity);
                    question.setText(R.string.ask_affordability);
                    answer.setText(null);
                    step = Step.TOTAL;
                } catch (Exception e) {
                    displayError();
                }
                break;
            case TOTAL:
                try {
                    affordability = getInt();
                    System.out.println("Affordability: " + affordability);
                    button.setText(R.string.start_again);
                    calculateTip();
                    String totalDisplay = getString(R.string.display_bill)
                            + " " + String.format(Locale.getDefault(), "%, .2f", bill)
                            + "\n \n" + getString(R.string.recommend_tip)
                            + " " + String.format(Locale.getDefault(), "%, .2f", tip)
                            + " (" + String.format(Locale.getDefault(), "%, .2f", rate * 100) + "%)"
                            + "\n \n" + getString(R.string.recommend_total)
                            + " "
                            + String.format(Locale.getDefault(), "%, .2f", total);
                    question.setText(totalDisplay);
                    answer.setHint(null);
                    answer.setEnabled(false);
                    step = Step.BILL;
                } catch (Exception e) {
                    displayError();
                }
                break;
            default:
                break;
        }
    }

    public void displayError() {
        Toast.makeText(this, getString(R.string.invalid), Toast.LENGTH_SHORT).show();
    }

    public int getInt() {
        return Integer.parseInt(answer.getText().toString());
    }

    public void calculateTip() {
        //My very special tip calculation algorithm!
        //Maximum tip of 25%
        //Tip = 65% friendliness, 10% quality, 5% speed, 15% generosity, 5% affordability
        //1% tip for meanest waiter
        //Guaranteed tip of 15% for friendliness of 3 or above
        //Guaranteed tip of 20% for nicest waiter
        rate = .05 * (.65 * friendliness + .1 * quality + .05 * speed + .15 * generosity + .05 * affordability);
        if (rate > .01 && friendliness <= 1) rate = .01;
        if (friendliness >= 5) {
            if (rate <= .20) rate = .2;
        } else if (friendliness >= 3) {
            if (rate <= .15) rate = .15;
        }
        tip = bill * rate;
        total = bill + tip;
    }
}