package com.sunny.service.impl;

import com.sunny.constant.ConfigConstant;
import com.sunny.exception.BusinessException;
import com.sunny.service.ConfigService;
import com.sunny.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ConfigService configService;

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
            if (localFile.exists()){
                boolean delete = localFile.delete();
                if (delete == false){
                    throw new BusinessException("该路径下不存在同名文件，删除失败！");
                }
            }
            // 文件不存在则创建新文件
            if (!localFile.exists()){
                localFile.createNewFile();
            }

            // 文件创建成功后，写入内容到文件里
            fileOutputStream = new FileOutputStream(localFile);
            inputStream = file.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,len);
            }
            fileOutputStream.flush();

        } catch (FileNotFoundException e){
            e.printStackTrace();
            throw new BusinessException("文件未找到！");
        } catch (IOException e){
            e.printStackTrace();
            throw new BusinessException("写入文件出错！");
        }finally {
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
                throw new BusinessException("关闭文件流出错！");
            }
        }
    }
}
