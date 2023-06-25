package com.novels.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("dancechar-system-service")
public interface UserClient {

//    @PostMapping("/user/findBlackList")
//    RespResult<List<SystemUserRespDTO>> findBlackList();
//
//    @PostMapping("/user/isBlackList")
//    RespResult<Boolean> isBlackList(SystemUserReqDTO req);
//

}
