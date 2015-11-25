package moonbeam.com.psychometrycalculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import moonbeam.com.psychometrycalculator.PsychometryFormulas;
import moonbeam.com.psychometrycalculator.R;

public class ResultsDisplayActivity extends AppCompatActivity {

    TextView quanScoreView, verbalScoreView, engScoreView;
    TextView multiDomainView, sciencesView, humanitiesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_display);
        int verbalScore = (int) getIntent().getExtras().get("verbalScore");
        int quanScore = (int) getIntent().getExtras().get("quanScore");
        int engScore = (int) getIntent().getExtras().get("engScore");

        PsychometryFormulas.Range generalScore = (PsychometryFormulas.Range) getIntent().getExtras().get("generalScore");
        PsychometryFormulas.Range humanitiesScore = (PsychometryFormulas.Range) getIntent().getExtras().get("humanitiesScore");
        PsychometryFormulas.Range sciencesScore = (PsychometryFormulas.Range) getIntent().getExtras().get("sciencesScore");

        quanScoreView = (TextView) findViewById(R.id.quanText);
        quanScoreView.setText(Integer.toString(quanScore));

        verbalScoreView = (TextView) findViewById(R.id.verbalText);
        verbalScoreView.setText(Integer.toString(verbalScore));

        engScoreView = (TextView) findViewById(R.id.engText);
        engScoreView.setText(Integer.toString(engScore));

        multiDomainView = (TextView) findViewById(R.id.multiDomainText);
        multiDomainView.setText(generalScore.toString());

        sciencesView = (TextView) findViewById(R.id.sciencesText);
        sciencesView.setText(sciencesScore.toString());

        humanitiesView = (TextView) findViewById(R.id.humanitiesText);
        humanitiesView.setText(humanitiesScore.toString());



        Toast.makeText(ResultsDisplayActivity.this, getEncourageMessage(generalScore.getFrom()), Toast.LENGTH_LONG).show();


    }

    private int getEncourageMessage(int from) {
        switch (from - from%100){
            case 200:
                return R.string.encourage_200;
            case 300:
                return R.string.encourage_300;
            case 400:
                return R.string.encourage_400;
            case 500:
                return R.string.encourage_500;
            case 600:
                return R.string.encourage_600;
            case 700:
                return R.string.encourage_700;
            case 800:
                return R.string.encourage_800;
        }
        return R.string.encourage_500;
    }
}
