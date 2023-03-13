package cunliffe.jack.acw;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private LiveData<ArrayList<Puzzle>> mPuzzles;
    private LiveData<Puzzle> mSelectedPuzzle;
    private PuzzleRepository mPuzzleRepository;
    private int mNoOfLoops;
    private int mSelectedIndex;

    public MyViewModel (@NonNull Application pApplication) {
        super(pApplication);
        mPuzzleRepository = PuzzleRepository.getInstance(getApplication());
        getPuzzles();
        mNoOfLoops = 0;
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles() {
        if(mPuzzles == null) {
            mPuzzles = mPuzzleRepository.getPuzzles();
        }
        return mPuzzles;
    }

    public LiveData<Puzzle> getPuzzle(int pPuzzleIndex) {
        return mPuzzleRepository.getPuzzle(pPuzzleIndex);
    }

    public void selectPuzzle(int pIndex) {
        if(pIndex != mSelectedIndex || mSelectedPuzzle == null) {
            mSelectedIndex = pIndex;
            mSelectedPuzzle = getPuzzle(mSelectedIndex);
        }
    }

    public void flipTile() {
        mSelectedPuzzle.getValue();
    }

    public int getPreviousId() {
        return mPuzzleRepository.getPreviousId();
    }

    public void setPreviousId(int pPreviousId) {
        mPuzzleRepository.setPreviousId(pPreviousId);
    }

    public int getPreviousIndex() {
        return mPuzzleRepository.getPreviousIndex();
    }

    public void setPreviousIndex(int pPreviousIndex) {
        mPuzzleRepository.setPreviousIndex(pPreviousIndex);
    }

    public int getNoOfLoops() {
        return mNoOfLoops;
    }

    public void setNoOfLoops(int pNoOfLoops) {
        mNoOfLoops = pNoOfLoops;
    }

    public int getCurrentIndex() {
        return mPuzzleRepository.getCurrentIndex();
    }

    public void setCurrentIndex(int pCurrentIndex) {
        mPuzzleRepository.setCurrentIndex(pCurrentIndex);
    }

    public int getSequenceCounter() {
        return mPuzzleRepository.getSequenceCounter();
    }

    public void setSequenceCounter(int pSequenceCounter) {
        mPuzzleRepository.setSequenceCounter(pSequenceCounter);
    }

    public int getHighestSequenceCounter() {
        return mPuzzleRepository.getHighestSequenceCounter();
    }

    public void setHighestSequenceCounter(int pHighestSequenceCounter) {
        mPuzzleRepository.setHighestSequenceCounter(pHighestSequenceCounter);
    }

    public boolean getSequence() {
        return mPuzzleRepository.getSequence();
    }

    public void setSequence(boolean pSequence) {
        mPuzzleRepository.setSequence(pSequence);
    }

    public int getFlipCount() {
        return mPuzzleRepository.getFlipCount();
    }

    public void setFlipCount(int pFlipCount) {
        mPuzzleRepository.setFlipCount(pFlipCount);
    }

    public int getCurrentId() {
        return mPuzzleRepository.getCurrentId();
    }

    public void setCurrentId(int pCurrentId) {
        mPuzzleRepository.setCurrentId(pCurrentId);
    }

    public int getScore() {
        return mPuzzleRepository.getScore();
    }

    public void setScore(int pScore) {
        mPuzzleRepository.setScore(pScore);
    }

    public int getTilesFlipped() {
        return mPuzzleRepository.getTilesFlipped();
    }

    public void setTilesFlipped(int pTilesFlipped) {
        mPuzzleRepository.setTilesFlipped(pTilesFlipped);
    }

    public ImageView getPreviousFrontTile() {
        return mPuzzleRepository.getPreviousFrontTile();
    }

    public void setPreviousFrontTile(ImageView pPreviousFrontTile) {
        mPuzzleRepository.setPreviousFrontTile(pPreviousFrontTile);
    }

    public ImageView getPreviousBackTile() {
        return mPuzzleRepository.getPreviousBackTile();
    }

    public void setPreviousBackTile(ImageView pPreviousBackTile) {
        mPuzzleRepository.setPreviousBackTile(pPreviousBackTile);
    }

    public ImageView getCurrentFrontTile() {
        return mPuzzleRepository.getCurrentFrontTile();
    }

    public void setCurrentFrontTile(ImageView pCurrentFrontTile) {
        mPuzzleRepository.setCurrentFrontTile(pCurrentFrontTile);
    }

    public ImageView getCurrentBackTile() {
        return mPuzzleRepository.getCurrentBackTile();
    }

    public void setCurrentBackTile(ImageView pCurrentBackTile) {
        mPuzzleRepository.setCurrentBackTile(pCurrentBackTile);
    }

    public LiveData<Puzzle> getSelectedPuzzle() {
        return mSelectedPuzzle;
    }


}
