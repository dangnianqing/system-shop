package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.IndexImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexImageMapper extends BaseMapper<IndexImage> {

    List<IndexImage> selectList();
}