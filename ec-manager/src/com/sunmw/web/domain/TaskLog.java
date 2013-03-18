package com.sunmw.web.domain;

import java.util.Date;

public class TaskLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_log.task_log_id
     *
     * @mbggenerated
     */
    private Integer taskLogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_log.task_id
     *
     * @mbggenerated
     */
    private Long taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_log.log_content
     *
     * @mbggenerated
     */
    private String logContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_log.log_start_time
     *
     * @mbggenerated
     */
    private Date logStartTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_log.log_end_time
     *
     * @mbggenerated
     */
    private Date logEndTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_log.task_log_id
     *
     * @return the value of task_log.task_log_id
     *
     * @mbggenerated
     */
    public Integer getTaskLogId() {
        return taskLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_log.task_log_id
     *
     * @param taskLogId the value for task_log.task_log_id
     *
     * @mbggenerated
     */
    public void setTaskLogId(Integer taskLogId) {
        this.taskLogId = taskLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_log.task_id
     *
     * @return the value of task_log.task_id
     *
     * @mbggenerated
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_log.task_id
     *
     * @param taskId the value for task_log.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_log.log_content
     *
     * @return the value of task_log.log_content
     *
     * @mbggenerated
     */
    public String getLogContent() {
        return logContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_log.log_content
     *
     * @param logContent the value for task_log.log_content
     *
     * @mbggenerated
     */
    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_log.log_start_time
     *
     * @return the value of task_log.log_start_time
     *
     * @mbggenerated
     */
    public Date getLogStartTime() {
        return logStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_log.log_start_time
     *
     * @param logStartTime the value for task_log.log_start_time
     *
     * @mbggenerated
     */
    public void setLogStartTime(Date logStartTime) {
        this.logStartTime = logStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_log.log_end_time
     *
     * @return the value of task_log.log_end_time
     *
     * @mbggenerated
     */
    public Date getLogEndTime() {
        return logEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_log.log_end_time
     *
     * @param logEndTime the value for task_log.log_end_time
     *
     * @mbggenerated
     */
    public void setLogEndTime(Date logEndTime) {
        this.logEndTime = logEndTime;
    }
}