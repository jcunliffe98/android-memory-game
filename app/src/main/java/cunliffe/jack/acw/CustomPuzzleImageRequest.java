package cunliffe.jack.acw;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class CustomPuzzleImageRequest implements Response.Listener<Bitmap>, Response.ErrorListener {

    private VolleyPuzzleImageResponse mVolleyPuzzleImageResponse;
    private Puzzle mPuzzle;
    private String mImageString;
    private ImageRequest mImageRequest;

    public ImageRequest getImageRequest() {
        return mImageRequest;
    }

    public CustomPuzzleImageRequest(String pUrl, Puzzle pPuzzle, VolleyPuzzleImageResponse pVolleyPuzzleImageResponse, String pImageString) {
        mVolleyPuzzleImageResponse = pVolleyPuzzleImageResponse;
        mPuzzle = pPuzzle;
        mImageString = pImageString;
        mImageRequest = new ImageRequest(pUrl, this, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, this);
    }

    @Override
    public void onErrorResponse(VolleyError pError) {
        mVolleyPuzzleImageResponse.onError(pError, mPuzzle.getFrontTileName());
    }

    @Override
    public void onResponse(Bitmap pResponse) {
        mVolleyPuzzleImageResponse.onResponse(pResponse, mPuzzle, mImageString);
    }
}
