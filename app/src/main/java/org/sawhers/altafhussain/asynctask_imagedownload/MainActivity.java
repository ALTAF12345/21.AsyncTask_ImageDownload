package org.sawhers.altafhussain.asynctask_imagedownload;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button btnCountDown,btnDownloadImage;
    TextView tvCount;
    ProgressDialog progressDialog;
    ImageView ivDownloadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCountDown= (Button) findViewById(R.id.btn_CountDown);
        tvCount= (TextView) findViewById(R.id.tv_count);
        ivDownloadImg= (ImageView) findViewById(R.id.iv_imageDownload);
        btnDownloadImage= (Button) findViewById(R.id.btn_imagedownlod);
        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   //Cause error of ANR
//                for (int i=5; i>=1;i--){
//                    tvCount.setText(String.valueOf(i));
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }

                MyCountDown myCountDown=new MyCountDown();
                myCountDown.execute();
            }
        });
  //***********************************************
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.800florals.com/img/TW511.jpg";
               // String url="https://www.google.com.pk/imgres?imgurl=http%3A%2F%2Fmedia02.hongkiat.com%2Fww-flower-wallpapers%2Froundflower.jpg&imgrefurl=http%3A%2F%2Fwww.hongkiat.com%2Fblog%2Fww-flower-wallpapers%2F&docid=6bbQ6K0Qhv8TsM&tbnid=SrU-3GY_g_6QqM%3A&vet=1&w=550&h=344&bih=638&biw=1366&q=flower%20images&ved=0ahUKEwiL8a-CiLjRAhXBrxoKHZI5AjIQMwg2KAowCg&iact=mrc&uact=8";
                ImageDownload imageDownload=new ImageDownload();
                imageDownload.execute(url);
            }
        });
    }
    private class MyCountDown extends AsyncTask<Void,Integer,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvCount.setVisibility(View.VISIBLE);
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Fasten you seatbelt ... ");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i=5; i>=1;i--){
                //tvCount.setText(String.valueOf(i));
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvCount.setText(String.valueOf(values[0]));

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Take Of NOW", Toast.LENGTH_SHORT).show();
            tvCount.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();

        }
    }
 //************************************************************************
    private class ImageDownload extends AsyncTask<String ,Void,Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... string) {

            try
            {
                URL url=new URL(string[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);


                return bitmap;

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                ivDownloadImg.setImageBitmap(bitmap);
            }
            super.onPostExecute(bitmap);
        }
    }

}
