package com.jiashn.springbootproject.email.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/20 11:55
 **/
@Data
@Accessors(chain = true)
public class ReceiveMailMessage {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 新邮件数量
     */
    private Integer newNum;

    /**
     * 未读邮件数量
     */
    private Integer unReadNum;
    /**
     * 已删除邮件数
     */
    private Integer deletedNum;

    @Data
    @Accessors(chain = true)
    public static class EmailContent{
        /**
         * 主题
         */
        private String subject;
        /**
         * 发件人
         */
        private String sender;
        /**
         * 收件人
         */
        private String sentTo;

        /**
         * 邮件状态
         */
        private String status;

        /**
         * 抄送者
         */
        private String ccPerson;
        /**
         * 密送人
         */
        private String bccPerson;

        /**
         * 邮件内容
         */
        private String content;

        /**
         * 发送时间
         */
        private Date sentDate;

        /**
         * 附件
         */
        private List<Map<String, String>> attachments;
    }

}
