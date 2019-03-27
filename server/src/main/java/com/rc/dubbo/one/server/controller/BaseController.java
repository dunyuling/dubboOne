package com.rc.dubbo.one.server.controller;


import com.rc.dubbo.one.api.enums.StatusCode;
import com.rc.dubbo.one.api.response.BaseResponse;
import com.rc.dubbo.one.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(RestController.class);

    private static final String prefix = "base";

    @RequestMapping(value = prefix + "/one", method = RequestMethod.GET)
    public BaseResponse one(@RequestParam("param") String param) {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);

        try {
            baseResponse.setData(new User("lhg",22));
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }


    @RequestMapping(value = prefix + "/two", method = RequestMethod.GET)
    public BaseResponse two() {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);

        try {
            baseResponse.setData("aa");
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
}
