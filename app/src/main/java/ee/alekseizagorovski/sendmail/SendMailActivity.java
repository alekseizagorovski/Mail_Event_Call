package ee.alekseizagorovski.sendmail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import org.apache.http.protocol.HTTP;
import java.util.Calendar;
import java.util.List;


public class SendMailActivity extends ActionBarActivity {

    private EditText To;
    private EditText Subject;
    private EditText TextMessage;
    private EditText editTextCalendar;
    private EditText editTextCall;
   



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        To = (EditText) findViewById(R.id.To);
        Subject = (EditText) findViewById(R.id.Subject);
        TextMessage = (EditText) findViewById(R.id.TextMessage);
        editTextCalendar = (EditText) findViewById(R.id.editTextCalendar);
        editTextCall = (EditText) findViewById(R.id.editTextCall);



    }

    public void sendMessage(View View) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{To.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Subject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, TextMessage.getText().toString());

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(emailIntent);
        }
    }

    public void addCalendar(View View) {

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 5, 28, 19, 15);
        Calendar endTime = Calendar.getInstance();
        beginTime.set(2015, 5, 28, 20, 15);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, editTextCalendar.getText().toString());
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(calendarIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(calendarIntent);
        }
    }


    public void takeCall(View view){

        String telNumber = editTextCall.getText().toString();

        String uri = "tel:" + telNumber ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }



}



