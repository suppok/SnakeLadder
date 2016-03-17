package com.ske.snakebaddesign.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ske.snakebaddesign.R;
import com.ske.snakebaddesign.guis.BoardView;
import com.ske.snakebaddesign.models.Game;
import com.ske.snakebaddesign.models.Player;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Observer {

    private int boardSize;
    private int p1Position;
    private int p2Position;
    private int turn;
    private Game game;

    private BoardView boardView;
    private Button buttonTakeTurn;
    private Button buttonRestart;
    private TextView textPlayerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initComponents() {
        boardSize = 6;
        game =  new Game(boardSize);
        game.addObserver(this);
        boardView = (BoardView) findViewById(R.id.board_view);
        boardView.setBoardSize(boardSize);
        buttonTakeTurn = (Button) findViewById(R.id.button_take_turn);
        buttonTakeTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeTurn();
            }
        });
        buttonRestart = (Button) findViewById(R.id.button_restart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        textPlayerTurn = (TextView) findViewById(R.id.text_player_turn);
    }

    private void resetGame() {
        turn = 0;
        p1Position = 0;
        p2Position = 0;
        boardSize = 6;
        boardView.setBoardSize(boardSize);
        boardView.setP1Position(p1Position);
        boardView.setP2Position(p2Position);
        game = new Game(6);
        textPlayerTurn.setText("Player 1's Turn");
        game.addObserver(this);
    }

    private void takeTurn() {
       final int value = game.getDieCup().roll();
        String title = "You rolled a die";
        String msg = "You got " + value;
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                game.play(value);
                dialog.dismiss();
            }
        };
        displayDialog(title, msg, listener);
    }

    private int adjustPosition(int current, int distance) {
        current = current + distance;
        int maxSquare = boardSize * boardSize - 1;
        if(current > maxSquare) {
            current = maxSquare - (current - maxSquare);
        }
        return current;
    }

    private void checkWin() {
        String title = "Game Over";
        String msg = game.sendGameStatus();
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
                dialog.dismiss();
            }
        };
        if(msg.equals("")) {
            return;
        }
        displayDialog(title, msg, listener);
    }

    private void checkSpecial() {


        String lucky = "LUCKY";
        String detail = "You get free roll";

        if(game.getP1Pos() == 3 || game.getP1Pos() == 10 || game.getP1Pos() == 14 ||
                game.getP1Pos() == 21 || game.getP1Pos() == 25 || game.getP1Pos() == 32) {
            OnClickListener popup = new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final int value = game.getDieCup().roll();
                    String title = "You rolled a die";
                    String msg = "You got " + value;
                    OnClickListener listener = new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            game.playSpecial1(value);
                            dialog.dismiss();
                        }
                    };
                    displayDialog(title, msg, listener);
                    dialog.dismiss();
                }
            };
            displayDialog(lucky, detail, popup);


        } else if(game.getP2Pos() == 3 || game.getP2Pos() == 10 || game.getP2Pos() == 14 ||
                game.getP2Pos() == 21 || game.getP2Pos() == 25 || game.getP2Pos() == 32){

            OnClickListener popup = new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final int value = game.getDieCup().roll();
                    String title = "You rolled a die";
                    String msg = "You got " + value;
                    OnClickListener listener = new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            game.playSpecial2(value);
                            dialog.dismiss();
                        }
                    };
                    displayDialog(title, msg, listener);
                    dialog.dismiss();
                }
            };
            displayDialog(lucky, detail, popup);
        }

    }

    private void displayDialog(String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", listener);
        alertDialog.show();
    }

    public void update(Observable o, Object obj) {
        Player player = (Player)obj;
        if(player.getNumber() == 1)
            boardView.setP1Position(player.getPosition());
        else
            boardView.setP2Position(player.getPosition());
        checkSpecial();
        if(player.getNumber() == 1)
            textPlayerTurn.setText("Player 2's Turn");
        else
            textPlayerTurn.setText("Player 1's Turn");
        checkWin();
        boardView.postInvalidate();

    }


}
