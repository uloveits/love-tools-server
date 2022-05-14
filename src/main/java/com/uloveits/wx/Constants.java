package com.uloveits.wx;

/**
 * 常量表
 *
 * @author lyrics
 * @version 1.0, 2019/11/29
 */
public interface Constants {

    //删除
    Integer IS_DELETE = 1;
    //没有删除
    Integer NO_DELETE = 0;

    //删除
    Integer ADD_STOCK = 1;
    //没有删除
    Integer REDUCE_STOCK = 0;

    interface GOODS {
        String NO_SKU    = "1";
        String HAS_SKU    = "2";
    }

    interface ORDER {
        interface STATUS {
            /**
             *  待付款
             */
            Integer WAIT_PAY = 1;
            /**
             *  待发货
             */
            Integer WAIT_SEND = 2;
            /**
             *  待收货
             */
            Integer WAIT_RECEIVE = 3;
            /**
             *  待评论
             */
            Integer WAIT_COMMENT = 4;

            /**
             *  完成
             */
            Integer COMPLETE = 5;
            /**
             *  订单取消
             */
            Integer CANCEL = 6;
            /**
             *  退款中
             */
            Integer REFUND_ING = 7;
            /**
             *  退款完成
             */
            Integer REFUND_ED = 8;
            /**
             *  退款拒绝
             */
            Integer REFUND_ERR = 9;

        }
    }

}
