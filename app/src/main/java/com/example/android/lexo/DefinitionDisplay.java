package com.example.android.lexo;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.view.View;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DefinitionDisplay extends AppCompatActivity {

   String[] metadata;
   List<item_definition> definitions;
   ListView definitionsRv;
   DefinitionsAdapter mAdapter;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    private Menu collapsedMenu;
    private boolean appBarExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_definition_display);
        Intent senderActivity = getIntent();


        if (senderActivity.hasExtra("metaData"))
            metadata = senderActivity.getStringArrayExtra("metaData");
        if(senderActivity.hasExtra("definitions"))
            definitions = (List<item_definition>)senderActivity.getSerializableExtra("definitions");

           /* display.append("-------------\n");
            for(int x = 0;x <= 3;x++)
                display.append(metadata[x]+"\n");
            for (item_definition item : definitions) {

                display.append(item.getType()+" "+item.getDefinition()+ "\n");
                */
           final List<item_definition> temp = definitions;
           definitionsRv = (ListView) findViewById(R.id.definition_listView);
           mAdapter = new DefinitionsAdapter(this, R.layout.item_definition, temp);
           definitionsRv.setAdapter(mAdapter);
        definitionsRv.setDivider(null);
        definitionsRv.setDividerHeight(0);


            }






    public void addToDb(View view)

    {
        ContentValues cv = new ContentValues();
        int index = 0;

        for (String entry : metadata) {


            switch (index) {
                case 0:
                    cv.put(DictionaryContract.Dictionary.WORD, entry);
                    break;
                case 1:
                    cv.put(DictionaryContract.Dictionary.PHONETIC_SPELLING, entry);
                    break;
                case 2:
                    cv.put(DictionaryContract.Dictionary.AUDIO_URL, entry);
                    break;
                case 3:
                    cv.put(DictionaryContract.Dictionary.DESCRIPTION, entry);
                    break;
                default:
                    break;
            }
            index++;
        }
        index = 0;
               for(item_definition item:definitions)
               {

                    if (index == 0)
                        cv.put(DictionaryContract.Dictionary.DEF1, item.getType()+" "+item.getDefinition());
                    else if (index == 1)
                        cv.put(DictionaryContract.Dictionary.DEF2,  item.getType()+" "+item.getDefinition());
                    else if (index == 2)
                        cv.put(DictionaryContract.Dictionary.DEF3,  item.getType()+" "+item.getDefinition());
                    else if(index == 3)
                        cv.put(DictionaryContract.Dictionary.DEF4,  item.getType()+" "+item.getDefinition());
                    index++;

            }


        MainActivity.mDb.insert(DictionaryContract.Dictionary.TABLE_NAME, null, cv);
        MainActivity.mAdapter.swapCursor(MainActivity.getAllwordsByTime());
        LocalWordsActivity.mAdapter.swapCursor(MainActivity.getAllwordsByAlphabet());
    }


}
