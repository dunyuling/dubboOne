package com.rc.dubbo.one.api.service;

import com.rc.dubbo.one.api.response.BaseResponse;

public interface IDubboItemInfoService {

    BaseResponse listItemInfos();

    BaseResponse listPageItems(Integer pageNo, Integer pageSize);

    BaseResponse listPageItemsParams(Integer pageNo, Integer pageSize, String search);
}
