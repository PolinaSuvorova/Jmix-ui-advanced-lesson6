package com.company.jmixpm.screen.main;

import com.company.jmixpm.app.TaskChangedEvent;
import com.company.jmixpm.app.TaskService;
import com.company.jmixpm.listener.TaskEventListener;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;

    @Autowired
    private Drawer drawer;

    @Autowired
    private Button collapseDrawerButton;

    @Autowired
    private SideMenu sideMenu;

     @Autowired
    private TaskService taskService;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
        updateTaskCount();
    }

    @EventListener
    public void taskChanged(TaskChangedEvent event){
        updateTaskCount();
    }

    private void updateTaskCount(){
        Long count = taskService.fetchTaskCount();
        sideMenu.getMenuItemNN("Task_.browse").setBadgeText( count + " tasks");
    }

}
