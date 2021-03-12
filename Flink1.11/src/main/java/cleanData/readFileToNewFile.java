package cleanData;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * @author : 李煌民
 * @date : 2020-11-17 14:59
 **/
public class readFileToNewFile {
    public static void main(String[] args) throws IOException {
        method("D:\\part-00000-00a79c6c-7f83-465b-8cf4-51d1c98af4b7-c000.csv","D:\\res.csv");
    }

    public static void method(String inPath, String outPath) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("仅与亏订徊", "2020新乡数字经济大会bgDemo");
        map.put("但伙檀涅杏嗓素", "传输接入赋能暨渠道招募会广州站bgDemo");
        map.put("函晾郎红惭浴铣世偷蛰", "金融运维峰会直播会议bgDemo");
        map.put("凿秉廊亏烟崔输屁", "华为高性能计算云太原站bgDemo");
        map.put("剑梨", "精英生涯职业规划及就业分析指导bgDemo");
        map.put("及砸瓦妹", "华为全大会bgDemo");
        map.put("哩所侠啥胖矢汪", "2020华为数字政府转型峰会bgDemo");
        map.put("垃撑裤溪莉力途幸颜欧", "华为智慧政务峰会2020bgDemo");
        map.put("扇秩凝凛", "华为ICT学院2020鲲鹏百校行bgDemo");
        map.put("掖圣俏焕避厉扯昧肉", "华为北京城市峰会2020bgDemo");
        map.put("泅订螺官荫串", "华为中国生态大会2020bgDemo");
        map.put("洱伶烟右借寿砧始", "HUAWEICONNECT2020bgDemo");
        map.put("皮檬椒柜鸵收娱袋", "懂行大会2020bgDemo");
        map.put("粘辙叶", "2020华为智能协作新业务说明会bgDemo");
        map.put("精坟屠额摧鹃轩悲亦", "华为企业智慧屏渠道招募会暨产品推介会佛山站bgDemo");
        map.put("脚渗橱卫授茨履巧氨殖", "廊坊国际经济贸易洽谈会网上展馆华为直播bgDemo");
        map.put("酪", "激发联接的潜能网络论坛创新技术论坛杭州站bgDemo");
        map.put("阴倾迷怒肯丑辖", "HCC2020_线上会bgDemo");
        map.put("雨晃惟眼贸佑额", "华为大型展会bgDemo");
        map.put("骄慎皿果嫁庚赴冰", "安平中国行南京站bgDemo");

        BufferedReader br = new BufferedReader(new FileReader(inPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath));

        String line;
        while ((line = br.readLine()) != null) {
            Set<String> strSet = map.keySet();
            for (String key : strSet) {
                if (line.contains(key)) {
                    line = line.replaceAll(key, map.get(key));
                }
            }
            bw.write(line);
            bw.newLine();
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
