package com.hsm.templatesynchronized;

import org.dom4j.Element;

public abstract class GameTemplateWorker<T> {
    private final IGameTemplateTask<T> task;

    protected volatile String status;

    public GameTemplateWorker(IGameTemplateTask<T> task)
    {
        this.task = task;
    }

    public abstract String getFileName();

    protected abstract T convertProcess(Element rootElement);

    public void syncProcess(Element rootElement) {
        task.doTask(convertProcess(rootElement));
    }
}
