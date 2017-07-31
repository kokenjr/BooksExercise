package com.possible.booksexercise.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.possible.booksexercise.R;
import com.possible.booksexercise.domain.Book;
import com.possible.booksexercise.rest.RestClient;
import com.possible.booksexercise.task.ImageDownloadTask;

import java.util.List;

/**
 * Created by korji on 7/29/17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{
    private List<Book> books;
    private Context context;
    private long animOffset = 0;
    private boolean stopAnimation = false;


    public BooksAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tvBookTitle;
        TextView tvAuthor;
        RelativeLayout rlBook;
        ImageView ivBookCover;

        ViewHolder(View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            rlBook = itemView.findViewById(R.id.rlBook);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
        }

    }

    public void clear() {
        this.books.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvBookTitle.setText(book.getTitle());
        if (!TextUtils.isEmpty(book.getAuthor())){
            holder.tvAuthor.setVisibility(View.VISIBLE);
            holder.tvAuthor.setText(book.getAuthor());
        } else {
            holder.tvAuthor.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(book.getImageUrl())){
            new ImageDownloadTask(holder.ivBookCover).execute(book.getImageUrl());
        } //else {
//            holder.ivApplicantPhoto.setImageResource(R.drawable.profile_placeholder);
//        }

        int elevation = position + 1;
        ViewCompat.setElevation(holder.rlBook, elevation);

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.rlBook.getLayoutParams();
        if (position == 0) {
            layoutParams.setMargins(layoutParams.leftMargin,
                    (int) context.getResources().getDimension(R.dimen.card_top_margin),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
        } else if (position == getItemCount() - 1){
            layoutParams.setMargins(layoutParams.leftMargin, 0, layoutParams.rightMargin,
                    (int) context.getResources().getDimension(R.dimen.card_top_margin));
        } else {
            layoutParams.setMargins(layoutParams.leftMargin, 0, layoutParams.rightMargin,
                    (int) context.getResources().getDimension(R.dimen.card_overlap_margin));
        }

        if (!stopAnimation){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_slide_up_and_fade_in_animation);
            animation.setStartOffset(animOffset);
            holder.rlBook.startAnimation(animation);
            animOffset += 200;
        }
    }


    public void stopAnimation() {
        this.stopAnimation  = true;
    }

    public boolean hasStoppedAnimation() {
        return stopAnimation;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
