package com.babak.utils.strategy.storage.impl;

import com.babak.utils.strategy.storage.StorageData;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class BuilderStorageData {

    private static final Logger LOGGER = Logger.getLogger(BuilderStorageData.class);
    private Map<String, StorageData> methods;

    /**
     * Initialize methods.
     */
    public BuilderStorageData() {
        this.methods = new HashMap<>();
        methods.put("session", new SessionStorage());
        methods.put("cookie", new CookieStorage());
        methods.put("hiddenFieldStorage", new HiddenFieldStorage());
    }

    /**
     * Choose method for working.
     *
     * @param valueParam the name method for working
     */
    public StorageData buildStorageData(String valueParam) {

        for (Map.Entry<String, StorageData> method : methods.entrySet()) {
            if (valueParam.equals(method.getKey())) {
                return method.getValue();
            }
        }
        LOGGER.debug(valueParam + " method for send captcha");
        return new SessionStorage();
    }
}
