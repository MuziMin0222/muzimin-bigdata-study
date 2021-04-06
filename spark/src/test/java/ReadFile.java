import java.io.*;

/**
 * @author : 李煌民
 * @date : 2021-03-31 15:36
 **/
public class ReadFile {
    public static void main(String[] args) throws IOException {
        process("D:\\res");
    }

    public static void process(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {//如果该目录存在
            if (!file.isDirectory()) {//如果是文件，输出目录路径
                System.out.println("是文件");
                System.out.println("path=" + file.getPath());
            } else {
                System.out.println("是文件夹！");
                File[] fileList = file.listFiles();
                if (fileList != null && fileList.length > 0) {
                    for (File file_ : fileList) {
                        String absolutePath = file_.getAbsolutePath();
                        if (!file_.isDirectory()) {//如果是文件，输出目录路径
                            System.out.println("path=" + absolutePath);
                            String parent = file_.getParent();
                            parent = parent.substring(parent.lastIndexOf("\\") + 1, parent.lastIndexOf("_"));

                            switch (parent) {
                                case "user_action":
                                    if (absolutePath.endsWith(".csv")) {
                                        write(absolutePath, "D:\\user_action.csv");
                                    }
                                    break;
                                case "user":
                                    if (absolutePath.endsWith(".csv")) {
                                        write(absolutePath, "D:\\user.csv");
                                    }
                                    break;
                                case "campaign":
                                    if (absolutePath.endsWith(".csv")) {
                                        write(absolutePath, "D:\\campaign.csv");
                                    }
                                    break;
                                case "survey":
                                    if (absolutePath.endsWith(".csv")) {
                                        write(absolutePath, "D:\\survey.csv");
                                    }
                                    break;
                                default:
                                    System.out.println("文件名不对");
                            }

                        } else if (file_.isDirectory()) {//如果不是文件，而是文件夹的话，则返回去重新执行readFile方法(迭代)
                            process(absolutePath);
                        }
                    }
                } else {
                    System.out.println("该目录下面为空！");
                }

            }
        } else if (!file.exists()) {//该目录不存在
            System.out.println("该目录或文件不存在");
        }
    }

    public static void write(String inPath, String outPath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath, true), "UTF-8"));
        BufferedReader br = new BufferedReader(new FileReader(inPath));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.replaceAll("\"\"", "").split("\u0001").length != 0) {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
