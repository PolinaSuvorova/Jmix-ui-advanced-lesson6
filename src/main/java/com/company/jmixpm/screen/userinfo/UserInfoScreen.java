package com.company.jmixpm.screen.userinfo;

import com.company.jmixpm.app.PostService;
import com.google.common.base.Preconditions;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.ui.action.Action;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Set;

@UiController("UserInfoScreen")
@UiDescriptor("user-info-screen.xml")
@EditedEntityContainer("userInfoDc")
@DialogMode(forceDialog = true, width = "AUTO", height = "AUTO")
public class UserInfoScreen extends Screen {
    @Autowired
    private PostService postService;
    private Long userId;

    public void setUserId(Long userId) {
        Preconditions.checkNotNull(userId);
        this.userId = userId;
    }

    @Install(to = "userInfoDl", target = Target.DATA_LOADER)
    private UserInfo userInfoDlLoadDelegate(final LoadContext<UserInfo> loadContext) {
        UserInfo userInfo = postService.fetchUserInfo(userId);
        if (userInfo == null ){
            throw new IllegalStateException("User wasn't loaded for id id:" + userId);        }

        return userInfo;
    }

    @Subscribe("windowClose")
    public void onWindowClose(final Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

}