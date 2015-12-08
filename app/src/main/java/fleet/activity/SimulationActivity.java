package fleet.activity;

import fleet.R;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.Game;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.players.AbstractPlayer;
import fleet.gameLogic.players.AdvancedCPU;
import fleet.gameLogic.players.ComputerPlayer;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * SimulationActivity class
 *
 * @Authors: Conner Ferguson and Anthony Cali
 */
public class SimulationActivity extends Activity {
    private final String FLEET_DIR = "fleets";
    private AssetManager assetManager;
    private ArrayList<Fleet> fleets = new ArrayList<Fleet>();
    private String[] fleetList;
    private ArrayList<AbstractPlayer> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity);
    }

    public void runSimulations(View parent) {
        players = new ArrayList<AbstractPlayer>();
        if(fleets.size() == 0) {
            loadFleets();
        }

        ArrayList<PlayerGameBoard> aiBoards = new ArrayList<PlayerGameBoard>();

        for(Fleet fleet : fleets) {
            PlayerGameBoard aiBoard = new PlayerGameBoard();
            aiBoard.fleetPositions[0] = fleet.getCarrier();
            aiBoard.fleetPositions[1] = fleet.getBattleships()[0];
            aiBoard.fleetPositions[2] = fleet.getBattleships()[1];
            aiBoard.fleetPositions[3] = fleet.getBattleships()[2];
            aiBoard.fleetPositions[4] = fleet.getCruisers()[0];
            aiBoard.fleetPositions[5] = fleet.getCruisers()[1];
            aiBoard.fleetPositions[6] = fleet.getDestroyers()[0];
            aiBoard.fleetPositions[7] = fleet.getDestroyers()[1];
            aiBoard.fleetPositions[8] = fleet.getDestroyers()[2];
            aiBoards.add(aiBoard);
        }

        Spinner numRuns = (Spinner) findViewById(R.id.select_runs);
        int runs = Integer.decode(numRuns.getSelectedItem().toString());

        Spinner ai1 = (Spinner) findViewById(R.id.select_ai_1);
        String ai1Name = (String) ai1.getSelectedItem();

        Spinner ai2 = (Spinner) findViewById(R.id.select_ai_2);
        String ai2Name = (String) ai2.getSelectedItem();

        if(ai1Name.equals("Basic")) {
            players.add(new ComputerPlayer(aiBoards.get(0),0));
        } else {
            players.add(new AdvancedCPU(aiBoards.get(0),0));
        }

        if(ai2Name.equals("Basic")) {
            players.add(new ComputerPlayer(aiBoards.get(1),1));
        } else {
            players.add(new AdvancedCPU(aiBoards.get(1),1));
        }

        Game aiGame = new Game(players, runs);

        TextView console = (TextView) findViewById(R.id.console);
        console.append(aiGame.startGame());
    }

    public void loadFleets() {
        assetManager = getAssets();
        // Get all the fleets
        try {
            fleetList = (assetManager.list(FLEET_DIR));
            for(String fleet : fleetList) {
                if(fleets.size() <= 1) {
                    String fleetPath = (FLEET_DIR + "/" + fleet);
                    InputStream kingImgStream = assetManager.open(fleetPath + "/King.png");
                    AssetFileDescriptor fleetSoundFile = assetManager.openFd(fleetPath + "/MainAttack.ogg");
                    Bitmap kingImg = BitmapFactory.decodeStream(kingImgStream);
                    Fleet newFleet = new Fleet(kingImg, fleetSoundFile, fleetPath);
                    for (String ship : assetManager.list(fleetPath)) {
                        if (!(ship.equals("King.png") || ship.equals("MainAttack.ogg"))) {
                            InputStream shipImgStream = assetManager.open(fleetPath + "/" + ship);
                            Bitmap shipImg = BitmapFactory.decodeStream(shipImgStream);
                            newFleet.populateFleet(ship, shipImg);
                        }
                    }
                    fleets.add(newFleet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
