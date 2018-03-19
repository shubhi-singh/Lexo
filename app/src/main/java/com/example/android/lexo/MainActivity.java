package com.example.android.lexo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private TextView mTest;
    public static SQLiteDatabase mDb;
    public RecyclerView mfrontRV;
    public static FrontPageRvAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        Intent intent = getIntent();
        handleIntent(intent);

        DictionaryDbHelper helper = new DictionaryDbHelper(this);
        mDb = helper.getWritableDatabase();

        mfrontRV = (RecyclerView) findViewById(R.id.frontPageRv);
        mAdapter = new FrontPageRvAdapter(getAllwordsByTime());

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mfrontRV.setLayoutManager(manager);
        mfrontRV.setHasFixedSize(true);
        mfrontRV.setAdapter(mAdapter);



    }
    public static Cursor getAllwordsByTime()
    {
        return mDb.query(DictionaryContract.Dictionary.TABLE_NAME,
                null,
                null,

                null,
                null,
                null,
                DictionaryContract.Dictionary.TIME_STAMP);
    }
    public static Cursor getAllwordsByAlphabet()
    {
        return mDb.query(DictionaryContract.Dictionary.TABLE_NAME,
                null,
                null,

                null,
                null,
                null,
                DictionaryContract.Dictionary.WORD);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY).toLowerCase();

            new LookUp().execute(query);
        }
        }



    public class LookUp extends AsyncTask<String, Void, ArrayList<String[]>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String[]> doInBackground(String... params) {
            String word = params[0];
            String url = "http://vocabulary.com/dictionary/" + word;
            String url2 = "https://en.oxforddictionaries.com/definition/" + word;

            ArrayList<String[]> result = new ArrayList<String[]>();
            try {
                Document doc = Jsoup.connect(url).get();
                Document doc2 = Jsoup.connect(url2).get();
                Elements phonetic = doc2.select("span.phoneticspelling");
                Elements metaVar = doc.getElementsByTag("meta");
                Elements audioLink = doc2.getElementsByTag("audio");
                Elements h3Var = doc.select("h3.definition");
                String[] temp1 = {"0", word};
                result.add(temp1);
                for(Element pronunciation : phonetic)
                {
                    String[] temp = {"1", pronunciation.text() };
                    result.add(temp);
                    break;
                }
                for(Element audio : audioLink)
                {
                    String[] temp = {"2", audio.attr("src")};
                    result.add(temp);
                    break;
                }
                for (Element description : metaVar) {
                    if (description.attr("name").equals( "description")) {
                        String[] temp = {"3", description.attr("content")};
                        result.add(temp);
                        break;
                    }

                }
                for (Element definition : h3Var) {
                    String temp[] = {"4",  definition.text()};
                    result.add(temp);
                }



            } catch (IOException e) {
                //TODO;
            }
            return result;

        }

        @Override
        protected void onPostExecute(ArrayList<String[]> result) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (result.size() != 0) {
                displayDef(result);
            } else {
                showErrorMessage();
            }
        }
    }

    public  void viewByAlphabet(View view)
    {
        Intent intent = new Intent(MainActivity.this, LocalWordsActivity.class);
        startActivity(intent);
    }
    public  void viewDef(View view)
    {
        long id = (long)view.getTag();

        Cursor row = mDb.query(DictionaryContract.Dictionary.TABLE_NAME,
                null,
                DictionaryContract.Dictionary._ID+"="+id,
                null,
                null,
                null,
                null
                );
        row.moveToFirst();

        //Cursor row = mDb.rawQuery("SELECT * FROM "+ DictionaryContract.Dictionary.TABLE_NAME+" WHERE " + DictionaryContract.Dictionary._ID + " = " + id, null);
        ArrayList<String[]> result = new ArrayList<String[]>();
        String[] word = {"0", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.WORD))};
        result.add(word);
        String[] phonetic = {"1", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.PHONETIC_SPELLING))};
        result.add(phonetic);
        String[] url = {"2",row.getString(row.getColumnIndex(DictionaryContract.Dictionary.AUDIO_URL))};
        result.add(url);
        String[] description = {"3", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DESCRIPTION))};
        result.add(description);
        String[] def1 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF1))};
        String[] def2 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF2))};
        String[] def3 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF3))};
        String[] def4 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF4))};
        String[] def5 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF5))};
        String[] def6 = {"4", row.getString(row.getColumnIndex(DictionaryContract.Dictionary.DEF6))};
        if(def1[1] != null)
            result.add(def1);

        if(def2[1] != null)
            result.add(def2);

        if(def3[1] != null)
            result.add(def3);

        if(def4[1] != null)
            result.add(def3);

        if(def5[1]  != null)
            result.add(def5);

        if(def6[1] != null)
            result.add(def6);


        displayDef(result);


    }

      void displayDef(ArrayList<String[]> result) {
       Intent intent = new Intent(MainActivity.this, DefinitionDisplay.class);
       String[] metaData = new String[4];
       List<item_definition> definitions = new ArrayList<item_definition>() ;


       for(String[] item:result)
       {
           int index = Integer.parseInt(item[0]);
           if(index <= 3)
           {
               metaData[index] = item[1];
           }
           else
           {
               String[] temp = item[1].split(" ", 2);
               definitions.add(new item_definition(temp[0], temp[1]));
           }
       }
       intent.putExtra("metaData", metaData);
       intent.putExtra("definitions", (Serializable) definitions);
       startActivity(intent);
    }
    void showErrorMessage(){
        ;
    }
    }

