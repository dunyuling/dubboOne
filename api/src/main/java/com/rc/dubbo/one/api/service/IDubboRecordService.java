package com.rc.dubbo.one.api.service;

import com.rc.dubbo.one.api.request.PushOrderDto;
import com.rc.dubbo.one.api.response.BaseResponse;

public interface IDubboRecordService {

    BaseResponse pushOrder(PushOrderDto pushOrderDto);

}
