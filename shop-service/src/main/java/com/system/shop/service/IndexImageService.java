package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.IndexImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IndexImageService extends IService<IndexImage> {
    List<IndexImage> selectList();
}
