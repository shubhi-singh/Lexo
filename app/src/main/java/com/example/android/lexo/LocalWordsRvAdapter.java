package com.example.android.lexo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

/**
 * Created by coolio1 on 28/1/18.
 */

public class LocalWordsRvAdapter extends RecyclerView.Adapter<LocalWordsRvAdapter.LocalWordsRvAdapterViewHolder> {
    public Cursor mCursor;
    public LocalWordsRvAdapter(Cursor cursor)
    {
        mCursor = cursor;
    }
    @Override
    public LocalWordsRvAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdforListItem = R.layout.local_words_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdforListItem, viewGroup, false);
        LocalWordsRvAdapterViewHolder vh = new LocalWordsRvAdapterViewHolder(view);
        return vh;
    }
    @Override public void onBindViewHolder(LocalWordsRvAdapterViewHolder holder, int position)
    {
        if(!mCursor.moveToPosition(position))
            return;
        long id = mCursor.getLong(mCursor.getColumnIndex(DictionaryContract.Dictionary._ID));
        String word = mCursor.getString(mCursor.getColumnIndex(DictionaryContract.Dictionary.WORD));
        String phonetic = mCursor.getString(mCursor.getColumnIndex(DictionaryContract.Dictionary.PHONETIC_SPELLING));
        holder.word.setText(word);
        holder.phonetic.setText(phonetic);
        holder.word.setTag(id);
    }


    public class LocalWordsRvAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView word;
        TextView phonetic;
        public LocalWordsRvAdapterViewHolder(View view)
        {
            super(view);
            this.word = (TextView) view.findViewById(R.id.word);
            this.phonetic = (TextView) view.findViewById(R.id.phonetic);

        }
    }

    @Override
    public int getItemCount()
    {
        return mCursor.getCount();
    }

    public  void swapCursor(Cursor newCursor)
    {
        if(mCursor != null)
        {
            mCursor.close();
            mCursor = newCursor;

        }
        if(newCursor != null) this.notifyDataSetChanged();
    }
}
