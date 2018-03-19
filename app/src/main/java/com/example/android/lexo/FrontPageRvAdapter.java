package com.example.android.lexo;

/**
 * Created by coolio1 on 28/1/18.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

public class FrontPageRvAdapter extends RecyclerView.Adapter<FrontPageRvAdapter.FrontPageRvAdapterViewHolder>{
   public static Cursor dbCursor;

    public FrontPageRvAdapter(Cursor dbCursor) {
        this.dbCursor = dbCursor;
    }
    @Override
    public FrontPageRvAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.front_page_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, viewGroup, false);
        FrontPageRvAdapterViewHolder vh = new FrontPageRvAdapterViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(FrontPageRvAdapterViewHolder holder, int position)
    {
        if(!dbCursor.moveToPosition(position))
            return;
        long id = dbCursor.getLong(dbCursor.getColumnIndex(DictionaryContract.Dictionary._ID));
        String word = dbCursor.getString(dbCursor.getColumnIndex(DictionaryContract.Dictionary.WORD));
        String phonetic = dbCursor.getString(dbCursor.getColumnIndex(DictionaryContract.Dictionary.PHONETIC_SPELLING));
        holder.word.setText(word);
        holder.phonetic.setText(phonetic);

        holder.word.setTag(id);
    }
    @Override
    public int getItemCount()
    {
        return dbCursor.getCount();
    }
    public class FrontPageRvAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView word;
        TextView phonetic;
        public FrontPageRvAdapterViewHolder(View view)
        {
            super(view);
            word = (TextView) view.findViewById(R.id.frontGridWord);
            phonetic = (TextView) view.findViewById(R.id.frontGridPhonetic);

        }


    }
    public  void swapCursor(Cursor newCursor)
    {
        if(dbCursor != null)
        {
            dbCursor.close();
            dbCursor = newCursor;

        }
        if(newCursor != null) this.notifyDataSetChanged();
    }
}
