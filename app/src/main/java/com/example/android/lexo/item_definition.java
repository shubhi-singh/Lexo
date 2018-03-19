package com.example.android.lexo;

import java.io.Serializable;

/**
 * Created by coolio1 on 4/2/18.
 */

public class item_definition implements Serializable {
    String word_type;
    String word_definition;
    public item_definition(){

    }
    public item_definition(String word_type, String word_definition)
    {
        this.word_definition = word_definition;
        this.word_type = word_type;
    }
    public String getType () {
        return word_type;
    }
    public String getDefinition(){
        return word_definition;
    }
    public void setType(String type)
    {
        this.word_type = type;
    }
    public void setDefinition(String definition)
    {
        this.word_definition = definition;
    }


}
