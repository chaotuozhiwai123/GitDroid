package end.feicui.gitdroid.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */

@SuppressWarnings("unused")
public class GankResult {

    private List<String> category;

    private boolean error;

    private Results results;

    public List<String> getCategory() {
        return category;
    }

    public Results getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }

    public static class Results {

        @SerializedName("Android")
        private List<GankItem> androidItems;

        public List<GankItem> getAndroidItems() {
            return androidItems;
        }
    }

}
