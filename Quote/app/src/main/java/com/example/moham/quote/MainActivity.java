package com.example.moham.quote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private TextView authorTextView;
    private Button newQuoteButton;

    private List<Quote> quotes;
    private Quote newQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quotes   = QuoteGenerator.generate();
        newQuote = getRandomQuote();

        quoteTextView  = findViewById(R.id.quoteTextView);
        authorTextView = findViewById(R.id.authorTextView);
        newQuoteButton = findViewById(R.id.button);

        showNewQuote();

        newQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuote = getRandomQuote();
                showNewQuote();
            }
        });

    }

    private Quote getRandomQuote () {
        Random random = new Random();
        int i = random.nextInt(quotes.size());
        return quotes.get(i);
    }

    private void showNewQuote () {
        String quote  = newQuote.getQuoteText();
        String author = newQuote.getQuoteAuthor();

        quoteTextView.setText(quote);
        authorTextView.setText("* " + author);
    }
}
