package com.techlon.base.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import com.techlon.base.util.string.StringUtil;

/**
 * 产生json文件的工具
 */
public class JsonUtil {
	public final static int FORM = 1;

	public final static int GRID = 2;

	public final static int STORE = 3;

	public final static int TREE = 4;

	/**
	 * 不允许实例化
	 */
	private JsonUtil() {
	}

	/**
	 * 获得读取数据成功的信息
	 * 
	 * @param data
	 *            封装form表单数据 key是表单的name value是name对应的值
	 *            封装grid表格数据，key为任意值，value为封装记录的Hashtable
	 * @param results
	 *            如果是返回grid数据，需要传入总条数
	 * @param type
	 *            需要返回的类型 目前提供 JsonUtil.FORM和JsonUtil.GRID
	 * @return success:true, data : { key1 : value1, key2 : value2, key3 :
	 *         value3, keyn : valuen } } 或者 { 'results': 总数,'rows': { {key1 :
	 *         value1,key2 : value2,...keyn : valuen} {key1 : value1,key2 :
	 *         value2,...keyn : valuen} } }
	 * @throws NullPointerException
	 */
	public static String getSuccess(Hashtable data, int results, int type)
			throws JsonUtilException {
		if (type == FORM) {
			return getFormLoad(data, "true");
		}
		throw new JsonUtilException("type解析错误");
	}

	/**
	 * 获得读取数据成功的信息
	 * 
	 * @param data
	 *            封装form表单数据 key是表单的name value是name对应的值
	 *            封装grid表格数据，key为任意值，value为封装记录的Hashtable
	 * @param results
	 *            如果是返回grid数据，需要传入总条数
	 * @param type
	 *            需要返回的类型 目前提供 JsonUtil.FORM和JsonUtil.GRID
	 * @return success:true, data : { key1 : value1, key2 : value2, key3 :
	 *         value3, ... keyn : valuen } } 或者 { 'results': 总数,'rows': { {key1
	 *         : value1,key2 : value2,...keyn : valuen} ... {key1 : value1,key2
	 *         : value2,...keyn : valuen} } }
	 * @throws JsonUtilException
	 */
	public static String getSuccess(List data, int results, int type)
			throws JsonUtilException {
		if (type == GRID) {
			return getGridLoad(data, results);
		} else if (type == STORE) {
			return getStore(data);
		}
		throw new JsonUtilException("type解析错误");
	}

	/**
	 * @param data
	 *            封装的数据String str = new String ["value1","key1"];....
	 *            list.add(str);
	 * @return 拼装好的json [ ['value1','key1'], ['value2','key2'], ...
	 *         ['valuen','keyn'] ]
	 * @throws JsonUtilException
	 */
	private static String getStore(List<String[]> data)
			throws JsonUtilException {
		StringBuffer json = new StringBuffer();
		json.append("[");
		int i = 1;
		for (String str[] : data) {
			json.append("['");
			json.append(str[0]);
			json.append("','");
			json.append(str[1]);
			json.append("']");
			if (i != data.size()) {
				json.append(",");
			}
			i++;
		}
		json.append("]");
		return json.toString();
	}

	/**
	 * 获得读取数据失败的信息
	 * 
	 * @param data
	 *            封装表单数据 key是表单的name value是name对应的值
	 * @return success:false, data : { key1 : value1, key2 : value2, key3 :
	 *         value3, ... keyn : valuen } }
	 * @param data
	 * @return
	 * @throws JsonUtilException
	 */
	public static String getFailure(Hashtable<String, String> data)
			throws JsonUtilException {
		return getFormLoad(data, "false");
	}

	/**
	 * 返回form表单读取数据用的json文件
	 * 
	 * @param data
	 *            封装表单数据 key是表单的name value是name对应的值
	 * @param success
	 *            成功还是失败
	 * @return
	 * @throws JsonUtilException
	 */
	private static String getFormLoad(Hashtable<String, String> data,
			String success) throws JsonUtilException {
		if (success == null) {
			throw new JsonUtilException("success 不能为空");
		}
		StringBuffer json = new StringBuffer("{success:").append(success)
				.append(",data:{");
		Object[] keys = data.keySet().toArray();
		int i = 0;
		for (Object key : keys) {
			i++;
			json.append(key).append(":'").append(data.get(key)).append("'");
			if (i != data.size()) {
				json.append(",");
			}
		}
		json.append("}");
		if ("false".equals(success)) {
			json.append(",errors:'").append(data.get("errors")).append("'");
		}
		json.append("}");
		return json.toString();
	}

	/**
	 * 返回Grid表格读取数据用的json文件
	 * 
	 * @param data
	 *            封装表单数据 key为任意值，value为封装记录的Hashtable
	 * @param results
	 *            总数
	 * @return
	 * @throws JsonUtilException
	 */
	private static String getGridLoad(List data, int results)
			throws JsonUtilException {
		StringBuffer json = new StringBuffer("{'results':").append(results)
				.append(",rows:[");
		int i = 0;
		int j = 0;
		// 取得外层的数据封装
		for (Object obj : data) {
			Hashtable<String, String> temp = (Hashtable<String, String>) obj;
			i++;
			if (temp == null) {
				continue;
			}
			json.append("{");
			// 取得内层的数据封装
			Object[] keyTemp = temp.keySet().toArray();
			j = 0;
			for (Object k : keyTemp) {
				j++;
				json.append(k).append(":'").append(temp.get(k)).append("'");
				if (j != temp.size()) {
					json.append(",");
				}
			}
			json.append("}");
			// 内层数据取得完成
			if (i != data.size()) {
				json.append(",");
			}
		}
		json.append("]}");
		return json.toString();
	}

	/**
	 * 获得josn树结构，只支持自关联对象
	 * 
	 * @param root
	 *            根节点对象
	 * @param idMethod
	 *            获得id的方法
	 * @param subIdsMethod
	 *            获得子对象id的方法
	 * @param excludes
	 *            不显示的字段
	 * @param treeFormat
	 *            需要替换掉的字段（被替换的字段:替换成的字段）
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static String getTree(Object root, String idMethod,
			String subIdsMethod, String[] excludes,
			Map<String, String> treeFormat) throws ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		return getTree(root, idMethod, subIdsMethod, excludes, treeFormat,
				false, null, null);
	}

	/**
	 * 获得josn树结构，只支持自关联对象
	 * 
	 * @param root
	 *            根节点对象
	 * @param idMethod
	 *            获得id的方法
	 * @param subIdsMethod
	 *            获得子对象id的方法
	 * @param excludes
	 *            不显示的字段
	 * @param treeFormat
	 *            需要替换掉的字段（被替换的字段:替换成的字段）
	 * @param isCheck
	 *            是否显示checkbox ，如果是false，那么checkedId不会处理
	 * @param checkedId
	 *            被选定的id
	 * @param includes
	 *            如果需要显示checkbox，那么需要添加要显示的对象
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static String getTree(Object root, String idMethod,
			String subIdsMethod, String[] excludes,
			Map<String, String> treeFormat, boolean isCheck,
			String[] checkedId, String[] includes)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		StringBuffer json = new StringBuffer("[");
		Class<?> clasz = root.getClass();
		// Method getId = clasz.getMethod(idMethod, new Class[] {});//
		// 获得id的方法，返回id，只能是返回String
		// Method getChildren = clasz.getMethod(subIdsMethod, new Class[] {});//
		// 获得子对象的方法，目前只支持返回set的方式
		// 获得root的主干
		json.append(getLeaf(root, idMethod, subIdsMethod, excludes, isCheck,
				checkedId, includes));
		if (Character.valueOf(json.charAt(json.length() - 1)).equals(',')) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]");
		String js = json.toString();
		// 替换树的结构
		if (treeFormat != null) {
			Object[] keys = treeFormat.keySet().toArray();
			for (Object key : keys) {
				// System.out.println(key);
				js = js.replaceAll((String) key, treeFormat.get(key));
			}
		}

		return js;
	}

	/**
	 * 通过递归遍历出树对象
	 * 
	 * @param obj
	 * @param idMethod
	 * @param getChildren
	 * @param excludes
	 * @param isCheck
	 * @param checkedId
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static String getLeaf(Object obj, String idMethod,
			String getChildren, String[] excludes, boolean isCheck,
			String[] checkedId, String[] includes)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		StringBuffer json = new StringBuffer();
		Method getChild = obj.getClass().getMethod(getChildren, new Class[] {});// 获得子对象的方法，目前只支持返回set的方式
		Set trunk = (Set) getChild.invoke(obj, null);
		if (trunk != null && trunk.size() > 0) {
			Method getId = obj.getClass().getMethod(idMethod, new Class[] {});// 获得id的方法，返回id，只能是返回String
			String id = (String) getId.invoke(obj, null);// 当前对象的id
			json.append("{");
			// json.append("\"id\"").append(":\"").append(id).append("\",");
			json.append("\"children\":[");
			Object objs[] = trunk.toArray();
			try {
				// 排序，不过类自己必须实现排序方法
				Arrays.sort(objs);
			} catch (Exception e) {
			}
			for (Object thisObj : objs) {
				Method getThisId = thisObj.getClass().getMethod(idMethod,
						new Class[] {});// 获得id的方法，返回id，只能是返回String
				String thisId = (String) getThisId.invoke(thisObj, null);// set中对象的id
				// System.out.println("thisId -- >" + thisId);
				// 抛掉自己
				if (!thisId.equals(id)) {
					json.append(getLeaf(thisObj, idMethod, getChildren,
							excludes, isCheck, checkedId, includes));
				}
			}
			// Iterator it = trunk.iterator();
			// while (it.hasNext()) {
			// Object thisObj = it.next();
			// String thisId = (String) idMethod.invoke(thisObj, null);//
			// set中对象的id
			// // 抛掉自己
			// if (!thisId.equals(id)) {
			// json.append(getLeaf(thisObj, idMethod, getChildren,
			// excludes, isCheck, checkedId, includes));
			// }
			// }
			// 去掉最后一个多余的逗号
			json.deleteCharAt(json.length() - 1);
			json.append("],");
			// 添加当前对象其他属性
			// 获得所有属性
			Class<?> clasz = obj.getClass();
			// 获得所有属性
			Field[] fields = clasz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// 如果当前字段是被排除
				if (StringUtil.contains(excludes, fields[i].getName().trim())) {
					continue;
				}
				// 获得方法名
				// TODO 没有处理boolean

				try {
					Method getter = clasz.getMethod(StringUtil.filedToMethod(
							fields[i].getName(), false)[1]);
					if (getChild.getName().equals(getter.getName())) {
						continue;
					}
					Object thisObj = getter.invoke(obj, null);
					json.append("\"").append(fields[i].getName()).append(
							"\":\"")
							.append(
									StringUtil.getValue(thisObj,
											"yyyy-MM-dd HH-mm-ss"))
							.append("\"");

					json.append(",");

				} catch (Exception e) {
					// e.printStackTrace();
				}

			}
			json.deleteCharAt(json.length() - 1);
			// 判断是否需要输入check,只要需要check，那么就需要自己处理
			if (isCheck) {
				json.append(",\"checked\":").append(
						StringUtil.contains(checkedId, id)).append(",");
				for (int i = 0; i < includes.length; i++) {
					String value = "null";
					try {
						String getter = StringUtil.filedToMethod(includes[i]
								.trim(), true)[1];
						Method m = obj.getClass().getMethod(getter);
						value = StringUtil.getValue(m.invoke(obj),
								"yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					json.append("\"").append(includes[i]).append("\":\"")
							.append(value).append("\"");
					// if (i != includes.length - 1) {
					json.append(",");
					// }
				}
				json.deleteCharAt(json.length() - 1);
			}
			json.append("}");
		} else {
			// 直接获得obj的对象封装
			JSONObject leaf;
			if (excludes == null) {
				excludes = new String[] {};
			}
			Method getId = obj.getClass().getMethod(idMethod, new Class[] {});// 获得id的方法，返回id，只能是返回String
			String id = (String) getId.invoke(obj, null);// 当前对象的id
			// 由于hibernate的问题，这里需要自己处理
			if (isCheck && StringUtil.contains(checkedId, id)) {
				json.append("{");
				if (includes == null) {
					includes = new String[0];
				}
				for (int i = 0; i < includes.length; i++) {
					String value = "null";
					try {
						String getter = StringUtil.filedToMethod(includes[i]
								.trim(), true)[1];
						Method m = obj.getClass().getMethod(getter);
						value = StringUtil.getValue(m.invoke(obj),
								"yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					json.append("\"").append(includes[i]).append("\":\"")
							.append(value).append("\"");
					if (i != includes.length - 1) {
						json.append(",");
					}
				}
				json.append(",\"leaf\":true,").append("\"checked\":true");
				json.append("}");
			} else {
				JsonConfig jsonConfig = configJson(excludes,
						"yyyy-MM-dd HH-mm-ss");
				leaf = JSONObject.fromObject(obj, jsonConfig);
				leaf.put("leaf", true);
				if (isCheck) {
					leaf.put("checked", false);
				}
				// 添加到json
				json.append(leaf.toString());
			}
		}
		json.append(",");
		return json.toString();
	}

	/**
	 * 通过递归遍历出树对象
	 * 
	 * @param obj
	 * @param getChildren
	 * @param excludes
	 * @param id
	 *            当前对象的id
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	// private static String getLeaf(Object obj, Method idMethod,
	// Method getChildren, String[] excludes)
	// throws IllegalArgumentException, IllegalAccessException,
	// InvocationTargetException {
	// StringBuffer json = new StringBuffer();
	// Set trunk = (Set) getChildren.invoke(obj, null);
	// if (trunk != null && trunk.size() > 0) {
	// String id = (String) idMethod.invoke(obj, null);// 当前对象的id
	// json.append("{\"children\":[");
	// Iterator it = trunk.iterator();
	// while (it.hasNext()) {
	// Object thisObj = it.next();
	// String thisId = (String) idMethod.invoke(thisObj, null);// set中对象的id
	// // 抛掉自己
	// if (!thisId.equals(id)) {
	// json.append(getLeaf(thisObj, idMethod, getChildren,
	// excludes));
	// }
	// }
	// // 去掉最后一个多余的逗号
	// json.deleteCharAt(json.length() - 1);
	// json.append("],");
	// // 添加当前对象其他属性
	// // 获得所有属性
	// Class<?> clasz = obj.getClass();
	// // 获得所有属性
	// Field[] fields = clasz.getDeclaredFields();
	// for (int i = 0; i < fields.length; i++) {
	// // 如果当前字段是被排除
	// if (StringUtil.contains(excludes, fields[i].getName().trim())) {
	// continue;
	// }
	// // 获得方法名
	// // TODO 没有处理boolean
	//
	// try {
	// Method getter = clasz.getMethod(StringUtil.filedToMethod(
	// fields[i].getName(), false)[1]);
	// if (getChildren.getName().equals(getter.getName())) {
	// continue;
	// }
	// Object thisObj = getter.invoke(obj, null);
	// json.append("\"").append(fields[i].getName()).append(
	// "\":\"")
	// .append(
	// StringUtil.getValue(thisObj,
	// "yyyy-MM-dd HH-mm-ss"))
	// .append("\"");
	//
	// json.append(",");
	//
	// } catch (Exception e) {
	// // e.printStackTrace();
	// }
	//
	// }
	// json.deleteCharAt(json.length() - 1);
	// json.append("}");
	// } else {
	// // 直接获得obj的对象封装
	// JSONObject leaf;
	// if (excludes == null) {
	// excludes = new String[] {};
	// }
	// JsonConfig jsonConfig = configJson(excludes, "yyyy-MM-dd HH-mm-ss");
	// leaf = JSONObject.fromObject(obj, jsonConfig);
	// leaf.put("leaf", true);
	// // 添加到json
	// json.append(leaf.toString());
	// }
	// json.append(",");
	// return json.toString();
	// }

	/** */
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/** */
	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/** */
	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/** */
	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/** */
	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/** */
	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Date 型对象数组，使用本方法必须保证
	 * 
	 * @param jsonString
	 * @return
	 */
	/*
	 * public static Date[] getDateArray4Json(String jsonString,String
	 * DataFormat){ JSONArray jsonArray = JSONArray.fromObject(jsonString);
	 * Date[] dateArray = new Date[jsonArray.size()]; String dateString; Date
	 * date; for( int i = 0 ; i<jsonArray.size() ; i++ ){ dateString =
	 * jsonArray.getString(i); date = DateUtil.stringToDate(dateString,
	 * DataFormat); dateArray[i] = date; } return dateArray; }
	 */
	/** */
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/** */
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
			String[] excludes, String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(excludes, dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * 将java对象序列转换成json字符串,并设定日期格式
	 * 
	 * @param javaObjs
	 *            对象序列
	 * @param excludes
	 *            排除的属性
	 * @param dataFormat
	 *            日期格式
	 * @return
	 */
	public static String getJsonString4Array(Collection javaObjs,
			String[] excludes, String dataFormat) {
		JSONArray json;
		JsonConfig jsonConfig = configJson(excludes, dataFormat);
		json = JSONArray.fromObject(javaObjs, jsonConfig);
		return json.toString();
	}

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	/** */
	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	/** */
	/**
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(final String[] excludes,
			String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (StringUtil.contains(excludes, name)) {
					return true;
				} else {
					return false;
				}
			}
		});
		return jsonConfig;
	}
}