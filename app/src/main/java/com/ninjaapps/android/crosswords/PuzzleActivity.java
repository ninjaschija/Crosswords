package com.ninjaapps.android.crosswords;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;
import com.ninjaapps.android.crosswords.puzzlemodel.IntPair;

import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {
    CrosswordBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_puzzle);

        layoutBoard(10, 10);
    }

    private CrosswordBoard newBoard(int width, int height) {
        CrosswordTemplate template = new CrosswordTemplate(width, height);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (i == j) {
                    template.setSeparator(i, j);
                }
                else {
                    Random r = new Random();
                    template.setAt(i, j, (char)('A' + r.nextInt(26)));
                }
            }
        }
        return new CrosswordBoard(template);
    }

    private void layoutBoard(int width, int height) {
        Point size = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(size);

        int padding = 50;
        int tileSize = Math.min(
                (size.x - 2 * padding) / width,
                (size.y - 2 * padding) / height);

        TableLayout table = findViewById(R.id.table_layout);
        table.setGravity(Gravity.CENTER);
        table.removeAllViews();

        board = newBoard(width, height);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(tileSize, tileSize);
        rowParams.setMargins(2, 2, 2, 2);
        for (int i = 0; i < height; ++i) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < width; ++j) {
                Button b = new Button(this);
                if (board.getBoardTile(j, i) == 1) {
                    b.setBackgroundColor(Color.BLACK);
                }
                else {
                    b.setBackgroundColor(Color.LTGRAY);
                    b.setOnClickListener(tileCallback);
                    b.setTag(new IntPair(j, i));
                }
                b.setLayoutParams(rowParams);
                row.addView(b);
            }
            table.addView(row);
        }
    }

    private final Button.OnClickListener tileCallback = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.getTag() != null) {
                IntPair tag = (IntPair) view.getTag();
                ((Button)view).setText("" + board.getTemplate().getAt(tag.getX(), tag.getY()));
            }
        }
    };
}
