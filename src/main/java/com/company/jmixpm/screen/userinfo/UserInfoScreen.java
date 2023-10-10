package com.company.jmixpm.screen.userinfo;

import com.company.jmixpm.app.PostService;
import com.company.jmixpm.entity.UserInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import io.jmix.core.LoadContext;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("users-info")
@UiController("UserInfoScreen")
@UiDescriptor("user-info-screen.xml")
@EditedEntityContainer("userInfoDc")
@DialogMode(forceDialog = true, width = "AUTO", height = "AUTO")
public class UserInfoScreen extends Screen {
    @Autowired
    private PostService postService;
    @Autowired
    private UrlRouting urlRouting;
    private Long userId;
    @Autowired
    private InstanceLoader<UserInfo> userInfoDl;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onAfterShow(final AfterShowEvent event) {
        String serialize = UrlIdSerializer.serializeId(userId);
        urlRouting.replaceState(this, ImmutableMap.of("id", serialize));
    }

    @Subscribe
    public void onUrlParamsChanged(final UrlParamsChangedEvent event) {
        String serialized = event.getParams().get("id");
        if (serialized != null) {
            userId = (Long) UrlIdSerializer.deserializeId(Long.class, serialized);
            userInfoDl.load();
        }
    }

    public void setUserId(Long userId) {
        Preconditions.checkNotNull(userId);
        this.userId = userId;
    }

    @Install(to = "userInfoDl", target = Target.DATA_LOADER)
    private UserInfo userInfoDlLoadDelegate(final LoadContext<UserInfo> loadContext) {
        UserInfo userInfo = postService.fetchUserInfo(userId);
        if (userInfo == null) {
            throw new IllegalStateException("User wasn't loaded for id id:" + userId);
        }

        return userInfo;
    }

    @Subscribe("windowClose")
    public void onWindowClose(final Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

}