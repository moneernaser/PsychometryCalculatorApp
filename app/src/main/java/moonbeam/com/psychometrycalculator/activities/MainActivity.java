package moonbeam.com.psychometrycalculator.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.io.Serializable;

import moonbeam.com.psychometrycalculator.PsychomeryExam;
import moonbeam.com.psychometrycalculator.PsychometryFormulas;
import moonbeam.com.psychometrycalculator.R;

public class MainActivity extends AppCompatActivity {

    NumberPicker quantitativeSectionsPicker = null;
    NumberPicker quantitativeAnswersPicker = null;
    NumberPicker verbalSectionsPicker = null;
    NumberPicker verbalAnswersPicker = null;
    NumberPicker engSectionsPicker = null;
    NumberPicker engAnswersPicker = null;

    Button calculateBtn = null;

    PsychomeryExam psychomeryExam = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantitativeAnswersPicker = (NumberPicker) findViewById(R.id.quantitativeAns);
        quantitativeSectionsPicker = (NumberPicker) findViewById(R.id.quantitativeSec);

        quantitativeSectionsPicker.setMinValue(2);
        quantitativeSectionsPicker.setMaxValue(3);
        quantitativeSectionsPicker.setValue(2);
        quantitativeAnswersPicker.setMinValue(0);
        quantitativeAnswersPicker.setMaxValue(40);
        quantitativeAnswersPicker.setValue(0);


        verbalAnswersPicker = (NumberPicker) findViewById(R.id.verbalAns);
        verbalSectionsPicker = (NumberPicker) findViewById(R.id.verbalSec);

        verbalSectionsPicker.setMinValue(2);
        verbalSectionsPicker.setMaxValue(3);
        verbalSectionsPicker.setValue(2);
        verbalAnswersPicker.setMinValue(0);
        verbalAnswersPicker.setMaxValue(40);
        verbalAnswersPicker.setValue(0);


        engAnswersPicker = (NumberPicker) findViewById(R.id.engAns);
        engSectionsPicker = (NumberPicker) findViewById(R.id.engSec);

        engSectionsPicker.setMinValue(2);
        engSectionsPicker.setMaxValue(3);
        engSectionsPicker.setMaxValue(2);
        engAnswersPicker.setMinValue(0);
        engAnswersPicker.setMaxValue(44);
        engAnswersPicker.setValue(0);

        setCalculateBtnListener();
        setSectionsListeners();
    }

    private void setSectionsListeners() {
        quantitativeSectionsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    quantitativeAnswersPicker.setMaxValue(40);
                }
                if (newVal == 3) {
                    quantitativeAnswersPicker.setMaxValue(60);
                }
            }
        });

        verbalSectionsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    verbalAnswersPicker.setMaxValue(40);
                }
                if (newVal == 3) {
                    verbalAnswersPicker.setMaxValue(60);
                }
            }
        });

        engSectionsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    engAnswersPicker.setMaxValue(44);
                }
                if (newVal == 3) {
                    engAnswersPicker.setMaxValue(66);
                }
            }
        });
    }

    private void setCalculateBtnListener() {
        calculateBtn = (Button) findViewById(R.id.calculateButton);
        calculateBtn.setOnClickListener(new View.OnClickListener() {

            int quanSections, quanAnswers, verbalSections, verbalAnswers, engSections, engAnswers;
            int verbalScore, quanScore, engScore;
            PsychometryFormulas.Range generalScore, humanitiesScore, sciencesScore;

            @Override
            public void onClick(View v) {


                quanSections = quantitativeSectionsPicker.getValue();
                quanAnswers = quantitativeAnswersPicker.getValue();

                verbalSections = verbalSectionsPicker.getValue();
                verbalAnswers = verbalAnswersPicker.getValue();

                engSections = engSectionsPicker.getValue();
                engAnswers = engAnswersPicker.getValue();


                try {
                    validate();
                    setInput();
                    verbalScore = psychomeryExam.getVerbalScore();
                    quanScore = psychomeryExam.getQuanScore();
                    engScore = psychomeryExam.getEngScore();
                    generalScore = psychomeryExam.calculateGeneralScore();
                    humanitiesScore = psychomeryExam.calculateHumanitiesScore();
                    sciencesScore = psychomeryExam.calculateSciencesScore();

                    Intent intent = new Intent(getBaseContext(), ResultsDisplayActivity.class);
                    intent.putExtra("quanScore", quanScore);
                    intent.putExtra("verbalScore", verbalScore);
                    intent.putExtra("engScore", engScore);
                    intent.putExtra("generalScore", (Serializable) generalScore);
                    intent.putExtra("humanitiesScore", (Serializable) humanitiesScore);
                    intent.putExtra("sciencesScore", (Serializable) sciencesScore);
                    startActivity(intent);


                } catch (AssertionError ex) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.errorTitle))
                            .setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("ok", null).create().show();
                }

            }

            private void setInput() {
                psychomeryExam = new PsychomeryExam();
                psychomeryExam.setEngAns(engAnswers).setEngSections(engSections)
                        .setQuanAns(quanAnswers).setQuanSections(quanSections)
                        .setVerbalAns(verbalAnswers).setVerbalSections(verbalSections);
            }

            private void validate() {

                int numOfUnits = quanSections + verbalSections + engSections;
                if (numOfUnits == 6 || numOfUnits == 8) {
                    return;
                } else {
                    throw new AssertionError(getString(R.string.invalidNumOfUnits));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
