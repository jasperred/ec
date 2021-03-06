package com.sunmw.paipai.dao;

import com.sunmw.paipai.domain.PpOrderHead;
import com.sunmw.paipai.domain.PpOrderHeadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PpOrderHeadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int countByExample(PpOrderHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int deleteByExample(PpOrderHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer ppOrderHeadId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int insert(PpOrderHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int insertSelective(PpOrderHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    List<PpOrderHead> selectByExample(PpOrderHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    PpOrderHead selectByPrimaryKey(Integer ppOrderHeadId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PpOrderHead record, @Param("example") PpOrderHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PpOrderHead record, @Param("example") PpOrderHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PpOrderHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pp_order_head
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PpOrderHead record);
}