package end.feicui.gitdroid.github.hotrepo.repolist;

import java.util.List;

import end.feicui.gitdroid.github.hotrepo.Language;
import end.feicui.gitdroid.github.hotrepo.repolist.modle.Repo;
import end.feicui.gitdroid.github.hotrepo.repolist.modle.RepoResult;
import end.feicui.gitdroid.github.hotrepo.repolist.view.RepoListView;
import end.feicui.gitdroid.github.network.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/28.
 */
public class RepoListPresenter {
    // 视图的接口
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;

    private Call<RepoResult> repoCall;



    public RepoListPresenter(RepoListView repoListView, Language language) {
        this.repoListView = repoListView;
        this.language = language;
    }

    // 下拉刷新处理
    public void refresh() {
        // 隐藏loadmore
        repoListView.hideLoadMoreLoading();
        repoListView.showContentView();
        nextPage = 1;// 永远刷新最新数据
        repoCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        repoCall.enqueue(repoCallback);


//        new RefreshTask().execute();
//        GitHubClient gitHubClient = new GitHubClient();
//        GitHubApi gitHubApi = gitHubClient.getGitHubApi();
//        Call<ResponseBody> call = gitHubApi.getRetrofitContributors();
//        // #1 直接在当前线程执行
//        // #2 异步执行
//        call.enqueue(refreshCallback);
    }
    private Callback<RepoResult> repoCallback=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 视图停止刷新
            repoListView.stopRefresh();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showErrorView("结果为空");
                return;
            }
            // 当前搜索的语言,没有仓库
            if (repoResult.getTotalCount() <= 0) {
                repoListView.refreshData(null);
                repoListView.showEmptyView();
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.refreshData(repoList);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.stopRefresh();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }

    };
//    private final Callback<ResponseBody> refreshCallback=new Callback<ResponseBody>() {
//        // 响应
//        @Override
//        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//            repoListView.stopRefresh();
//            // 成功200 - 299
//            if (response.isSuccessful()) {
//                try {
//                    ResponseBody body = response.body();
//                    if (body == null) {
//                        repoListView.showMessage("未知错误！");
//                        return;
//                    }
//                    String content = body.string();
//                    // content:就是从服务器拿到的响应体数据
//                    Log.d("TAG", content);
//                    repoListView.showContentView();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                repoListView.showMessage("code:" + response.code());
//            }
//        }
//        // 失败(如网络连接异常)
//        @Override
//        public void onFailure(Call<ResponseBody> call, Throwable t) {
//            repoListView.stopRefresh();
//            repoListView.showMessage(t.getMessage());
//            repoListView.showContentView();
//        }
//    };

    // 加载更多处理
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        repoCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        repoCall.enqueue(loadMoreCallback);
    }
    private Callback<RepoResult> loadMoreCallback=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMoreLoading();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showLoadMoreErro("结果为空");
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.addMoreData(repoList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.hideLoadMoreLoading();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };

//    final class LoadMoreTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            ArrayList<String> datas = new ArrayList<String>();
//            for (int i = 0; i < 20; i++) {
//                datas.add("测试数据 " + (count++));
//            }
//            repoListView.addMoreData(datas);
//            repoListView.hideLoadMore();
//        }
//    }
//
//    final class RefreshTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            ArrayList<String> datas = new ArrayList<String>();
//            for (int i = 0; i < 20; i++) {
//                datas.add("测试数据" + (count++));
//            }
//            repoListView.stopRefresh();
//            repoListView.refreshData(datas);
//            repoListView.showContentView();
//        }
//    }


}
