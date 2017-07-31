package com.possible.booksexercise.activity;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.possible.booksexercise.R;
import com.possible.booksexercise.adapter.BooksAdapter;
import com.possible.booksexercise.domain.Book;
import com.possible.booksexercise.rest.RestClient;
import com.possible.booksexercise.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private List<Book> books;
    private List<Book> pagedBooks = new ArrayList<>();
    private int bookIndex = 0;
    private BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                books = null;
                bookIndex = 0;
                if (booksAdapter != null)
                    booksAdapter.clear();
                swipeRefreshLayout.setRefreshing(false);
                getBooks();
            }
        });

        getBooks();
    }

    private void getBooks() {
        final CoordinatorLayout clMain = findViewById(R.id.clMain);
        RestClient restClient = new RestClient();
        Call<List<Book>> call = restClient.getApiService().getBooks();
        showLoadingProgressDialog();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()){
                    books = response.body();
                    if (books != null && !books.isEmpty()){
                        pageBooks();
                        booksAdapter = new BooksAdapter(pagedBooks, getApplicationContext());
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        final RecyclerView rvBooks = findViewById(R.id.rvBooks);
                        rvBooks.setLayoutManager(layoutManager);
                        rvBooks.setAdapter(booksAdapter);

                        rvBooks.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                rvBooks.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                if (!booksAdapter.hasStoppedAnimation()) {
                                    booksAdapter.stopAnimation();
                                }

                            }
                        });

                        rvBooks.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if(dy > 0) {
                                    visibleItemCount = layoutManager.getChildCount();
                                    totalItemCount = layoutManager.getItemCount();
                                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                    if (loading) {
                                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                            loading = false;
//                                            final int initialSize = pagedBooks.size();
                                            pageBooks();
//                                            final int updatedSize = pagedBooks.size();
                                            rvBooks.post(new Runnable() {
                                                public void run() {
                                                    booksAdapter.notifyDataSetChanged();
                                                    //TODO: Prefer to use notifyItemRangeInserted, but seems to cause issue when scrolling
//                                                    booksAdapter.notifyItemRangeInserted(initialSize, updatedSize);
                                                }
                                            });
                                            if (bookIndex <= books.size()) {
                                                loading = true;
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                } else {
                    showError(clMain, getString(R.string.error_getting_books));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                dismissProgressDialog();
                showError(clMain, t.getMessage());
            }
        });
    }

    protected void showError(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        snackbar.show();
    }

    private void pageBooks() {
        if (bookIndex <= books.size()) {
            int endIndex = bookIndex + 20;
            if (endIndex > books.size()) {
                endIndex = books.size();
            }
            pagedBooks.addAll(books.subList(bookIndex, endIndex));
            bookIndex += 20;
        }
    }

    public void showLoadingProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
