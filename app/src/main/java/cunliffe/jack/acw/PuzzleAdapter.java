package cunliffe.jack.acw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PuzzleAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Puzzle> mPuzzleList;

    public PuzzleAdapter(@NonNull Context pContext, ArrayList<Puzzle> pList) {
        super(pContext, 0, pList);
        mContext = pContext;
        mPuzzleList = pList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listPuzzle = convertView;
        if(listPuzzle == null) {
            listPuzzle = LayoutInflater.from(mContext).inflate(R.layout.fragment_puzzle, parent, false);
        }
        Puzzle currentPuzzle = mPuzzleList.get(position);

        ImageView frontTile = (ImageView) listPuzzle.findViewById(R.id.frontTile);
        frontTile.setImageBitmap(currentPuzzle.getFrontTile());
        frontTile.setVisibility(View.INVISIBLE);

        ImageView backTile = (ImageView) listPuzzle.findViewById(R.id.backTile);
        backTile.setImageBitmap(currentPuzzle.getBackTile());
        backTile.setVisibility(View.VISIBLE);

        return listPuzzle;
    }
}
