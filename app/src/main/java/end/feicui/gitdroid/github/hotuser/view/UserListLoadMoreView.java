package end.feicui.gitdroid.github.hotuser.view;

import java.util.List;

import end.feicui.gitdroid.github.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface UserListLoadMoreView {

    /** 显示上拉加载时的加载中视图*/
    void showLoadMoreLoading();

    /** 隐藏上拉加载时的加载中视图*/
    void hideLoadMore();

    /** 隐藏上拉加载时的错误视图*/
    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<User> datas);
}
