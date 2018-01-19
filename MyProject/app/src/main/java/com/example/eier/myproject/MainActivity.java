package com.example.eier.myproject;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageButton[] playerButtons = new ImageButton[3];
    private ImageButton[] CPUButtons = new ImageButton[3];
    private TextView playerScore;
    private TextView cpuScore;
    private Button playNewRound;

    private final String PLAYERCOLORPICKED = "#9d3cff00";
    private final String CPUCOLORPICKED = "#9dff0000";
    private View decorView;
    private String plPick;
    private String cpuPick;
    private int pScore = 0;
    private int cScore = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;

        initialize();

        textView.setText("Take your pick");

            cpuScore.setText("Computer Score: 0");
            playerScore.setText("Player Score: 0");

            for (int i = 0; i < playerButtons.length; i++) {

                playerButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String idname = v.getResources().getResourceName(v.getId());

                        int choice = Integer.parseInt(String.valueOf(idname.charAt(idname.length() - 1)));

                        switch (choice) {
                            case 1:
                                vibe.vibrate(20);
                                setButtonsOff();

                                playerButtons[0].setColorFilter(Color.parseColor(PLAYERCOLORPICKED));
                                textView.setText("You picked Rock");
                                plPick = "rock";

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setComputersHand();
                                    }
                                }, 1200);
                                break;
                            case 2:
                                vibe.vibrate(20);
                                setButtonsOff();
                                playerButtons[1].setColorFilter(Color.parseColor(PLAYERCOLORPICKED));
                                textView.setText("You picked Scissor");
                                plPick = "scissor";

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setComputersHand();
                                    }
                                }, 1200);

                                break;

                            case 3:
                                vibe.vibrate(20);
                                setButtonsOff();
                                playerButtons[2].setColorFilter(Color.parseColor(PLAYERCOLORPICKED));
                                textView.setText("You picked Paper");
                                plPick = "paper";
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setComputersHand();
                                    }
                                }, 1200);

                                break;
                        }
                    }
                });
            }

    }




    public void initialize(){

        textView = (TextView) findViewById(R.id.TotalText);
        playerScore = (TextView) findViewById(R.id.PlayerScore);
        cpuScore = (TextView) findViewById(R.id.CPUScore);
        playNewRound = (Button) findViewById(R.id.playbutton);

        playerButtons[0] = (ImageButton) findViewById(R.id.p1);
        playerButtons[1] = (ImageButton) findViewById(R.id.p2);
        playerButtons[2] = (ImageButton) findViewById(R.id.p3);

        CPUButtons[0] = (ImageButton) findViewById(R.id.cp1);
        CPUButtons[1] = (ImageButton) findViewById(R.id.cp2);
        CPUButtons[2] = (ImageButton) findViewById(R.id.cp3);

    }


    /**
     * Simple method that will put the buttons off. User will not
     * have the opportunity to use the buttons. 
     */

    public void setButtonsOff(){

        for (int i = 0; i < playerButtons.length; i++){
            playerButtons[i].setEnabled(false);
        }

    }

    /**
     * Simple method that will put the buttons ON for clickOnListener.
     */
   public void setButtonsOn(){

       for (int i = 0; i < playerButtons.length; i++){
           playerButtons[i].setEnabled(true);
       }

   }

    /**
     * This method will randomly pick the Computers hand, and then present it on the application
     */
   public void setComputersHand(){

       Random rnd = new Random();
       int hand = rnd.nextInt(3)+1;

       switch (hand){
           case 1:
               cpuPick = "rock";
               CPUButtons[0].setColorFilter(Color.parseColor(CPUCOLORPICKED));
               break;

           case 2:
               cpuPick = "scissor";
               CPUButtons[1].setColorFilter(Color.parseColor(CPUCOLORPICKED));
               break;

           case 3:
               cpuPick = "paper";
               CPUButtons[2].setColorFilter(Color.parseColor(CPUCOLORPICKED));
               break;
       }
       textView.setText("Computer picked " + cpuPick);

       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               checkResult(cpuPick, plPick);
           }
       }, 1200);

    }

    /**
     * Simple method that will check the result after the cpu and the player have chosen their hands.
     * This method will also keep scores on who has won the most between the cpu and the player.
     *
     */
   public void checkResult(String cpuHand, String plHand){

       if (cpuHand.equals(plHand)){
           textView.setText("Draw");
       }
       else if (plHand.equals("rock") && cpuHand.equals("scissor") || plHand.equals("paper") &&
               cpuHand.equals("rock") || plHand.equals("scissor") && cpuHand.equals("paper")){
           textView.setText("YOU WON!!");
           pScore++;
           playerScore.setText("Player Score: " + pScore);

       }
       else{
           textView.setText("Computer Won!!");
           cScore++;
           cpuScore.setText("Computer Score: " + cScore);
       }

       chooseWinner();
   }

    /**
     * This method determines whether the Player or the CPU won the game.
     * The method will also start the round over again after the player has chosen this.
     */

   public void chooseWinner(){
       setButtonsOn();

       if (cScore >= 3){
           textView.setText("YOU LOSE THE ROUND! ");
           setButtonsOff();
           playNewRound.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   removeFilter(CPUButtons);
                   removeFilter(playerButtons);
                   textView.setText("Take your pick");
                   cpuScore.setText("Computer Score: 0");
                   playerScore.setText("Player Score: 0");
                   cScore = 0;
                   pScore = 0;
                   setButtonsOn();
               }
           });
       }
       else if (pScore >= 3){
           textView.setText("YOU WON THE ROUND!");
           setButtonsOff();
           playNewRound.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   removeFilter(CPUButtons);
                   removeFilter(playerButtons);
                   textView.setText("Take your pick");
                   cpuScore.setText("Computer Score: 0");
                   playerScore.setText("Player Score: 0");
                   cScore = 0;
                   pScore = 0;

                   setButtonsOn();

               }
           });

       }else {
            removeFilter(CPUButtons);
            removeFilter(playerButtons);
            setButtonsOn();
       }
   }

    /**
     * Removes the color filter of the Buttons after the round is done.
     */

   public void removeFilter(ImageButton[] buttons){

       for (int i = 0; i < buttons.length; i++){
           buttons[i].clearColorFilter();
       }

   }

}
