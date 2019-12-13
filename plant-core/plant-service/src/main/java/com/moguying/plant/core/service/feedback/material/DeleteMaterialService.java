package com.moguying.plant.core.service.feedback.material;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.dao.feedback.FeedbackMaterialDAO;
import com.moguying.plant.core.entity.feedback.FeedbackItem;
import com.moguying.plant.core.entity.feedback.FeedbackMaterial;
import com.moguying.plant.core.service.upload.qiniu.QiniuManager;
import com.moguying.plant.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


@Slf4j
public class DeleteMaterialService implements Runnable {

    private Integer id;
    private QiniuManager qiniuManager = ApplicationContextUtil.getBean(QiniuManager.class);
    private MongoTemplate mongoTemplate = ApplicationContextUtil.getBean(MongoTemplate.class);
    private FeedbackMaterialDAO dao = ApplicationContextUtil.getBean(FeedbackMaterialDAO.class);

    public DeleteMaterialService(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        FeedbackMaterial feedbackMaterial = dao.selectById(id);
        if (null != feedbackMaterial) {
            //根据KEY删除七牛云指定空间的文件
            qiniuManager.delete(feedbackMaterial.getMaterialName());
            //替换溯源列表的素材URl
            List<FeedbackItem> items = mongoTemplate.find(new Query(), FeedbackItem.class);
            String itemStr = JSON.toJSONString(items).replace(feedbackMaterial.getMaterialPath(), "");
            List<FeedbackItem> items2 = JSON.parseArray(itemStr, FeedbackItem.class);
            for (FeedbackItem feedbackItem : items2) {
                Query updateQuery = new Query();
                updateQuery.addCriteria(Criteria.where("_id").is(feedbackItem.get_id()));
                Update update = new Update();
                update.set("feedbackType", feedbackItem.getFeedbackType());
                update.set("banners", feedbackItem.getBanners());
                update.set("describeInfo", feedbackItem.getDescribeInfo());
                update.set("feedbackTypes", feedbackItem.getFeedbackTypes());
                mongoTemplate.updateFirst(updateQuery, update, FeedbackItem.class);
            }
        }
    }
}
