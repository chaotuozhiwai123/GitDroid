package end.feicui.gitdroid.github.hotrepo.repolist.view;

/**
 * Created by Administrator on 2016/7/28.
 */

import java.util.List;

import end.feicui.gitdroid.github.hotrepo.repolist.modle.Repo;

/**
 * 上拉加载更多接口
 */
public interface RepoListLoadMoreView {
    /** 在开始loadMore业务时，将触发，用来显示上拉加载时的加载中视图*/
    void showLoadMoreLoading();

    /** 在结束loadMore业务时，将触发，隐藏上拉加载时的加载中视图*/
    void hideLoadMoreLoading();

    /** 显示上拉加载时的错误视图*/
    void showLoadMoreErro(String erroMsg);

    /** 数据添加到视图*/
    void addMoreData(List<Repo> datas);
}
