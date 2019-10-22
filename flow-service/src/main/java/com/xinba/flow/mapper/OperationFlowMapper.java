package com.xinba.flow.mapper;

import com.xinba.flow.entity.*;
import com.xinba.flow.entity.form.FirmTypeProduct;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * (OperationFlow)表数据库访问层
 *
 * @author makejava
 * @since 2019-07-16 09:03:23
 */
public interface OperationFlowMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OperationFlow queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OperationFlow> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param operationFlow 实例对象
     * @return 对象列表
     */
    List<OperationFlow> queryAll(OperationFlow operationFlow);

    /**
     * 新增数据
     *
     * @param operationFlow 实例对象
     * @return 影响行数
     */
    void insertBasePack(OperationFlow operationFlow);

    /**
     * 修改数据
     *
     * @param operationFlow 实例对象
     * @return 影响行数
     */
    int update(OperationFlow operationFlow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<OperationFlow>selectAll();
    List<OperationFlow>selectOperationPack(@Param("name") String name,@Param("code")String code,@Param("type")Integer type);
    void deleteByCode(@Param("id")int id);
    void updateStatus(@Param("id")int id);

    void insertFirmPack(TcFirmPack tcFirmPack);

    List<TcFirmPack>selectAllFirmPa(@Param("name")String name, @Param("info")String info, @Param("firmId")Integer firmId,@Param("firmTypeId")Integer firmTypeId,@Param("typeProductId")Integer typeProductId);

    void insertRelation(TcPackRelate tcPackRelate);

    List<TcPackRelate>selectPack(@Param("packId")int packId);
    OperationFlow selectPackById(@Param("id")int id);
    void addPack(TcPack tcPack);
    List<TcPack>getPack(@Param("name") String name,@Param("cycle")Integer cycle);
    List<TcFirmPack> selectPackByCar(@Param("carType")String carType);
    TcPack getPackById(@Param("packId") Integer packId);
    String getFirmName(@Param("firmId")Integer firmId);
    String getFirmTypeName(@Param("firmTypeId")Integer firmTypeId);
    String getTypeProductName(@Param("typeProductId")Integer typeProductId);
    int countItems(@Param("firmId") Integer firmId,@Param("firmTypeId")Integer firmTypeId,@Param("typeProductId")Integer typeProductId,@Param("packId")Integer packId);
    int deleteFirmPack(@Param("id")Integer id);
    int deletePack(@Param("packId")Integer packId);
    List<Integer>getFirmPackByPackId(@Param("packId") Integer packId);
    int updateFirmPack(TcFirmPack tcFirmPack);
}