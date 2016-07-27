package end.feicui.gitdroid.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import end.feicui.gitdroid.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RepoListFragment extends Fragment {

    @BindView(R.id.lvRepos) ListView lvRepos;
    private ArrayAdapter<String> adapter;
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

        String[] datas = {
                "1111",
                "2222",
                "3333",
                "4444",
                "5555"};
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,datas);
        lvRepos.setAdapter(adapter);
    }
}
