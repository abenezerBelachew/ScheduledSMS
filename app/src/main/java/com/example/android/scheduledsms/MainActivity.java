package com.example.android.scheduledsms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.scheduledsms.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap b2;


    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayDate = (TextView) findViewById(R.id.day_to_Send);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        // This button will send the message
        Button submit = (Button) findViewById(R.id.submit);

        // Create the intent.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 * Getting the Message to be sent
                 * */
                EditText messageEditText = (EditText) findViewById(R.id.message);
                // the message the user wrote
                String message = messageEditText.getText().toString();


                /*
                 * Getting the receiver phone
                 * */
                EditText phoneEditText = (EditText) findViewById(R.id.reciever_number);
                // receiver phone number
                String phoneNumber = String.format("smsto: %s", phoneEditText.getText().toString());


                /*
                 * Getting the date to be sent
                 * */
                EditText dateEditText = (EditText) findViewById(R.id.day_to_Send);
                // the day to be sent
                // Date date = dateEditText
                String date = dateEditText.getText().toString();

                Log.v("DATE OBJECT: ", date);

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                // Set the data for the intent as the phone number.
                smsIntent.setData(Uri.parse(phoneNumber));
                // Add the message (sms) with the key ("sms_body").
                smsIntent.putExtra("sms_body", message);

                smsIntent.putExtra("sms_body", b2);
                smsIntent.setType("image/jpeg");



                Date today = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String todayString = formatter.format(today);


                if (new Date().parse(date) > new Date().parse(todayString)) {
                    Button submit = (Button) findViewById(R.id.submit);
                    submit.setText("Cool! Your message will be sent on: " + date);
                    startActivity(smsIntent);
                }

                if (new Date().parse(date) < new Date().parse(todayString)) {
                    Button submit = (Button) findViewById(R.id.submit);
                    submit.setText("The date has already passed.");
                }

                if (new Date().parse(date) == new Date().parse(todayString)) {
                    Button submit = (Button) findViewById(R.id.submit);
                    submit.setText("Sending...");
                    if (smsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(smsIntent);
                    }
                }



                /* else {
                    if (smsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(smsIntent);
                    } else {
                        Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent");
                    }
                } */
            }
        });


        ImageView add_image = (ImageView) findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                */
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                b2 = bitmap;
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);

//                intent.putExtra(Intent.EXTRA_STREAM, imageUri);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


