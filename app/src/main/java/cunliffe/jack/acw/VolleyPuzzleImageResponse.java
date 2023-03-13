package cunliffe.jack.acw;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

public interface VolleyPuzzleImageResponse {

    void onResponse(Bitmap pImage, Puzzle pItem, String pImageString);

    void onError(VolleyError pError, String pTag);
}
