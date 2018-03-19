package com.example.android.lexo;

/**
 * Created by coolio1 on 28/1/18.
 */

import android.provider.BaseColumns;
public class DictionaryContract {
    private DictionaryContract(){}
    public static final class Dictionary implements BaseColumns
    {
        public static final String TABLE_NAME = "dictionary";
        public static final String TIME_STAMP = "time_stamp";
        public static final String WORD="word";
        public static final String PHONETIC_SPELLING = "phonetic_spelling";
        public static final String AUDIO_URL = "audio_url";
        public static final String DESCRIPTION = "description";
        public static final String DEF1 = "def1";
        public static final String DEF2 = "def2";
        public static final String DEF3 = "def3";
        public static final String DEF4 = "def4";
        public static final String DEF5 = "def5";
        public static final String DEF6 = "def6";

    }

}
