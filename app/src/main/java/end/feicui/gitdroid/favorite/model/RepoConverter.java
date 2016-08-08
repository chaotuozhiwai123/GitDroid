package end.feicui.gitdroid.favorite.model;

import java.util.ArrayList;
import java.util.List;

import end.feicui.gitdroid.github.hotrepo.repolist.modle.Repo;

/**
 * Created by Administrator on 2016/8/4.
 */
public class RepoConverter {

    public RepoConverter() {
    }

    /**
     * 将Repo(热门仓库)转换为LocalRepo(本地仓库)对象, 默认为未分类
     */
    public static LocalRepo convert(Repo repo) {
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStartCount(repo.getStarCount());
        localRepo.setForkCount(repo.getForkCount());
        localRepo.setRepoGroup(null);
        return localRepo;
    }

    public static List<LocalRepo> converAll(List<Repo> repos) {
        ArrayList<LocalRepo> localRepos = new ArrayList<LocalRepo>();
        for (Repo repo : repos) {
            localRepos.add(convert(repo));
        }
        return localRepos;
    }
}
