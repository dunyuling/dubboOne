package com.rc.dubbo.one.server.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.rc.dubbo.one.api.enums.StatusCode;
import com.rc.dubbo.one.api.request.PushOrderDto;
import com.rc.dubbo.one.api.response.BaseResponse;
import com.rc.dubbo.one.api.service.IDubboRecordService;
import com.rc.dubbo.one.model.entity.ItemInfo;
import com.rc.dubbo.one.model.entity.OrderRecord;
import com.rc.dubbo.one.model.mapper.ItemInfoMapper;
import com.rc.dubbo.one.model.mapper.OrderRecordMapper;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Service(protocol = {"dubbo","rest"}, validation = "true", version = "1.0", timeout = 30000)
@Path("record")
public class DubboRecordService implements IDubboRecordService {

    private static final Logger log = LoggerFactory.getLogger(DubboRecordService.class);

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Path("push")
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public BaseResponse pushOrder(PushOrderDto pushOrderDto) {
        if (pushOrderDto.getItemId() == null || pushOrderDto.getItemId() <= 0
                || Strings.isEmpty(pushOrderDto.getCustomerName()) || pushOrderDto.getTotal() == null) {
            return new BaseResponse(StatusCode.InvalidParams);
        }

        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            //TODO 实际的业务逻辑

            //TODO 校验商品信息是否存在
            ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(pushOrderDto.getItemId());
            if (itemInfo == null) {
                return new BaseResponse(StatusCode.ItemNotExist);
            }

            //TODO 库存服务--校验...

            //TODO 客服中心服务--校验...

            //TODO 订单服务-下单
            OrderRecord orderRecord = new OrderRecord();
            BeanUtils.copyProperties(pushOrderDto,orderRecord);
            orderRecord.setOrderTime(new Date());
            orderRecordMapper.insertSelective(orderRecord);

            //TODO orderRecord此时是否含有 id 信息
            baseResponse.setData(orderRecord.getId());
            log.info("响应数据： {} " ,baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下单逻辑错误 {} ", e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }

        return baseResponse;
    }
}
