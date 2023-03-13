package cunliffe.jack.acw;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Puzzle {
    MutableLiveData<Puzzle> mPuzzleData;
    private String mName;
    private String mFrontTileName;
    private String mFrontTileUrl;
    private String mBackTileUrl;
    private String mTilebackName;
    private int mId;
    private Bitmap mFrontTile;
    private Bitmap mBackTile;
    boolean mIsFlipped;

    public Puzzle(String pName, String pTilebackName, String pFrontTileName, String pFrontTileUrl, String pBackTileUrl, int pId) {
        mPuzzleData = new MutableLiveData<>();
        setName(pName);
        setTilebackName(pTilebackName);
        setFrontTileName(pFrontTileName);
        setFrontTileUrl(pFrontTileUrl);
        setBackTileUrl(pBackTileUrl);
        setIsFlipped(false);
        setId(pId);
        mPuzzleData.setValue(this);
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public Boolean getIsFlipped() {
        return mIsFlipped;
    }

    public void setIsFlipped(Boolean pIsFlipped) {
        mIsFlipped = pIsFlipped;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getFrontTileName() {
        return mFrontTileName;
    }

    public void setFrontTileName(String pFrontTileName) {
        mFrontTileName = pFrontTileName;
    }

    public String getTilebackName() {
        return mTilebackName;
    }

    public void setTilebackName(String pTilebackName) {
        mTilebackName = pTilebackName;
    }

    public String getFrontTileUrl() {
        return mFrontTileUrl;
    }

    public void setFrontTileUrl(String pFrontTileUrl) {
        mFrontTileUrl = pFrontTileUrl;
    }

    public Bitmap getFrontTile() {
        return mFrontTile;
    }

    public void setFrontTile(Bitmap pFrontTile) {
        mFrontTile = pFrontTile;
    }

    public void setBackTileUrl(String pBackTileUrl) {
        mBackTileUrl = pBackTileUrl;
    }

    public String getBackTileUrl(){
        return mBackTileUrl;
    }

    public Bitmap getBackTile() {
        return mBackTile;
    }

    public void setBackTile(Bitmap pBackTile) {
        mBackTile = pBackTile;
    }

    public void flip(){
        mIsFlipped = !mIsFlipped;
    }

    public LiveData<Puzzle> getPuzzleData() { return  mPuzzleData; }
}
