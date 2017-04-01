package com.islavstan.realmexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    private List<Book> bookList;
    private UserActionsListener mItemListener;


    public RecyclerAdapter(List<Book> bookList, UserActionsListener mItemListener) {
        this.bookList = bookList;
        this.mItemListener = mItemListener;

    }

    public void newList(List<Book> books) {
        bookList = books;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);

        return new RecyclerAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Book book = bookList.get(position);
        holder.text.setText(book.getTitle() + " " + book.getPage());
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemListener.deleteBook(book.getTitle(), position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout item;

        public CustomViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            item = (LinearLayout) itemView.findViewById(R.id.item);
        }

    }
}
