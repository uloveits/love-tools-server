package com.uloveits.wx.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author lyrics
 */
public class XmlUtil {
    private static Log log = LogFactory.getLog(XmlUtil.class);

    /**
     * 解析XML并将其节点元素压入Dto返回(基于节点值形式的XML格式)
     *
     * @param pStrXml 待解析的XML字符串
     * @return outDto 返回Dto
     */
    public static final Map parseXml2Map(String pStrXml) {
        Map<String,Object> map = new HashMap<>();
        String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Document document = getDocWithStrXml(pStrXml, strTitle);
        // 获取根节点
        Element elNode = document.getRootElement();
        // 遍历节点属性值将其压入Dto
        for (Iterator it = elNode.elementIterator(); it.hasNext();) {
            Element leaf = (Element) it.next();
            map.put(leaf.getName().toLowerCase(), leaf.getData());
        }
        return map;
    }

    private static final Document getDocWithStrXml(String pStrXml, String strTitle) {

        try {
            if (!pStrXml.contains("<?xml")) {
                pStrXml = strTitle + pStrXml;
            }
            return DocumentHelper.parseText(pStrXml);

        } catch (DocumentException e) {
            log.error("==开发人员请注意:==\n将XML格式的字符串转换为XML DOM对象时发生错误啦!" + "\n详细错误信息如下:", e);
        }
        return null;
    }
}
