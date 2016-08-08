package end.feicui.gitdroid.github.hotrepo.repolist.view;

/**
 * Created by Administrator on 2016/7/28.
 */

import java.util.List;

import end.feicui.gitdroid.github.hotrepo.repolist.modle.Repo;

/**
 * 下拉刷新接口
 */
public interface RepoListPtrView {
    void showContentView();
    void showErrorView(String errorMsg);
    void showEmptyView();
    void showMessage(String msg);
    void stopRefresh();
    void refreshData(List<Repo> data);
}
