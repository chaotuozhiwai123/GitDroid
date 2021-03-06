package end.feicui.gitdroid.github.hotuser;

import java.util.List;

import end.feicui.gitdroid.github.hotuser.model.UsersResult;
import end.feicui.gitdroid.github.hotuser.view.UserListView;
import end.feicui.gitdroid.github.login.modle.User;
import end.feicui.gitdroid.github.network.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotUserPresenter {
    // 用户列表视图接口对象
    private UserListView userListView;
    private Call<UsersResult> usersCall;
    private int nextPage = 0;



    public HotUserPresenter(UserListView userListView) {
        this.userListView = userListView;
    }

    // 下拉刷新处理
    public void refresh() {
        userListView.hideLoadMore();
        userListView.showContentView();
        nextPage = 1; // 永远刷新最新数据
        if (usersCall!=null) usersCall.cancel();
        usersCall = GitHubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersCall.enqueue(ptrCallback);
    }
    private Callback<UsersResult> ptrCallback=new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.stopRefresh();
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                userListView.showErrorView("结果为空");
                return;
            }
            // 取出搜索到的所有用户
            List<User> userList = usersResult.getRepoList();
            userListView.refreshData(userList);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {
            userListView.stopRefresh();
            userListView.showMessage("ptrCallback onFailure" + t.getMessage());
        }
    };

    // 加载更多处理
    public void loadMore() {
        userListView.showLoadMoreLoading();
        if (usersCall!=null) usersCall.cancel();
        usersCall = GitHubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersCall.enqueue(loadmoreCallback);
    }
    private Callback<UsersResult> loadmoreCallback=new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.hideLoadMore();
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                userListView.showLoadMoreErro("结果为空");
                return;
            }
            // 取出搜索到的所有用户
            List<User> userList = usersResult.getRepoList();
            userListView.addMoreData(userList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {
            // 视图停止刷新
            userListView.hideLoadMore();
            userListView.showMessage("loadmoreCallback onFailure" + t.getMessage());
        }
    };

}
