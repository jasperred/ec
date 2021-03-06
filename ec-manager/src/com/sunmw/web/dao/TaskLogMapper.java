package com.sunmw.web.dao;

import com.sunmw.web.domain.TaskLog;
import com.sunmw.web.domain.TaskLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int countByExample(TaskLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int deleteByExample(TaskLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer taskLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int insert(TaskLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int insertSelective(TaskLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    List<TaskLog> selectByExample(TaskLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    TaskLog selectByPrimaryKey(Integer taskLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskLog record, @Param("example") TaskLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskLog record, @Param("example") TaskLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskLog record);
}