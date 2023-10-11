package com.company.jmixpm.listener;

import com.company.jmixpm.app.TaskChangedEvent;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.ui.UiEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TaskEventListener {

    @Autowired
    private UiEventPublisher uiEventPublisher;

    @TransactionalEventListener
    public void onTaskChangedAfterCommit(final EntityChangedEvent<Task> event) {
        EntityChangedEvent.Type type = event.getType();
        if (type == EntityChangedEvent.Type.CREATED ||
                type == EntityChangedEvent.Type.DELETED) {
            uiEventPublisher.publishEvent(new TaskChangedEvent(this));
        }
    }


}