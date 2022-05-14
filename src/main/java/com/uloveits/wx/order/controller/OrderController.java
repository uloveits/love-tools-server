package com.uloveits.wx.order.controller;

import com.uloveits.wx.Constants;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.support.pay.WxPay;
import com.uloveits.wx.common.support.pay.WxPayment;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.order.model.Order;
import com.uloveits.wx.order.model.OrderComment;
import com.uloveits.wx.order.model.OrderRefund;
import com.uloveits.wx.order.service.OrderCommentService;
import com.uloveits.wx.order.service.OrderRefundService;
import com.uloveits.wx.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 订单管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "订单管理", tags = "order")

@RequestMapping("${api.version.app}/order")
@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderCommentService orderCommentService;

    @Autowired
    private OrderRefundService orderRefundService;

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request, @RequestBody Order param) {
        super.insertActionLog(request,"post:/app/order/add",param.toString());

        String result = orderService.add(param);
        if(result.equals("0")){
            return JsonResult.error("操作失败");
        }else {
            return JsonResult.ok("操作成功").put("orderId",result);
        }

    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request,@RequestBody Order param) {
        super.insertActionLog(request,"post:/app/order/update",param.toString());

        if(orderService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.error("修改失败");
        }
    }

    @ApiOperation(value = "取消订单")
    @PostMapping(value = "/cancel")
    public JsonResult cancel(HttpServletRequest request,@RequestBody Map<String, Object> param ) {
        super.insertActionLog(request,"post:/app/order/cancel",param.toString());

        if(orderService.cancel(param)){
            return JsonResult.ok("取消成功");
        }else {
            return JsonResult.error("取消失败");
        }
    }

    @ApiOperation(value = "去发货")
    @PostMapping(value = "/send")
    public JsonResult send(HttpServletRequest request,@RequestBody Map<String, Object> param ) {
        super.insertActionLog(request,"post:/app/order/send",param.toString());

        if(orderService.send(param)){
            return JsonResult.ok("操作成功");
        }else {
            return JsonResult.error("发货失败");
        }
    }

    @ApiOperation(value = "确认收货")
    @PostMapping(value = "/confirm")
    public JsonResult confirm(HttpServletRequest request,@RequestBody Map<String, Object> param ) {
        super.insertActionLog(request,"post:/app/order/confirm",param.toString());

        if(orderService.confirm(param)){
            return JsonResult.ok("确认成功");
        }else {
            return JsonResult.error("确认失败");
        }
    }

    @ApiOperation(value = "申请退款")
    @PostMapping(value = "/refund/apply")
    public JsonResult refundApply(HttpServletRequest request,@RequestBody OrderRefund param ) {
        super.insertActionLog(request,"post:/app/order/refund/apply",param.toString());

        OrderRefund orderRefund = param;
        Order order = orderService.selectById(orderRefund.getOrderId());
        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId(orderRefund.getFileList());
        orderRefund.setUrlId(fileId);
        orderRefund.setMoney(order.getPayPrice());

        if (null != order.getStatus() && order.getStatus().equals(Constants.ORDER.STATUS.WAIT_RECEIVE)) {
            order.setStatus(Constants.ORDER.STATUS.REFUND_ING);
            orderService.updateById(order);
            orderRefundService.insert(orderRefund);
            return JsonResult.ok("申请成功");
        }
        return JsonResult.error("申请失败");
    }

    @ApiOperation(value = "去评价")
    @PostMapping(value = "/comment")
    public JsonResult comment(HttpServletRequest request,@RequestBody Map<String,List<OrderComment>>param) {
        super.insertActionLog(request,"post:/app/order/comment",param.toString());

        List<OrderComment> orderComments = param.get("comments");
        if(orderCommentService.insertBatch(orderComments)){
            //改变订单状态
            String orderId = orderComments.get(0).getOrderId();
            Order order = orderService.selectById(orderId);
            if (null != order.getStatus() && order.getStatus().equals(Constants.ORDER.STATUS.WAIT_COMMENT)) {
                order.setStatus(Constants.ORDER.STATUS.COMPLETE);
                orderService.updateById(order);
                return JsonResult.ok("评论成功");
            }
        }
        return JsonResult.error("评论失败");
    }


    @ApiOperation(value = "查询各订单状态的数量")
    @PostMapping(value = "/status/count")
    public JsonResult statusCount(HttpServletRequest request,@RequestBody Map<String, Object> param ) {
        super.insertActionLog(request,"post:/app/order/status/count",param.toString());

        Integer waitPay  = orderService.selectCountByStatus(param.get("userId").toString(),Constants.ORDER.STATUS.WAIT_PAY);
        Integer waitSend  = orderService.selectCountByStatus(param.get("userId").toString(),Constants.ORDER.STATUS.WAIT_SEND);
        Integer waitReceive  = orderService.selectCountByStatus(param.get("userId").toString(),Constants.ORDER.STATUS.WAIT_RECEIVE);
        Integer waitComment  = orderService.selectCountByStatus(param.get("userId").toString(),Constants.ORDER.STATUS.WAIT_COMMENT);
        Integer refund  = orderService.selectCountByStatus(param.get("userId").toString(),Constants.ORDER.STATUS.REFUND_ING);
        Map<String,Integer> map = new HashMap<>();
        map.put("waitPay",waitPay);
        map.put("waitSend",waitSend);
        map.put("waitReceive",waitReceive);
        map.put("waitComment",waitComment);
        map.put("refund",refund);
        return JsonResult.ok("操作成功").put("data",map);
    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/all")
    public PageResult<Order> list(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/order/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return orderService.getList(param);
        }else {
            return orderService.getPage(param);
        }
    }

    @ApiOperation(value = "详情")
    @PostMapping("/detail")
    public JsonResult detail(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/order/detail",param.toString());

        Order _order =  orderService.detail(param.get("id").toString());

        return JsonResult.ok("操作成功").put("data",_order);
    }

    @ApiOperation(value = "微信支付")
    @GetMapping(value = "/pay/wx/{id}")
    public JsonResult wxPay(HttpServletRequest request,@PathVariable String id) {
        super.insertActionLog(request,"post:/app/order/add",id);

       Order order = orderService.detail(id);
       if(order != null) {
           if(StringUtil.isNotBlank(order.getTransactionId())) {
               Map<String, String> resp = WxPayment.buildOrderPaySign(
                       Resources.THIRDPARTY.getString("app_id_wx"),
                       Resources.THIRDPARTY.getString("mchid"),
                       order.getTransactionId(),
                       WxPay.TradeType.JSAPI.toString(),
                       Resources.THIRDPARTY.getString("mch_Key"));
               return JsonResult.ok("支付成功").put("data",resp);
           }else if(order.getStatus().equals(Constants.ORDER.STATUS.WAIT_PAY)){
               String appId = Resources.THIRDPARTY.getString("app_id_wx");
               Map<String, String> map = WxPayment.buildUnifiedOrderParasMap(
                       appId,
                       null,
                       Resources.THIRDPARTY.getString("mchid"),
                       null,
                       null,
                       order.getNo(),
                       null,
                       null,
                       order.getId(),
                       new BigDecimal(order.getOrderPrice()).stripTrailingZeros().toPlainString(),
                       request.getHeader("remoteHost"),
                       Resources.THIRDPARTY.getString("notify_url_wx"),
                       WxPay.TradeType.JSAPI.toString(),
                       Resources.THIRDPARTY.getString("mch_Key"),
                       null,
                       null,
                       order.getUser().getOpenId()
               );
               String xmlStr = WxPay.pushOrder(map);
               if (StringUtil.isNotBlank(xmlStr)) {

                   Map<String, String> obj = WxPayment.xmlToMap(xmlStr);

                   if (WxPayment.codeIsOK(obj.get("result_code"))) {
                       // 下单成功
                       order.setTransactionId(obj.get("prepay_id"));
                       order.setTransactionId(obj.get("prepay_id"));
                       orderService.update(order);
                       Map<String, String> resp = WxPayment.buildOrderPaySign(
                               Resources.THIRDPARTY.getString("app_id_wx"),
                               Resources.THIRDPARTY.getString("mchid"),
                               obj.get("prepay_id"),
                               WxPay.TradeType.JSAPI.toString(),
                               Resources.THIRDPARTY.getString("mch_Key"));
                       return JsonResult.ok("支付成功").put("data",resp);
                   }
               }
           }
       }

        return JsonResult.error("支付失败");
    }

    @ApiOperation(value = "微信支付异步通知")
    @RequestMapping(value = "/notify/wx", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public JsonResult wxNotify(HttpServletRequest request,@RequestBody String xmlStr) {
        super.insertActionLog(request,"post:/app/order/notify/wx","{}");


        Map<String, String> obj = WxPayment.xmlToMap(xmlStr);
        String id = obj.get("out_trade_no");
        Order order = orderService.detail(id);
        Map<String, String> map = new HashMap<>();

        if (WxPayment.verifyNotify(obj, Resources.THIRDPARTY.getString("mch_Key")) && verifyTotalFee(obj,order)) {
            // 修改订单状态为已支付，待发货
            order.setStatus(Constants.ORDER.STATUS.WAIT_SEND);
            orderService.update(order);
            map.put("return_msg", "OK");
            map.put("return_code", "SUCCESS");
        } else {
            map.put("return_msg", "签名失败");
            map.put("return_code", "FAIL");
        }

        return JsonResult.ok("操作成功").put("data",map);

    }

    /**
     * 校验金额
     *
     * @param obj
     * @return
     */
    public boolean verifyTotalFee(Map<String, String> obj,Order order) {
        boolean f = false;
        String order_fee = obj.get("total_fee");
        // 校验金额
        if (order_fee.compareTo(order.getOrderPrice()) == 0) {
            f = true;
        }
        return f;
    }


}
