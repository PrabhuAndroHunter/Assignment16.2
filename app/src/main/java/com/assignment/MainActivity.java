package com.assignment;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    Button buttonStart;
    TextView mProgressTextOneTv, mProgressTextTwoTv;
    ProgressBar progressBar1, progressBar2;
    DownloadManager downLoad1, downLoad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialise layout
        setContentView(R.layout.activity_main);
        // init all views
        inItViews();
        //set onclick listener on start button
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating object of DownloadManager
                downLoad1 = new DownloadManager(progressBar1, mProgressTextOneTv);
                StartAsyncTaskInParallel(downLoad1);
                downLoad2 = new DownloadManager(progressBar2, mProgressTextTwoTv);
                StartAsyncTaskInParallel(downLoad2);
            }
        });
    }

    // this method will initialise all views
    private void inItViews() {
        progressBar1 = (ProgressBar) findViewById(R.id.progressbar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressbar2);
        buttonStart = (Button) findViewById(R.id.button_start_download);
        mProgressTextOneTv = (TextView) findViewById(R.id.text_view_progress1);
        mProgressTextTwoTv = (TextView) findViewById(R.id.text_view_progress2);
    }

    //method StartAsyncTaskInParallel
    private void StartAsyncTaskInParallel(DownloadManager task) {
        //Applying condition for asyncTask
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

    /*  create class DownloadManager extends AsyncTask */
    public class DownloadManager extends AsyncTask <Void, Integer, Void> {
        ProgressBar mprogressbar;
        TextView mProgressUpdateTv;

        //Creating method doInBackground()
        @Override
        protected Void doInBackground(Void... voids) {
            //Applying loop for progressbar
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(TAG, "doInBackground: " + e.getMessage());
                }
                //publishing progress
                publishProgress(i);
            }

            return null;//returns null
        }

        public DownloadManager(ProgressBar mprogressbar, TextView mProgressUpdateTv) {
            this.mprogressbar = mprogressbar;
            this.mProgressUpdateTv = mProgressUpdateTv;
        }

        /*
         *  This method contains the code which is executed before
         *   the background processing starts */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /*
         *  This method is called after doInBackground method completes processing.
         *  Result from doInBackground is passed to this method */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        /*
        * This method receives progress updates from doInBackground method,
        * This method can use this progress update to update the UI thread*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];//get progress value
            mprogressbar.setProgress(progress); // update ProgressBar
            mProgressUpdateTv.setText("" + progress); // update Progress text
            if (progress == 100) {
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
            super.onProgressUpdate(values[0]);
        }
    }
}


