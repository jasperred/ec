package com.sunmw.web.dao;

import com.sunmw.web.domain.TaskQueue;
import com.sunmw.web.domain.TaskQueueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskQueueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int countByExample(TaskQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int deleteByExample(TaskQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer taskQueueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int insert(TaskQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int insertSelective(TaskQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    List<TaskQueue> selectByExample(TaskQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    TaskQueue selectByPrimaryKey(Integer taskQueueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskQueue record, @Param("example") TaskQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskQueue record, @Param("example") TaskQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_queue
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskQueue record);
}