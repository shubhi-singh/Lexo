package com.example.android.lexo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coolio1 on 4/2/18.
 */
public class DefinitionsAdapter extends ArrayAdapter<item_definition> {
    public DefinitionsAdapter(Context context, int resource, List<item_definition> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_definition, parent, false);
        }

        TextView word_type = (TextView) convertView.findViewById(R.id.word_type);
        TextView word_definition = (TextView) convertView.findViewById(R.id.word_definition);


        item_definition def = getItem(position);

        word_type.setText(def.getType());
        word_definition.setText(def.getDefinition());

        return convertView;
    }
}


