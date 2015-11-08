package fleet.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import fleet.activity.PlayActivity;
import fleet.activity.SelectionActivity;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.TransferBuffer;
import fleet.gameLogic.players.HumanPlayer;

/**
 * Created by Radu on 10/20/2015.
 */
public class BuildView extends View {
    int screenW;
    int screenH;
    Fleet playerFleet;
    int destroyerCount = 0;
    int cruiserCount = 0;
    int battleShipCount = 0;
    Bitmap[] battleshipImgs = new Bitmap[4];
    Bitmap[] cruiserImgs = new Bitmap[4];
    Bitmap[] destroyerImgs = new Bitmap[4];
    Bitmap[] scaledImgs = new Bitmap[14];
    Bitmap carrierImg;
    PlayerGameBoard board = new PlayerGameBoard();
    int movingX;
    int movingY;
    int movingShipSlot = -1;
    boolean mutedMusic;
    Bitmap movingShipImg = null;


    SelectionActivity myContext;
    Point[] slotsOrigin = new Point[12];

    /**
     * BuildView constructor
     * @param myContext the context instance
     * @param playerFleet the player's selected fleet
     * @param musicMuted music muting option boolean
     */
    public BuildView(Context myContext, Fleet playerFleet, boolean musicMuted) {
        super(myContext);
        mutedMusic = musicMuted;
        this.playerFleet = playerFleet;
        this.myContext = (SelectionActivity) myContext;
        board.fleetPositions[4] = playerFleet.getCarrier();
    }

    /**
     * Called when the screen orientation is changed
     *
     * @param w Width of the screen
     * @param h Hight of the screen
     * @param oldw Previous width of the screen
     * @param oldh Previous height of the screen
     **/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        Point origin;
        int x;
        int y;
        int pointNum = 0;
        //Creating a 3x4 grid for card placement
        for (int row = 0; row < 4; row++) {
            y = (int) ((screenH * .025) + (row * (screenH * .25)));
            for (int column = 0; column < 3; column++) {
                x = (int) ((screenW * .025) + (column * (screenW * .33)));
                origin = new Point(x, y);
                slotsOrigin[pointNum] = origin;
                pointNum++;
            }
        }
        //scaling Images
        Ship[] battleships = playerFleet.getBattleships();
        Ship[] cruisers = playerFleet.getCruisers();
        Ship[] destroyers = playerFleet.getDestroyers();
        int shipXScale = screenW / 4;
        int shipYScale = screenH / 5;
        for (int i = 0; i < 4; i++) {
            battleshipImgs[i] = Bitmap.createScaledBitmap(battleships[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[battleships[i].getShipNum()] = battleshipImgs[i];
            cruiserImgs[i] = Bitmap.createScaledBitmap(cruisers[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[cruisers[i].getShipNum()] = cruiserImgs[i];
            destroyerImgs[i] = Bitmap.createScaledBitmap(destroyers[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[destroyers[i].getShipNum()] = destroyerImgs[i];
        }
        carrierImg = Bitmap.createScaledBitmap(playerFleet.getCarrier().faceUp, shipXScale, shipYScale, false);
        scaledImgs[1] = carrierImg;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * onDraw callback routine
     * @param canvas the canvas we will be drawing on
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Drawing stacks
        if (destroyerCount < 4) {
            Point destroyerStack = slotsOrigin[9];
            canvas.drawBitmap(destroyerImgs[destroyerCount], destroyerStack.x, destroyerStack.y, null);
        }
        if (cruiserCount < 4) {
            Point cruiserStack = slotsOrigin[10];
            canvas.drawBitmap(cruiserImgs[cruiserCount], cruiserStack.x, cruiserStack.y, null);
        }
        if (battleShipCount < 4) {
            Point battleShipStack = slotsOrigin[11];
            canvas.drawBitmap(battleshipImgs[battleShipCount], battleShipStack.x, battleShipStack.y, null);
        }
        //Drawing positions
        for (int i = 0; i < 9; i++) {
            if (board.fleetPositions[i] != null && i != movingShipSlot) {
                Ship ship = board.fleetPositions[i];
                Bitmap scaledImg = scaledImgs[ship.getShipNum()];
                canvas.drawBitmap(scaledImg, slotsOrigin[i].x, slotsOrigin[i].y, null);
            }
        }
        //Drawing moving Image
        if (movingShipImg != null) {
            canvas.drawBitmap(movingShipImg, movingX, movingY, null);
        }
        //canvas.drawBitmap(carrierImg, slotsOrigin[4].x, slotsOrigin[4].y, null);
    }

    /**
     * Execute game
     */
    public void startGame() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            /**
             * onClick callback routine
             * @param dialog dialog option
             * @param which case check
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent playIntent = new Intent(myContext, PlayActivity.class);
                        playIntent.putExtra("mutedMusic", mutedMusic);
                        // playIntent.putExtra("playerBoard",playerFleet);
                        TransferBuffer.board = board;
                        myContext.startActivity(playIntent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        // The actual dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage("Play with this setup?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    /**
     * onTouchEvent callback routine
     * @param event gesture event
     * @return event handled
     */
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        int slotScaleX = screenW / 4;
        int slotScaleY = screenH / 5;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Get a ship on the grid
                for (movingShipSlot = 0; movingShipSlot < 12; movingShipSlot++) {
                    Point slot = slotsOrigin[movingShipSlot];
                    if (x > slot.x
                            && x < slot.x + slotScaleX
                            && y > slot.y
                            && y < slot.y + slotScaleY) {
                        movingX = x;
                        movingY = y;
                        break;
                    }
                }
                // Get a ship from the grid if one exists
                if (movingShipSlot > 0 && movingShipSlot < 9) {
                    if (board.fleetPositions[movingShipSlot] != null) {
                        movingShipImg = scaledImgs[board.fleetPositions[movingShipSlot].getShipNum()];
                        invalidate();
                    }
                }
                // Get a ship from the destroyers pile
                if (movingShipSlot == 9 ) {
                    //checking if there are cards left on the stack
                    if (destroyerCount == 4){
                        movingShipSlot = -1;
                    }else {
                        movingShipImg = destroyerImgs[destroyerCount];
                        destroyerCount++;
                        invalidate();
                    }
                }
                // Get a ship from the cruisers pile
                if (movingShipSlot == 10) {
                    //checking if there are cards left on the stack
                    if (cruiserCount == 4){
                        movingShipSlot = -1;
                    }else {
                        movingShipImg = cruiserImgs[cruiserCount];
                        cruiserCount++;
                        invalidate();
                    }
                }
                // Get a ship from the battleships pile
                if (movingShipSlot == 11) {
                    //checking if there are cards left on the stack
                    if (battleShipCount == 4){
                        movingShipSlot = -1;
                    }else {
                        movingShipImg = battleshipImgs[battleShipCount];
                        battleShipCount++;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // Allow for moving of the cards
                movingX = x;
                movingY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (movingShipSlot >= 0) {
                    Ship temp = null;
                    boolean placed = false;
                    for (int i = 0; i < 12; i++) {
                        Point slot = slotsOrigin[i];
                        if (x > slot.x
                                && x < slot.x + slotScaleX
                                && y > slot.y
                                && y < slot.y + slotScaleY) {
                            //We are moving a ship already on the grid to another location
                            if (i < 9 && movingShipSlot < 9) {
                                if (board.fleetPositions[i] != null) {
                                    temp = board.fleetPositions[i];
                                }
                                board.fleetPositions[i] = board.fleetPositions[movingShipSlot];
                                board.fleetPositions[movingShipSlot] = temp;
                                placed = true;
                            }
                            //We are moving a destroyer off the stack onto the board
                            else if (movingShipSlot == 9 &&  board.fleetPositions[i] == null) {
                                board.fleetPositions[i] = playerFleet.getDestroyers()[destroyerCount - 1];
                                placed = true;
                            }
                            //We are moving a cruiser off the stack onto the board
                            else if (movingShipSlot == 10 &&  board.fleetPositions[i] == null) {
                                board.fleetPositions[i] = playerFleet.getCruisers()[cruiserCount - 1];
                                placed = true;
                            }
                            //We are moving a battleship off the stack onto the board
                            else if (movingShipSlot == 11 &&  board.fleetPositions[i] == null) {
                                board.fleetPositions[i] = playerFleet.getBattleships()[battleShipCount - 1];
                                placed = true;
                            }
                        }
                    }
                    if (!placed) {
                        //If the moving ship was coming off one of the stacks we want to put it back.
                        if (movingShipSlot == 9) {
                            destroyerCount--;
                        } else if (movingShipSlot == 10) {
                            cruiserCount--;
                        } else if (movingShipSlot == 11) {
                            battleShipCount--;
                        }
                    }
                    movingShipSlot = -1;
                    movingShipImg = null;
                    invalidate();
                    if (board.isFull()) {
                        startGame();
                        break;
                    }
                }
                return true;
        }
        return true;
    }
}

