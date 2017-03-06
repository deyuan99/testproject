package ict2105.team10project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends Activity {

    // Variables List
    Button playBtn, dailyBtn, guildBtn, leaderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        //findviewbyid for the UI elements
        playBtn = (Button) findViewById(R.id.play_btn);
        dailyBtn = (Button) findViewById(R.id.daily_btn);
        guildBtn = (Button) findViewById(R.id.guild_btn);
        leaderBtn = (Button) findViewById(R.id.leader_btn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, PlayView.class);
                startActivity(intent);
            }
        });

        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, DailyChallengesView.class);
                startActivity(intent);
            }
        });

        guildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, GuildView.class);
                startActivity(intent);
            }
        });

        leaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, LeaderboardView.class);
                startActivity(intent);
            }
        });
    }
}
