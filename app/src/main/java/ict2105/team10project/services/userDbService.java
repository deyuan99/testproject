package ict2105.team10project.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ict2105.team10project.database.User;

public class userDbService extends IntentService {

    private static User user;
    private DatabaseReference mDatabase;

    /**
     * Constructor
     */
    public userDbService() {
        super("userDbService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Get receiver from incoming intent
        ResultReceiver rec = intent.getParcelableExtra("receiver");
        user = new User();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Get data from incoming intent, sorting the intent purpose
        switch (intent.getStringExtra("purpose")) {
            case "newUser":
                newUser(intent, rec, user, mDatabase);
                break;

            case "updateDb":
                updateDb(intent, rec, user, mDatabase);
                break;

            case "getData":
                getData(intent, rec, user, mDatabase);
                break;

            default:
                break;
        }
    }

    /**
     * If purpose is creating new user, add new user information into
     * firebase database
     * @param intent incoming intent
     * @param rec incoming receiver
     * @param user user object
     * @param mDatabase firebase database
     */
    private void newUser(Intent intent, ResultReceiver rec, User user, DatabaseReference mDatabase) {

    }

    /**
     * If purpose is updating user's information in firebase database
     * @param intent incoming intent
     * @param rec incoming receiver
     * @param user user object
     * @param mDatabase firebase database
     */
    private void updateDb(Intent intent, ResultReceiver rec, User user, DatabaseReference mDatabase) {

    }

    /**
     * If purpose is getting user's information from firebase database
     * @param intent incoming intent
     * @param rec incoming receiver
     * @param user user object
     * @param mDatabase firebase database
     */
    private void getData(Intent intent, ResultReceiver rec, User user, DatabaseReference mDatabase) {


    }
}
