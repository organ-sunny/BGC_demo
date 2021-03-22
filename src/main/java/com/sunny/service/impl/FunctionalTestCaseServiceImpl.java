package com.sunny.service.impl;

import com.sunny.dto.FunctionalTestCaseDTO;
import com.sunny.dto.ObjectModuleDTO;
import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.FunctionalTestCaseEntity;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.FunctionalTestCaseRepository;
import com.sunny.repository.ObjectModuleRepository;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.FunctionalTestCaseService;
import com.sunny.service.ObjectModuleService;
import com.sunny.service.ObjectSystemService;
import com.sunny.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FunctionalTestCaseServiceImpl implements FunctionalTestCaseService {

    @Resource
    private ObjectSystemRepository objectSystemRepository;

    @Resource
    private ObjectSystemService objectSystemService;

    @Resource
    private ObjectModuleRepository objectModuleRepository;

    @Resource
    private ObjectModuleService objectModuleService;

    @Resource
    private FunctionalTestCaseRepository functionalTestCaseRepository;

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 入参:
     * 被测系统：objectSystemName
     * 被测模块：objectModuleName
     * 用例名称：caseName
     * 用例编号：caseNum
     * 用例描述：caseDescription
     * 前置条件：preconditions
     * 测试步骤：testSteps
     * 预期结果：expectedResult
     * 实际结果：actualResult
     * 是否通过：isPassed
     * 备   注：caseRemark
     *
     * @param functionalTestCaseDTO
     */
    @Override
    public void addFunctionalCase(FunctionalTestCaseDTO functionalTestCaseDTO) {

        // 判断功能测试用例是否存在
        List<FunctionalTestCaseEntity> caseEntityList = functionalTestCaseRepository.findByCaseNum(functionalTestCaseDTO.getCaseNum());
        if (caseEntityList.size() == 0) {
            throw new BusinessException("用例编号" + functionalTestCaseDTO.getCaseNum() + "已存在！");
        }

        // 先判断系统是否存在
        String objectSystemName = functionalTestCaseDTO.getObjectSystemName();
        List<ObjectSystemEntity> systemEntityList = objectSystemRepository.findByObjectSystem(objectSystemName);
        ObjectSystemEntity objectSystemEntity;
        // 如果系统不存在则新增到系统表
        if (systemEntityList.size() == 0) {
            ObjectSystemDTO objectSystemDTO = new ObjectSystemDTO();
            objectSystemDTO.setObjectSystem(objectSystemName);
            objectSystemEntity = objectSystemService.addObjectSystem(objectSystemDTO);
        } else {
            objectSystemEntity = systemEntityList.get(0);
        }

        // 判断模块是否存在
        String objectModuleName = functionalTestCaseDTO.getObjectModuleName();
        List<ObjectModuleEntity> moduleEntityList = objectModuleRepository.findByModuleNameAndAndObjsystemId(objectModuleName, objectSystemEntity.getId());
        ObjectModuleEntity objectModuleEntity;
        // 如果模块不存在则新增到模块表
        if (moduleEntityList.size() == 0) {
            ObjectModuleDTO objectModuleDTO = new ObjectModuleDTO();
            objectModuleDTO.setModuleName(objectModuleName);
            objectModuleDTO.setObjsystemId(objectSystemEntity.getId());
            objectModuleEntity = objectModuleService.addModule(objectModuleDTO);
        } else {
            objectModuleEntity = moduleEntityList.get(0);
        }

        // 当前操作用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        Integer moduleId = objectModuleEntity.getId();
        FunctionalTestCaseEntity functionalTestCaseEntity = functionalTestCaseDTO.getEntity();
        functionalTestCaseEntity.setModuleId(moduleId);
        functionalTestCaseEntity.setCreator(user.getUsername());
        functionalTestCaseEntity.setCreatedTime(new Date());
        functionalTestCaseEntity.setUpdatedBy(user.getUsername());
        functionalTestCaseEntity.setUpdatedTime(new Date());
        functionalTestCaseRepository.save(functionalTestCaseEntity);
    }

    @Override
    public void deleteFunctionalCase(Integer id) {
        functionalTestCaseRepository.deleteById(id);
    }

    @Override
    public List<FunctionalTestCaseEntity> queryFunctionalTestCase(Map<String, Object> map) {
        Integer systemId = (Integer) map.get("systemId");
        Integer moduleId = (Integer) map.get("moduleId");
        String caseName = (String) map.get("functinalTestCaseName");

        boolean[] a = {true};

        final List<FunctionalTestCaseEntity> all = functionalTestCaseRepository.findAll(new Specification<FunctionalTestCaseEntity>() {
            @Override
            public Predicate toPredicate(Root<FunctionalTestCaseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate restriction = criteriaQuery.where().getRestriction();

                List<Integer> moduleIdList = new ArrayList<>();

                if (moduleId != null) {
                    moduleIdList.add(moduleId);
                }

                if (systemId != null) {
                    List<ObjectModuleEntity> bySystemId = objectModuleService.getBySystemId(systemId);
                    for (ObjectModuleEntity objectModuleEntity : bySystemId) {
                        List<FunctionalTestCaseEntity> caseEntityList = functionalTestCaseRepository.findByModuleId(objectModuleEntity.getId());
                        for (FunctionalTestCaseEntity functionalTestCaseEntity : caseEntityList) {
                            moduleIdList.add(functionalTestCaseEntity.getModuleId());
                        }
                    }
                } else {
                    return restriction;
                }

                if (moduleIdList.size() == 0) {
                    a[0] = false;
                } else {
                    restriction = criteriaBuilder.equal(root.get("moduleId").as(Integer.class), moduleIdList.get(0));
                    for (Integer integer : moduleIdList) {
                        restriction = criteriaBuilder.or(restriction, criteriaBuilder.equal(root.get("moduleId").as(Integer.class), integer));
                    }

                    if (!StringUtil.isEmpty(caseName)) {
                        restriction = criteriaBuilder.and(restriction, criteriaBuilder.equal(root.get("functinalTestCaseName").as(String.class), caseName));
                    }
                }

                return restriction;
            }
        });

        if (a[0]) {
            return all;
        } else {
            return new ArrayList<>();
        }

    }
}
