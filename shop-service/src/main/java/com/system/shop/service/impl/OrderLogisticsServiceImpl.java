package com.system.shop.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.OrderLogisticsMapper;
import com.system.shop.entity.Logistics;
import com.system.shop.entity.OrderLogistics;
import com.system.shop.service.LogisticsService;
import com.system.shop.service.OrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {
    private static final char[] BASE64_ENCODE_CHARS = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};


    @Autowired
    private LogisticsService logisticsService;

    @Override
    public List<OrderLogistics> selectOrderLogistics(String orderCode) {
        return baseMapper.selectOrderLogistics(orderCode);
    }

    @Override
    public Boolean update(String orderCode, List<OrderLogistics> orderLogisticsList) {
        baseMapper.deleteByOrderCode(orderCode);
        orderLogisticsList.forEach(orderLogistics -> {
            Logistics logistics = logisticsService.selectById(orderLogistics.getLogisticsCompanyId());
            orderLogistics.setOrderCode(orderCode);
            orderLogistics.setLogisticsCompanyCode(logistics.getLogisticsCompanyCode());
            orderLogistics.setLogisticsCompanyName(logistics.getLogisticsCompanyName());
        });
        return this.batchInsert(orderLogisticsList);
    }

    @Override
    public OrderLogistics selectLogisticsId(Long orderLogisticsId) {
        Map<String, Object> map = new HashMap<>();
        OrderLogistics orderLogistics = baseMapper.selectById(orderLogisticsId);
        //ID
        String EBusinessID = "1767798";
        //KEY
        String AppKey = "1a2b0a63-c1d9-4925-b138-c12300d4b623";
        //请求url
        String ReqURL = "https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

        try {
            Map<String, String> requestData = new HashMap<String, String>() {{
                put("ShipperCode", orderLogistics.getLogisticsCompanyCode());
                put("LogisticCode", orderLogistics.getLogisticsNo());
            }};
            Map<String, Object> params = new HashMap<>(8);
            params.put("RequestData", URLEncoder.encode(JSONUtil.toJsonStr(requestData), "UTF-8"));
            params.put("EBusinessID", EBusinessID);
            params.put("RequestType", "1002");
            String dataSign = encrypt(JSONUtil.toJsonStr(requestData), AppKey, "UTF-8");
            params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
            params.put("DataType", "2");
            String result = HttpUtil.post(ReqURL, params);
            JSONObject resultObj = JSONUtil.parseObj(result);
            if (resultObj.getBool("Success")){
                orderLogistics.setNodes(resultObj.getJSONArray("Traces"));
                orderLogistics.setState(resultObj.getInt("State"));
            }
            return orderLogistics;
        } catch (Exception e) {
            return orderLogistics;
        }
    }


    private static String encrypt(String content, String keyValue, String charset) throws Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式di
     * @throws UnsupportedEncodingException
     */
    private static String base64(String str, String charset) throws UnsupportedEncodingException {
        return base64Encode(str.getBytes(charset));
    }

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64_ENCODE_CHARS[b1 >>> 2]);
                sb.append(BASE64_ENCODE_CHARS[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64_ENCODE_CHARS[b1 >>> 2]);
                sb.append(BASE64_ENCODE_CHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(BASE64_ENCODE_CHARS[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(BASE64_ENCODE_CHARS[b1 >>> 2]);
            sb.append(BASE64_ENCODE_CHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(BASE64_ENCODE_CHARS[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(BASE64_ENCODE_CHARS[b3 & 0x3f]);
        }
        return sb.toString();
    }
}
