/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan-service
 * Author: author  wushyue@gmail.com
 * Create On: Apr 27, 2018 11:02:30 AM
 * Modify On: Apr 27, 2018 11:02:30 AM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.task.service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserInviteService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.exception.TaskException;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.common.util.PaginationUtil;
import com.artqiyi.tanqiu.payment.domain.CoinTranslog;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.task.domain.Task;
import com.artqiyi.tanqiu.task.domain.TaskExample;
import com.artqiyi.tanqiu.task.domain.TaskLog;
import com.artqiyi.tanqiu.task.domain.TaskLogExample;
import com.artqiyi.tanqiu.task.domain.TaskRewardRule;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleExample;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleMapExample;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleMapKey;
import com.artqiyi.tanqiu.task.domain.TaskLogExample.Criteria;
import com.artqiyi.tanqiu.task.domain.ext.MyTask;
import com.artqiyi.tanqiu.task.mapper.TaskLogMapper;
import com.artqiyi.tanqiu.task.mapper.TaskMapper;
import com.artqiyi.tanqiu.task.mapper.TaskRewardRuleMapMapper;
import com.artqiyi.tanqiu.task.mapper.TaskRewardRuleMapper;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;

/**
 * 任务服务类
 *
 */
@Service("taskService")
public class TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Autowired
    TaskMapper taskMapper;


    @Autowired
    private TaskRewardRuleMapper taskRewardRuleMapper;

    @Autowired
    private TaskRewardRuleMapMapper taskRewardRuleMapMapper;

    @Autowired
    private IUserInfoService userInfoService;
    
    @Autowired
    private IUserInviteService userInviteService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private TransLogService transLogService;

    	
    
    /**
     * 获取每日领奖任务
     * @param userId
     * @return
     */
    public MyTask findReceivePrizeTask(Long userId) {
    	 TaskExample example = new TaskExample();
    	 com.artqiyi.tanqiu.task.domain.TaskExample.Criteria criteria = example.createCriteria();
    	 criteria.andTaskDisplayEqualTo(SystemConstant.TASK_DISP_RENDER);
    	 criteria.andTaskTypeEqualTo("ReceivePrize"); //每日领奖

         List<Task> list = taskMapper.selectByExample(example);
         
         if(list == null || list.size() == 0) {
        	 return null;
         }

         MyTask myTask = new MyTask();
         Task task = list.get(0);
         BeanUtils.copyProperties(task, myTask);
         
         //每日领奖进度和状态
         
         //当天的领奖次数,即该任务的当天记录次数	
         int num = countTaskLog(userId, task.getTaskId(), DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
         myTask.setTaskFinishedNum(num);
         
         Date now = new Date();
         int hour = DateUtil.getHour(now);
         if(hour < 10) {
        	 myTask.setTaskState((short)1);
         }else if(hour >= 10 && hour < 18){
        	 if(num < 1) {
        		 myTask.setTaskState((short)1);
        	 }else {
        		 myTask.setTaskState((short)2);
        	 }
         }else { //hour>=18
        	 List<TaskLog> taskLogList = findTaskLogList(userId, task.getTaskId(), null, DateUtil.getSubsectionHourBegin(now, 18), null);
             if(taskLogList != null && taskLogList.size() > 0) {  //当天18点后有记录
            	 myTask.setTaskState((short)2);
             }else {
            	 myTask.setTaskState((short)1);
             }
         }
         
         List<TaskRewardRule> ruleList = getTaskRewardRule(task.getTaskId());
         if (CollectionUtils.isEmpty(ruleList)) {
             ruleList = Lists.newArrayList();
         }
         myTask.setRewardRuleList(ruleList);


        return myTask;
    }

    /**
     * 获取某个用户待做的任务和当天进行中的任务
     *
     * @param userId 用户ID
     * @return List<MyTask>
     * @throws
     */
    
    //
    public List<MyTask> findTask(Long userId) {

        List<MyTask> resultList = Lists.newArrayList();

        //首选获取所有任务,判断任务完成状态
        List<Task> allTask = findDisplayTask(1, 200);

        for (Task task : allTask) {
            findSingleTask(userId, resultList, task);

        }

        return resultList;
    }
    
    //taskTypeAttr=0 日常任务-福利-邀请好友上线---过滤每日领奖      taskTypeAttr=1 一次性任务-邀请新人   
    public List<MyTask> findTask(Long userId, Short taskTypeAttr) {
        List<MyTask> resultList = Lists.newArrayList();

        //首选获取所有任务,判断任务完成状态
        List<Task> allTask = findDisplayTaskByTaskTypeAttr(1, 200, taskTypeAttr);

        for (Task task : allTask) {
            findSingleTask(userId, resultList, task);
        }
        return resultList;
    }
    
    /**
     * 处理单个任务     
     *
     * @param userId
     * @param resultList
     * @param task
     */
    private void findSingleTask(Long userId, List<MyTask> resultList, Task task) {
        if (task == null) {
            return;
        }
        
        Short taskCatalog = task.getTaskTypeCatalog();
        Short display = task.getTaskDisplay();

        //邀请用户任务处理方式
        handleInviteUserTask(userId, resultList, task);
    	
    }
 
    private void handleInviteUserTask(Long userId, List<MyTask> resultList, Task task) {
    	 Integer repetNum = task.getTaskRepetNum(); 
         Integer friendNum = 0;
     	//查询该用户邀请新人的次数
         if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(task.getTaskTypeAttr())) {//一次性任务
         	friendNum = (int)userInviteService.countFriendNum(userId, true, null, null);
         }else {//日常任务
         	friendNum = (int)userInviteService.countFriendNum(userId, null, DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
         }
         	
         if (repetNum <= friendNum) {
             //邀请人数次数已经达成
             if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(task.getTaskTypeAttr())) { //一次性任务
                 /**
                  * 一次性任务
                  */
                 //插入task_log表之前先查询是否已经存在有记录;
             	TaskLog taskLog = findTaskLog(userId, task, null, null);
                 if (taskLog != null) {
                     //已经存在记录
                     Short taskState = taskLog.getTaskState();
                     //if (SystemConstant.TASK_STAT_FINISHED.equals(taskState)) {
                         //没有领取的加入到resultList中, 领取过的,不加入resultList
                         wireMyTask(taskLog, friendNum, resultList);
                    //}
                 } else {
                     //没有存在记录
                     addTaskLog(userId, task);
                     wireMyTask(task, friendNum, resultList);
                 }
                
             } else {
                 /**
                  * 每日任务
                  */
                 //插入task_log表之前先查询当天是否已经存在有记录;
             	TaskLog taskLog = findTaskLog(userId, task, DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
                 if (taskLog != null) {
                     //已经有记录了,就不用插入
                     wireMyTask(taskLog, friendNum, resultList);
                 } else {
                     //没有存在当天的记录
                     addTaskLog(userId, task);
                     wireMyTask(task, friendNum, resultList);
                 }
             }
         } else {
             /**
              * 未完成的任务
              */
             wireMyTask(task, friendNum, resultList);
         }
    }

    /**
     * 处理非闯关游戏类和闯关游戏类任务
     *
     * @param userId
     * @param resultList
     * @param task
     * @param currentDoGameNum 当前任务实际完成次数
     */
    private void handleGeneralGameTypeTask2(Long userId, List<MyTask> resultList, Task task, Integer currentDoGameNum, TaskLog taskLog) {
        //游戏类任务
        Integer repetNum = task.getTaskRepetNum();
        if (repetNum <= currentDoGameNum) {
            //游戏次数已经达成
            if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(task.getTaskTypeAttr())) {
                /**
                 * 一次性任务
                 */
                //插入task_log表之前先查询是否已经存在有记录;
                if (null != taskLog) {
                    //已经存在记录
                    Short taskState = taskLog.getTaskState();
                    if (SystemConstant.TASK_STAT_FINISHED.equals(taskState)) {
                        //没有领取的加入到resultList中, 领取过的,不加入resultList
                        wireMyTask(taskLog, currentDoGameNum, resultList);
                    }
                } else {
                    //没有存在记录
                    addTaskLog(userId, task);
                    wireMyTask(task, currentDoGameNum, resultList);
                }
                //是否有后续任务
                if (task.getSuccessorTaskId() > 0) {
                    //有后续任务
                    Task successorTask = taskMapper.selectByPrimaryKey(task.getSuccessorTaskId());
                    if (successorTask != null) {
                        findSingleTask(userId, resultList, successorTask);
                    }
                }
            } else {
                /**
                 * 每日任务
                 */
                //插入task_log表之前先查询当天是否已经存在有记录;
                if (null != taskLog) {
                    //已经有记录了,就不用插入, 但是对于非一次性任务需要加入到resultList列表中.界面上需要显示当天已经完成未领取的, 也要显示当天已经完成并领取过的
                    wireMyTask(taskLog, currentDoGameNum, resultList);
                } else {
                    //没有存在当天的记录
                    addTaskLog(userId, task);
                    wireMyTask(task, currentDoGameNum, resultList);
                }
            }
        } else {
            /**
             * 未完成的游戏类任务
             */
            wireMyTask(task, currentDoGameNum, resultList);
        }//未完成的游戏类任务
    }
    

    /**
     * 获取实际完成的次数，不计入其他一次性任务已经完成的次数 但是共享日常任务的次数
     *
     * @param userId
     * @param task
     * @param currentDoGameNum
     * @return
     */
    private Integer getActualDoGameNum(Long userId, Task task, Integer currentDoGameNum) {
        TaskLogExample taskLogExample = new TaskLogExample();
        taskLogExample.or()
                .andUserIdEqualTo(userId)//用户id
                .andTaskTypeEqualTo(task.getTaskType())
                .andTaskTypeAttrEqualTo(task.getTaskTypeAttr())
                .andTaskIdNotEqualTo(task.getTaskId());
        List<TaskLog> taskLogList = taskLogMapper.selectByExample(taskLogExample);
        if (taskLogList != null && taskLogList.size() > 0) {
            IntSummaryStatistics statistics = taskLogList.stream().mapToInt((x) -> x.getTaskRepetNum()).summaryStatistics();
            long finishedCount = statistics.getSum();
            currentDoGameNum = currentDoGameNum - Integer.valueOf(finishedCount + "");
        }
        return currentDoGameNum;
    }

    private void wireMySuccessorTaskList(List<Task> onlyOnceTaskSuccessorList, List<MyTask> resultList) {

        for (Task task : onlyOnceTaskSuccessorList) {
            MyTask myTask = new MyTask();

            BeanUtils.copyProperties(task, myTask);
            myTask.setTaskFinishedNum(0);
            //myTask.setTaskDisplay(SystemConstant.TASK_DISP_RENDER);

            if (myTask.getTaskFinishedNum().equals(task.getTaskRepetNum())) {
                myTask.setTaskState(SystemConstant.TASK_STAT_FINISHED);
            } else {
                myTask.setTaskState(SystemConstant.TASK_STAT_NOFINISHED);
            }

            List<TaskRewardRule> ruleList = getTaskRewardRule(task.getTaskId());
            myTask.setRewardRuleList(ruleList);

            resultList.add(myTask);
        }
    }


    /**
     * @param userId    用户Id
     * @param task      任务
     * @param startDate 开始时间
     * @param endDate   截止时间
     * @return TaskLog
     */
    public TaskLog findTaskLog(Long userId, Task task, Date startDate, Date endDate) {
        TaskLogExample example = new TaskLogExample();

        Criteria criteria = example.createCriteria();

        if (null != userId) {
            criteria.andUserIdEqualTo(userId);
        }

        if (null != startDate) {
            criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
        }

        if (null != endDate) {
            criteria.andCreateTimeLessThanOrEqualTo(endDate);
        }

        if (null != task) {
            Integer taskId = task.getTaskId();
            String taskType = task.getTaskType();
            Short taskTypeAttr = task.getTaskTypeAttr();
            Short taskTypeCatalog = task.getTaskTypeCatalog();

            criteria.andTaskIdEqualTo(taskId);
            criteria.andTaskTypeEqualTo(taskType);
            criteria.andTaskTypeAttrEqualTo(taskTypeAttr);
            criteria.andTaskTypeCatalogEqualTo(taskTypeCatalog);
        }

        List<TaskLog> list = taskLogMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public List<TaskLog> findTaskLogList(Long userId, Integer taskId, Short taskState, Date startDate, Date endDate) {
        TaskLogExample example = new TaskLogExample();

        Criteria criteria = example.createCriteria();

        if (null != userId) {
            criteria.andUserIdEqualTo(userId);
        }
        if (null != taskId && taskId > 0) {
            criteria.andTaskIdEqualTo(taskId);
        }
        if (null != taskState && taskState > 0) {
            criteria.andTaskStateEqualTo(taskState);
        }
        if (null != startDate) {
            criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
        }
        if (null != endDate) {
            criteria.andCreateTimeLessThanOrEqualTo(endDate);
        }
        List<TaskLog> list = taskLogMapper.selectByExample(example);
        
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();

        }
        return list;
    }
    
    
    /**
     * 按条件计算任务记录次数
     * @param userId
     * @param taskId
     * @param startDate
     * @param endDate
     * @return
     */
    public int countTaskLog(Long userId, Integer taskId, Date startDate, Date endDate) {
    	 TaskLogExample taskLogExample = new TaskLogExample();
         Criteria criteria = taskLogExample.createCriteria();
         if(userId != null && userId > 0) {
        	 criteria.andUserIdEqualTo(userId);
         }
         if(taskId != null && taskId > 0) {
        	 criteria.andTaskIdEqualTo(taskId);
         }
         if (startDate != null) {
             criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
         }
         if (endDate != null) {
             criteria.andCreateTimeLessThanOrEqualTo(endDate);
         }
      
         return taskLogMapper.countByExample(taskLogExample);
    }

    /**
     * @param userId
     * @param taskId
     * @param startDate
     * @param endDate
     * @return
     */
    public TaskLog findTaskLog(Long userId, Integer taskId, Date startDate, Date endDate) {
        TaskLogExample example = new TaskLogExample();

        Criteria criteria = example.createCriteria();

        if (null != userId) {
            criteria.andUserIdEqualTo(userId);
        }

        if (null != startDate) {
            criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
        }

        if (null != endDate) {
            criteria.andCreateTimeLessThanOrEqualTo(endDate);
        }

        if (null != taskId && taskId > 0) {
            criteria.andTaskIdEqualTo(taskId);
        }

        List<TaskLog> list = taskLogMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取当天待做的任务列表, 任务Id不在notInTaskIds的任务
     *
     * @param notInTaskIds - 任务ID列表 @return List<Task> @exception
     */
    public List<Task> findTaskList(List<Integer> notInTaskIds) {
        TaskExample example = new TaskExample();
        TaskExample.Criteria criteria = example.createCriteria();

        if (notInTaskIds != null && notInTaskIds.size() > 0) {
            criteria.andTaskIdNotIn(notInTaskIds);
        }

        List<Task> list = taskMapper.selectByExample(example);

        return list;
    }

    public List<Task> findTask(Integer pageNum, Integer pageSize) {

        Integer startNum = PaginationUtil.getStartNum(pageNum, pageSize, 20);

        TaskExample example = new TaskExample();
        example.setOffset(startNum);
        example.setLimit(pageSize);

        List<Task> list = taskMapper.selectByExample(example);

        return list;
    }

    /**
     * 查找没有隐藏的任务
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Task> findDisplayTask(Integer pageNum, Integer pageSize) {

        Integer startNum = PaginationUtil.getStartNum(pageNum, pageSize, 20);

        TaskExample example = new TaskExample();
        example.setOffset(startNum);
        example.setLimit(pageSize);
        example.or().andTaskDisplayEqualTo(SystemConstant.TASK_DISP_RENDER);

        List<Task> list = taskMapper.selectByExample(example);

        return list;
    }

    /**
     * 查找没有隐藏的任务 根据任务类型
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Task> findDisplayTaskByTaskTypeAttr(Integer pageNum, Integer pageSize, Short taskTypeAttr) {

        Integer startNum = PaginationUtil.getStartNum(pageNum, pageSize, 20);

        TaskExample example = new TaskExample();
        example.setOffset(startNum);
        example.setLimit(pageSize);
        example.or().andTaskDisplayEqualTo(SystemConstant.TASK_DISP_RENDER)
        	.andTaskTypeAttrEqualTo(taskTypeAttr)
        	.andTaskTypeNotEqualTo("ReceivePrize");

        List<Task> list = taskMapper.selectByExample(example);

        return list;
    }

    /**
     * 获取任务ID列表
     *
     * @param taskList - 任务list @return List<Integer> @exception
     */
    public List<Integer> getTaskIdList(List<TaskLog> taskList) {
        List<Integer> list = Lists.transform(taskList, new Function<TaskLog, Integer>() {
            @Override
            public Integer apply(TaskLog input) {
                return input.getTaskId();
            }
        });
        return list;
    }

    /**
     * 获取后续任务id
     *
     * @param taskList
     * @return List<Integer>
     */
    public List<Integer> getTaskSuccessorIds(List<TaskLog> taskList) {
        List<Integer> list = Lists.transform(taskList, new Function<TaskLog, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable TaskLog input) {
                return input.getSuccessorTaskId();
            }
        });
        return list;
    }

    List<Task> getOnlyOnceSuccessorTask(List<Task> allTask) {
        return Lists.newArrayList(Collections2.filter(allTask, new Predicate<Task>() {
            @Override
            public boolean apply(@Nullable Task input) {
                if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(input.getTaskTypeAttr()) && SystemConstant.TASK_DISP_HIDDEN.equals(input.getTaskDisplay())) {
                    return true;
                }
                return false;
            }
//            public boolean test(@Nullable Task input){
//                return apply(input);
//            }
        }));

    }

    /**
     * 获取奖励规则ID列表
     *
     * @param rewardRuleMapKeyList 任务-奖励规则映射列表
     * @return
     */
    public List<Integer> getRuleIdList(List<TaskRewardRuleMapKey> rewardRuleMapKeyList) {
        List<Integer> list = Lists.transform(rewardRuleMapKeyList, new Function<TaskRewardRuleMapKey, Integer>() {
            @Override
            public Integer apply(TaskRewardRuleMapKey input) {
                return input.getRewardRuleId();
            }
        });
        return list;
    }

    /**
     * 根据奖品规则ID批量查对应实体列表
     *
     * @param ruleIds
     * @return
     */
    public List<TaskRewardRule> findRewardRule(List<Integer> ruleIds) {
        TaskRewardRuleExample example = new TaskRewardRuleExample();
        TaskRewardRuleExample.Criteria criteria = example.createCriteria();

        if (ruleIds == null || ruleIds.size() == 0) {
            return null;
        }
        criteria.andRewardRuleIdIn(ruleIds);
        List<TaskRewardRule> list = taskRewardRuleMapper.selectByExample(example);

        return list;
    }

    public Task getTastById(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        return task;
    }

    public TaskLog getTaskLogById(Long taskLogId) {
        TaskLog taskLog = taskLogMapper.selectByPrimaryKey(taskLogId);
        return taskLog;
    }

    public List<TaskLog> getTaskLogByTaskId(Integer taskId) {
        List<TaskLog> list = getTaskLog(null, taskId);
        return list;
    }

    public List<TaskLog> getTaskLog(Long userId, Integer taskId) {
        TaskLogExample example = new TaskLogExample();
        TaskLogExample.Criteria criteria = example.createCriteria();

        if (null != userId) {
            criteria.andUserIdEqualTo(userId);
        }
        if (null != taskId) {
            criteria.andTaskIdEqualTo(taskId);
        }

        List<TaskLog> list = taskLogMapper.selectByExample(example);
        return list;
    }

    /**
     * 查询任务ID匹配到的奖品规则
     *
     * @param taskId 任务id
     * @return
     */
    public List<TaskRewardRuleMapKey> findRewardRuleMapKey(Integer taskId) {
        TaskRewardRuleMapExample example = new TaskRewardRuleMapExample();
        com.artqiyi.tanqiu.task.domain.TaskRewardRuleMapExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(taskId);
        List<TaskRewardRuleMapKey> list = taskRewardRuleMapMapper.selectByExample(example);
        return list;
    }

    /**
     * 查询rewardRuleId对应的实体
     *
     * @param rewardRuleId
     * @return
     */
    public TaskRewardRule getTaskRewardRuleByid(Integer rewardRuleId) {
        TaskRewardRule taskRewardRule = taskRewardRuleMapper.selectByPrimaryKey(rewardRuleId);
        return taskRewardRule;
    }

    /**
     * @param userId
     * @param taskId
     * @return Boolean
     * @throws TaskException
     */
   public Boolean drawReward(Long userId, Integer taskId) {
        Optional<Task> task = Optional.ofNullable(taskMapper.selectByPrimaryKey(taskId));
        task.orElseThrow(() -> new TaskException("没有id为" + taskId + "的任务"));
        Short taskTypeAttr = task.get().getTaskTypeAttr();

        TaskLog taskLog = null;
        if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(taskTypeAttr)) {
            //新手任务 即一次性任务
            taskLog = findTaskLog(userId, taskId, null, null);
        } else if (SystemConstant.TASK_TYPE_ATTR_DAILY_.equals(taskTypeAttr)) {
            //日常任务
            taskLog = findTaskLog(userId, taskId, DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
        }

        Date now = new Date();

        if (null == taskLog) {
            throw new TaskException("没有完成这个任务");
        }

        Short taskState = taskLog.getTaskState();

        if (SystemConstant.TASK_STAT_AACPT.equals(taskState)) {
            throw new TaskException("本任务已经领取过");
        }

        // 查询对应用户
        UserInfo userInfo = userInfoService.selectByUserId(userId);
       /* if (userInfo == null || userInfo.getId() <= 0) {// 找不到用户信息
            throw new TaskException("找不到用户账户信息");
        }*/
        User user = userService.selectById(userId);

        List<TaskRewardRuleMapKey> list = findRewardRuleMapKey(taskId);

        if (CollectionUtils.isEmpty(list)) {
            //没有奖励，也就将状态设置为已领取
            taskLog.setTaskState(SystemConstant.TASK_STAT_AACPT);
            taskLog.setUpdateTime(now);
            taskLogMapper.updateByPrimaryKey(taskLog);
            return true;
        }

        for (TaskRewardRuleMapKey rewardRuldMap : list) {
            TaskRewardRule taskRewardRule = getTaskRewardRuleByid(rewardRuldMap.getRewardRuleId());

            Integer awaradNum = taskRewardRule.getAwardNum();

            //领取奖励流水生成
            CoinTranslog translog = new CoinTranslog();
            translog.setUserId(userInfo.getUserId());
            translog.setUserName(user.getNickName());
            translog.setTransType(SystemConstant.TRANS_TYPE_AWARD_FOR_FINISH_TASK);
            translog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
            translog.setTransAmount(awaradNum);
            translog.setRemark(taskLog.getTaskDesc());

            if (SystemConstant.TASK_AWARD_TYPE_COIN.equals(taskRewardRule.getAwardType())) { // 趣币
                userInfo.setCoin(userInfo.getCoin() + awaradNum);
                translog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
                translog.setBalance(userInfo.getCoin());
            }
            if (SystemConstant.TASK_AWARD_TYPE_SCORE.equals(taskRewardRule.getAwardType())) { // 积分
                userInfo.setPoint(userInfo.getPoint() + awaradNum);
                translog.setAccountType(SystemConstant.ACC_TYPE_SCORE);
                translog.setBalance(userInfo.getPoint());
            }
            if (SystemConstant.TASK_AWARD_TYPE_HONGBAO.equals(taskRewardRule.getAwardType())) { //红包
                userInfo.setBalance(userInfo.getBalance() + awaradNum);
                translog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
                translog.setBalance(Integer.valueOf(userInfo.getBalance() + ""));
            }
            userInfo.setUpdateTime(now);
            try {
                userInfoService.saveOrUpdate(userInfo);
            } catch (Exception e) {
                logger.error("更新账户失败", e);
                throw new TaskException("更新账户失败");
            }
            //添加领取奖励历史记录
            transLogService.addTranslog(translog);
        }

        // 入账成功状态更新
        taskLog.setTaskState(SystemConstant.TASK_STAT_AACPT);
        taskLog.setUpdateTime(now);
        taskLogMapper.updateByPrimaryKey(taskLog);

        return true;
    }
   
   
   /*
    * 每日领奖任务的领取
    */
   public Boolean drawReward4EveryDayPrize(Long userId, Integer taskId) {
       Optional<Task> task = Optional.ofNullable(taskMapper.selectByPrimaryKey(taskId));
       task.orElseThrow(() -> new TaskException("没有id为" + taskId + "的任务"));
       
       Short taskTypeAttr = task.get().getTaskTypeAttr();
       

       if (!"ReceivePrize".equals(task.get().getTaskType())) {
           throw new TaskException("该任务不是每日领奖任务");
       }
       
       Date now = new Date();
       int hour = DateUtil.getHour(now);
       TaskLog taskLog = null;
       if(hour < 10) {
    	   //不是每日领奖的时间段
    	   throw new TaskException("不是每日领奖的时间段");
       }else if(hour >= 10 && hour < 18) {
    	   taskLog = findTaskLog(userId, taskId, DateUtil.getSubsectionHourBegin(now, 10), DateUtil.getSubsectionHourEnd(now, 17));
    	   if(taskLog != null) {
    		   throw new TaskException("该时间段已经领过奖了");
    	   }
       }else { //hour>=18
    	   taskLog = findTaskLog(userId, taskId, DateUtil.getSubsectionHourBegin(now, 18), null);
    	   if(taskLog != null) {
    		   throw new TaskException("该时间段已经领过奖了");
    	   }
       }
       
       // 查询对应用户
       UserInfo userInfo = userInfoService.selectByUserId(userId);
      /* if (userInfo == null || userInfo.getId() <= 0) {// 找不到用户信息
           throw new TaskException("找不到用户账户信息");
       }*/
       
       User user = userService.selectById(userId);

       /****************************正常领奖****************************/
       //领奖加记录
       taskLog = new TaskLog();
       BeanUtils.copyProperties(task.get(), taskLog);
       taskLog.setCreateTime(now);
       taskLog.setUpdateTime(now);
       taskLog.setUserId(userId);
       taskLog.setTaskState(SystemConstant.TASK_STAT_AACPT);
       taskLogMapper.insertSelective(taskLog);
       
       List<TaskRewardRuleMapKey> list = findRewardRuleMapKey(taskId);
       if (CollectionUtils.isEmpty(list)) {
           //没有奖励，也就将状态设置为已领取
    	   if(taskLog != null) {
    		   taskLog.setTaskState(SystemConstant.TASK_STAT_AACPT);
               taskLog.setUpdateTime(now);
               taskLogMapper.updateByPrimaryKey(taskLog);
    	   }
           return true;
       }

       
       for (TaskRewardRuleMapKey rewardRuldMap : list) {
           TaskRewardRule taskRewardRule = getTaskRewardRuleByid(rewardRuldMap.getRewardRuleId());

           Integer awaradNum = taskRewardRule.getAwardNum();

           //领取奖励流水生成
           CoinTranslog translog = new CoinTranslog();
           translog.setUserId(userInfo.getUserId());
           translog.setUserName(user.getNickName());
           translog.setTransType(SystemConstant.TRANS_TYPE_AWARD_FOR_FINISH_TASK);
           translog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
           translog.setTransAmount(awaradNum);
           translog.setRemark(taskLog.getTaskDesc());

           if (SystemConstant.TASK_AWARD_TYPE_COIN.equals(taskRewardRule.getAwardType())) { // 趣币
               userInfo.setCoin(userInfo.getCoin() + awaradNum);
               translog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
               translog.setBalance(userInfo.getCoin());
           }
           if (SystemConstant.TASK_AWARD_TYPE_SCORE.equals(taskRewardRule.getAwardType())) { // 积分
               userInfo.setPoint(userInfo.getPoint() + awaradNum);
               translog.setAccountType(SystemConstant.ACC_TYPE_SCORE);
               translog.setBalance(userInfo.getPoint());
           }
           if (SystemConstant.TASK_AWARD_TYPE_HONGBAO.equals(taskRewardRule.getAwardType())) { //红包
               userInfo.setBalance(userInfo.getBalance() + awaradNum);
               translog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
               translog.setBalance(Integer.valueOf(userInfo.getBalance() + ""));
           }
           userInfo.setUpdateTime(now);
           try {
               userInfoService.saveOrUpdate(userInfo);
           } catch (Exception e) {
               logger.error("更新账户失败", e);
               throw new TaskException("更新账户失败");
           }
           //添加领取奖励历史记录
           transLogService.addTranslog(translog);
       }

       // 入账成功状态更新
       taskLog.setTaskState(SystemConstant.TASK_STAT_AACPT);
       taskLog.setUpdateTime(now);
       taskLogMapper.updateByPrimaryKey(taskLog);

       return true;
   }


    public boolean existTaskLog(Long userId, Integer taskId) {
        List<TaskLog> list = getTaskLog(userId, taskId);

        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return true;
    }


    public List<TaskRewardRule> getTaskRewardRule(Integer taskId) {

        List<TaskRewardRuleMapKey> list = findRewardRuleMapKey(taskId);
        List<Integer> ruleIds = getRuleIdList(list);
        List<TaskRewardRule> taskRewardRuleList = findRewardRule(ruleIds);

        return taskRewardRuleList;
    }

    private void wireMyTask(TaskLog taskLog, Integer currDayDoGameNum, List<MyTask> resultList) {
        MyTask myTask = new MyTask();

        BeanUtils.copyProperties(taskLog, myTask);
        myTask.setTaskFinishedNum(taskLog.getTaskRepetNum());
        //myTask.setTaskFinishedNum(currDayDoGameNum);//避免第一次进入结算时，执行的是下面的wireMyTask，显示currDayDoGameNum，第二次进入显示taskLog.getTaskRepetNum()

        if (currDayDoGameNum >= taskLog.getTaskRepetNum()) {//改为大于等于，由于是进入后才查询做判断，可能已经超过需求的次数，但也算是完成 20180531 吴付畅
            myTask.setTaskState(SystemConstant.TASK_STAT_FINISHED);
            //如果已经领取了，返回已经领取状态， 比如日常任务中的午间登录等 不然返回的依然是完成未领取状态
            if (taskLog.getTaskState().equals(SystemConstant.TASK_STAT_AACPT)) {
                myTask.setTaskState(SystemConstant.TASK_STAT_AACPT);
            }
        } else {
            myTask.setTaskState(SystemConstant.TASK_STAT_NOFINISHED);
        }

        List<TaskRewardRule> ruleList = getTaskRewardRule(taskLog.getTaskId());

        if (CollectionUtils.isEmpty(ruleList)) {
            ruleList = Lists.newArrayList();
        }

        myTask.setRewardRuleList(ruleList);

        resultList.add(myTask);
    }

    private void wireMyTask(Task task, Integer currDayDoGameNum, List<MyTask> resultList) {
        MyTask myTask = new MyTask();

        BeanUtils.copyProperties(task, myTask);

        if (currDayDoGameNum >= task.getTaskRepetNum()) {//改为大于等于，由于是进入后才查询做判断，可能已经超过需求的次数，但也算是完成 20180531 吴付畅
            myTask.setTaskFinishedNum(task.getTaskRepetNum());
            myTask.setTaskState(SystemConstant.TASK_STAT_FINISHED);
        } else {
            myTask.setTaskFinishedNum(currDayDoGameNum);
            myTask.setTaskState(SystemConstant.TASK_STAT_NOFINISHED);
        }

        List<TaskRewardRule> ruleList = getTaskRewardRule(task.getTaskId());
        if (CollectionUtils.isEmpty(ruleList)) {
            ruleList = Lists.newArrayList();
        }
        myTask.setRewardRuleList(ruleList);

        resultList.add(myTask);
    }

    public Integer addTaskLog(Long userId, Task task) {
        TaskLog taskLog = new TaskLog();
        BeanUtils.copyProperties(task, taskLog);
        taskLog.setCreateTime(null);//避免保存了task的创建时间，导致 findTaskLog 使用的create_time来筛选当天 没有找到 2018 05 31 吴付畅
        taskLog.setUpdateTime(null);
        taskLog.setUserId(userId);
        taskLog.setTaskState(SystemConstant.TASK_STAT_FINISHED);

        Integer rtn = taskLogMapper.insertSelective(taskLog);

/*      不能获取在task表中修改，每个用户都是不一样的
        if (SystemConstant.TASK_TYPE_ATTR_ONLYONCE.equals(task.getTaskTypeAttr()) && task.getSuccessorTaskId() > 0) {
            //该一次性任务有后续任务, 需要把任务的显示状态改成能显示的

            Task successTask = new Task();
            successTask.setTaskId(task.getSuccessorTaskId());
            successTask.setTaskDisplay(SystemConstant.TASK_DISP_RENDER);
            taskMapper.updateByPrimaryKeySelective(successTask);

        }*/

        return rtn;
    }

    public List<TaskLog> findTaskLogListWithSuccessor(Long userId) {
        List<TaskLog> list = null;
        TaskLogExample example = new TaskLogExample();
        example.setOrderByClause("create_time desc");

        TaskLogExample.Criteria criteria = example.createCriteria();

        if (null != userId && userId > 0) {
            criteria.andUserIdEqualTo(userId);
        }

        criteria.andTaskTypeAttrEqualTo(SystemConstant.TASK_TYPE_ATTR_ONLYONCE);
        criteria.andSuccessorTaskIdGreaterThan(0);
        criteria.andTaskStateEqualTo(SystemConstant.TASK_STAT_AACPT);

        list = taskLogMapper.selectByExample(example);

        return list;
    }

    /**
     * 判断是否有未领取的任务
     *
     * @param userId
     * @return
     */
    public Boolean haveUnDrawReward(Long userId) {
        //对于新手任务 查找所有
        List<TaskLog> newbeelist = findTaskLogListByTaskTypeAttr(userId, SystemConstant.TASK_TYPE_ATTR_ONLYONCE,SystemConstant.TASK_STAT_FINISHED, null, null);
        //对于日常任务 查找当天
        List<TaskLog> dailylist = findTaskLogListByTaskTypeAttr(userId, SystemConstant.TASK_TYPE_ATTR_DAILY_,SystemConstant.TASK_STAT_FINISHED, DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());

        if (CollectionUtils.isEmpty(newbeelist)&&CollectionUtils.isEmpty(dailylist)) {
            //如果没有记录，那么结算一下，看看是否有没有结算的记录
            List<MyTask> myTaskList = findTask(userId);
            for (MyTask myTask : myTaskList) {
                if (SystemConstant.TASK_STAT_FINISHED.equals(myTask.getTaskState())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

  

    private List<TaskLog> findTaskLogListByTaskTypeAttr(Long userId, Short taskTypeAttr, Short taskState, Date startDate, Date endDate) {
        TaskLogExample example = new TaskLogExample();

        Criteria criteria = example.createCriteria();

        if (null != userId) {
            criteria.andUserIdEqualTo(userId);
        }

        if (null != taskState && taskState > 0) {
            criteria.andTaskStateEqualTo(taskState);
        }

        if (null != startDate) {
            criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
        }

        if (null != endDate) {
            criteria.andCreateTimeLessThanOrEqualTo(endDate);
        }

        if (null != taskTypeAttr && taskTypeAttr >= 0 ) {
            criteria.andTaskTypeAttrEqualTo(taskTypeAttr);
        }

        List<TaskLog> list = taskLogMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        return list;

    }


}
