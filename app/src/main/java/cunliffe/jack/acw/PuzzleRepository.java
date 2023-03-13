package cunliffe.jack.acw;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class PuzzleRepository {
    private static PuzzleRepository sPuzzleRepository;
    private Context mApplicationContext;

    private MediatorLiveData<ArrayList<Puzzle>> mPuzzles;
    private MutableLiveData<Puzzle> mSelectedPuzzle;

    private Game mGame;

    private VolleyPuzzleListRetriever mRemotePuzzleList;

    private PuzzleRepository(Context pApplicationContext) {
        this.mApplicationContext = pApplicationContext;
        mPuzzles = new MediatorLiveData<>();
        mRemotePuzzleList = new VolleyPuzzleListRetriever("https://goparker.com/600096/moag/puzzles/moag.json", pApplicationContext);
        mGame = new Game();
    }

    public static PuzzleRepository getInstance(Context pApplicationContext) {
        //Makes an instance of repository if it doesn't already exist
        if (sPuzzleRepository == null) {
            sPuzzleRepository = new PuzzleRepository(pApplicationContext);
        }
        return sPuzzleRepository;
    }

    public int getPreviousId() {
        return mGame.getPreviousId();
    }

    public void setPreviousId(int pPreviousId) {
        mGame.setPreviousId(pPreviousId);
    }

    public int getPreviousIndex() {
        return mGame.getPreviousIndex();
    }

    public void setPreviousIndex(int pPreviousIndex) {
        mGame.setPreviousIndex(pPreviousIndex);
    }

    public int getCurrentIndex() {
        return mGame.getCurrentIndex();
    }

    public void setCurrentIndex(int pCurrentIndex) {
        mGame.setCurrentIndex(pCurrentIndex);
    }

    public int getCurrentId() {
        return mGame.getCurrentId();
    }

    public void setCurrentId(int pCurrentId) {
        mGame.setCurrentId(pCurrentId);
    }

    public int getFlipCount() {
        return mGame.getFlipCount();
    }

    public void setFlipCount(int pFlipCount) {
        mGame.setFlipCount(pFlipCount);
    }

    public int getScore() {
        return mGame.getScore();
    }

    public void setScore(int pScore) {
        mGame.setScore(pScore);
    }

    public int getTilesFlipped() {
        return mGame.getTilesFlipped();
    }

    public void setTilesFlipped(int pTilesFlipped) {
        mGame.setTilesFlipped(pTilesFlipped);
    }

    public int getSequenceCounter() {
        return mGame.getSequenceCounter();
    }

    public void setSequenceCounter(int pSequenceCounter) {
        mGame.setSequenceCounter(pSequenceCounter);
    }

    public int getHighestSequenceCounter() {
        return mGame.getHighestSequenceCounter();
    }

    public void setHighestSequenceCounter(int pHighestSequenceCounter) {
        mGame.setHighestSequenceCounter(pHighestSequenceCounter);
    }

    public boolean getSequence() {
        return mGame.getSequence();
    }

    public void setSequence(boolean pSequence) {
        mGame.setSequence(pSequence);
    }

    public ImageView getPreviousFrontTile() {
        return mGame.getPreviousFrontTile();
    }

    public void setPreviousFrontTile(ImageView pPreviousFrontTile) {
        mGame.setPreviousFrontTile(pPreviousFrontTile);
    }

    public ImageView getPreviousBackTile() {
        return mGame.getPreviousBackTile();
    }

    public void setPreviousBackTile(ImageView pPreviousBackTile) {
        mGame.setPreviousBackTile(pPreviousBackTile);
    }

    public ImageView getCurrentFrontTile() {
        return mGame.getCurrentFrontTile();
    }

    public void setCurrentFrontTile(ImageView pCurrentFrontTile) {
        mGame.setCurrentFrontTile(pCurrentFrontTile);
    }

    public ImageView getCurrentBackTile() {
        return mGame.getCurrentBackTile();
    }

    public void setCurrentBackTile(ImageView pCurrentBackTile) {
        mGame.setCurrentBackTile(pCurrentBackTile);
    }

    public LiveData<ArrayList<Puzzle>> loadPuzzleIndexFromJSON() {
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        String url = "https://goparker.com/600096/moag/puzzles/moag.json";
        final MutableLiveData<ArrayList<Puzzle>> mutablePuzzle = new MutableLiveData<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        saveIndexLocally(response, "index.json");
                        ArrayList<Puzzle> puzzle = parseJSONResponse(response);
                        mutablePuzzle.setValue(puzzle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse = "That didn't work!";
                    }
                }
        );
        queue.add(jsonObjectRequest);
        return mutablePuzzle;
    }

    public void saveIndexLocally(JSONObject pIndexObject, String pFilename) {
        ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(contextWrapper.openFileOutput(pFilename, Context.MODE_PRIVATE));
            outputStreamWriter.write(pIndexObject.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private LiveData<ArrayList<Puzzle>> loadIndexLocally(String pFilename) {
        JSONObject indexObject = null;
        MutableLiveData<ArrayList<Puzzle>> mutablePuzzles = new MutableLiveData<>();
        try{
            InputStream inputStream = mApplicationContext.openFileInput(pFilename);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                String builtString = stringBuilder.toString();
                indexObject = new JSONObject(builtString);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("JSONLoading", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("JSONLoading", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            Log.e("JSONLoading", "json error: " + e.toString());
        }
        if(indexObject != null) {
            ArrayList<Puzzle> puzzles = parseJSONResponse(indexObject);
            mutablePuzzles.setValue(puzzles);
        }
        return mutablePuzzles;
    }

    private ArrayList<Puzzle> parseJSONResponse(JSONObject pResponse) {
        ArrayList<Puzzle> puzzles = new ArrayList();
        try {
            JSONArray puzzleArray = pResponse.getJSONArray("PictureSet");
            for (int i = 0; i < puzzleArray.length(); i++){
                String puzzlePictureSet = puzzleArray.getString(i);
                Puzzle puzzle = parseJSONPuzzle(pResponse, puzzlePictureSet, i);
                puzzles.add(puzzle);
                Puzzle puzzle2 = parseJSONPuzzle(pResponse, puzzlePictureSet, i);
                puzzles.add(puzzle2);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return puzzles;
    }

    private Puzzle parseJSONPuzzle(JSONObject pPuzzleIndexObject, String pPuzzlePictureSet, int pIndex) throws JSONException {
        String name = pPuzzleIndexObject.getString("name");
        String tileback = pPuzzleIndexObject.getString("TileBack");
        String frontTileUrl = "https://goparker.com/600096/moag/images/" + pPuzzlePictureSet + ".png";
        String backTileUrl = "https://goparker.com/600096/moag/images/" + tileback + ".png";

        return new Puzzle(name, tileback, pPuzzlePictureSet, frontTileUrl, backTileUrl, pIndex);
    }

    public void loadImage(String pUrl, MutableLiveData<Puzzle> pPuzzleData) {
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        final MutableLiveData<Puzzle> mutablePuzzle = pPuzzleData;

        ImageRequest imageRequest = new ImageRequest(pUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap pBitmap) {
                Puzzle puzzle = mutablePuzzle.getValue();
                puzzle.setFrontTile(pBitmap);
                saveImageLocally(pBitmap, Uri.parse(pUrl).getLastPathSegment());
                mutablePuzzle.setValue(puzzle);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError pError) {
                String errorResponse = "That didn't work!";
            }
        });
        queue.add(imageRequest);
    }

    public void saveImageLocally(Bitmap pBitmap, String pFilename) {
        ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
        File directory = contextWrapper.getDir("puzzleImages", Context.MODE_PRIVATE);
        File file = new File(directory, pFilename);
        if(!file.exists()) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                pBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loadImageLocally(String pFilename, MutableLiveData<Puzzle> pPuzzleData) {
        boolean loaded = false;
        ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
        File directory = contextWrapper.getDir("puzzleImages", Context.MODE_PRIVATE);
        File file = new File(directory, pFilename);
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                Puzzle puzzle = pPuzzleData.getValue();
                if(puzzle.getFrontTile() == null){
                    puzzle.setFrontTile(bitmap);
                }
                else{
                    puzzle.setBackTile(bitmap);
                }
                pPuzzleData.setValue(puzzle);

                fileInputStream.close();
                loaded = true;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles() {
        LiveData<ArrayList<Puzzle>> remoteData = mRemotePuzzleList.getPuzzles();
        LiveData<ArrayList<Puzzle>> localData = loadIndexLocally("index.json");
        mPuzzles.addSource(remoteData, value-> mPuzzles.setValue(value));
        mPuzzles.addSource(localData, value-> mPuzzles.setValue(value));
        return mPuzzles;
    }

    public LiveData<Puzzle> getPuzzle(final int pPuzzleIndex) {
        LiveData<Puzzle> transformedPuzzle = Transformations.switchMap(mPuzzles, puzzles -> {
            if(mSelectedPuzzle == null){
                mSelectedPuzzle = new MutableLiveData<>();
            }

            Puzzle puzzle = puzzles.get(pPuzzleIndex);
            mSelectedPuzzle.setValue(puzzle);
            if(!loadImageLocally(Uri.parse(puzzle.getFrontTileUrl()).getLastPathSegment(), mSelectedPuzzle)){
                loadImage(puzzle.getFrontTileUrl(), mSelectedPuzzle);
            }

            return mSelectedPuzzle;
        });
        return  transformedPuzzle;
    }
}
