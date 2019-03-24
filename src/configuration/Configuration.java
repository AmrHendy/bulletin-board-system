package configuration;

import java.util.HashMap;

public class Configuration {

    private static Configuration configurationInstance = null;
    private HashMap<String, String> confMap;

    private Configuration() {
        confMap = new HashMap<>()
    }

    public static Configuration getConfiguration() {
        if (configurationInstance == null) {
            configurationInstance = new Configuration();
        }
        return configurationInstance;
    }

    public void clearAllConf() {
        confMap.clear();
    }

    public void addConf(String key, String value) {
        confMap.put(key, value);
    }

    public void removeConf(String key) {
        confMap.remove(key);
    }

    public String getConf(String key) {
        return confMap.get(key);
    }
}
