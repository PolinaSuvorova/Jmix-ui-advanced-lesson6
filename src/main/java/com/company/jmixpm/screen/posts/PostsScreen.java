package com.company.jmixpm.screen.posts;

import com.company.jmixpm.app.PostService;
import com.company.jmixpm.entity.Post;
import com.company.jmixpm.screen.userinfo.UserInfoScreen;
import io.jmix.core.LoadContext;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(path = "posts")
@UiController("PostsScreen")
@UiDescriptor("posts-screen.xml")
public class PostsScreen extends Screen {
    @Autowired
    private PostService postService;
    @Autowired
    private Table<Post> postsTable;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Install(to = "postsDl", target = Target.DATA_LOADER)
    private List<Post> postsDlLoadDelegate(final LoadContext<Post> loadContext) {
        return postService.fetchPosts();
    }

    @Subscribe("postsTable.viewUserInfo")
    public void onPostsTableViewUserInfo(final Action.ActionPerformedEvent event) {
        Post post = postsTable.getSingleSelected();
        if (post == null || post.getUserId() == null) {
            return;
        }
        UserInfoScreen userInfoScreen = screenBuilders.screen(this)
                .withScreenClass(UserInfoScreen.class)
                .build();
        userInfoScreen.setUserId(post.getUserId());
        userInfoScreen.show();
    }
}