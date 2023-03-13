package cunliffe.jack.acw;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PuzzleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PuzzleFragment extends Fragment implements GestureDetector.OnGestureListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INDEX = "index";

    private int mIndex;

    private MyViewModel mViewModel;
    private View mInflatedView;
    private Animator mFlipInAnimator;
    private Animator mFlipOutAnimator;
    private int mAnimationCompleteCount;
    private int mOnChanged = 1;
    private GestureDetectorCompat mDetector;

    public int getShownIndex() {
        return mIndex;
    }

    public PuzzleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1 is the index of the selected tile.
     * @return A new instance of fragment PuzzleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PuzzleFragment newInstance(int index) {
        PuzzleFragment fragment = new PuzzleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_INDEX);
        }
        mViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        mViewModel.selectPuzzle(mIndex);

        mFlipInAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_in);
        mFlipOutAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_out);
        mDetector = new GestureDetectorCompat(getContext(),this);
        mViewModel.setFlipCount(0);
        addAnimationListeners();

        mViewModel.setScore(0);
        mViewModel.setTilesFlipped(0);
        mViewModel.setSequenceCounter(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mInflatedView = inflater.inflate(R.layout.fragment_puzzle, container, false);

        FrameLayout tileFrame = mInflatedView.findViewById(R.id.container);
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // pass the events to the gesture detector
                // a return value of true means the detector is handling it
                // false means the detector didn't recognise the event
                return mDetector.onTouchEvent(event);
            }
        };
        tileFrame.setOnTouchListener(touchListener);
        final Observer<List<Puzzle>> puzzlesObserver = new Observer<List<Puzzle>>() {
            @Override
            public void onChanged(List<Puzzle> puzzles) {
                PuzzleAdapter puzzleAdapter = new PuzzleAdapter(getActivity(), mViewModel.getPuzzles().getValue());
                puzzleAdapter.getView(mIndex, mInflatedView, container);
            }
        };
        mViewModel.getPuzzles().observe(getViewLifecycleOwner(), puzzlesObserver);
        return mInflatedView;
    };

    private void animationComplete() {
        if(mAnimationCompleteCount == 2) {
            mAnimationCompleteCount = 0;
            Log.i(this.getClass().getSimpleName() + "Animation", "Animation Complete: " + mIndex);
            //mViewModel.flipTile();
        }
    }

    private void addAnimationListeners() {
        mFlipInAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationCompleteCount++;
                animationComplete();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mFlipOutAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationCompleteCount++;
                animationComplete();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e) {
        final Observer<List<Puzzle>> puzzleObserver = new Observer<List<Puzzle>>() {
            @Override
            public void onChanged(List<Puzzle> puzzles) {

                int previousIndex = mViewModel.getPreviousIndex();
                if(previousIndex == mIndex && puzzles.get(mIndex).getIsFlipped())
                {
                    return;
                }
                else if(puzzles.get(mIndex).getIsFlipped())
                {
                    return;
                }

                int tilesFlipped = mViewModel.getTilesFlipped();
                tilesFlipped++;
                mViewModel.setTilesFlipped(tilesFlipped);

                int flipCount = mViewModel.getFlipCount();
                flipCount++;
                mViewModel.setFlipCount(flipCount);

                mViewModel.setCurrentId(puzzles.get(mIndex).getId());
                int prevId = mViewModel.getPreviousId();

                ImageView frontTile = mInflatedView.findViewById(R.id.frontTile);
                ImageView backTile = mInflatedView.findViewById(R.id.backTile);

                backTile.setVisibility(View.VISIBLE);
                frontTile.setVisibility(View.VISIBLE);

                int curId = puzzles.get(mIndex).getId();

                if(prevId == curId && flipCount == 2){
                    int score = mViewModel.getScore();
                    boolean sequence = mViewModel.getSequence();
                    mViewModel.setSequence(true);
                    int sequenceCounter = mViewModel.getSequenceCounter();
                    if(sequence) {
                        sequenceCounter++;
                        if(sequenceCounter > mViewModel.getSequenceCounter()){
                            mViewModel.setHighestSequenceCounter(sequenceCounter);
                        }
                    }
                    else{
                        sequenceCounter = 1;
                    }
                    mViewModel.setSequenceCounter(sequenceCounter);
                    score++;
                    mViewModel.setScore(score);
                    if(!puzzles.get(mIndex).getIsFlipped()) {
                        mFlipInAnimator.setTarget(frontTile);
                        mFlipOutAnimator.setTarget(backTile);
                    }

                    mFlipInAnimator.start();
                    mFlipOutAnimator.start();
                    flipCount = 0;
                    mViewModel.setFlipCount(flipCount);
                    puzzles.get(mIndex).flip();

                    Animation mRotationAnimation;
                    Animation mRotationAnimation2;
                    mRotationAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation);
                    mRotationAnimation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation);
                    frontTile.startAnimation(mRotationAnimation);
                    mViewModel.getCurrentFrontTile().startAnimation(mRotationAnimation2);
                    if(score == 20){
                        ConstraintLayout endScreenLayout = getActivity().findViewById(R.id.endScreen);
                        endScreenLayout.setVisibility(View.VISIBLE);
                        endScreenLayout.bringToFront();

                        TextView tilesFlippedEndText = getActivity().findViewById(R.id.tilesFlippedEndText);
                        String tileFlippedEndString = getString(R.string.tilesFlipped) + Integer.toString(mViewModel.getTilesFlipped());
                        tilesFlippedEndText.setText(tileFlippedEndString);

                        TextView timerEndText = getActivity().findViewById(R.id.timerEndText);
                        TextView timerText = getActivity().findViewById(R.id.timerTextView);
                        String timer = (String) timerText.getText();
                        timerEndText.setText(timer);

                        TextView sequenceCounterEndText = getActivity().findViewById(R.id.sequenceCounterEndText);
                        String sequenceCounterEndString = getString(R.string.sequenceCounter) + Integer.toString(mViewModel.getHighestSequenceCounter());
                        sequenceCounterEndText.setText(sequenceCounterEndString);
                    }
                }
                else if(flipCount == 1){
                    mViewModel.setCurrentIndex(mIndex);
                    mViewModel.setCurrentFrontTile(frontTile);
                    mViewModel.setCurrentBackTile(backTile);
                    mFlipInAnimator.setTarget(frontTile);
                    mFlipOutAnimator.setTarget(backTile);
                    mFlipInAnimator.start();
                    mFlipOutAnimator.start();
                    puzzles.get(mIndex).flip();
                }
                else if(flipCount == 2){
                    mViewModel.setPreviousFrontTile(mViewModel.getCurrentFrontTile());
                    mViewModel.setPreviousBackTile(mViewModel.getCurrentBackTile());
                    mViewModel.setPreviousIndex(mViewModel.getCurrentIndex());
                    mViewModel.setCurrentFrontTile(frontTile);
                    mViewModel.setCurrentBackTile(backTile);
                    mViewModel.setCurrentIndex(mIndex);
                    mFlipInAnimator.setTarget(frontTile);
                    mFlipOutAnimator.setTarget(backTile);
                    mViewModel.setSequence(false);
                    mViewModel.setSequenceCounter(0);

                    puzzles.get(mIndex).flip();
                    mFlipInAnimator.start();
                    mFlipOutAnimator.start();
                }
                else if(flipCount == 3){
                    Animator mFlipInAnimator2;
                    Animator mFlipOutAnimator2;
                    Animator mFlipInAnimator3;
                    Animator mFlipOutAnimator3;
                    mFlipInAnimator2 = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_in);
                    mFlipOutAnimator2 = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_out);
                    mFlipInAnimator3 = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_in);
                    mFlipOutAnimator3 = AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_vertically_top_out);
                    mViewModel.setSequence(false);
                    mViewModel.setSequenceCounter(0);

                    mFlipInAnimator.setTarget(mViewModel.getPreviousBackTile());
                    mFlipOutAnimator.setTarget(mViewModel.getPreviousFrontTile());
                    mFlipInAnimator.start();
                    mFlipOutAnimator.start();
                    puzzles.get(mViewModel.getPreviousIndex()).flip();

                    mFlipInAnimator2.setTarget(mViewModel.getCurrentBackTile());
                    mFlipOutAnimator2.setTarget(mViewModel.getCurrentFrontTile());
                    mFlipInAnimator2.start();
                    mFlipOutAnimator2.start();
                    puzzles.get(mViewModel.getCurrentIndex()).flip();

                    mFlipInAnimator3.setTarget(frontTile);
                    mFlipOutAnimator3.setTarget(backTile);
                    mFlipInAnimator3.start();
                    mFlipOutAnimator3.start();
                    puzzles.get(mIndex).flip();

                    mViewModel.setPreviousFrontTile(mViewModel.getCurrentFrontTile());
                    mViewModel.setPreviousBackTile(mViewModel.getCurrentBackTile());
                    mViewModel.setPreviousIndex(mViewModel.getCurrentIndex());

                    mViewModel.setCurrentFrontTile(frontTile);
                    mViewModel.setCurrentBackTile(backTile);
                    mViewModel.setCurrentIndex(mIndex);


                    flipCount = 1;
                    mViewModel.setFlipCount(flipCount);
                }

                prevId = curId;
                mViewModel.setPreviousId(prevId);

                TextView tilesFlippedText = getActivity().findViewById(R.id.tilesFlippedText);
                String tileFlippedString = getString(R.string.tilesFlipped) + Integer.toString(mViewModel.getTilesFlipped());
                tilesFlippedText.setText(tileFlippedString);

                TextView sequenceCounterText = getActivity().findViewById(R.id.sequenceCounterText);
                String sequenceCounterString = getString(R.string.sequenceCounter) + Integer.toString(mViewModel.getSequenceCounter());
                sequenceCounterText.setText(sequenceCounterString);
            }
        };

        mViewModel.getPuzzles().observe(getViewLifecycleOwner(), puzzleObserver);
        mViewModel.getPuzzles().removeObserver(puzzleObserver);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}