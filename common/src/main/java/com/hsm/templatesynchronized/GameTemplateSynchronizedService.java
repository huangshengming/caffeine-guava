package com.hsm.templatesynchronized;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class GameTemplateSynchronizedService {

    private static final Logger logger = LoggerFactory.getLogger(GameTemplateSynchronizedService.class);

    private WatchService watchService;
    private Path dir;
    private ConcurrentHashMap<String, GameTemplateWorker> workerMap;

    public GameTemplateSynchronizedService(String path) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        dir = Paths.get(path);
        dir.register(watchService, ENTRY_MODIFY);
        workerMap = new ConcurrentHashMap<>();
    }

    /** 注册加载xml文件*/
    public void register(GameTemplateWorker worker){
        callWorker(worker);
        workerMap.put(worker.getFileName(), worker);
    }

    public boolean processEvent(){
        boolean isUpdate = false;
        WatchKey key = watchService.poll();
        if(null == key){
            return isUpdate;
        }
        for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent.Kind kind = event.kind();
            if(OVERFLOW == kind){
                continue;
            }
            String fileName = event.context().toString();
            //加载文件
            GameTemplateWorker worker = workerMap.get(fileName);
            if(worker != null){
                callWorker(worker);
                isUpdate = true;
            }
        }
        key.reset();
        return isUpdate;
    }

    public void callWorker(GameTemplateWorker worker){
        Element rootElement = null;
        try {
            SAXReader reader = new SAXReader();
            File file = new File(dir.resolve(worker.getFileName()).toString());
            rootElement = reader.read(file).getRootElement();
        } catch (DocumentException e) {
            logger.error("reader file fail, error: {}", e.getMessage());
        }
        if(null != rootElement){
            worker.syncProcess(rootElement);
        }
        logger.info("Sync game template: {}", worker.getFileName());
    }

}
