package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.IndexImageMapper;
import com.system.shop.entity.IndexImage;
import com.system.shop.service.IndexImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IndexImageServiceImpl extends ServiceImpl<IndexImageMapper, IndexImage> implements IndexImageService {
    @Override
    public List<IndexImage> selectList() {
        return baseMapper.selectList();
    }
}
