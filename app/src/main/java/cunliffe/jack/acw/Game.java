package cunliffe.jack.acw;

import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

public class Game {
    private int mPreviousId;
    private int mCurrentId;
    private int mCurrentIndex;
    private int mScore;
    private int mTilesFlipped;
    private int mFlipCount;
    private int mPreviousIndex;
    private int mSequenceCounter;
    private int mHighestSequenceCounter;
    private boolean mSequence;
    private ImageView mPreviousFrontTile;
    private ImageView mPreviousBackTile;
    private ImageView mCurrentFrontTile;
    private ImageView mCurrentBackTile;

    public Game() {
        mScore = 0;
        mFlipCount = 0;
        mPreviousId = -1;
        mCurrentId = -1;
        mPreviousIndex = -1;
        mTilesFlipped = 0;
        mSequence = true;
        mSequenceCounter = 0;
    }

    public int getPreviousId() {
        return mPreviousId;
    }

    public void setPreviousId(int pPreviousId) {
        mPreviousId = pPreviousId;
    }

    public int getSequenceCounter() {
        return mSequenceCounter;
    }

    public void setSequenceCounter(int pSequenceCounter) {
        mSequenceCounter = pSequenceCounter;
    }

    public int getHighestSequenceCounter() {
        return mHighestSequenceCounter;
    }

    public void setHighestSequenceCounter(int pHighestSequenceCounter) {
        mHighestSequenceCounter = pHighestSequenceCounter;
    }

    public boolean getSequence() {
        return mSequence;
    }

    public void setSequence(boolean pSequence) {
        mSequence = pSequence;
    }

    public int getPreviousIndex() {
        return mPreviousIndex;
    }

    public void setPreviousIndex(int pPreviousIndex) {
        mPreviousIndex = pPreviousIndex;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentIndex(int pCurrentIndex) {
        mCurrentIndex = pCurrentIndex;
    }

    public int getCurrentId() {
        return mCurrentId;
    }

    public void setCurrentId(int pCurrentId) {
        mCurrentId = pCurrentId;
    }

    public int getFlipCount() {
        return mFlipCount;
    }

    public void setFlipCount(int pFlipCount) {
        mFlipCount = pFlipCount;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int pScore) {
        mScore = pScore;
    }

    public int getTilesFlipped() {
        return mTilesFlipped;
    }

    public void setTilesFlipped(int pTilesFlipped) {
        mTilesFlipped = pTilesFlipped;
    }

    public ImageView getCurrentFrontTile() {
        return mCurrentFrontTile;
    }

    public void setCurrentFrontTile(ImageView pCurrentFrontTile) {
        mCurrentFrontTile = pCurrentFrontTile;
    }

    public ImageView getCurrentBackTile() {
        return mCurrentBackTile;
    }

    public void setCurrentBackTile(ImageView pCurrentBackTile) {
        mCurrentBackTile = pCurrentBackTile;
    }

    public ImageView getPreviousFrontTile() {
        return mPreviousFrontTile;
    }

    public void setPreviousFrontTile(ImageView pPreviousFrontTile) {
        mPreviousFrontTile = pPreviousFrontTile;
    }

    public ImageView getPreviousBackTile() {
        return mPreviousBackTile;
    }

    public void setPreviousBackTile(ImageView pPreviousBackTile) {
        mPreviousBackTile = pPreviousBackTile;
    }
}
