package com.chards.committee.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.News;
import com.chards.committee.mapper.NewsMapper;
import com.chards.committee.vo.NewsUpdateVO;
import org.springframework.stereotype.Service;

/**
 * @author LiuSu
 * @create 2021/5/19 22:36
 */
@Service("newsService")
public class NewsService extends ServiceImpl<NewsMapper, News> {

	public Page<News> getList(String title, Page<News> page) {
		return baseMapper.getList(title,page);
	}

	public Integer updateByRoot(NewsUpdateVO newsUpdateVO) {
		return baseMapper.updateByRoot(newsUpdateVO);
	}
}