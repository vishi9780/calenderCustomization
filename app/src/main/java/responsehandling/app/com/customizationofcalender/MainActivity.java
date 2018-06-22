package responsehandling.app.com.customizationofcalender;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
        //24 hours = 86400 seconds = 86400000 milliseconds. Just multiply your number with 86400000.

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_startDate,edt_startTime,edt_EndDate,edt_EndTime;
    private Button btn_OK;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog datePickerDialog;
    private String formattedDate, today, startdate;
    private long timeInMillisecondsEnd,timeInMillisecondsStart;
    public static final String TAG="TAG";
    private TimePickerDialog mTimePicker;
    CustomTimePickerDialog customTimePickerDialog;
    private boolean isTIme=false,compareCurrentTime=false;
    private String startdatetime="",endDateTime="";
    Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=Calendar.getInstance();
        findIDS();
    }

    private void findIDS() {
        //start date time
        edt_startDate=(EditText)findViewById(R.id.edt_startDate);
        edt_startTime=(EditText)findViewById(R.id.edt_startTime);
        //end date time
        edt_EndTime=(EditText)findViewById(R.id.edt_EndTime);
        edt_EndDate=(EditText)findViewById(R.id.edt_EndDate);
        btn_OK=(Button)findViewById(R.id.btn_OK);

        edt_startDate.setOnClickListener(this);
        edt_startTime.setOnClickListener(this);
        edt_EndTime.setOnClickListener(this);
        edt_EndDate.setOnClickListener(this);
        btn_OK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edt_startDate:
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, final int year,
                                                  final int monthOfYear, final int dayOfMonth) {
                                today =year  + "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date mDate = formatter.parse(today);
                                    timeInMillisecondsStart = mDate.getTime();
                                    edt_startDate.setText(today);
                                    Log.e(TAG, "onDateSet:::::>>>>"+timeInMillisecondsStart );
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 10000);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+86400000*3);
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                if (!datePickerDialog.isShowing()) {
                    datePickerDialog.show();
                }
                break;
            case R.id.edt_startTime:
                customTimePickerDialog=new CustomTimePickerDialog(this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        edt_startTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));

                    }
                },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
                if (!customTimePickerDialog.isShowing()){
                    customTimePickerDialog.show();
                }
                break;
            case R.id.edt_EndTime:
                customTimePickerDialog=new CustomTimePickerDialog(this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        edt_EndTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));

                    }
                },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);

                if (!customTimePickerDialog.isShowing()){
                    customTimePickerDialog.show();
                }
                break;
            case R.id.edt_EndDate:
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, final int year,
                                                  final int monthOfYear, final int dayOfMonth) {

                                today =year  + "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date mDate = formatter.parse(today);
                                    timeInMillisecondsEnd = mDate.getTime();
                                    edt_EndDate.setText(today);
                                    Log.e(TAG, "onDateSet:::::>>>>"+timeInMillisecondsEnd );
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 10000);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+86400000*3);
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        Log.e(TAG, "onCancel: Dismiss" );
                    }
                });
                if (!datePickerDialog.isShowing()) {
                    datePickerDialog.show();
                }
                break;
            case R.id.btn_OK:
                startdatetime=edt_startDate.getText().toString()+" "+edt_startTime.getText().toString();
                endDateTime=edt_EndDate.getText().toString()+" "+edt_EndTime.getText().toString();
                if (edt_startDate.getText().toString().equals("")){
                    dialogBox("Custom","please select start date first",MainActivity.this);
                }else if (edt_startTime.getText().toString().equals("")){
                    dialogBox("Custom","please select start time first",MainActivity.this);
                }else if (edt_EndDate.getText().toString().equals("")){
                    dialogBox("Custom","please select end date first",MainActivity.this);
                }else if (edt_EndTime.getText().toString().equals("")){
                    dialogBox("Custom","please select end time first",MainActivity.this);
                }else {
                    if (timeInMillisecondsStart>timeInMillisecondsEnd){
                        dialogBox("Custom","Start Date & Time should be less than End Date & Time",MainActivity.this);
                    }else if (timeInMillisecondsStart==timeInMillisecondsEnd ){
                        if (timeInMillisecondsStart==timeInMillisecondsEnd){
                            getDateDiff(edt_startDate.getText().toString().trim(),edt_startTime.getText().toString().trim(),
                                    edt_EndDate.getText().toString().trim(), edt_EndTime.getText().toString().trim());
                        }else {
                            compareTwoTime(edt_startTime.getText().toString().trim(), edt_EndTime.getText().toString().trim());
                            if (isTIme == true) {
                                compareWithCurrentTime(edt_startDate.getText().toString(), edt_EndTime.getText().toString());
                                if (compareCurrentTime == true) {
                                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    dialogBox("Custom","Start Date & Time should be more than current time",MainActivity.this);
                                }
                            } else {
                                if (startdatetime.equals(endDateTime)) {
                                    dialogBox("Custom","Start Date & Time and End Date & Time should'nt be equal",MainActivity.this);
                                } else {
                                    dialogBox("Custom","End Date & Time should be more than Start Date & Time",MainActivity.this);
                                }
                            }
                        }
                    }else {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void compareWithCurrentTime(String startdate , String startTIme) {
        SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        Log.e("187",">>>"+formattedDate);
        String dateafter = startdate+" "+startTIme;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd-yyyy hh:mm");
        Date convertedDate = new Date();
        Date convertedDate2 = new Date();
        try {
            convertedDate = dateFormat.parse(formattedDate);
            convertedDate2 = dateFormat.parse(dateafter);
            Log.e("198","<<>>"+convertedDate);
            Log.e("198","<<convertedDate2>>"+convertedDate2);
            if (convertedDate2.after(convertedDate)) {
                compareCurrentTime=true;

            } else {
                compareCurrentTime=false;

            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("208","<<<>>>"+e);
        }
    }
    private void displayFullClock(final EditText editText, Context context){
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    editText.setText(selectedHour + ":" + selectedMinute);

            }
        }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        if (!mTimePicker.isShowing()) {
            mTimePicker.show();
        }
    }
    public boolean compareTwoTime(String starttime, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date_starttime = simpleDateFormat.parse(starttime);
            Date date_endtime = simpleDateFormat.parse(endtime);
            if (date_starttime.before(date_endtime)) {
                isTIme=true;
                return true;
            } else {
                isTIme=false;
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TAG", "Error in compareTwoTime: " + e);
        }
        return false;
    }
    public  void getDateDiff(String startdate,String starttime,String endDate,String endTime) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dateOne = df.parse(startdate+" "+starttime);
            Date dateTwo = df.parse(endDate+" "+endTime);
            long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
            System.out.println("difference:" + timeDiff);
            Log.e("156","<time>"+timeDiff);
            long diif=timeDiff/1000;
            if (diif>=3600){
                compareTwoTime(edt_startTime.getText().toString().trim(), edt_EndTime.getText().toString().trim());
                if (isTIme == true) {
                    compareWithCurrentTime(edt_startDate.getText().toString(), edt_startTime.getText().toString());
                    if (compareCurrentTime == true) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        dialogBox("Custom","Start Date & Time should be more than current time",MainActivity.this);
                    }
                } else {
                    if (startdatetime.equals(endDateTime)) {
                        dialogBox("Custom","Start Date & Time and End Date & Time should'nt be equal",MainActivity.this);
                    } else {
                        dialogBox("Custom","End Date & Time should be more than Start Date & Time",MainActivity.this);
                    }
                }
            }else if (diif==0){
                dialogBox("Custom","Start Date & Time and End Date & Time should'nt be equal",MainActivity.this);
                }else {
                dialogBox("Custom","Start Date & Time should be more than 1 Hour",MainActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("159","<<catch>>"+e);

        }
    }
    public void dialogBox(String title,String msg,Activity ctx) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        })
              /*  .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
*/
        // Create the AlertDialog object and return it
        ;
            if (!builder.create().isShowing()) {
                builder.create().show();
            }
    }
}
