package com.company.jmixpm.app;

import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Metadata metadata;

    public long fetchTaskCount(){
        return  dataManager.getCount(new LoadContext<>(metadata.getClass(Task.class)));
    }
}
