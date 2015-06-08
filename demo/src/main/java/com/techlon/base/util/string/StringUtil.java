package com.techlon.base.util.string;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * <p>文件名称 : StringUtil.java</p>
 * <p>文件描述 : 此类中封装一些常用的字符串操作（和业务无关） </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月5日 下午2:26:09</p>
 * @author 李文军
 */
@SuppressWarnings("restriction")
public class StringUtil {
	
	public static String KEY = "HELLOSTRING";

	// 定义 加密算法,可用 DES,DESede,Blowfish
	public static String DES = "DES";

	public static String DESEDE = "DESede";

	public static String BLOWFISH = "Blowfish";

	// dateSize中的日期参数
	/*
	 * 近一日
	 */
	public static char DATE_SIZE_DAY = 'd';

	/*
	 * 近一周
	 */
	public static char DATE_SIZE_WEEK = 'w';

	/*
	 * 近一月
	 */
	public static char DATE_SIZE_MONTH = 'm';

	/*
	 * 近一年
	 */
	public static char DATE_SIZE_YEAR = 'y';

	/*
	 * 明天
	 */
	public static char DATE_SIZE_TOMORROW = 'a';

	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private StringUtil() {
	}

	public static String randomName() throws Exception {
		String strName = null;
		// 产生10位随机传
		RandomStrg rst = new RandomStrg();
		rst.setCharset("a-zA-Z0-9");
		rst.setLength("17");
		rst.generateRandomObject();
		strName = rst.getRandom() + System.currentTimeMillis();

		return strName;
	}

	/**
	 * 取得时间
	 * 
	 * @param str
	 *            日期格式，可以为空（非null）
	 * @param dates
	 *            时间/如果为空 那么取得当前时间
	 * @return
	 */
	public static String now(String str, java.sql.Date dates) {
		if ("".equals(str))
			str = "yyyy-MM-dd";
		java.util.Date date = dates;
		return now(str, date);
	}

	public static String randomNum() {
		String strName = null;
		// 产生10位随机传
		RandomStrg rst = new RandomStrg();
		rst.setCharset("0-9");
		rst.setLength("6");
		try {
			rst.generateRandomObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		strName = StringUtil.now("yyyyMMdd", null) + rst.getRandom();

		return strName;
	}

	/**
	 * 取得时间
	 * 
	 * @param str
	 *            日期格式，可以为空（非null）
	 * @param dates
	 *            时间/如果为空 那么取得当前时间
	 * @return
	 */
	public static String now(String str, java.util.Date dates) {
		if ("".equals(str))
			str = "yyyy-MM-dd";
		java.text.SimpleDateFormat date = new java.text.SimpleDateFormat(str);
		String currentDate = date.format(dates != null ? dates : thisTime());
		return currentDate;
	}

	/**
	 * 取得时间
	 * 
	 * @param str
	 *            日期格式，可以为空（非null）
	 * @param dates
	 *            时间/如果为空 那么取得当前时间
	 * @return
	 */
	public static String now4Timestamp(String str, java.sql.Timestamp times) {
		if ("".equals(str))
			str = "yyyy-MM-dd HH:mm:ss";
		java.text.SimpleDateFormat date = new java.text.SimpleDateFormat(str);
		String currentDate = date.format(times != null ? times : thisTime());
		return currentDate;
	}

	/**
	 * 取得当前时间
	 * 
	 * @return uitl.Data对象
	 */
	public static java.util.Date thisTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 取得当前时间
	 * 
	 * @return java.sql.Timestamp对象
	 */
	public static java.sql.Timestamp thisTime(String format) {
		String dataFormat = format == null ? "yyyy-MM-dd HH:mm:ss.SSS" : format;
		SimpleDateFormat fmtDate = new SimpleDateFormat(dataFormat);
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		try {
			date.setTime(fmtDate.parse(StringUtil.now("yyyy-MM-dd HH:mm:ss.SSS", null)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 此方法将给出的字符串source使用delim划分为单词数组。
	 * 
	 * @param source
	 *            需要进行划分的原字符串
	 * @param delim
	 *            单词的分隔字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组， 如果delim为null则使用逗号作为分隔字符串。
	 * @since 0.1
	 */
	public static String[] split(String source, String delim) {
		String[] wordLists;
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		if (delim == null) {
			delim = ",";
		}
		StringTokenizer st = new StringTokenizer(source, delim);
		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}

	/**
	 * 此方法将给出的字符串source使用delim划分为单词数组。
	 * 
	 * @param source
	 *            需要进行划分的原字符串
	 * @param delim
	 *            单词的分隔字符
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
	 * @since 0.2
	 */
	public static String[] split(String source, char delim) {
		return split(source, String.valueOf(delim));
	}

	/**
	 * 此方法将给出的字符串source使用逗号划分为单词数组。
	 * 
	 * @param source
	 *            需要进行划分的原字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
	 * @since 0.1
	 */
	public static String[] split(String source) {
		return split(source, ",");
	}

	/**
	 * 循环打印字符串数组。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param delim
	 *            分隔符
	 * @param out
	 *            打印到的输出流
	 * @since 0.4
	 */
	public static void printStrings(String[] strings, String delim, OutputStream out) {
		try {
			if (strings != null) {
				int length = strings.length - 1;
				for (int i = 0; i < length; i++) {
					if (strings[i] != null) {
						if (strings[i].indexOf(delim) > -1) {
							out.write(("\"" + strings[i] + "\"" + delim).getBytes());
						} else {
							out.write((strings[i] + delim).getBytes());
						}
					} else {
						out.write("null".getBytes());
					}
				}
				if (strings[length] != null) {
					if (strings[length].indexOf(delim) > -1) {
						out.write(("\"" + strings[length] + "\"").getBytes());
					} else {
						out.write(strings[length].getBytes());
					}
				} else {
					out.write("null".getBytes());
				}
			} else {
				out.write("null".getBytes());
			}
			out.write(java.lang.Character.LINE_SEPARATOR);
		} catch (IOException e) {

		}
	}

	/**
	 * 循环打印字符串数组到标准输出。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param delim
	 *            分隔符
	 * @since 0.4
	 */
	public static void printStrings(String[] strings, String delim) {
		printStrings(strings, delim, System.out);
	}

	/**
	 * 循环打印字符串数组。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param out
	 *            打印到的输出流
	 * @since 0.2
	 */
	public static void printStrings(String[] strings, OutputStream out) {
		printStrings(strings, ",", out);
	}

	/**
	 * 循环打印字符串数组到系统标准输出流System.out。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @since 0.2
	 */
	public static void printStrings(String[] strings) {
		printStrings(strings, ",", System.out);
	}

	/**
	 * 将字符串中的变量使用values数组中的内容进行替换。 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
	 * 
	 * @param prefix
	 *            变量前缀字符串
	 * @param source
	 *            带参数的原字符串
	 * @param values
	 *            替换用的字符串数组
	 * @return 替换后的字符串。 如果前缀为null则使用“%”作为前缀； 如果source或者values为null或者values的长度为0则返回source； 如果values的长度大于参数的个数，多余的值将被忽略； 如果values的长度小于参数的个数，则后面的所有参数都使用最后一个值进行替换。
	 * @since 0.2
	 */
	public static String getReplaceString(String prefix, String source, String[] values) {
		String result = source;
		if (source == null || values == null || values.length < 1) {
			return source;
		}
		if (prefix == null) {
			prefix = "%";
		}

		for (int i = 0; i < values.length; i++) {
			String argument = prefix + Integer.toString(i + 1);
			int index = result.indexOf(argument);
			if (index != -1) {
				String temp = result.substring(0, index);
				if (i < values.length) {
					temp += values[i];
				} else {
					temp += values[values.length - 1];
				}
				temp += result.substring(index + 2);
				result = temp;
			}
		}
		return result;
	}

	/**
	 * 将字符串中的变量（以“%”为前导后接数字）使用values数组中的内容进行替换。 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
	 * 
	 * @param source
	 *            带参数的原字符串
	 * @param values
	 *            替换用的字符串数组
	 * @return 替换后的字符串
	 * @since 0.1
	 */
	public static String getReplaceString(String source, String[] values) {
		return getReplaceString("%", source, values);
	}

	/**
	 * 字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string, boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。大小写敏感。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 不区分大小写判定字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean containsIgnoreCase(String[] strings, String string) {
		return contains(strings, string, false);
	}

	/**
	 * 将字符串数组使用指定的分隔符合并成一个字符串。
	 * 
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return 合并后的字符串
	 * @since 0.4
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 以指定的字符和长度生成一个该字符的指定长度的字符串。
	 * 
	 * @param c
	 *            指定的字符
	 * @param length
	 *            指定的长度
	 * @return 最终生成的字符串
	 * @since 0.6
	 */
	public static String fillString(char c, int length) {
		String ret = "";
		for (int i = 0; i < length; i++) {
			ret += c;
		}
		return ret;
	}

	/**
	 * 去除左边多余的空格。
	 * 
	 * @param value
	 *            待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 * @since 0.6
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去除右边多余的空格。
	 * 
	 * @param value
	 *            待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 * @since 0.6
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 根据转义列表对字符串进行转义。
	 * 
	 * @param source
	 *            待转义的字符串
	 * @param escapeCharMap
	 *            转义列表
	 * @return 转义后的字符串
	 * @since 0.6
	 */
	public static String escapeCharacter(String source, HashMap<?, ?> escapeCharMap) {
		if (source == null || source.length() == 0)
			return source;
		if (escapeCharMap.size() == 0)
			return source;
		StringBuffer sb = new StringBuffer();
		StringCharacterIterator sci = new StringCharacterIterator(source);
		for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
			String character = String.valueOf(c);
			if (escapeCharMap.containsKey(character))
				character = (String) escapeCharMap.get(character);
			sb.append(character);
		}
		return sb.toString();
	}

	/**
	 * 得到字符串的字节长度。
	 * 
	 * @param source
	 *            字符串
	 * @return 字符串的字节长度
	 * @since 0.6
	 */
	public static int getByteLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	/**
	 * 得到字符串中的子串的个数。
	 * 
	 * @param source
	 *            字符串
	 * @param sub
	 *            子串
	 * @return 字符串中的子串的个数
	 * @since 0.6
	 */
	public static int getSubtringCount(String source, String sub) {
		if (source == null || source.length() == 0) {
			return 0;
		}
		int count = 0;
		int index = source.indexOf(sub);
		while (index >= 0) {
			count++;
			index = source.indexOf(sub, index + 1);
		}
		return count;
	}

	/**
	 * 判断是否为null 如果是 返回""，不然返回原串
	 * 
	 * @param source
	 * @return
	 */
	public static String checkNull(String source) {
		return checkNull(source, null);
	}

	/**
	 * 判断是否为null 如果是 返回"-1"，不然返回数的toString
	 * 
	 * @param source
	 * @return
	 */
	public static String checkNull(Integer source) {
		return source == null ? "-1" : source.toString();
	}

	/**
	 * 判断是否为null 如果是 返回"-1"，不然返回数的toString
	 * 
	 * @param source
	 * @return
	 */
	public static String checkNull(Long source) {
		return source == null ? "-1" : source.toString();
	}

	/**
	 * 判断是否为null 如果是 返回需要变化的串，不然返回原串
	 * 
	 * @param source
	 * @param change
	 * @return
	 */
	public static String checkNull(String source, String change) {
		if (change == null) {
			change = "";
		}
		return (source = (source == null ? change : source));
	}

	/**
	 * 仿javascript的escape方法
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 仿javascript的unescape方法
	 * 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 在html里面显示加密过的中文
	 * 
	 * @param str
	 * @return
	 */
	public static String getHtmlEscape(String str) {
		return "<script>  document.write(unescape('" + escape(str) + "')); </script>";
	}

	/**
	 * 获得2个关键字之间的内容
	 * 
	 * @param s
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getBettown(String s, String str1, String str2) {
		return s.substring(s.indexOf(str1) + 1, s.indexOf(str2));
	}

	/**
	 * flq 字符串转换成时间
	 * 
	 * @param str
	 *            时间字符串
	 * @return
	 */
	public static Date strToDate(String str) {

		Date date = null;
		try {
			SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd");
			date = formatters.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 自定义格式串转换date
	 * 
	 * @param str
	 * @param dateformat
	 * @return
	 */
	public static Date strToDate(String str, String dateformat) {

		Date date = null;
		try {
			SimpleDateFormat formatters = new SimpleDateFormat(dateformat == null ? "yyyy-MM-dd HH:mm:ss" : dateformat);
			date = formatters.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 格式化timestamp
	 * 
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static java.sql.Timestamp formatTimestamp(java.sql.Timestamp dateTime, String format) {
		if (dateTime == null) {
			dateTime = StringUtil.thisTime(format);
		}
		String dataFormat = format == null ? "yyyy-MM-dd HH:mm:ss.SSS" : format;

		SimpleDateFormat fmtDate = new SimpleDateFormat(dataFormat);

		return java.sql.Timestamp.valueOf(fmtDate.format(dateTime));
	}

	/**
	 * 自定义格式串转换date
	 * 
	 * @param str
	 * @return
	 */
	public static java.sql.Timestamp strToTimestamp(String str) {
		if (str.trim().length() == 10) {
			str += " 00:00:00";
		}

		return java.sql.Timestamp.valueOf(str);
	}

	/**
	 * flq 转换成时间字符串
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String dateToStr(Date time, String dateformat) {

		String strDate = "";

		try {
			SimpleDateFormat formatters = new SimpleDateFormat(dateformat == null ? "yyyy-MM-dd HH:mm:ss" : dateformat);
			strDate = formatters.format(time);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * flq 取得日期范围
	 * 
	 * @param t
	 *            d:近一日 w:近一周 m:近一月 y:近一年 a:下一天
	 * @return Date
	 */
	public static Date dateSize(char t) {

		Date date = thisTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (t) {
		case 'd':
			// calendar.roll(calendar.DATE,false);
			break;
		case 'w':
			calendar.roll(Calendar.WEEK_OF_YEAR, false);
			break;
		case 'm':
			calendar.roll(Calendar.MONTH, false);
			break;
		case 'y':
			calendar.roll(Calendar.YEAR, false);
			break;
		case 'a':
			calendar.roll(Calendar.DATE, true);
			break;
		default:
			System.out.println("dateSize 函数参数错误");
		}
		date = calendar.getTime();
		return date;
	}

	/**
	 * 根据数组返回 str1,str2,str3,...strn的串
	 * 
	 * @param str
	 * @return
	 * @throws NullPointerException
	 */
	public static String getStringFromArray(String str[]) throws NullPointerException {
		if (str == null)
			throw new NullPointerException("请输入需要转换的数组");
		String strs = "";
		for (String s : str) {
			strs += s + ",";
		}
		if (strs.length() > 0)
			strs = strs.substring(0, strs.length() - 1);
		return strs;
	}

	/**
	 * 根据入口的String，返回截取后的String，其中，2个数字算一个中文
	 * 
	 * @param source
	 * @return 截取后的新串 10个中文，后面是...
	 */
	public static String interceptString(String source) {
		return interceptString(source, "...", 10);
	}

	/**
	 * 根据入口的String，返回截取后的String，其中，2个数字算一个中文
	 * 
	 * @param source
	 *            需要变更的String
	 * @param str
	 *            在String后面加的符号，默认为“...”
	 * @param number
	 *            剩余的中文个数
	 * @return 截取后的新串
	 */
	public static String interceptString(String source, String str, int number) {
		source = checkNull(source);
		str = str == null ? "..." : str;

		int len = strlen(source);// 获得含有汉字的长度
		if (len <= number * 2)// 2个汉字为一个字符
			return source;
		int k = 0;
		for (int i = source.length() - 1; i >= 0; i--) {
			char c = source.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))// 数字
			{
				k = +2;
			} else {
				if (Character.isLetter(c)) { // 中文
					continue;
				} else { // 符号或控制字符
					// k++;
					continue;
				}
			}
			if (k == 3) {
				number++;
				k = 0;
			}
		}
		if (k == 1)
			number++;
		return source.substring(0, number - 1) + str;
	}

	/**
	 * 计算字符串长度. 一个汉字的长度按2计算. 如果给定的字符串为null, 返回0.
	 * 
	 * @param str
	 * @return
	 */
	public static int strlen(String str) {
		if (str == null || str.length() <= 0) {
			return 0;
		}
		int len = 0;
		char c;
		for (int i = str.length() - 1; i >= 0; i--) {
			c = str.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				// 字母, 数字
				len++;
			} else {
				if (Character.isLetter(c)) { // 中文
					len += 2;
				} else { // 符号或控制字符
					len++;
				}
			}
		}
		return len;
	}

	/**
	 * 加密用，初始化JCE
	 */
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param b
	 *            需要转换的字节流
	 * @return 以“:”分割的字符串
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 16进制字符串转换字节码
	 * 
	 * @param str
	 *            需要转换的字符串，以“:”分割
	 * @return 转换后的字节流
	 */
	public static byte[] hex2byte(String str) {
		String stmp[] = str.split(":");
		byte[] btmp = new byte[stmp.length];
		for (int n = 0; n < stmp.length; n++) {
			int i = Integer.valueOf(stmp[n], 16);
			btmp[n] = (byte) (i & 0XFF);
		}
		return btmp;
	}

	/**
	 * 生成密生成密钥，此方法比较慢
	 * 
	 * @param algorithm
	 *            使用那个算法
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] getKey(String algorithm) throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		SecretKey deskey = keygen.generateKey();

		return deskey.getEncoded();
	}

	/**
	 * 加密算法
	 * 
	 * @param input
	 *            byte[]需要加密的byte流
	 * @param key
	 *            byte[]密钥
	 * @param algorithm
	 *            使用那个算法
	 * @return 加密后的byte流
	 * @throws Exception
	 */
	public static byte[] encode(byte[] input, byte[] key, String algorithm) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, algorithm);

		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);// 加密

		return cipherByte;
	}

	/**
	 * 封装好的加密算法
	 * 
	 * @param input
	 *            String 需要加密的串
	 * @param key
	 *            String 密钥
	 * @param algorithm
	 *            使用那个算法
	 * @return 加密后的String
	 * @throws Exception
	 */
	public static String encode(String input, String key, String algorithm) throws Exception {
		return byte2hex(encode(input.getBytes(), key.getBytes(), algorithm));
	}

	/**
	 * 解密算法
	 * 
	 * @param input
	 *            byte[]需要解密的byte流
	 * @param key
	 *            byte[] 密钥
	 * @param algorithm
	 *            使用那个算法
	 * @return 解密后的byte流
	 * @throws Exception
	 */
	public static byte[] decode(byte[] input, byte[] key, String algorithm) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, algorithm);

		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);

		return clearByte;
	}

	/**
	 * 封装好的解密算法
	 * 
	 * @param input
	 *            String需要解密的字符串
	 * @param key
	 *            String 密钥
	 * @param algorithm
	 *            使用那个算法
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decode(String input, String key, String algorithm) throws Exception {
		return new String(decode(hex2byte(input), key.getBytes(), algorithm));
	}

	/**
	 * MD5摘要，不可逆
	 * 
	 * @param input
	 *            byte[] 需要加密的byte流
	 * @return 加密后的byte流
	 * @throws Exception
	 */
	public static byte[] md5(byte[] input) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5"); // or "SHA-1"

		alg.update(input);
		byte[] digest = alg.digest();

		return digest;
	}

	/**
	 * 数字转换成人民币
	 * 
	 * @param double 需要转换的人民币金额
	 * @return String
	 * @throws Exception
	 */
	public static String changeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 把回车替换成<br>
	 * 
	 * @param str
	 * @return
	 */
	public static String enterChange(String str) {
		StringBuffer sbTemp = new StringBuffer(str);
		for (int i = 0; i < sbTemp.length(); i++) {
			if (sbTemp.substring(i, i + 1).equals("\n")) {
				sbTemp.delete(i, i + 1);
				sbTemp.insert(i, "<br>");
			}
		}
		return sbTemp.toString();
	}

	/**
	 * 把<br>
	 * 替换成回车
	 * 
	 * @param str
	 * @return
	 */
	public static String enterChangeBack(String str) {
		str = str.replaceAll("<br>", "\n");
		str = str.replaceAll("<br/>", "\n");
		return str;
	}

	/**
	 * 返回num个空格
	 * 
	 * @param num
	 * @return
	 */
	public static String getSpaces(int num) {
		StringBuffer sbr = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sbr.append(" ");
		}
		return sbr.toString();
	}

	/**
	 * 返回num个0
	 * 
	 * @param num
	 * @return
	 */
	public static String getZero(int num) {
		StringBuffer sbr = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sbr.append("0");
		}
		return sbr.toString();
	}

	/**
	 * 把文件输出成byte数组
	 * 
	 * @param file
	 * @return byte数组
	 * @throws IOException
	 */
	public static byte[] file2Byte(File file) throws IOException {
		InputStream stream = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream(512);
		byte[] b = new byte[512];
		int n;
		while ((n = stream.read(b)) != -1) {
			out.write(b, 0, n);
		}
		stream.close();
		out.close();
		return out.toByteArray();
	}

	/**
	 * 把byte数组输出成文件 如果参数isReplace为false，将判断文件是否存在，如果文件存在，将抛出IOExcpetion
	 * 
	 * @param fileName
	 *            生成file的文件名
	 * @param file
	 *            byte数组
	 * @param isReplace
	 *            是否替换
	 * @throws IOException
	 */
	public static void byte2File(String fileName, byte[] file, boolean isReplace) throws IOException {
		File f = new File(fileName);
		if (!isReplace && f.isFile()) {
			throw new IOException("文件【" + fileName + "】存在");
		}
		OutputStream stream = new FileOutputStream(f);
		stream.write(file);
		stream.close();
	}

	/**
	 * 使用大对象数组把输入流转换成String
	 * 
	 * @param is
	 *            InutStrema
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 使用分段方式读取输入流，转换成String
	 * 
	 * @param in
	 *            输入流
	 * @param temp
	 *            步长（一般1024或者4096）
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream in, int temp) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[temp];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * 通过字段名，获得该字段名的封装方法，比如 String name;对应的封装方法为 setName 和 getName boolean key;对应的封装方法为setKey 和 isKey;boolean isTrue;对应的封装方法为setTrue 和 isTrue
	 * 
	 * @param filedName
	 *            字段的名字
	 * @param isBoolean
	 *            是否是boolean型 //目前不支持，都是使用的setter和getter
	 * @return String[]{"setter","getter"}
	 */
	public static String[] filedToMethod(String filedName, boolean isBoolean) {
		// TODO isBoolean目前没处理
		// 把filedName的第一个字符换成大写
		char[] filed = filedName.toCharArray();
		filed[0] = Character.toUpperCase(filed[0]);
		return new String[] { "set" + new String(filed), "get" + new String(filed) };
	}

	/**
	 * 通过对象，获得对象的值。如果是自定义对象，会调用toString方法，如果是Date类型，需要传入日期格式化
	 * 
	 * @param obj
	 * @param dateformat
	 * @return
	 */
	public static String getValue(Object obj, String dateformat) {
		if (obj == null) {
			return "null";
		}
		String re = "";
		if (obj instanceof String) {
			re = (String) obj;
		} else if (obj instanceof Integer) {
			re = String.valueOf(obj);
		} else if (obj instanceof Double) {
			re = String.valueOf(obj);
		} else if (obj instanceof Float) {
			re = String.valueOf(obj);
		} else if (obj instanceof Long) {
			re = String.valueOf(obj);
		} else if (obj instanceof Boolean) {
			re = String.valueOf(obj);
		} else if (obj instanceof Date) {
			re = StringUtil.dateToStr((Date) obj, dateformat);
		} else {
			re = obj.toString();
		}
		return re;
	}

	// 返回一个字节的十六进制字符串
	public static String hexByte(byte b) {
		String s = "000000" + Integer.toHexString(b);
		return s.substring(s.length() - 2);
	}

	/**
	 * 根据长度补0(前面)
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String makeZero(Integer num, int length) {
		StringBuilder snum = new StringBuilder(num.toString());
		int temp = 4 - snum.length();
		for (int i = 0; i < temp; i++) {
			snum.insert(0, "0");
		}
		return snum.toString();
	}

	/**
	 * 去掉重复的，大小写区分
	 * 
	 * @param source
	 * @return
	 */
	public static String[] distinct(String[] source) {
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < source.length; i++) {
			if (!list.contains(source[i])) {
				list.add(source[i]);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);

	}

	/**
	 * 获取月份的天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 */
	public static int getMonthDaysCount(int year, int month) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, year);
		gc.set(GregorianCalendar.MONTH, month);
		gc.set(GregorianCalendar.DATE, 1);
		gc.add(GregorianCalendar.DATE, -1);
		return gc.getTime().getDate();
	}

	/**
	 * 获取指定日期剩余天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @return
	 */
	public static Integer getRemainDays(Date date) {
		String[] time = now("yyyy-MM-dd", date).split("-");
		int totalDays = getMonthDaysCount(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		return totalDays - Integer.parseInt(time[2]) + 1;
	}

	/**
	 * 产生随机7位整数
	 */
	public static Integer getRandomInt() {
		Random rand = new Random();
		int i = rand.nextInt(); // int范围类的随机数
		i = rand.nextInt(1000000); // 生成0-100以内的随机数
		i = (int) (Math.random() * 1000000); // 0-100以内的随机数，用Matn.random()方式
		return i;
	}

	/**
	 * 组合字符串
	 * 
	 * @param source
	 * @param len
	 * @return e.g combinationStr(Integer 10,int 4)=0010 combinationStr(Integer 198,int 4)=0198
	 */
	public static String combinationStr(Integer source, int len) {
		StringBuffer sb = new StringBuffer();
		int size = String.valueOf(source).length();
		int zeroNum = len - size;
		if (zeroNum > 0) {
			for (int i = 0; i < zeroNum; i++) {
				sb.append("0");
			}
		}
		sb.append(String.valueOf(source));
		return sb.toString();
	}
}
