package lbx.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private OkHttpClient okHttpClient;
    private String string;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient = new OkHttpClient();
        tv = (TextView) findViewById(R.id.showhttpmessage);

    }

    public void clickGridView(final View v) {
        Intent i = new Intent(this, GridViewActivity.class);
        startActivity(i);
    }

    public void clickDynamicViewFlipper(View v) {
        startActivity(new Intent(this, DynamicViewFlipperActivity.class));
    }

    public void clickViewFlipper(View v) {
        startActivity(new Intent(this, ViewFlipperActivity.class));
    }

    public void clickCalendar(final View v) {
        startActivity(new Intent(this, AndroidCalendarActivity.class));
    }

    public void simpleGetClick(View view) {
        Log.d("TAGsimpleget", "simplegetclick----");

        Request request = new Request.Builder()
                .url("http://jeff-pluto-1874.iteye.com/blog/869710")
                .build();

        /*Request request1=new Request.Builder().url("https://github.com/huyongli/TigerOkHttp").build();
        //æ ¹æ®Requestå¯¹è±¡åèµ·Getåæ­¥Httpè¯·æ±
        try {
            Response response=okHttpClient.newCall(request1).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //æ ¹æ®Requestå¯¹è±¡åèµ·GetåŒæ­¥Httpè¯·æ±ïŒå¹¶æ·»å è¯·æ±åè°
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //è¯·æ±æåïŒæ­€å€å¯¹è¯·æ±ç»æè¿è¡å€ç
                //String result = response.body().string();
                //InputStream is = response.body().byteStream();
                //byte[] bytes = response.body().bytes();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void addParamGetClick(View view) {
        Request request = new Request.Builder().addHeader("hello", "gonghao 9527").url("http://jeff-pluto-1874.iteye.com/blog/869710").build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void simplePostClick(View view) {
        RequestBody requestBody = new FormBody.Builder().add("name", "jay").add("sex", "ç?").build();
        Request request = new Request.Builder().url("http://jeff-pluto-1874.iteye.com/blog/869710").addHeader("welcome", "helloworld").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    private Callback callback = new Callback() {
        //onResponseåonFailureéœäžæ¯åšUIçº¿çšäž­æ§è¡ç
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TAGcallback", "failure----");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.d("TAGcallback", "success----");
            string = response.body().string();
            showmessage(string);

        }
    };

    public void showmessage(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(string);
            }
        });

    }
}
