package com.sunny.service.impl;

import com.alibaba.fastjson.JSON;
import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ObjectApiEntity;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ApiTestCaseRepository;
import com.sunny.repository.ObjectApiRepository;
import com.sunny.repository.ObjectModuleRepository;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ApiTestCaseService;
import com.sunny.service.ObjectApiService;
import com.sunny.service.ObjectModuleService;
import com.sunny.service.ObjectSystemService;
import com.sunny.util.GetRequestUrl;
import com.sunny.util.HttpUtil;
import com.sunny.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private ObjectModuleRepository objectModuleRepository;

    @Resource
    private ObjectSystemService objectSystemService;

    @Resource
    private ObjectModuleService objectModuleService;

    @Resource
    private ObjectApiService objectApiService;

    /**
     * 入参：
     * objectApiId":"接口编号",
     * apiCaseNum":"接口用例编号",
     * apiCaseName":"接口用例名称",
     * apiCaseDescription":"用例描述",
     * apiCaseRequestHeader":"接口请求头",
     * apiCaseRequestParam":"接口请求入参",
     * apiCaseExpectedResult":"预期结果",
     * apiCaseActualResult":"实际结果",
     * isPassed":"是否通过",
     * apiCaseRemark":"备注"
     */
    @Override
    public void addApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        // 校验用例编号
        List<ApiTestCaseEntity> byApiCaseNum = apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        if (byApiCaseNum.size() != 0) {
            throw new BusinessException("该用例编号:" + byApiCaseNum.get(0).getApiCaseNum() + "已存在！");
        }

        ApiTestCaseEntity apiCaseEntity = apiTestCaseDTO.getEntity();
        Date nowTime = new Date();
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        apiCaseEntity.setCreatedTime(nowTime);
        apiCaseEntity.setUpdatedTime(nowTime);
        apiCaseEntity.setCreator(user.getUsername());
        apiCaseEntity.setUpdatedBy(user.getUsername());
        apiTestCaseRepository.save(apiCaseEntity);
    }

    @Override
    public void deleteApiCase(List<Integer> idList) {
        if (idList.size() == 0) {
            return;
        }
        for (Integer apiCaseId : idList) {
            try {
                apiTestCaseRepository.deleteById(apiCaseId);
            } catch (Exception e) {
                throw new BusinessException("id为" + apiCaseId + "的接口用例不存在！");
            }
        }
    }

    @Override
    public void editApiCase(Integer apiCaseId, ApiTestCaseDTO apiTestCaseDTO) {
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        ApiTestCaseEntity apiTestCaseEntity = apiTestCaseRepository.getOne(apiCaseId);
        if (!apiTestCaseEntity.getApiCaseNum().equals(apiTestCaseDTO.getApiCaseNum()) &&
                apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum()).size() != 0) {
            throw new BusinessException("该案例编号已存在！");
        }

        apiTestCaseEntity.setApiCaseName(apiTestCaseDTO.getApiCaseName());
        apiTestCaseEntity.setApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        apiTestCaseEntity.setApiCaseDescription(apiTestCaseDTO.getApiCaseDescription());
        apiTestCaseEntity.setApiCaseRequestHeader(apiTestCaseDTO.getApiCaseRequestHeader());
        apiTestCaseEntity.setApiCaseRequestParam(apiTestCaseDTO.getApiCaseRequestParam());
        apiTestCaseEntity.setApiCaseExpectedResult(apiTestCaseDTO.getApiCaseExpectedResult());
        apiTestCaseEntity.setApiCaseActualResult(apiTestCaseDTO.getApiCaseActualResult());
        apiTestCaseEntity.setIsPassed(apiTestCaseDTO.getIsPassed());
        apiTestCaseEntity.setApiCaseRemark(apiTestCaseDTO.getApiCaseRemark());
        apiTestCaseEntity.setUpdatedTime(new Date());
        apiTestCaseEntity.setUpdatedBy(user.getUsername());
        apiTestCaseRepository.save(apiTestCaseEntity);
    }

    @Override
    public List<ApiTestCaseEntity> queryApiCase(Integer apiId) {
        List<ApiTestCaseEntity> apiTestCaseEntityList = apiTestCaseRepository.findByObjectApiId(apiId);
        return apiTestCaseEntityList;
    }

    @Override
    public List<ApiTestCaseEntity> queryAllApiCases(Map<String, Object> map) {
        Integer systemId = (Integer) map.get("systemId");
        Integer moduleId = (Integer) map.get("moduleId");
        Integer apiId = (Integer) map.get("apiId");
        String caseName = (String) map.get("apiCaseName");

        final boolean[] a = {true};

        List<ApiTestCaseEntity> all = apiTestCaseRepository.findAll(new Specification<ApiTestCaseEntity>() {
            @Override
            public Predicate toPredicate(Root<ApiTestCaseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate restriction = criteriaQuery.where().getRestriction();
                // 所有符合条件的apiId
                List<Integer> apiIdList = new ArrayList<>();

                // 通过apiId
                if (apiId != null) {
                    apiIdList.add(apiId);
                }

                // 模块id不为空，则找到接口表下所有模块id为这个的接口记录，并通过这个记录，获取对应的接口id，最终添加到apiIdList中
                else if (moduleId != null) {
                    List<ObjectApiEntity> apiEntityList = objectApiService.getByModuleId(moduleId);
                    for (ObjectApiEntity apiEntity : apiEntityList) {
                        apiIdList.add(apiEntity.getId());
                    }
                }

                // 系统id
                else if (systemId != null) {
                    List<ObjectModuleEntity> bySystemId = objectModuleService.getBySystemId(systemId);
                    for (ObjectModuleEntity objectModuleEntity : bySystemId) {
                        List<ObjectApiEntity> apiEntityList = objectApiService.getByModuleId(objectModuleEntity.getId());
                        for (ObjectApiEntity apiEntity : apiEntityList) {
                            apiIdList.add(apiEntity.getId());
                        }
                    }
                }

                // 全部
                else {
                    return restriction;
                }

                // 动态筛选
                if (apiIdList.size() == 0) {
                    a[0] = false;
                } else {
                    restriction = criteriaBuilder.equal(root.get("objectApiId").as(Integer.class), apiIdList.get(0));
                    for (Integer integer : apiIdList) {
                        restriction = criteriaBuilder.or(restriction, criteriaBuilder.equal(root.get("objectApiId").as(Integer.class), integer));
                    }

                    if (!StringUtil.isEmpty(caseName)) {
                        restriction = criteriaBuilder.and(restriction, criteriaBuilder.equal(root.get("apiCaseName").as(String.class), caseName));
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

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> runApiCase(Integer apiCaseId, boolean isDebug) {
        Map<String, Object> result = new HashMap<>();
        ApiTestCaseEntity apiTestCaseEntity = apiTestCaseRepository.getOne(apiCaseId);

        Integer objectApiId = apiTestCaseEntity.getObjectApiId();
        ObjectApiEntity objectApiEntity = objectApiRepository.getOne(objectApiId);

        // 请求地址
        String url = objectApiEntity.getApiAddress();
        // 请求方式
        String method = objectApiEntity.getApiMethod();
        // 请求头
        Map<String, String> headers = new HashMap<>();
        // 请求体
        String body = null;
        // 实际响应结果
        String response = null;
        // 是否通过
        boolean isPass = true;
        // 备注
        String remark = null;

        // 解析请求头
        String apiCaseRequestHeader = apiTestCaseEntity.getApiCaseRequestHeader();
        if (!StringUtil.isEmpty(apiCaseRequestHeader)) {
            headers = JSON.parseObject(apiCaseRequestHeader, Map.class);
        }

        // 解析请求参数
        String apiCaseRequestParam = apiTestCaseEntity.getApiCaseRequestParam();
        if (!StringUtil.isEmpty(apiCaseRequestParam)) {
            List<Object> list = JSON.parseObject(apiCaseRequestParam, List.class);
            for (Object item : list) {
                Map<String, Object> itemMap = JSON.parseObject(item.toString(), Map.class);
                String type = itemMap.get("type") == null ? "" : itemMap.get("type").toString();
                String content = itemMap.get("content") == null ? "" : itemMap.get("content").toString();

                // params : url中的参数
                if ("params".equals(type)) {
                    url = url + GetRequestUrl.getRequestUrl(content);
                }

                // body
                if ("body".equals(type)) {
                    body = content;
                }
            }
        }

        // 发送请求
        HttpUtil httpUtil;
        try {
            httpUtil = new HttpUtil(url, method);
            httpUtil.setHeader(headers);
            // 实际响应结果
            response = httpUtil.send(body);
            Map<String, Object> responseJson = JSON.parseObject(response, Map.class);

            // 比对预期结果
            String apiCaseExpectedResult = apiTestCaseEntity.getApiCaseExpectedResult();
            Map<String, Object> apiCaseExpectedResultJson = JSON.parseObject(apiCaseExpectedResult, Map.class);
            Set<String> keySet = apiCaseExpectedResultJson.keySet();
            for (String key : keySet) {
                String value = apiCaseExpectedResultJson.get(key).toString();
                String value2 = responseJson.get(key) == null ? "" : responseJson.get(key).toString();
                if (!value.equals(value2)) {
                    throw new Exception("实际结果与预期结果不一致");
                }
            }
        } catch (Exception e) {
            isPass = false;
            remark = e.getMessage();
        } finally {
            // 如果是调试模式，不进行入库
            if (!isDebug) {
                apiTestCaseEntity.setApiCaseActualResult(response);
                if (isPass) {
                    apiTestCaseEntity.setIsPassed("Pass");
                } else {
                    apiTestCaseEntity.setIsPassed("Failed");
                }
                apiTestCaseEntity.setApiCaseRemark(remark);
                apiTestCaseRepository.save(apiTestCaseEntity);
            }

            result.put("response", response);
            result.put("remark", remark);
        }

        return result;
    }

    @Override
    public void runApiCase(List<Integer> apiCaseIdList) {
        for (Integer apiCaseId : apiCaseIdList) {
            if (apiCaseId == null) {
                continue;
            }
            runApiCase(apiCaseId, false);
        }
    }

}

