package site.xleon.template.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author leon.xu
 */
public class Utils {
    private Utils() {
      throw new IllegalStateException("Utility class");
    }

  /**
   * Object to Map
   * @param obj Object
   * @return map
   */
    public static Map<String, Object> objectToMap(Object obj){
        Map<String, Object> map = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(obj);
                if (value == null) {
                    value = "";
                }
                map.put(fieldName, value);
            }catch (Exception e) {
                //
            }
        }
        return map;
    }

    /**
     * 返回对象值
     *
     * @param object object
     * @param name name
     * @return Optional<T>
     */
    public static Optional<Object> getValueByName(Object object, String name) {
        if (object == null) {
            return Optional.empty();
        }

        String methodName = "get" + Utils.StringExtends.firstUppercase(name);
        Object value = null;
        try {
            Method method = object.getClass().getMethod(methodName);
            value = method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(value);
    }

    public static Optional<Object> getFieldValueByName(Object object, String fieldName) {
        if (object == null) {
            return Optional.empty();
        }
        Object value = null;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            value = field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(value);
    }

  /**
   * 对象转json string
   *
   * @param data 解析对象
   * @return string
   */
  public static Optional<String> jsonString(Object data) {
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = null;
    try {
      jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    } catch (Exception exception) {
      //
    }

    return Optional.ofNullable(jsonString);
  }

    static final Integer DEFAULT_PAGE_SIZE = 50;
    public static <T> Page<T> page(Integer aPage, Integer aPageSize) {
      Integer page = Optional.ofNullable(aPage).orElse(1);
      Integer pageSize = Optional.ofNullable(aPageSize).orElse(DEFAULT_PAGE_SIZE);
      Page<T> paging = new Page<>((long)page, (long)pageSize);
      paging.setMaxLimit(100L);
      return paging;
    }

    /**
     * 获取北京时间时间戳
     *
     * @param dateTime dateTime
     * @return long
     */
    public static long getTime(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * Enum Extends
     */
    public static class EnumExtends {
        private EnumExtends() {
        }

        /**
         * enumOf 通过value构造enum, value可以为null, 不能非法
         */
        @SneakyThrows
        public static <T> T enumOf(Class<T> enumClass, Object value) {
            if (value == null) {
                return null;
            }
            Method valuesMethod = enumClass.getDeclaredMethod("values");
            T[] enums = (T[]) valuesMethod.invoke(enumClass);

            for (T item : enums) {
                Field field = item.getClass().getDeclaredField("value");
                field.setAccessible(true);
                Object enumValue = field.get(item);
                if (enumValue.equals(value)) {
                    return item;
                }
            }
            throw new MyException("create enum '" + enumClass.getName() + "' fail by invalid value: " + value);
        }
    }

    /**
     * Long extends
     */
    public static class LongExtends {
        private LongExtends() {
        }

        /**
         * timestamp to date string
         *
         * @param timestamp 时间戳
         * @return date string
         */
        public static String getDateString(Long timestamp, String pattern, boolean isGmt) {
            if (timestamp ==  null) {
                return null;
            }

//            YdAssert.of(pattern)
//                    .notEmpty()
//                    .failure(value -> value = "yyyy-MM-dd HH:mm:ss");
            long ms = timestamp;
            if (isGmt) {
                ms = timestamp - TimeZone.getDefault().getRawOffset();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(ms);
        }

        public static String getDateString(Long timestamp) {
            return LongExtends.getDateString(timestamp, "yyyy-MM-dd HH:mm:ss", false);
        }


        /**
         * bytes 转 kb mb gb
         *
         * @param size size
         * @return 单位
         */
        public static String getPrintSize(Long size, String pattern) {
            String aPattern = Optional.ofNullable(pattern).orElse("###.00");
            StringBuilder bytes = new StringBuilder();
            DecimalFormat format = new DecimalFormat(aPattern);
            if (size >= 1024L * 1024L * 1024L * 1024L) {
                double i = (size / (1024.0 * 1024.0 * 1024.0 * 1024.0));
                bytes.append(format.format(i)).append("TB");
            } else if (size >= 1024L * 1024L * 1024L) {
                double i = (size / (1024.0 * 1024.0 * 1024.0));
                bytes.append(format.format(i)).append("GB");
            } else if (size >= 1024L * 1024L) {
                double i = (size / (1024.0 * 1024.0));
                bytes.append(format.format(i)).append("MB");
            } else if (size >= 1024L) {
                double i = (size / (1024.0));
                bytes.append(format.format(i)).append("KB");
            } else {
                bytes.append("0B");
            }
            return bytes.toString();
        }
    }

    /**
     * String extends
     */
    public static class StringExtends {
        private StringExtends() {
        }

        private static final String SLAT = "20e26cafd6a7";

        /**
         * String to md5
         *
         * @param string input string
         * @return md5
         */
        public static String getMd5(String string) {
            String base = string + "/" + SLAT;
            return DigestUtils.md5DigestAsHex(base.getBytes());
        }

        /**
         * 首字母大写
         *
         * @param string input string
         * @return new  string
         */
        public static String firstUppercase(String string) {
            if (string == null || "".equals(string)) {
                return string;
            }
            return string.substring(0, 1).toUpperCase() + string.substring(1);
        }

        /**
         * 首字母小写
         *
         * @param string string
         * @return new string
         */
        public static String firstLowercase(String string) {
            if (string == null || "".equals(string)) {
                return string;
            }
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    public static class CheckIdCard{

        private static final String BIRTH_DATE_FORMAT = "yyyyMMdd"; // 身份证号码中的出生日期的格式

        private static final Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L); // 身份证的最小出生日期,1900年1月1日

        private static final int NEW_CARD_NUMBER_LENGTH = 18;

        private static final int OLD_CARD_NUMBER_LENGTH = 15;

        private static final char[] VERIFY_CODE = { '1', '0', 'X', '9', '8', '7',
                '6', '5', '4', '3', '2' }; // 18位身份证中最后一位校验码

        private static final int[] VERIFY_CODE_WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1,
                6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 18位身份证中，各个数字的生成校验码时的权值


        /**
         * 如果是15位身份证号码，则自动转换为18位
         * 可以为null
         *
         * @param cardNumber 身份证号
         * @return bool success or false
         */
        public static boolean check(String cardNumber){
            if (null == cardNumber){
                return true;
            }

            cardNumber = cardNumber.trim();
            if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()){
                cardNumber = convertToNewCardNumber(cardNumber);
            }
            return validate(cardNumber);
        }

        public static boolean validate(String cardNumber){
            boolean result = true;
            result = result && (null != cardNumber); // 身份证号不能为空
            result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length(); // 身份证号长度是18(新证)
            // 身份证号的前17位必须是阿拉伯数字
            for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++){
                char ch = cardNumber.charAt(i);
                result = result && ch >= '0' && ch <= '9';
            }
            // 身份证号的第18位校验正确
            result = result
                    && (calculateVerifyCode(cardNumber) == cardNumber
                    .charAt(NEW_CARD_NUMBER_LENGTH - 1));
            // 出生日期不能晚于当前时间，并且不能早于1900年
            try{
                Date birthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT)
                        .parse(getBirthDayPart(cardNumber));
                result = result && null != birthDate;
                result = result && birthDate.before(new Date());
                result = result && birthDate.after(MINIMAL_BIRTH_DATE);
                /**
                 * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],
                 * 日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
                 * 月份和日期相符合
                 */
                String birthdayPart = getBirthDayPart(cardNumber);
                String realBirthdayPart = new SimpleDateFormat(BIRTH_DATE_FORMAT)
                        .format(birthDate);
                result = result && (birthdayPart.equals(realBirthdayPart));
            }catch(Exception e){
                result = false;
            }
            return result;
        }

        private static String getBirthDayPart(String cardNumber){
            return cardNumber.substring(6, 14);
        }

        /**
         * 校验码（第十八位数）：
         *
         * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
         * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
         * 2; 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9
         * 8 7 6 5 4 3 2
         *
         * @param cardNumber card number
         * @return 校验码
         */
        private static char calculateVerifyCode(CharSequence cardNumber){
            int sum = 0;
            for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++){
                char ch = cardNumber.charAt(i);
                sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
            }
            return VERIFY_CODE[sum % 11];
        }

        /**
         * 把15位身份证号码转换到18位身份证号码<br>
         * 15位身份证号码与18位身份证号码的区别为：<br>
         * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
         * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
         *
         * @param oldCardNumber old card number
         * @return card number
         */
        private static String convertToNewCardNumber(String oldCardNumber){
            StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
            buf.append(oldCardNumber.substring(0, 6));
            buf.append("19");
            buf.append(oldCardNumber.substring(6));
            buf.append(CheckIdCard.calculateVerifyCode(buf));
            return buf.toString();
        }
    }
}
