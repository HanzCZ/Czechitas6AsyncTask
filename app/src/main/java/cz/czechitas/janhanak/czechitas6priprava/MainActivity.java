package cz.czechitas.janhanak.czechitas6priprava;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int seconds = 5;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                seconds = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepAndToast();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread();
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, "wake up", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void sleepAndToast() {
        SystemClock.sleep(seconds * 1000);
        Toast.makeText(this, "wake up", Toast.LENGTH_SHORT).show();
    }

    public void runAsyncTask() {
        new LongOperation(seconds).execute(seconds);
    }

    public void runThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(seconds * 1000);
                    handler.sendMessage(new Message());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    private class LongOperation extends AsyncTask<Integer, Void, String> {

        public int seconds;

        public LongOperation(int seconds) {
            this.seconds = seconds;
        }

        @Override
        protected String doInBackground(Integer... params) {
            int count = params.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                try {
                    Thread.sleep(params[i] * 1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, "wake up async", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
