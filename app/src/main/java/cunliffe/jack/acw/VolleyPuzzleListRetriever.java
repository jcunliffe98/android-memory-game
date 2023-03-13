package cunliffe.jack.acw;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class VolleyPuzzleListRetriever implements VolleyJSONObjectResponse, VolleyPuzzleImageResponse {
    private String mUrl;
    private MutableLiveData<ArrayList<Puzzle>> mPuzzlesData;
    private ArrayList<Puzzle> mPuzzles;
    private RequestQueue mQueue;
    private Context mContext;

    public VolleyPuzzleListRetriever(String pUrl, Context pContext) {
        mUrl = pUrl;
        mQueue = Volley.newRequestQueue(pContext);
        mContext = pContext;
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles() {
        mPuzzlesData = new MutableLiveData<>();
        CustomJSONObjectRequest request = new CustomJSONObjectRequest(Request.Method.GET, mUrl, null, "PuzzleListJSON", this);
        mQueue.add(request.getJsonObjectRequest());
        return mPuzzlesData;
    }

    @Override
    public void onResponse(JSONObject pObject, String pTag) {
        Log.i("PuzzleListRetriever", pTag);
        mPuzzles = parseJSONResponse(pObject);
        mPuzzlesData.setValue(mPuzzles);
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
        Collections.shuffle(puzzles);
        return puzzles;
    }

    private Puzzle parseJSONPuzzle(JSONObject pPuzzleIndexObject, String pPuzzlePictureSet, int pId) throws JSONException {
        String name = pPuzzleIndexObject.getString("name");
        String tileback = pPuzzleIndexObject.getString("TileBack");
        String frontTile = pPuzzlePictureSet;
        String frontTileUrl = "https://goparker.com/600096/moag/images/" + frontTile + ".png";
        String backTileUrl = "https://goparker.com/600096/moag/images/" + tileback + ".png";
        int id = pId;

        Puzzle puzzle = new Puzzle(name, tileback, frontTile, frontTileUrl, backTileUrl, id);

        boolean localImage = loadImageLocally(frontTile + ".png", puzzle);
        if(!localImage) {
            CustomPuzzleImageRequest frontTileImageRequest = new CustomPuzzleImageRequest(puzzle.getFrontTileUrl(), puzzle, this, "front");
            mQueue.add(frontTileImageRequest.getImageRequest());
        }

        localImage = loadImageLocally(tileback + ".png", puzzle);
        if(!localImage) {
            CustomPuzzleImageRequest backTileImageRequest = new CustomPuzzleImageRequest(puzzle.getBackTileUrl(), puzzle, this, "back");
            mQueue.add(backTileImageRequest.getImageRequest());
        }

        return puzzle;
    }

    public void saveImageLocally(Bitmap pBitmap, String pFilename) {
        ContextWrapper contextWrapper = new ContextWrapper(mContext);
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

    public boolean loadImageLocally(String pFilename, Puzzle pPuzzle) {
        boolean loaded = false;
        ContextWrapper contextWrapper = new ContextWrapper(mContext);
        File directory = contextWrapper.getDir("puzzleImages", Context.MODE_PRIVATE);
        File file = new File(directory, pFilename);
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                Puzzle puzzle = pPuzzle;
                if(puzzle.getFrontTile() == null){
                    puzzle.setFrontTile(bitmap);
                }
                else{
                    puzzle.setBackTile(bitmap);
                }

                fileInputStream.close();
                loaded = true;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }

    @Override
    public void onResponse(Bitmap pImage, Puzzle pPuzzle, String pImageString) {
        if(pImageString == "front") {
            pPuzzle.setFrontTile(pImage);
            saveImageLocally(pImage, pPuzzle.getFrontTileName() + ".png");
        }
        else{
            pPuzzle.setBackTile(pImage);
            saveImageLocally(pImage, pPuzzle.getTilebackName() + ".png");
        }
        mPuzzlesData.setValue(mPuzzles);
    }

    @Override
    public void onError(VolleyError pError, String pTag) {
        Log.e("VolleyListRetriever", pTag);
    }
}
