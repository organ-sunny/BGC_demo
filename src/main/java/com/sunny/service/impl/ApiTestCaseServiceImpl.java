package com.sunny.service.impl;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.dto.ObjectApiDTO;
import com.sunny.dto.ObjectModuleDTO;
import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.*;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ApiTestCaseRepository;
import com.sunny.repository.ObjectApiRepository;
import com.sunny.repository.ObjectModuleRepositiry;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ApiTestCaseService;
import com.sunny.service.ObjectApiService;
import com.sunny.service.ObjectModuleService;
import com.sunny.service.ObjectSystemService;
import com.sunny.util.HttpUtil;
import com.sunny.util.RegexUtil;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApiTestCaseServiceImpl implements ApiTestCaseService {

    @Resource
    private ObjectApiRepository objectApiRepository;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ApiTestCaseRepository apiTestCaseRepository;

    @Resource
    private ObjectSystemRepository objectSystemRepository;

    @Resource
    private ObjectModuleRepositiry objectModuleRepositiry;

    @Resource
    private ObjectSystemService objectSystemService;

    @Resource
    private ObjectModuleService objectModuleService;

    @Resource
    private ObjectApiService objectApiService;

    /**
     * 入参：
     * objectSystemName":"系统名称",
     * objectModuleName":"模块名称",
     * objectApiName":"接口名称",
     * apiCaseNum":"接口用例编号",
     * apiCaseName":"接口用例名称",
     * apiCaseDescription":"用例描述",
     * apiCaseRequestAddress":"接口请求地址",
     * apiCaseRequestMethod":"接口请求方式",
     * apiCaseRequestHeader":"接口请求头",
     * apiCaseRequestParam":"接口请求入参",
     * apiCaseExpectedResult":"预期结果",
     * apiCaseActualResult":"实际结果",
     * apiCaseRemark":"备注"
     */
    @Override
    public void addApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        // 校验请求方法是否正确
        if (!RegexUtil.isRequestMethodRight(apiTestCaseDTO.getApiCaseRequestMethod())) {
            throw new BusinessException("请求方法填写错误：请填写POST、GET、PUT、DELETE中的一个，并注意大小写！");
        }

        // 校验用例编号
        List<ApiTestCaseEntity> byApiCaseNum = apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        if (byApiCaseNum.size() != 0) {
            throw new BusinessException("该用例编号:" + byApiCaseNum.get(0).getApiCaseNum() + "已存在！");
        }

        ApiTestCaseEntity apiCaseEntity = apiTestCaseDTO.getEntity();
        Date nowTime = new Date();
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        /**
         * 业务逻辑：先判断系统是否存在
         *          系统存在：再判断模块是否存在
         *              模块存在：再判断接口是否存在
         *                  接口存在：拿到接口编号，塞给case，保存入库
         *                  接口不存在：先新增接口，然后拿到接口编号，塞给case，再保存入库
         *              模块不存在：先拿到系统编号，去新增模块，然后拿到新增后的模块编号，再去新增接口，再拿到接口编号，最后去新增case
         *          系统不存在：拿到case传的系统名去新增系统，然后拿到系统编号，去新增模块，最后拿到新增后的模块编号，再去新增接口，再拿到接口编号，最后去新增case
         */

        String objectSystemName = apiTestCaseDTO.getObjectSystemName();
        List<ObjectSystemEntity> objectSystemEntityList = objectSystemRepository.findByObjectSystem(objectSystemName);
        ObjectSystemEntity objectSystemEntity;

        // 先判断系统表是否有数据，如果系统表无数据
        if (objectSystemEntityList.size() == 0) {
            // 添加系统表记录
            ObjectSystemDTO systemDTO = new ObjectSystemDTO();
            systemDTO.setObjectSystem(apiTestCaseDTO.getObjectSystemName());
            objectSystemEntity = objectSystemService.addObjectSystem(systemDTO);
        } else {
            objectSystemEntity = objectSystemEntityList.get(0);
        }

        // 判断模块是否存在
        String moduleName = apiTestCaseDTO.getObjectModuleName();
        List<ObjectModuleEntity> objectModuleEntityList = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, objectSystemEntity.getId());
        ObjectModuleEntity objectModuleEntity;
        // 如果模块表记录不存在
        if (objectModuleEntityList.size() == 0) {
            // 添加模块表记录
            ObjectModuleDTO moduleDTO = new ObjectModuleDTO();
            moduleDTO.setModuleName(moduleName);
            moduleDTO.setObjsystemId(objectSystemEntity.getId());
            objectModuleEntity = objectModuleService.addModule(moduleDTO);
        } else {
            objectModuleEntity = objectModuleEntityList.get(0);
        }

        // 判断接口表记录是否存在
        String objectApiName = apiTestCaseDTO.getObjectApiName();
        List<ObjectApiEntity> objectApiEntityList = objectApiRepository.findByModuleIdAndApiName(objectModuleEntity.getId(), objectApiName);
        ObjectApiEntity objectApiEntity;
        // 如果接口表记录不存在
        if (objectApiEntityList.size() == 0) {
            // 添加接口表记录
            ObjectApiDTO objectApiDTO = new ObjectApiDTO();
            objectApiDTO.setApiName(objectApiName);
            objectApiDTO.setApiAddress(apiTestCaseDTO.getApiCaseRequestAddress());
            objectApiDTO.setApiMethod(apiTestCaseDTO.getApiCaseRequestMethod());
            objectApiDTO.setModuleId(objectModuleEntity.getId());
            objectApiEntity = objectApiService.addObjectApi(objectApiDTO);
        } else {
            objectApiEntity = objectApiEntityList.get(0);
        }

        // 添加用例
        apiCaseEntity.setObjectApiId(objectApiEntity.getId());
        apiCaseEntity.setCreatedTime(nowTime);
        apiCaseEntity.setUpdatedTime(nowTime);
        apiCaseEntity.setCreator(user.getUsername());
        apiCaseEntity.setUpdatedBy(user.getUsername());
        apiTestCaseRepository.save(apiCaseEntity);
    }


    /**
     * 入参-查询条件：系统名、模块名、接口名、案例名
     */
    @Override
    public List<ApiTestCaseEntity> queryApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        String systemName = apiTestCaseDTO.getObjectSystemName();
        String moduleName = apiTestCaseDTO.getObjectModuleName();
        String objectApiName = apiTestCaseDTO.getObjectApiName();
        String apiCaseName = apiTestCaseDTO.getApiCaseName();

        List<ApiTestCaseEntity> apiTestCaseEntityList = new ArrayList<>();
        if (StringUtil.isEmpty(systemName) && StringUtil.isEmpty(moduleName) && StringUtil.isEmpty(objectApiName) && StringUtil.isEmpty(apiCaseName)) {
            apiTestCaseEntityList = apiTestCaseRepository.findAll();
        }
        if (!StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName) && !StringUtil.isEmpty(objectApiName) && !StringUtil.isEmpty(apiCaseName)) {
            if (objectSystemRepository.findByObjectSystem(systemName).size() == 0){
                throw new BusinessException("未查询到相关结果1！");
            }
            Integer systemId = objectSystemRepository.findByObjectSystem(systemName).get(0).getId();
            if (objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId).size() == 0){
                throw new BusinessException("未查询到相关结果2！");
            }
            Integer moduleId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId).get(0).getId();
            if (objectApiRepository.findByModuleIdAndApiName(moduleId, objectApiName).size() == 0){
                throw new BusinessException("未查询到相关结果3！");
            }
            Integer apiId = objectApiRepository.findByModuleIdAndApiName(moduleId, objectApiName).get(0).getId();
            apiTestCaseEntityList = apiTestCaseRepository.findByApiCaseNameAndObjectApiId(apiCaseName, apiId);

        }
        return apiTestCaseEntityList;

//        if (!StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName) && !StringUtil.isEmpty(objectApiName) && StringUtil.isEmpty(apiCaseName)) {
//            Integer systemId = objectSystemRepository.findByObjectSystem(systemName).get(0).getId();
//            Integer moduleId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId).get(0).getId();
//            Integer apiId = objectApiRepository.findByModuleIdAndApiName(moduleId, objectApiName).get(0).getId();
//            apiTestCaseEntityList = apiTestCaseRepository.myFindById(apiId);
//
//        }
//        if (!StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName) && StringUtil.isEmpty(objectApiName) && !StringUtil.isEmpty(apiCaseName)) {
//            Integer systemId = objectSystemRepository.findByObjectSystem(systemName).get(0).getId();
//            Integer moduleId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId).get(0).getId();
//            List<ObjectApiEntity> byModuleId = objectApiRepository.findByModuleId(moduleId);
//            for (ObjectApiEntity objectApiEntity : byModuleId) {
//                Integer apiId = objectApiEntity.getId();
//                apiTestCaseEntityList = apiTestCaseRepository.findByApiCaseNameAndId(apiCaseName, apiId);
//            }
//        }
//        if (!StringUtil.isEmpty(systemName) && StringUtil.isEmpty(moduleName) && !StringUtil.isEmpty(objectApiName) && !StringUtil.isEmpty(apiCaseName)) {
//            Integer systemId = objectSystemRepository.findByObjectSystem(systemName).get(0).getId();
//            List<ObjectModuleEntity> byObjsystemId = objectModuleRepositiry.findByObjsystemId(systemId);
//            for (ObjectModuleEntity objectModuleEntity : byObjsystemId){
//                Integer moduleId = objectModuleEntity.getId();
//                List<ObjectApiEntity> byModuleIdAndApiName = objectApiRepository.findByModuleIdAndApiName(moduleId, objectApiName);
//                for (ObjectApiEntity objectApiEntity :byModuleIdAndApiName){
//                    Integer apiId = objectApiEntity.getId();
//                    apiTestCaseEntityList = apiTestCaseRepository.findByApiCaseNameAndId(apiCaseName, apiId);
//                }
//            }
//        }
//
//        if (StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName) && !StringUtil.isEmpty(objectApiName) && !StringUtil.isEmpty(apiCaseName)) {
//            List<ObjectModuleEntity> byModuleName = objectModuleRepositiry.findByModuleName(moduleName);
//            for (ObjectModuleEntity objectModuleEntity : byModuleName){
//                Integer moduleId = objectModuleEntity.getId();
//                List<ObjectApiEntity> byModuleIdAndApiName = objectApiRepository.findByModuleIdAndApiName(moduleId, objectApiName);
//                for (ObjectApiEntity objectApiEntity :byModuleIdAndApiName){
//                    Integer apiId = objectApiEntity.getId();
//                    apiTestCaseEntityList = apiTestCaseRepository.findByApiCaseNameAndId(apiCaseName, apiId);
//                }
//            }
//        }


    }

    @Override
    public void runApiCase(Integer apiCaseId) {
        ApiTestCaseEntity apiTestCaseEntity = apiTestCaseRepository.getOne(apiCaseId);
        Integer objectApiId = apiTestCaseEntity.getObjectApiId();
        ObjectApiEntity objectApiEntity = objectApiRepository.getOne(objectApiId);
        try {

            HttpUtil httpUtil = new HttpUtil(objectApiEntity.getApiAddress(), objectApiEntity.getApiMethod());
            // 设置请求头
            String apiCaseRequestHeader = apiTestCaseEntity.getApiCaseRequestHeader();
            if (!StringUtil.isEmpty(apiCaseRequestHeader)) {
                Map<String, Object> requestHeader = StringUtil.toMap(apiCaseRequestHeader);
                for (String key : requestHeader.keySet()) {
                    httpUtil.setHeader(key, (String) requestHeader.get(key));
                }
            }

            String send = httpUtil.send(apiTestCaseEntity.getApiCaseRequestParam());
            Map<String, Object> actualResult = StringUtil.toMap(send);
            Map<String, Object> expectedResult = StringUtil.toMap(apiTestCaseEntity.getApiCaseExpectedResult());


            // 比对结果
            String isPass = "Pass";
            for (String key : expectedResult.keySet()) {
                if (!actualResult.containsKey(key) || !actualResult.get(key).equals(expectedResult.get(key))) {
                    isPass = "Failed";
                    break;
                }
            }
            apiTestCaseEntity.setIsPassed(isPass);
            apiTestCaseEntity.setApiCaseActualResult(send);
        } catch (Exception e) {
            apiTestCaseEntity.setIsPassed("Failed");

            // 404
            if (e.getClass() == FileNotFoundException.class) {
                apiTestCaseEntity.setApiCaseActualResult("404");
            } else {
                apiTestCaseEntity.setApiCaseActualResult(e.getMessage());
            }
        } finally {
            apiTestCaseRepository.save(apiTestCaseEntity);
        }

        apiTestCaseRepository.save(new ApiTestCaseEntity());
    }

    @Override
    public void runApiCase(List<Integer> apiCaseIds) {
        for (Integer integer : apiCaseIds) {
            if (integer == null) {
                continue;
            }
            runApiCase(integer);
        }
    }

}

