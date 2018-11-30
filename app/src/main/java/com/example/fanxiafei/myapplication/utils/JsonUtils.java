package com.example.fanxiafei.myapplication.utils;

import android.os.Environment;
import android.util.Log;

import com.example.fanxiafei.myapplication.model.HotTopicModel;
import com.example.fanxiafei.myapplication.model.PlainTopicModel;
import com.example.fanxiafei.myapplication.model.TopicModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static void toJson() {
        TopicModel<List<HotTopicModel>, List<PlainTopicModel>> topicModel = new TopicModel<>();
        topicModel.setReturnCode(200);
        topicModel.setStatus(1);

        List<PlainTopicModel> plainTopicModels = new ArrayList<>();
        PlainTopicModel model1 = new PlainTopicModel();
        PlainTopicModel model2 = new PlainTopicModel();
        plainTopicModels.add(model1);
        plainTopicModels.add(model2);

        List<HotTopicModel> hotTopicModels = new ArrayList<>();
        HotTopicModel model3 = new HotTopicModel();
        HotTopicModel model4 = new HotTopicModel();
        hotTopicModels.add(model3);
        hotTopicModels.add(model4);

        topicModel.setHotTopicCard(hotTopicModels);
        topicModel.setPlainTopicCard(plainTopicModels);

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtils.writeFileFromString(file, GsonUtil.toJson(topicModel));
    }

}
