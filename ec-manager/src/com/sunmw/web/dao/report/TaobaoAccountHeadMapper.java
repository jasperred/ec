package com.sunmw.web.dao.report;

import com.sunmw.web.domain.report.TaobaoAccountHead;
import com.sunmw.web.domain.report.TaobaoAccountHeadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaobaoAccountHeadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int countByExample(TaobaoAccountHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int deleteByExample(TaobaoAccountHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer accountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int insert(TaobaoAccountHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int insertSelective(TaobaoAccountHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    List<TaobaoAccountHead> selectByExample(TaobaoAccountHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    TaobaoAccountHead selectByPrimaryKey(Integer accountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaobaoAccountHead record, @Param("example") TaobaoAccountHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaobaoAccountHead record, @Param("example") TaobaoAccountHeadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaobaoAccountHead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taobao_account_head
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaobaoAccountHead record);
}