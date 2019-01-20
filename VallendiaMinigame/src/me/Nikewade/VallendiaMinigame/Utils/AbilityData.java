package me.Nikewade.VallendiaMinigame.Utils;
import java.util.HashMap;

public class AbilityData {


    private HashMap<String, Object> abilityData = new HashMap<>();

    public void setData(String key, Object value) {
        abilityData.put(key, value);
    }

    public void removeData(String key) {
        abilityData.remove(key);
    }

    public Object getData(String key) {
        return abilityData.get(key);
    }

    public HashMap<String, Object> getAllData() {
        return abilityData;
    }
}
