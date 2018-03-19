package com.example.android.lexo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalWordsActivity extends AppCompatActivity {

    public RecyclerView mRv;
    public static LocalWordsRvAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_words);

        mRv  = (RecyclerView) findViewById(R.id.localWordsRv);
        mAdapter = new LocalWordsRvAdapter(MainActivity.getAllwordsByAlphabet());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(manager);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
    }

    public  void viewDef(View view)
    {
        long id = (long)view.getTag();

        Cursor row = MainActivity.mDb.query(DictionaryContract.Dictionary.TABLE_NAME,
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
        Intent intent = new Intent(LocalWordsActivity.this, DefinitionDisplay.class);
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
}
