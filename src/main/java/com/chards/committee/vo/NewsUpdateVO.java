package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/5/23 21:02
 */
@Data
public class NewsUpdateVO {
    @NotBlank(message = "id并不能为空")
    private Long id;

    private String title;

    private String content;

    private String author;

    private Boolean showState;

    private LocalDateTime updateTime;
}
