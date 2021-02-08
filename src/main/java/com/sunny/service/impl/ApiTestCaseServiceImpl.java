package com.sunny.service.impl;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.dto.ObjectModuleDTO;
import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ApiTestCaseRepository;
import com.sunny.repository.ObjectModuleRepositiry;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ApiTestCaseService;
import com.sunny.service.ObjectModuleService;
import com.sunny.service.ObjectSystemService;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApiTestCaseServiceImpl implements ApiTestCaseService {
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

    /**
     * 入参：
     *     objectSystemName":"系统名称",
     *     objectModuleName":"模块名称",
     *     apiCaseNum":"接口用例编号",
     *     apiCaseName":"接口用例名称",
     *     apiCaseDescription":"用例描述",
     *     apiCaseRequestAddress":"接口请求地址",
     *     apiCaseRequestMethod":"接口请求方式",
     *     apiCaseRequestParam":"接口请求入参",
     *     apiCaseExpectedResult":"预期结果",
     *     apiCaseActualResult":"实际结果",
     *     creator":"",
     *     createdTime":"",
     *     updatedBy":"",
     *     updatedTime":"",
     *     apiCaseRemark":"备注"
     */
    @Override
    public void addApiCase(ApiTestCaseDTO apiTestCaseDTO) {
        ApiTestCaseEntity apiCaseEntity = apiTestCaseDTO.getEntity();
        Date nowTime = new Date();
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        /**
         * 业务逻辑：先判断系统是否存在
         *          系统存在：再判断模块是否存在
         *              模块存在：先拿到模块编号，再去新增case
         *              模块不存在：先拿到系统编号，去新增模块，然后拿到新增后的模块编号，再去新增case
         *          系统不存在：拿到case传的系统名去新增系统，然后拿到系统编号，去新增模块，最后拿到新增后的模块编号，再去新增case
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

        // 新加用例
        apiCaseEntity.setObjectModuleId(objectModuleEntity.getId());
        apiCaseEntity.setCreatedTime(nowTime);
        apiCaseEntity.setUpdatedTime(nowTime);
        apiCaseEntity.setCreator(user.getUsername());
        apiCaseEntity.setUpdatedBy(user.getUsername());
        apiTestCaseRepository.save(apiCaseEntity);


//
//
//        // 先判断系统表是否有数据，如果系统表有数据
//        List<ObjectSystemEntity> byObjectSystem = objectSystemRepository.findByObjectSystem(apiTestCaseDTO.getObjectSystemName());
//
//
//        // 如果系统存在
//        if (byObjectSystem.size() != 0) {
//
//            // 拿到模块名称
//            String moduleName = apiTestCaseDTO.getObjectModuleName();
//
//            // 拿到系统编号
//            Integer systemId = byObjectSystem.get(0).getId();
//
//            // 实例化模块实体
//            List<ObjectModuleEntity> objectModuleEntityList = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId);
//
//            // 如果模块存在
//            if (objectModuleEntityList.size() != 0) {
//
//                // 拿到模块编号
//                Integer moduleId = objectModuleEntityList.get(0).getId();
//                // 再把模块编号塞给case表
//                apiCaseEntity.setObjectModuleId(moduleId);
//
//            }
//
//            // 如果模块不存在
//            if (objectModuleEntityList.size() == 0) {
//
//                // 新增模块表记录
//                ObjectModuleDTO moduleDTO = new ObjectModuleDTO();
//                moduleDTO.setModuleName(moduleName);
//                moduleDTO.setObjsystemId(systemId);
//                objectModuleService.addModule(moduleDTO);
//
//                // 拿到模块编号
//                List<ObjectModuleEntity> objectModuleEntityList1 = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId);
//                Integer moduleId = objectModuleEntityList1.get(0).getId();
//
//                // 再把模块编号塞给case表
//                apiCaseEntity.setObjectModuleId(moduleId);
//            }
//        } else {
//            // 如果系统表没记录，则先新增系统表的记录，并将新增后的系统编号塞给模块表，并新增模块表记录，最后将模块表的模块编号塞给case表
//            ObjectSystemDTO systemDTO = new ObjectSystemDTO();
//            systemDTO.setObjectSystem(apiTestCaseDTO.getObjectSystemName());
//            objectSystemService.addObjectSystem(systemDTO);
//
//            // 拿到系统编号
//            List<ObjectSystemEntity> byObjectSystem1 = objectSystemRepository.findByObjectSystem(apiTestCaseDTO.getObjectSystemName());
//            Integer systemId = byObjectSystem1.get(0).getId();
//
//            // 拿到模块名称
//            String moduleName = apiTestCaseDTO.getObjectModuleName();
//
//            // 新增模块表记录
//            ObjectModuleDTO moduleDTO = new ObjectModuleDTO();
//            moduleDTO.setModuleName(moduleName);
//            moduleDTO.setObjsystemId(systemId);
//            objectModuleService.addModule(moduleDTO);
//
//            // 拿到模块编号
//            List<ObjectModuleEntity> objectModuleEntityList1 = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId);
//            Integer moduleId = objectModuleEntityList1.get(0).getId();
//
//            // 再把模块编号塞给case表
//            apiCaseEntity.setObjectModuleId(moduleId);
//
//        }
//
//        // 上面判断完了之后，case表记录的模块编号也有了，然后要判断case编号是否有重复的，有重复的就提示“该用例编号已存在”，没有就新增case表记录
//        List<ApiTestCaseEntity> apiTestCaseEntity = apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum());
//        if (apiTestCaseEntity.size() != 0) {
//            throw new BusinessException("该用例编号已存在！");
//        }
//        apiCaseEntity.setCreatedTime(nowTime);
//        apiCaseEntity.setUpdatedTime(nowTime);
//
//        apiCaseEntity.setCreator(user.getUsername());
//        apiCaseEntity.setUpdatedBy(user.getUsername());
//        apiTestCaseRepository.save(apiCaseEntity);
    }

    @Override
    public List<ApiTestCaseEntity> queryApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        List<ApiTestCaseEntity> apiTestCaseEntityList = new ArrayList<>();
        String systemName = apiTestCaseDTO.getObjectSystemName();
        String moduleName = apiTestCaseDTO.getObjectModuleName();

        if (StringUtil.isEmpty(systemName) && StringUtil.isEmpty(moduleName)) {
            apiTestCaseEntityList = apiTestCaseRepository.findAll();
        }
        if (!StringUtil.isEmpty(systemName) && StringUtil.isEmpty(moduleName)) {
            apiTestCaseEntityList = apiTestCaseRepository.findByObjectSystemName(systemName);
        }
        if (StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName)) {
            apiTestCaseEntityList = apiTestCaseRepository.findByObjectModuleName(moduleName);
        }
        if (!StringUtil.isEmpty(systemName) && !StringUtil.isEmpty(moduleName)) {
            apiTestCaseEntityList = apiTestCaseRepository.findByObjectModuleNameAndObjectSystemName(moduleName, systemName);
        }
        if (apiTestCaseEntityList.size() != 0){

            return apiTestCaseEntityList;
        }else {
            throw new BusinessException("未查询出结果！");
        }
    }

    @Override
    public void editApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        ApiTestCaseEntity caseEntity = apiTestCaseRepository.getOne(apiTestCaseDTO.getId());
        caseEntity.setApiCaseName(apiTestCaseDTO.getApiCaseName());
        caseEntity.setApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        caseEntity.setObjectModuleName(apiTestCaseDTO.getObjectModuleName());
        caseEntity.setObjectSystemName(apiTestCaseDTO.getObjectSystemName());
        caseEntity.setUpdatedBy(user.getUsername());
        caseEntity.setUpdatedTime(new Date());
        caseEntity.setApiCaseDescription(apiTestCaseDTO.getApiCaseDescription());
        caseEntity.setApiCaseRequestParam(apiTestCaseDTO.getApiCaseRequestParam());
        caseEntity.setApiCaseRequestAddress(apiTestCaseDTO.getApiCaseRequestAddress());
        caseEntity.setApiCaseRequestMethod(apiTestCaseDTO.getApiCaseRequestMethod());
        caseEntity.setApiCaseExpectedResult(apiTestCaseDTO.getApiCaseExpectedResult());
        caseEntity.setApiCaseActualResult(apiTestCaseDTO.getApiCaseActualResult());
        caseEntity.setApi_testcase_remark(apiTestCaseDTO.getApiCaseRemark());

        /**
         * 根据传来的模块名和系统名，判断这2张表里是不是有记录：
         *                                  如果系统表有记录，
         *                                          模块表有记录，直接根据模块名拿到模块id，塞给case表
         *                                          模块表没记录，则先新增模块表，然后拿到模块id，塞给case表
         *                                  如果系统表没记录，则对应的模块表应该也是没记录的，则需要新增系统，再新增模块，最后拿到模块id
         */
        // 系统表有记录
        List<ObjectSystemEntity> systemEntityList = objectSystemRepository.findByObjectSystem(apiTestCaseDTO.getObjectSystemName());
        if(systemEntityList.size() != 0){
            List<ObjectModuleEntity> moduleEntityList = objectModuleRepositiry.findByModuleName(apiTestCaseDTO.getObjectModuleName());
            // 模块表无记录
            if (moduleEntityList.size() == 0){
                Integer systemId = systemEntityList.get(0).getId();
                ObjectModuleDTO objectModuleDTO = new ObjectModuleDTO();
                objectModuleDTO.setModuleName(apiTestCaseDTO.getObjectModuleName());
                objectModuleDTO.setObjsystemId(systemId);
                objectModuleService.addModule(objectModuleDTO);

                // 拿到模块id塞给case表
                List<ObjectModuleEntity> byModuleNameAndAndObjsystemId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(apiTestCaseDTO.getObjectModuleName(), systemId);
                Integer moduleId = byModuleNameAndAndObjsystemId.get(0).getId();
                caseEntity.setObjectModuleId(moduleId);
            }
            // 如果模块表有记录
            if(moduleEntityList.size() != 0){
                Integer systemId = systemEntityList.get(0).getId();
                List<ObjectModuleEntity> byModuleNameAndAndObjsystemId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(apiTestCaseDTO.getObjectModuleName(), systemId);
                Integer moduleId = byModuleNameAndAndObjsystemId.get(0).getId();
                caseEntity.setObjectModuleId(moduleId);
            }
        }
        // 如果系统表无记录
        if (systemEntityList.size() == 0){
            // 先新增系统
            ObjectSystemDTO objectSystemDTO = new ObjectSystemDTO();
            objectSystemDTO.setObjectSystem(apiTestCaseDTO.getObjectSystemName());
            objectSystemService.addObjectSystem(objectSystemDTO);
            // 拿到系统id
            systemEntityList = objectSystemRepository.findByObjectSystem(apiTestCaseDTO.getObjectSystemName());
            Integer systemId = systemEntityList.get(0).getId();
            // 新增模块
            ObjectModuleDTO objectModuleDTO = new ObjectModuleDTO();
            objectModuleDTO.setModuleName(apiTestCaseDTO.getObjectModuleName());
            objectModuleDTO.setObjsystemId(systemId);
            objectModuleService.addModule(objectModuleDTO);
            // 拿到模块id塞给case表
            List<ObjectModuleEntity> byModuleNameAndAndObjsystemId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(apiTestCaseDTO.getObjectModuleName(), systemId);
            Integer moduleId = byModuleNameAndAndObjsystemId.get(0).getId();
            caseEntity.setObjectModuleId(moduleId);
        }
        apiTestCaseRepository.save(caseEntity);
    }
}

