package com.chards.committee.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.News;
import com.chards.committee.vo.NewsUpdateVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-05-23
 */
public interface NewsMapper extends BaseMapper<News> {

    Page<News> getList(@Param("title") String title,
                       @Param("page") Page<News> page);

    Integer updateByRoot(@Param("param")NewsUpdateVO newsUpdateVO);
}