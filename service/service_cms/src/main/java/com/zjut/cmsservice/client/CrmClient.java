package com.zjut.cmsservice.client;

import com.zjut.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient("service-oss")
public interface CrmClient {

    @ApiOperation("上传banner到阿里云oss")
    // consumes 解决报错 Content-Type "multipart/form-data" not set for request body of type StandardMultipartFile
    // value必须写全路径
    @PostMapping(value = "/eduoss/fileoss/uploadBanners", consumes = "multipart/form-data")
    public String uploadBanner(MultipartFile file);

}
