package com.lhm.Tree.HuffmanTree;

import java.io.*;
import java.util.*;

/**
 * @author : 李煌民
 * @date : 2019-11-10 11:29
 * <h3>Data_Structure</h3>
 * <p>使用哈夫曼树来对数据或者文件进行压缩和解压缩</p>
 **/
public class UseHuffmanTreeDemo {
    public static void main(String[] args) {
        //测试压缩文件
        //zipFile("D:\\数据.txt","D:\\数据.zip");

        unZipFile("D:\\数据.zip","D:\\a.txt");

    }

    //前序遍历
    private static void preOrder(TreeNode root){
        if (root != null){
            root.preOrder();
        }else {
            System.out.println("树为空。");
        }
    }

    //通过list来创建对应的哈夫曼树
    private static TreeNode createHuffmanTree(List<TreeNode> list){
        while (list.size() > 1){
            //排序，从小到大
            Collections.sort(list);
            //取出该树中权值最小和第二小的二叉树，一个节点可以看成一个最简单的二叉树
            TreeNode leftNode = list.get(0);
            TreeNode rightNode = list.get(1);

            //参加一颗新的二叉树，他的根节点没有数据，只有权值
            TreeNode parent = new TreeNode(leftNode.value + rightNode.value, null);
            parent.left = leftNode;
            parent.right = rightNode;

            //将已经处理的二叉树从list中删除
            list.remove(leftNode);
            list.remove(rightNode);

            //将新的二叉树加入到list中
            list.add(parent);
        }

        //list的最后，就是哈夫曼树的根节点
        return list.get(0);
    }

    //生成哈夫曼树对应的哈夫曼编码
    //思路：
    //1、将哈夫曼编码存放到Map集合中
    static Map<Byte,String> huffmanCodes = new HashMap<>();
    //2、在生成哈夫曼编码时，需要去拼接路径，定义一个StringBuffer存储某个叶子节点的路径
    static StringBuffer sb = new StringBuffer();

    /**
     *  将传入的node节点的所有叶子节点的哈夫曼编码得到，并放入到HuffmanCodes集合
     * @param node  传入的节点
     * @param code  路径，左子树为0，右子树为1
     * @param sb 用于拼接路径
     */
    private static void getCodes(TreeNode node,String code,StringBuffer sb){
        //将code加入到sb中
        sb.append(code);
        if (node != null){//如果node==null，就不处理
            //判断当前node是叶子节点还是非叶子节点
            if (node.data == null){//说明是非叶子节点
                //递归处理
                //向左递归
                getCodes(node.left,"0",sb);
                //向右递归
                getCodes(node.right,"1",sb);
            }else {//说明不是叶子节点
                //就表示找到某个叶子节点的最后
                huffmanCodes.put(node.data,sb.toString());
            }

        }
    }
    //重载getCodes
    private static Map<Byte,String> getCodes(TreeNode root){
        if (root == null){
            return null;
        }
        //处理左子树
        getCodes(root.left,"0",sb);
        //处理右子树
        getCodes(root.right,"1",sb);
        return huffmanCodes;
    }

    //计算每一个字符出现的次数并把值和次数当成一个node传入到list中
    private static List<TreeNode> getNodes(byte[] bytes){
        //创建一个Arraylist
        ArrayList<TreeNode> list = new ArrayList<>();

        //遍历bytes，统计一个byte出现的次数，用map来统计
        HashMap<Byte, Integer> map = new HashMap<>();
        for (byte b : bytes) {
            Integer count = map.get(b);
            if (count == null){
                //说明map集合中还没有该数据
                map.put(b,1);
            }else {
                map.put(b,count + 1);
            }
        }

        //把每一个键值对转化成一个treeNode对象，并加入到list集合中
        for (Map.Entry<Byte,Integer> entry : map.entrySet()){
            list.add(new TreeNode(entry.getValue(),entry.getKey()));
        }

        return list;
    }

    /**编写一个方法，将字符串对应的byte[]数组，通过生成的哈夫曼编码表，返回一个哈夫曼编码压缩后的byte[]
     * @param bytes 原始字符串对应的byte[]
     * @param huffmanCodes 生成哈夫曼编码map
     * @return 返回哈夫曼编码处理后的byte[]
     * 举例：Stringcontent="i like like like java do you like a java";=》byte[]contentBytes=content.getBytes();
     * 返回的是字符串"1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * 对应的byte[]huffmanCodeBytes，即8位对应一个byte,放入到huffmanCodeBytes
     * huffmanCodeBytes[0]=10101000(补码)=>byte[推导10101000=>10101000-1=>10100111(反码)=>11011000=-88]
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //1、利用huffmanCodes将byte转成哈夫曼编码对应的字符串
        StringBuffer sb = new StringBuffer();
        //遍历byte数组
        for (byte b : bytes){
            sb.append(huffmanCodes.get(b));
        }

        //将长字符串转成byte[]，统计返回byte[] huffmanCodeByte长度，一句话int len = (sb.length() + 7)/8;
        int len;
        if (sb.length() % 8 == 0){
            len = sb.length() / 8;
        }else{
            len = sb.length() / 8 + 1;
        }

        //创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录是第几个byte
        for (int i = 0;i < sb.length();i += 8){
            //因为是每八位对应一个byte，所以步长+8
            String strByte;
            if (i + 8 > sb.length()){
                //不够8位
                strByte = sb.substring(i);
            }else {
                strByte = sb.substring(i,i+8);
            }
            //将strByte转成一个byte，放入到HuffmancodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte,2);
            index++;
        }
        return huffmanCodeBytes;
    }


    /**
     *  //将前面的方法封装起来，便于我们的调用
     * @param bytes 元素字符串对应的字节数组
     * @return 结果哈夫曼编码处理后的字节数组（压缩后的数组）
     */
    private static byte[] huffmanZip(byte[] bytes){
        List<TreeNode> nodes = getNodes(bytes);
        //根据nodes创建的哈夫曼树
        TreeNode huffmanTreeRoot = createHuffmanTree(nodes);
        //对应的哈夫曼编码（哈夫曼树）
        Map<Byte, String> codes = getCodes(huffmanTreeRoot);
        //根据生成的哈夫曼编码，压缩得到压缩后的哈夫曼编码字节数组
        byte[] zip = zip(bytes, codes);
        return zip;
    }

    /**
     * 将一个byte转成一个二进制的字符串
     * @param flag 标志是否需要补高位如果是true，表示需要补高位，如果是false表示不补,如果是最后一个字节，无需补高位
     * @param b 传入的byte
     * @return 是该b对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag,byte b){
        //将b转为int
        int temp = b;
        //如果是正数我们还需要补高位
        if (flag){
            //按位或
            temp |= 256;
        }
        //返回的是temp对应的二进制的补码
        String str = Integer.toBinaryString(temp);
        if (flag){
            return str.substring(str.length() - 8);
        }else {
            return str;
        }
    }

    /**
     * 完成对数据的解压缩
     * 思路
     * 1、将huffmanCodeBytes[-88,-65,-56,-65,-56,-65,-55,77,-57,6,-24,-14,-117,-4,-60,-90,28]
     * 重写先转成赫夫曼编码对应的二进制的字符串"1010100010111..."
     * 2、赫夫曼编码对应的二进制的字符串"1010100010111..."=》对照赫夫曼编码=》"i like like like java do you like a java"
     * @param huffmanCodes 哈夫曼编码表map
     * @param huffmanBytes 哈夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //1、先得到HuffmanByte对应的二进制的字符串，形式1010100010111...
        StringBuffer sb = new StringBuffer();
        //将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            sb.append(byteToBitString(!flag,b));
        }
        //把字符串安装到指定的哈夫曼编码进行解码，把哈夫曼编码进行调换，因为方向查询a->97 97->a
        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> entry : huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }

        //创建集合存放byte
        ArrayList<Byte> list = new ArrayList<>();
        //i可以理解为索引，扫描StringBuffer
        for (int i = 0; i < sb.length();) {
            int count = 1;//计算器
            boolean flag = true;
            Byte b = null;
            while (flag){
                //1010100010111...递增的取出key1
                String key = sb.substring(i, i + count);//i不动，让count移动，指定匹配到一个字符串
                b = map.get(key);
                if (b == null){
                    //说明没有匹配到
                    count++;
                }else {
                    //匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count;//i直接移动到count
        }
        //当for循环结束后，list中就存放了所有的字符"i like like like java do you like a java"
        //把list中的数据放入到byte[]并返回
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    /**
     * 将一个文件进行压缩
     * @param srcFile 传入的希望压缩的文件的全路径
     * @param dstFile 压缩后将压缩文件放到哪个目录
     */
    private static void zipFile(String srcFile,String dstFile){
        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建输入流
        InputStream is = null;
        try {
            //创建文件输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件一样大小的byte[]
            byte[] bytes = new byte[is.available()];
            //读取文件
            is.read(bytes);

            //直接对源文件压缩
            byte[] HuffmanBytes = huffmanZip(bytes);

            //创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);

            //把哈夫曼编码后的字节数组写入到压缩文件
            oos.writeObject(HuffmanBytes);
            //注意一定要把赫夫曼编码写入压缩文件
            oos.writeObject(huffmanCodes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                oos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 完成对文件解压
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压的文件路径
     */
    private static void unZipFile(String zipFile,String dstFile){
        //定义文件输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件的输出流
        OutputStream os = null;
        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组Huffmanbytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取哈夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            //解码
            byte[] bytes = decode(huffmanCodes,huffmanBytes);
            //将bytes数组写入到目标文件
            os = new FileOutputStream(dstFile);
            //写文件到dstFile
            os.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//创建一个节点类
class TreeNode implements Comparable<TreeNode>{
    //节点的权值:相同数据所出现的次数
    int value;
    //该节点代表的数据所对应的字符值
    Byte data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value, Byte data) {
        this.value = value;
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                ", data=" + data +
                '}';
    }

    @Override
    public int compareTo(TreeNode o) {
        //让该节点从小到大比较
        return this.value - o.value;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }

}
