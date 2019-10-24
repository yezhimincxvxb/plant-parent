package com.moguying.plant.core.service.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.dto.AdminMessage;
import com.moguying.plant.core.service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Slf4j
public class DownloadService<T extends BaseMapper, K> implements Runnable {

    private T dao;

    private PageSearch<K> search;

    private Class<?> classes;

    private DownloadInfo downloadInfo;

    public DownloadService(T dao, PageSearch<K> search, Class<?> classes, DownloadInfo downloadInfo) {
        this.dao = dao;
        this.search = search;
        this.classes = classes;
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void run() {
        try {
            List<K> data = dao.selectList(null);
            if (data.size() <= 0) return;
            String fileName = downloadInfo.getFileName();
            ServletContext servletContext = downloadInfo.getContext();
            File savePath =  new File(servletContext.getRealPath(downloadInfo.getSavePath()));

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(fileName, fileName, ExcelType.HSSF), classes, data);
            if(!savePath.exists())
                savePath.mkdirs();
            String saveFile = fileName.concat(RandomStringUtils.randomNumeric(8)).concat(".xls");
            FileOutputStream fos = new FileOutputStream(savePath.getPath().concat("/").concat(saveFile));
            workbook.write(fos);
            fos.close();
            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            AdminUserService service = context.getBean(AdminUserService.class);
            AdminMessage add = new AdminMessage();
            add.setAddTime(new Date());
            add.setDownloadUrl(downloadInfo.getSavePath().concat(saveFile));
            add.setUserId(downloadInfo.getUserId());
            add.setMessage("下载".concat(fileName).concat("表格"));
            service.addAdminMessage(add);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
