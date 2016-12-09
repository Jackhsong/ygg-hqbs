package com.ygg.webapp.code;

/**
 *
 */
public class OrderRelationEnum
{
    /**
     * 订单审核状态
     *
     */
    public static enum CHECK_STATUS_ENUM
    {
        WAIT_FOR_CHECK("1", "待审核"),

        CHECK_PASS("2", "审核通过"),

        CHECK_UN_PASS("3", "审核通过");

        private String code;

        private String description;

        private CHECK_STATUS_ENUM(String code, String description)
        {
            this.code = code;
            this.description = description;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }
    }
}
