package ict2105.team10project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ict2105.team10project.dailychallenges.MapsActivity;
import ict2105.team10project.dailychallenges.PushupsView;
import ict2105.team10project.dailychallenges.StepsView;

public class DailyChallengesView extends Activity {
    // Variables List
    private Button stepBtn, pushBtn, sprintBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_challenges_view);

        //findviewbyid for the UI elements
        stepBtn = (Button) findViewById(R.id.step_btn);
        pushBtn = (Button) findViewById(R.id.push_btn);
        sprintBtn = (Button) findViewById(R.id.sprint_btn);

        stepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyChallengesView.this, StepsView.class);
                startActivity(intent);
            }
        });

        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyChallengesView.this, PushupsView.class);
                startActivity(intent);
            }
        });

        sprintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyChallengesView.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
