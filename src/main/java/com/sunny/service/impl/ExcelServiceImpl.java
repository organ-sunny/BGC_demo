package com.sunny.service.impl;

import com.sunny.constant.ConfigConstant;
import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.exception.BusinessException;
import com.sunny.service.ApiTestCaseService;
import com.sunny.service.ConfigService;
import com.sunny.service.ExcelService;
import com.sunny.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ConfigService configService;

    @Resource
    private ApiTestCaseService apiTestCaseService;

    @Override
    public void uploadExcel(MultipartFile file) {

        File localFile = new File(configService.getUploadExcelPath(ConfigConstant.UPLOAD_EXCEL_PATH));
        // 创建文件缓存目录
        localFile.mkdirs();

        // 获取原始文件名
        String originFileName = file.getOriginalFilename();

        // 重新定位到文件
        localFile = new File(localFile.getAbsolutePath(), originFileName);
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            // 如果存在该文件，则先删除
            if (localFile.exists()) {
                boolean delete = localFile.delete();
                if (delete == false) {
                    throw new BusinessException("该路径下不存在同名文件，删除失败！");
                }
            }
            // 文件不存在则创建新文件
            if (!localFile.exists()) {
                localFile.createNewFile();
            }

            // 文件创建成功后，写入内容到文件里
            fileOutputStream = new FileOutputStream(localFile);
            inputStream = file.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new BusinessException("文件未找到！");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("写入文件出错！");
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("关闭文件流出错！");
            }
        }

        // 读取Excel文件中接口案例，并写入数据库
        ExcelUtil excelUtil = new ExcelUtil(localFile.getAbsolutePath(), 0);
        List<Map<String, Object>> allDataOfMap = excelUtil.getAllDataOfMap();

        for (int i = 0; i < allDataOfMap.size(); i++) {

            Map<String, Object> stringObjectMap = allDataOfMap.get(i);
            ApiTestCaseDTO apiTestCaseDTO = new ApiTestCaseDTO();

            /**
             * objectSystemName":"系统名称",
             * objectModuleName":"模块名称",
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
            String objectSystemName = (String) stringObjectMap.get("OBJECT_SYSTEM_NAME");
            String objectModuleName = (String) stringObjectMap.get("OBJECT_MODULE_NAME");
            String apiCaseNum = (String) stringObjectMap.get("API_TESTCASE_NUM");
            String apiCaseName = (String) stringObjectMap.get("API_TESTCASE_NAME");
            String apiCaseDescription = (String) stringObjectMap.get("API_TESTCASE_DESCRIPTION");
            String apiCaseRequestAddress = (String) stringObjectMap.get("API_TESTCASE_REQUESTADDRESS");
            String apiCaseRequestMethod = (String) stringObjectMap.get("API_TESTCASE_REQUESTMETHOD");
            String apiCaseRequestHeader = (String) stringObjectMap.get("API_TESTCASE_REQUESTHEADER");
            String apiCaseRequestParam = (String) stringObjectMap.get("API_TESTCASE_REQUESTPARA");
            String apiCaseExpectedResult = (String) stringObjectMap.get("API_TESTCASE_EXPECTEDRESULT");
            String apiCaseActualResult = (String) stringObjectMap.get("API_TESTCASE_ACTUALRESULT");
            String apiCaseRemark = (String) stringObjectMap.get("API_TESTCASE_REMARK");

            apiTestCaseDTO.setObjectSystemName(objectSystemName);
            apiTestCaseDTO.setObjectModuleName(objectModuleName);
            apiTestCaseDTO.setApiCaseNum(apiCaseNum);
            apiTestCaseDTO.setApiCaseName(apiCaseName);
            apiTestCaseDTO.setApiCaseDescription(apiCaseDescription);
            apiTestCaseDTO.setApiCaseRequestAddress(apiCaseRequestAddress);
            apiTestCaseDTO.setApiCaseRequestMethod(apiCaseRequestMethod);
            apiTestCaseDTO.setApiCaseRequestHeader(apiCaseRequestHeader);
            apiTestCaseDTO.setApiCaseRequestParam(apiCaseRequestParam);
            apiTestCaseDTO.setApiCaseExpectedResult(apiCaseExpectedResult);
            apiTestCaseDTO.setApiCaseActualResult(apiCaseActualResult);
            apiTestCaseDTO.setApiCaseRemark(apiCaseRemark);

            apiTestCaseService.addApiCase(apiTestCaseDTO);
        }
    }

    @Override
    public void downloadExcel(HttpServletResponse httpServletResponse) {
        /**
         * 下载文件
         *
         * @param filePath 文件路径
         * @param httpServletResponse 响应
         * @param fileName 文件名
         * */
        String filePath = configService.getDownloadExcelPath(ConfigConstant.DOWNLOAD_EXCEL_PATH);
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("文件不存在");
        }

        try {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        } catch (Exception e) {
            throw new BusinessException("设置响应内容错误：" + e.getMessage());
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            httpServletResponse.setHeader("Content-Length", String.valueOf(inputStream.available()));
            outputStream = httpServletResponse.getOutputStream();
            byte[] bytes = new byte[2048];
            int read = inputStream.read(bytes);
            while (read != -1) {
                outputStream.write(bytes);
                read = inputStream.read(bytes);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("关闭文件流出错！");
            }
        }
    }

}
