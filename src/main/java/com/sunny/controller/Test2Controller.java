package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.exception.BusinessException;
import com.sunny.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/test2")
public class Test2Controller {

    @PostMapping
    public ResponseEntity add(@RequestParam(required = false) Map<String, String> RequestParam, @RequestBody(required = false) Map<String, String> RequestBody) {
        if (RequestParam == null) {
            throw new BusinessException("请求头参数缺失");
        }
        if (RequestBody == null) {
            throw new BusinessException("请求体参数缺失");
        }

        String name1 = RequestParam.get("name");
        if (StringUtil.isEmpty(name1)) {
            throw new BusinessException("请求头用户名不能为空");
        }


        String name = RequestBody.get("name");
        if (StringUtil.isEmpty(name)) {
            throw new BusinessException("用户名不能为空");
        }
        if (!name.equals("ZJ")) {
            throw new BusinessException("用户名错误");
        }

        return ResponseEntity.normalReturn(null, 200, null);
    }

}
