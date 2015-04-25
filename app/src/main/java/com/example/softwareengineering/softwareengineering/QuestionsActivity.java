package com.example.softwareengineering.softwareengineering;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import domain.Dilution;
import domain.ExternalStandards;
import domain.InternalStandards;
import domain.Solution;
import domain.SolutionSet;


public class QuestionsActivity extends Activity {
    int count = 0, current = 0, trys = 0;
    boolean file = false, correct = false;
    int id;
    SolutionSet soluType;
    String[] data;
    Solution sol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_questions);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/DK Cool Crayon.ttf");
        TextView text = (TextView) findViewById(R.id.text);
        text.setTypeface(myTypeface);
        EditText answer = (EditText) findViewById(R.id.answer);
        answer.setTypeface(myTypeface);
        answer.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        TextView myprevButton = (TextView) findViewById(R.id.prev);
        myprevButton.setTypeface(myTypeface);
        TextView mycontButton = (TextView) findViewById(R.id.cont);
        mycontButton.setTypeface(myTypeface);

        Bundle type = getIntent().getExtras();
        if(type != null) {
            this.id = type.getInt("id");
            this.file = type.getBoolean("file");
            if(this.file) {
                this.data = type.getStringArray("data");
                this.sol = new Solution(data);
            }
            createSolutionType();
            text.setText(soluType.getQuestion(count));
            setEdit(answer, "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            finish();
        }
    }

    public void onPrevious(View view) {
        TextView text = (TextView) findViewById(R.id.text);
        EditText answer = (EditText) findViewById(R.id.answer);
        if(count == 0) {
            finish();
        }
        else {
            soluType.setAnswerValue(count, answer.getText().toString());
            count--;
            setEdit(answer, soluType.getAnswerValue(count));
            text.setText(soluType.getQuestion(count));
        }
    }

    public void onContinue(View view) {
        TextView text = (TextView) findViewById(R.id.text);
        EditText answer = (EditText) findViewById(R.id.answer);
        soluType.setAnswerValue(count, answer.getText().toString());
        if(answer.getText().toString().trim().equals("")) {
            Toast.makeText(QuestionsActivity.this, "Please enter something in before continuing", Toast.LENGTH_SHORT).show();
        }
        else if(soluType.getANSWERS()[count].getCHECK()) {
            compute();
            check();
        }
        else {
            correct = true;
        }

        if(correct) {
            if(count == (soluType.getQUESTIONS().length - 1)) {
                save();
            }
            else {
                count++;
                setEdit(answer, soluType.getAnswerValue(count));
                text.setText(soluType.getQuestion(count));
            }
            trys = 0;
            correct = false;
        }
    }

    public void setEdit(EditText answer, String input) {
        answer.setText(input);
        answer.setSelection(answer.getText().length());
        if(soluType.getANSWERS()[count].getTYPE().equals("String"))
            answer.setInputType(InputType.TYPE_CLASS_TEXT);
        else
            answer.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void check() {
        if(trys < 3) {
            if(soluType.getCompare(count) != soluType.getAnsw()) {
                Toast.makeText(QuestionsActivity.this, "Incorect please try again", Toast.LENGTH_SHORT).show();
                trys++;
            }
            else {
                Toast.makeText(QuestionsActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                correct = true;
            }
        }
        else {
            if(soluType.getCompare(count) != soluType.getAnsw()) {
                Toast.makeText(QuestionsActivity.this, "Incorrect the correct answer is: " + soluType.getCompare(count), Toast.LENGTH_SHORT).show();
                soluType.setAnswerValue(count, String.valueOf(soluType.getCompare(count)));
            }
            else {
                Toast.makeText(QuestionsActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            }
            correct = true;
        }
    }

    public void compute() {
        soluType.setValues(soluType.getANSWERS(), count);
        soluType.compute(count);
    }

    public void save() {
        compute();
        Intent nextScreen = new Intent(QuestionsActivity.this, SaveActivity.class);
        nextScreen.putExtra("solutionDetails", soluType.getDETAILS());
        nextScreen.putExtra("solutionData", soluType.getDATA());
        startActivityForResult(nextScreen, 1);
    }

    public void createSolutionType() {
        if (file) {
            switch (id) {
                case 1:
                    soluType = new Dilution(sol, false);
                    count = 6;
                    break;
                case 2:
                    soluType = new Dilution(sol, true);
                    count = 6;
                    break;
                case 3:
                    soluType = new ExternalStandards(sol);
                    count = 6;
                    break;
                case 4:
                    soluType = new InternalStandards(new ExternalStandards(sol));
                    count = 6;
                    break;
            }
        } else {
            switch (id) {
                case 0:
                    soluType = new Solution();
                    break;
                case 1:
                    soluType = new Dilution(new Solution(), false);
                    break;
                case 2:
                    soluType = new Dilution(new Solution(), true);
                    break;
                case 3:
                    soluType = new ExternalStandards(new Solution());
                    break;
                case 4:
                    soluType = new InternalStandards(new ExternalStandards(new Solution()));
                    break;
            }
        }
    }
}
