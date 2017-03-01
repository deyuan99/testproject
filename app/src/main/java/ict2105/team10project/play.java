package ict2105.team10project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import ict2105.team10project.pushup.Pushup;

public class play extends Activity {
    private Button findmatch,challengefri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        findmatch= (Button)findViewById(R.id.findmatchbtn);

        findmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
