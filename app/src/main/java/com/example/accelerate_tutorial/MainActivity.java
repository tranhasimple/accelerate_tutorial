package com.example.accelerate_tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //    private static final String HOSTNAME = "http://192.168.68.141:5000/";
    private static final String TAG = "MainActivity";
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//    public static final int REQUEST_WRITE_STORAGE = 112;


    private volatile boolean stopThread = false;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private TextView tvX, tvY, tvZ, tvTimestamp;
    private Button btnStop;

    // create global listData posted to server
    List<Data> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvX = findViewById(R.id.tv_x);
        tvY = findViewById(R.id.tv_y);
        tvZ = findViewById(R.id.tv_z);
        tvTimestamp = findViewById(R.id.tv_timestamp);
        btnStop = findViewById(R.id.btn_stop);


        // Enable accelerometer
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d(TAG, "onCreate: Registered Accelerometer Listener");

        // Tạo timer schedule -> Cứ 5s sẽ tổng hợp dữ liệu gửi lên server 1 lần
//        Timer t = new java.util.Timer();
//        t.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                if (listData.size() < 100) {
//                    listData = new ArrayList<>();
//                    return;
//                }
//                Map<String, Object> mapObj = new HashMap<>();
//                mapObj.put("data", listData);
//
//                ObjectMapper mapper = new ObjectMapper();
//                String jsonInString = null; // JSON data
//                try {
//                    jsonInString = mapper.writeValueAsString(mapObj);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }

//                Log.e(TAG, "[INFO]: " + listData.toString());
//                String message;
//                String delim = "-";


//                    try {
//                        FileOutputStream fw = new FileOutputStream("input.txt", true);
////                        fos.write(text.getBytes());
//                        int i = 0;
//                        while (i < listData.size() - 1) {
//                            StringBuilder sb = new StringBuilder();
//                            sb.append(listData.get(i).getValueX());
//                            sb.append(" ");
//                            sb.append(listData.get(i).getValueY());
//                            sb.append(" ");
//                            sb.append(listData.get(i).getValueZ());
//                            sb.append(" ");
//                            sb.append(listData.get(i).getTimestamp());
////                          sb.append("\n");
//                            String res = sb.toString();
//                            Log.e(TAG, res);
//                            fw.write(res.getBytes());
//                            fw.close();
//                            i++;
//
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                Log.i(TAG, "[LENGTH]: " + String.valueOf(listData.size()));


//                Writer output;
//                try {
//                    output = new BufferedWriter(new FileWriter(input.getAbsolutePath().toString()));  //clears file every time
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    output.append("New Line!");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("input.txt", Context.MODE_PRIVATE));
//                    outputStreamWriter.write(listData.toString());
//                    outputStreamWriter.close();
//                }
//                catch (IOException e) {
//                    Log.e("Exception", "File write failed: " + e.toString());
//                }


//                RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), input.getAbsolutePath());
//                MultipartBody.Part fileImage = null;
//                try {
//                    if (requestFile.contentLength() != 0)
//                        fileImage = MultipartBody.Part.createFormData("file", input.getName(), requestFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                OkHttpClient onOkHttpClient = new OkHttpClient();
//                RequestBody body = RequestBody.create(jsonInString, JSON);
//
//                Request request = new Request
//                        .Builder()
//                        .url(HOSTNAME + "post_3_value")
//                        .post(body)
//                        .build();
//
//                onOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
//                    @Override
//                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
//                        Log.e(TAG, "[ERROR]: Network not found");
//                        Toast.makeText(MainActivity.this, "network not found", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//                        String result = response.body().string();
//                        Log.i(TAG, "[RESULT]: " + result);
//                        txtResult.setText("Result: " + result);
//                    }
//                });

        // reset data
//                listData = new ArrayList<>();
//            }
//        }, 100, 5000);


    }

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("url").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        return retrofit;
    }

    public void stop() {
        stopThread = true;
    }

    public void writeToFile(View view) {
        String FILE_NAME = "input.txt";
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("vi", "VN"));

        String date = simpleDateFormat.format(new Date());
        String text = date.toString() + " Hello\n";
        StringBuilder sb = new StringBuilder();
        sb.append(tvX.getText().toString());
        sb.append(" ");
        sb.append(tvY.getText().toString());
        sb.append(" ");
        sb.append(tvY.getText().toString());
        sb.append(" ");
        sb.append(tvTimestamp.getText().toString());
        sb.append("\n");
        String res = sb.toString();
        Log.e(TAG, res);


        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(FILE_NAME, true);
            fos.write(res.getBytes());

            Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    // Event extract accelerometer and display to view
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = Double.parseDouble(String.format("%.6f", sensorEvent.values[0]));
        double y = Double.parseDouble(String.format("%.6f", sensorEvent.values[1]));
        double z = Double.parseDouble(String.format("%.6f", sensorEvent.values[2]));
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("vi", "VN"));

        String date = simpleDateFormat.format(new Date());
//        System.out.println(date);

        tvX.setText(Double.toString(x));
        tvY.setText(Double.toString(y));
        tvZ.setText(Double.toString(z));
        tvTimestamp.setText(date);
        listData.add(new Data(x, y, z, date));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}