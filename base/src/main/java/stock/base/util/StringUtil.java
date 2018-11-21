package stock.base.util;



import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 
* 创建时间: 2017年4月17日
* 功能描述:String 工具类
 */
public class StringUtil {
    /*
    * mysql数据库分页查询
    * */
    public static int pageNew(int page, int pageSize) {
        int pageNew = (page - 1) * pageSize;
        return pageNew;
    }

    /**
     * 字符串判断为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        // TODO Auto-generated method stub
        if (string == null || "".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串判断能否转INT类型
     *
     * @param string
     * @return
     */
    public static boolean isDigits(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断不为空
     *
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        // TODO Auto-generated method stub
        if (object == null || "".equals(object)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 字符串判断是否为null
     *
     * @param object 为null返回"" 空字符串 否则原样返回！
     * @return
     */
    public static String ifNull(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

    /**
     * 对象转字符串
     *
     * @param args
     * @return
     */
    public static String getString(Object[] args) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg == null ? "" : arg.toString());
            sb.append(";");
        }
        return sb.toString();
    }

    public static <T> String getString(List<T> args) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        for (T arg : args) {
            sb.append(arg == null ? "" : arg.toString());
            sb.append(";");
        }
        return sb.toString();
    }

    // List 转 String 且拆分
    public static <T> String listToString(List<T> list, String separator) {
        if (empty(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString()).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    // double List to array
    public static double[] toDoubleArray(List list) {

        double[] array;
        array = new double[10];
        if (empty(list)) {
            return new double[]{};
        }
        Double tmpdouble[] = (Double[]) list.toArray(new Double[list.size()]);
        double tmpdob[] = new double[list.size()];
        for (int i = 0; i < tmpdouble.length; i++) {
            tmpdob[i] = tmpdouble[i].intValue();
            System.out.println(tmpdob[i]);
        }

        return tmpdob;
    }

    // 判空
    public static boolean empty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && ("".equals(obj) || "0".equals(obj))) {
            return true;
        } else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
            return true;
        } else if (obj instanceof Boolean && !((Boolean) obj)) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        }
        return false;
    }

    // 去除冗余保持原顺序
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        // System.out.println(" remove duplicate " + list);
    }

    public static boolean isInList(List list, String word) {
        // String[] strArray = word.split(",");
        // List fauCodeList=Arrays.asList(list);
        if (StringUtil.empty(list)) {
            return false;
        }
        if (StringUtil.isEmpty(word)) {
            return false;
        }
        if (list.contains(word)) {
            return true;
        }
        return false;
    }

    public static List<String> getList(String value, String split) {
        // TODO Auto-generated method stub
        String[] array = value.split(split);
        if (array.length > 0 && !"".equals(array[0])) {
            return Arrays.asList(array);
        } else {
            return null;
        }
    }

    public static String addString(String str, int fullLength) {
        int strLen = str.length();
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        if (strLen < fullLength) {
            while (strLen < fullLength) {
                sb.append(" ");
                strLen += 1;
            }
        }
        return sb.toString();
    }

    /**
     * 拆分集合
     *
     * @param <T>
     * @param resList 要拆分的集合
     * @param count   每个集合的元素个数
     * @return 返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {

        if (resList == null || count < 1) {
            return null;
        }
        List<List<T>> ret = new ArrayList<List<T>>();
        int size = resList.size();
        if (size <= count) { // 数据量不足count指定的大小
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            // 前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }

    public static String getDictValue(String key) {
        return key + "123";
    }


    public static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 判断list是否为空。即本身为空和内容为空都属于list为空。
     * @Author fu
     * @Date 2018/4/28
     * @param list 要检查的list
     * @return boolean
     * @Throws
     */
    public static <T> boolean listIsEmpty(List<T> list){
        return list == null || list.size() == 0 || list.isEmpty();

    }

}
