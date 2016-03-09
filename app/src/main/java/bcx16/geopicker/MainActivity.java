package bcx16.geopicker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final String APP = "GeoPicker";
    public final double FROM_LAT  = 52.519939;
    public final double FROM_LONG = 13.424431;
    public final double TO_LAT  = 52.419939;
    public final double TO_LONG = 13.424431;

    private double to_lat, to_long;
    private String req_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.socketButton).setOnClickListener(socketButtonListener);

    }


    public void receiveSocketMsg () {

        // To be received from socket message
        to_lat = TO_LAT;
        to_long = TO_LONG;
        req_message = "Forgot my S-Bahn pass";

        // Inflate the request view
        LinearLayout layoutRoot = (LinearLayout)findViewById(R.id.requestView);
        LayoutInflater inflater = getLayoutInflater();

        View myView = inflater.inflate(R.layout.request_ride, layoutRoot, false);
        layoutRoot.addView(myView);

        // Update screen with pickup message
        TextView tv = (TextView) findViewById(R.id.helloMessage);
        tv.setText("Please pick me up!");

        TextView tvMsg = (TextView) findViewById(R.id.msg);
        tvMsg.setText(req_message);

        // Add button listeners
        findViewById(R.id.reqOkBtton).setOnClickListener(reqOkButtonListener);
        findViewById(R.id.reqNoButton).setOnClickListener(reqNoButtonListener);

    }

    private View.OnClickListener socketButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            receiveSocketMsg();
        }
    };

    void restoreView () {
        // Go back to the listening screen
        TextView tv = (TextView) findViewById(R.id.helloMessage);
        tv.setText("Waiting for requests");

        LinearLayout layoutRoot = (LinearLayout)findViewById(R.id.requestView);
        layoutRoot.removeAllViews();
    }

    View.OnClickListener reqOkButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            restoreView();
            requestRoute(TO_LAT, TO_LONG);
        }
    };

    View.OnClickListener reqNoButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            restoreView ();
        }
    };


    public void requestRoute (double to_lat, double to_long) {
        String url = "google.navigation:q=" + to_lat + "," + to_long;
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

}
