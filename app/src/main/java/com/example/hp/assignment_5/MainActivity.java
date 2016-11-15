package com.example.hp.assignment_5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    TextView mAboutTextView;
    TextView mTitleTextView;
    private final String TITLE="Title of the page";
    private final String DATA="The main text";
    String data=null;
    String title=null;
    String info=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mAboutTextView = (TextView) findViewById(R.id.aboutTextView);
            mTitleTextView = (TextView) findViewById(R.id.titleTextView);
            Button mGoButton = (Button) findViewById(R.id.goButton);
            mGoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new inBackground().execute();
                }
            });
        }
        else
        {
            title=savedInstanceState.getString(TITLE);
            info=savedInstanceState.getString(DATA);
            mAboutTextView = (TextView) findViewById(R.id.aboutTextView);
            mTitleTextView = (TextView) findViewById(R.id.titleTextView);
            mAboutTextView.setText(info);
            mTitleTextView.setText(title);

        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(TITLE,title);
        savedInstanceState.putString(DATA,info);
    }
    public class inBackground extends AsyncTask<Void,Void,Void>
    {

       StringBuffer buffer=new StringBuffer();
        @Override
        protected Void doInBackground(Void... params) {
         try
         {
             Document document= Jsoup.connect("https://www.iiitd.ac.in/about").get();
             data=document.text();
             title=document.title();
             Elements paragraphs=document.getElementsByTag("p");
             int i=0;
             for (Element paragraph :paragraphs) {
                 buffer.append(paragraph.text());
                 buffer.append("\n");
                 if(i==6)
                 info = paragraph.text();
                 i++;
             }

         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           mAboutTextView.setText(info);
            mTitleTextView.setText(title);
            Log.v("CompleteText",buffer.toString());
        }
    }
}
