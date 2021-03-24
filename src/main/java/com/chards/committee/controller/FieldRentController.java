package com.chards.committee.controller;


import com.chards.committee.domain.FieldRent;
import com.chards.committee.service.FieldRentService;
import com.chards.committee.service.TbAdminRoleService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.util.UploadUtil;
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
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/field-rent")
@Api(tags = "场地管理",value = "租借场地管理接口")
@Slf4j
public class FieldRentController {
    // 图片路径
    @Value("${filepath}")
    private String filePath;

    private static final String PATH = "/field/";

    @Autowired
    private FieldRentService fieldRentService;

    @Autowired
    private TbAdminRoleService tbAdminRoleService;

    @ApiOperation(value = "上传租借场地照片")
    @PostMapping("/uploadPicture")
    @PreAuthorize("hasRole('ROOT')")
    public R uploadPic(@RequestParam(value = "files") MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        if (multipartFiles == null || multipartFiles.length <= 0) {
            log.error("文件不能为空");
            return R.failure(Code.PARAM_IS_BLANK);
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<String> paths = UploadUtil.uploadPic(multipartFiles,filePath + PATH);
        resultMap.put("path",paths);
        return R.success(resultMap);
    }

    @ApiOperation(value = "获取场地负责人学号或者工号")
    @PreAuthorize("hasRole('ROOT')")
    @GetMapping("/getAllManager")
    public R getAllManager(){
        return R.success(tbAdminRoleService.getTbAdminRoleByRoleId(8));
    }

    @ApiOperation(value = "新增租借场地")
    @PreAuthorize("hasRole('ROOT')")
    @PostMapping("/add")
    public R add(@Valid @RequestBody FieldRent fieldRent){
        fieldRent.setCreateTime(LocalDateTime.now());
        fieldRent.setUpdateTime(LocalDateTime.now());
        return R.success(fieldRentService.save(fieldRent));
    }

    @ApiOperation(value = "获取所有租借场地接口")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ROOT')")
    @GetMapping("/getAll")
    public R getAll() {
        return R.success(fieldRentService.list());
    }

    @ApiOperation("根据场地id获取详细信息")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ROOT')")
    @GetMapping("/getDetailById/{id}")
    public R getDetailById(@PathVariable Serializable id){
        FieldRent fieldRent = fieldRentService.getById(id);
        return R.success(fieldRent);
    }

    @ApiOperation("根据id更新场地信息")
    @PreAuthorize("hasRole('ROOT')")
    @PutMapping("/update")
    public R update(@RequestBody FieldRent fieldRent) {
        return R.success(fieldRentService.updateById(fieldRent));
    }

    @ApiOperation("根据id删除场地信息")
    @PreAuthorize("hasRole('ROOT')")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Serializable id) {
        return R.success(fieldRentService.removeById(id));
    }


}

