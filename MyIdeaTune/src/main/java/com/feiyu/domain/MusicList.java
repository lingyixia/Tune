package com.feiyu.domain;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MusicList {
    private static JSONArray instance;

    private MusicList() {

    }

    public static synchronized JSONArray getInstance() {
        if (instance == null) {
            instance = new JSONArray();
        }
        return instance;
    }

    public static boolean getJsonObject(String musicId) {
        JSONObject jsonObject = null;
//        System.out.println(instance.size());
        for (int i = 0; i < instance.size(); i++) {
            jsonObject = instance.getJSONObject(i);
            if (jsonObject.getString("musicId").equals(musicId)) {
                return true;
            }
        }
        return false;
    }

    public static JSONObject deleteCurrentMusic(String musicId) {
        JSONObject jsonObject = null;
        for (int i = 0; i < instance.size(); i++) {
            jsonObject = instance.getJSONObject(i);
            if (jsonObject.getString("musicId").equals(musicId)) {
                instance.remove(jsonObject);
                System.out.println("musicId = "+musicId+"删除成功");
                break;
            }
        }
        return jsonObject;
    }
}
