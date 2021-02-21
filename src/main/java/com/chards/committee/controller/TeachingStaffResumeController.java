package com.chards.committee.controller;

import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.service.TeachingStaffResumeService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author LiuSu
 * @create 2021/2/21 0:44
 */
@RestController
@RequestMapping("/teachingStaffResume")
@Api(tags = "教职工简历相关接口", value = "教职工简历接口")
@Slf4j
public class TeachingStaffResumeController {

    @Autowired
    private TeachingStaffResumeService teachingStaffResumeService;

    @Value("${tFilePath}")
    private String tFilePath;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT'))")
    @ApiOperation(value = "教职工自己添加自己的简历")
    public R add(@RequestBody TeachingStaffResume teachingStaffResume){
        return R.success(teachingStaffResumeService.save(teachingStaffResume));
    }

    //添加一个修改的接口

    //图片上传，这里主要是头像
    @PostMapping("/avatar")
    @PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT'))")
    @ApiOperation(value = "上传头像")
    public R singleFileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String fileId = RequestUtil.getId();
        if (file.isEmpty()) {
            log.error("文件不能为空");
        }
        String extension = getFileExtension(file); // 后缀名
        // 对文件后缀名进行判断,如果不是png或者jpg则返回
        if (!extension.equals(".jpg") && !extension.equals(".png")) {
            return R.failure("图片后缀只能为jpg或者png");
        }
        // 对文件大小进行限定，如果超过某一大小则进行压缩
        // https://blog.csdn.net/qq_42476834/article/details/108886493
        // TODO
        String fileName = fileId + ".png"; // 新文件名
        File dest = new File(tFilePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error(e.toString());
        }
        return R.success(Code.SUCCESS);
    }

    @GetMapping("/get/{staffId}")
    @PreAuthorize("hasAuthority('teacher_select')")
    @ApiOperation(value = "根据教职工工号获取简历信息")
    public R getByStaffId(@PathVariable String staffId){
        return R.success(teachingStaffResumeService.getByStaffId(staffId));
    }

    private String getFileExtension(MultipartFile File) {
        String originalFileName = File.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
}
