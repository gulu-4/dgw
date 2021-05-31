package com.chards.committee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.News;
import com.chards.committee.service.NewsService;
import com.chards.committee.vo.NewsUpdateVO;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/5/23 20:49
 */
@RestController
@RequestMapping("/news")
@Api(tags = "新闻模块",value = "新闻模块接口文档")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "新闻公告新增")
    @PreAuthorize("hasRole('ROOT')")
    @PostMapping("/add")
    public R add(@RequestBody News news){
        news.setShowState(true);
        news.setCreateTime(LocalDateTime.now());
        news.setUpdateTime(LocalDateTime.now());
        return R.success(newsService.save(news));
    }


    @ApiOperation(value = "分页查找新闻公告列表")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @GetMapping("/getList")
    public R getList(String title, Page<News> page){
        return R.success(newsService.getList(title,page));
    }

    @ApiOperation(value = "根据id查找新闻公告")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Serializable id){
        return R.success(newsService.getById(id));
    }

    @ApiOperation(value = "根据id删除新闻公告")
    @PreAuthorize("hasRole('ROOT')")
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Serializable id){
        return R.success(newsService.removeById(id));
    }

    @ApiOperation(value = "修改内容")
    @PreAuthorize("hasRole('ROOT')")
    @PutMapping("/update")
    public R update(@RequestBody NewsUpdateVO newsUpdateVO){
        newsUpdateVO.setUpdateTime(LocalDateTime.now());
        return R.success(newsService.updateByRoot(newsUpdateVO));
    }
}
