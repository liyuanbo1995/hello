package com.xinba.flow.service.impl;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.algorithm.CutList;
import com.xinba.flow.algorithm.DemoThread;
import com.xinba.flow.algorithm.IccidThread;
import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.SimActiveUnbind;
import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.entity.TcFirmPack;
import com.xinba.flow.entity.form.FlowPol;
import com.xinba.flow.mapper.SimInfoMapper;
import com.xinba.flow.service.SimInfoService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Console;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (SimInfo)表服务实现类
 *
 * @author tsw
 * @since 2019-07-25 14:01:09
 */
@Service("simInfoService")
public class SimInfoServiceImpl implements SimInfoService {
    @Resource
    private SimInfoMapper simInfoMapper;

    //excel导入sim卡
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Wrapper addSimAuto(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<SimInfo> simInfoList = new ArrayList<SimInfo>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return WrapMapper.wrap(500, "上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        SimInfo simInfo;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            simInfo = new SimInfo();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String iccid = row.getCell(0).getStringCellValue();
            System.out.println("hang" + r + "  " + iccid + "  " + iccid.getClass().getName());
            if (iccid == null || iccid.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,iccid未填写)");
            }
            simInfo.setIccid(iccid);
            simInfoList.add(simInfo);
        }
        DemoThread demoThread = new DemoThread(simInfoList, simInfoMapper);
        Thread thread = new Thread(demoThread);
        thread.start();
        return WrapMapper.ok();
    }

    //查询单卡信息
    public SimInfo selectByIccid(String iccid) {
        return simInfoMapper.selectByIccid(iccid);
    }

    //查询卡对应套餐
    public List<TcFirmPack> selectPack(Integer firmId, Integer firmTypeId, Integer typeProductId) {
        return simInfoMapper.selectPack(firmId, firmTypeId, typeProductId);
    }

    //更新卡
    public void updateSim(SimInfo simInfo) {
        simInfo.setSynNum(selectByIccid(simInfo.getIccid()).getSynNum() + 1);
        simInfoMapper.updateSim(simInfo);
    }

    //查询卡的信息
    public List<SimInfo> selectSimInfo(String iccid, String updateTime, String phone, String vin, String msisdn, String status, String ratePlan, String communicationPlan) {
        return simInfoMapper.selectSimInfo(iccid, updateTime, phone, vin, msisdn, status, ratePlan, communicationPlan);
    }

    //查询流量池
    public Map<String, Integer> getFlowPol() {
        Map<String, Integer> map = new HashMap<>();
        FlowPol simInfoFlowPol = simInfoMapper.getFlowPol();
        map.put("可用总流量", simInfoFlowPol.getTotalFlowYl());
        map.put("基础总流量", simInfoFlowPol.getTotalFlowJc());
        map.put("已用流量", simInfoFlowPol.getUsedFlow());
        map.put("剩余流量", simInfoFlowPol.getRemainFlow());
        return map;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Wrapper addSimVinInfo(String fileName, MultipartFile file) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean notNull = false;
        List<IccidVin> iccidVinList = new ArrayList<IccidVin>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return WrapMapper.wrap(500, "上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        IccidVin iccidVin;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            iccidVin = new IccidVin();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String iccid = row.getCell(0).getStringCellValue();
            if (iccid == null || iccid.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,iccid未填写)");
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String phone = row.getCell(1).getStringCellValue();
            if (phone == null || phone.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,phone未填写)");
            }
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String vin = row.getCell(2).getStringCellValue();
            if (vin == null || vin.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,vin未填写)");
            }
            String date = row.getCell(3).getStringCellValue();
            iccidVin.setIccid(iccid);
            iccidVin.setPhone(phone);
            iccidVin.setVin(vin);
            iccidVin.setOrderTime(date);
            iccidVinList.add(iccidVin);
        }
        IccidThread iccidThread = new IccidThread(iccidVinList, simInfoMapper);
        Thread thread = new Thread(iccidThread);
        thread.start();
        return WrapMapper.ok();
    }

    public List<String> groupByRatePlan() {
        return simInfoMapper.groupByRatePlan();
    }

    public List<String> groupByCommunicationPlan() {
        return simInfoMapper.groupByCommunicationPlan();
    }

    public List<SimActiveUnbind> getSimActiveOrUnbindRecord(String iccid, String beginTime, String endTime, String status) {
        return simInfoMapper.getSimActiveOrUnbindRecord(iccid, beginTime, endTime, status);
    }

    //导入激活卡的数据
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Wrapper addSimActiveRecord(String fileName, MultipartFile file) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean notNull = false;
        List<SimActiveUnbind> simActiveRecordList = new ArrayList<SimActiveUnbind>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return WrapMapper.wrap(500, "上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        SimActiveUnbind simActiveRecord;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            simActiveRecord = new SimActiveUnbind();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String iccid = row.getCell(0).getStringCellValue();
            if (iccid == null || iccid.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,iccid未填写)");
            }
            String date = row.getCell(1).getStringCellValue();
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String status = row.getCell(2).getStringCellValue();
            if (status == null || status.isEmpty()) {
                return WrapMapper.wrap(500, "导入失败(第" + (r + 1) + "行,status未填写)");
            }

            simActiveRecord.setIccid(iccid);
            simActiveRecord.setCreateTime(date);
            simActiveRecord.setStatus(status);
            simActiveRecordList.add(simActiveRecord);
        }
        simInfoMapper.addSimActiveRecord(simActiveRecordList);
        return WrapMapper.ok();
    }
}