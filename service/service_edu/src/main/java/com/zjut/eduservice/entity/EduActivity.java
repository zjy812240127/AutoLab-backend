package com.zjut.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduActivity对象", description="")
public class EduActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    private String description;

    @ApiModelProperty(value = "活动封面路径")
    private String photoUrl;

    @ApiModelProperty(value = "云端视频资源路径（这里存储的不是视频的真实播放地址，而是阿里云上视频的id）")
    private String videoUrl;

    @TableLogic
    private Integer isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
