package lyhoangvinh.com.callback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lyhoangvinh.com.callback.thread.BackgroundThreadExecutor;
import lyhoangvinh.com.callback.thread.UIThreadExecutor;

public class MainActivity extends BaseActivity {

    private TextView tvKQ;
    private Button btnStart;
    List<HashMap<String,String>> attrs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attrs = new ArrayList<>();
        tvKQ = (TextView) findViewById(R.id.tvKQ);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiThreadPool();
            }
        });
    }

    private void callApiThreadPool(){
        if (!isConnected()){
            tvKQ.setText("Please check your connection!");

        }else {
            showProgress("Loading");
            String URL_LOGIN = Contants.URL + "driver/login";
            HashMap<String,String> hsPass = new HashMap<>();
            hsPass.put("password", "123456");

            HashMap<String,String> hsEmail = new HashMap<>();
            hsEmail.put("email", "vinh270795@gmail.com");

            attrs.add(hsPass);
            attrs.add(hsEmail);

            TaskRunnable taskRunnable = new TaskRunnable(URL_LOGIN, attrs, new UserCallBack() {
                @Override
                public void OnComplete(final String data) {
                    UIThreadExecutor.getInstance().runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                String status = jsonObject.getString("status");
                                if (status.equals("success")){
                                    dismissProgress();
                                    tvKQ.setText("Cập nhật UI pải nằm ở MainThread");

                                }else {
                                    dismissProgress();
                                    tvKQ.setText(status);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void OnError(Exception ex) {
                    Log.d("OnError","callApiThreadPool: "+ ex.toString());
                }
            });
            BackgroundThreadExecutor.getInstance().runOnBackground(taskRunnable);
        }
    }

    private void callApiRunnable(){
        String URL_LOGIN = Contants.URL + "driver/login";
        HashMap<String,String> hsPass = new HashMap<>();
        hsPass.put("password", "123456");

        HashMap<String,String> hsEmail = new HashMap<>();
        hsEmail.put("email", "vinh270795@gmail.com");

        attrs.add(hsPass);
        attrs.add(hsEmail);

        TaskRunnable taskRunnable = new TaskRunnable(URL_LOGIN, attrs, new UserCallBack() {
            @Override
            public void OnComplete(String data) {

            }

            @Override
            public void OnError(Exception ex) {

            }
        });
        Thread thread = new Thread(taskRunnable);
        thread.start();
    }

    private void CallApiThred(){
        String URL_LOGIN = Contants.URL + "driver/login";
        HashMap<String,String> hsPass = new HashMap<>();
        hsPass.put("password", "123456");

        HashMap<String,String> hsEmail = new HashMap<>();
        hsEmail.put("email", "vinh270795@gmail.com");

        attrs.add(hsPass);
        attrs.add(hsEmail);

        TaskThred taskThred = new TaskThred(URL_LOGIN, attrs, new UserCallBack() {
            @Override
            public void OnComplete(String data) {
                if (data!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String status = jsonObject.getString("status");
                        Log.d("status", status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void OnError(Exception ex) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        taskThred.start();
    }
}
