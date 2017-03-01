package ict2105.team10project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import ict2105.team10project.pushup.Pushup;

public class challenge extends Activity {
    private Button stepb,pushb,sprintb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        pushb = (Button)findViewById(R.id.pushbtn);

        pushb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(challenge.this, Pushup.class));
            }
        });

    }

}
