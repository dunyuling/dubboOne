package com.rc.dubbo.one.server.service.dubbo;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rc.dubbo.one.api.enums.StatusCode;
import com.rc.dubbo.one.api.response.BaseResponse;
import com.rc.dubbo.one.api.service.IDubboItemInfoService;
import com.rc.dubbo.one.model.entity.ItemInfo;
import com.rc.dubbo.one.model.mapper.ItemInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Service(protocol = {"dubbo","rest"}, validation = "true", version = "1.0", timeout = 3000)
@Path("moocOne")
public class DubboItemInfoService implements IDubboItemInfoService {

    private static final Logger log = LoggerFactory.getLogger(DubboItemInfoService.class);

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    /**
     * 列表查询服务-实际业务实现逻辑
     * @return
     */
    @Path("/item/list")
    public BaseResponse listItemInfos() {
        BaseResponse baseResponse  = new BaseResponse(StatusCode.Success);
        try {
            List<ItemInfo> infos = itemInfoMapper.selectAll();
            log.info("查询到的商品列表数据：{} ", infos);
            baseResponse.setData(infos);
        } catch (Exception e) {
            log.error("列表查询服务-实际业务实现逻辑-发生异常：" + e.fillInStackTrace());
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    /**
     * 列表查询-分页查询
     * @return
     */
    @Path("/item/page/list")
    public BaseResponse listPageItems(Integer pageNo, Integer pageSize) {
        BaseResponse baseResponse  = new BaseResponse(StatusCode.Success);
        try {
            PageHelper.startPage(pageNo, pageSize);
            PageInfo<ItemInfo> pageInfo = new PageInfo<>(itemInfoMapper.selectAll());
            baseResponse.setData(pageInfo);
        } catch (Exception e) {
            log.error("列表查询-分页查询-实际业务实现逻辑-发生异常：" + e.fillInStackTrace());
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    /**
     * 列表查询-分页查询-模糊查询
     * @return
     */
    @Path("/item/page/list")
    public BaseResponse listPageItemsParams(Integer pageNo, Integer pageSize, String search) {
        BaseResponse baseResponse  = new BaseResponse(StatusCode.Success);
        try {
            PageHelper.startPage(pageNo, pageSize);
            PageInfo<ItemInfo> pageInfo = new PageInfo<>(itemInfoMapper.selectAllByParams(search));
            baseResponse.setData(pageInfo);
        } catch (Exception e) {
            log.error("列表查询-分页查询--模糊查询-实际业务实现逻辑-发生异常：" + e.fillInStackTrace());
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
}
