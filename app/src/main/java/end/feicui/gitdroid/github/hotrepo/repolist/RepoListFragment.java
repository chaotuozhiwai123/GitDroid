package end.feicui.gitdroid.github.hotrepo.repolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import end.feicui.gitdroid.R;
import end.feicui.gitdroid.commons.ActivityUtils;
import end.feicui.gitdroid.components.FooterView;
import end.feicui.gitdroid.favorite.dao.DBHelp;
import end.feicui.gitdroid.favorite.dao.LocalRepoDao;
import end.feicui.gitdroid.favorite.model.LocalRepo;
import end.feicui.gitdroid.favorite.model.RepoConverter;
import end.feicui.gitdroid.github.hotrepo.Language;
import end.feicui.gitdroid.github.hotrepo.repolist.modle.Repo;
import end.feicui.gitdroid.github.hotrepo.repolist.view.RepoListView;
import end.feicui.gitdroid.github.repoinfo.RepoInfoActivity;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 仓库列表
 * 将显示当前语言的所有仓库，有下拉刷新，上拉加载更多的效果
 * Created by Administrator on 2016/7/27.
 */
public class RepoListFragment extends Fragment implements RepoListView{

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(args);
        return fragment;
    }

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @BindView(R.id.lvRepos) ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout) PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView) TextView emptyView;
    @BindView(R.id.errorView) TextView errorView;

    private RepoListAdapter adapter;

    // 用来做当前页面业务逻辑及视图更新的
    private RepoListPresenter presenter;

    private FooterView footerView; // 上拉加载更多的视图
    private ActivityUtils activityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        activityUtils = new ActivityUtils(this);
        presenter = new RepoListPresenter(this,getLanguage());

        adapter = new RepoListAdapter();
        lvRepos.setAdapter(adapter);
        // 按下某个仓库后，进入此仓库详情
        lvRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);
            }
        });
        // 长按某个仓库后，加入收藏
        lvRepos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 热门仓库列表上的Repo
                Repo repo = adapter.getItem(position);
                LocalRepo localRepo = RepoConverter.convert(repo);
                // 添加到本地仓库表中去(只认本地仓库实体LocalRepo)
                new LocalRepoDao(DBHelp.getInstance(getContext())).createOrUpdate(localRepo);
                activityUtils.showToast("收藏成功！");
                return true;
            }
        });
        // 初始下拉刷新
        initPullToRefresh();
        // 初始上拉加载更多
        initLoadMoreScroll();
        // 如果当前页面没有数据，开始自动刷新
        if (adapter.getCount() == 0) {
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            }, 200);
        }
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        Mugen.with(lvRepos, new MugenCallbacks() {
            // listview，滚动到底部,将触发此方法
            @Override
            public void onLoadMore() {
                // 执行上拉加载数据的业务处理
                presenter.loadMore();
            }
            // 是否正在加载中
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean isLoading() {
                return lvRepos.getFooterViewsCount() > 0 && footerView.isLoading();
            }
            // 是否已加载完成所有数据
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean hasLoadedAllItems() {
                return lvRepos.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    private void initPullToRefresh() {
        // 使用当前对象做为key，来记录上一次的刷新时间,如果两次下拉太近，将不会触发新刷新
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        // 下拉刷新监听处理
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            // 当你"下拉时",将触发此方法
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 去做数据的加载，做具体的业务
                // 也就是说，你要抛开视图，到后台线程去做你的业务处理(数据刷新加载)
                presenter.refresh();
            }
        });
        // 以下代码（只是修改了header样式）
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LOVE " + "JAVA");
        header.setPadding(0, 60, 0, 60);
        // 修改Ptr的HeaderView效果
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.addPtrUIHandler(header);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    /**
     * 下拉刷新视图实现--------------------------------------------------
     */
    // 刷新的方法
    // 视图上:
    // 显示内容 or 错误 or 空白 , 三选一
    public void showContentView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void showErrorView(String errorMsg) {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }
    // 显示提示信息
    // 如：Toast， 直接在当前页面上页面
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }
    public void stopRefresh() {
        ptrClassicFrameLayout.refreshComplete();
    }

    // 刷新数据
    // 将后台线程更新加载到的数据，刷新显示到视图(listview)上来显示给用户看
    public void refreshData(List<Repo> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    /**
     * 上拉加载更多视图实现----------------------------------------
     */
    @Override
    public void showLoadMoreLoading() {
        if (lvRepos.getFooterViewsCount() == 0) {
            lvRepos.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMoreLoading() {
        lvRepos.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreErro(String erroMsg) {
        if (lvRepos.getFooterViewsCount() == 0) {
            lvRepos.addFooterView(footerView);
        }
        footerView.showError(erroMsg);
    }

    @Override
    public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }
}
