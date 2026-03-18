package com.system.shop.controller.activity;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Activity;
import com.system.shop.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/activity")
@RestController
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;
    @PostMapping("/selectActivity/{storeId}/{productId}")
    public Result<List<Activity>> selectActivityByProductIdStoreId(@PathVariable String storeId, @PathVariable String productId) {
        return Result.success(activityService.selectActivityByProductIdStoreId(storeId, productId));
    }

}
