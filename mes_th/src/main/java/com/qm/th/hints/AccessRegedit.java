package com.qm.th.hints;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RootKey;

/**
 * 通过读取注册表，获取信息
 * 
 * @author GaoHF
 */
final class AccessRegedit {
	/** 用于匹配目录字段的正则 */
	private static final Pattern pattern = Pattern.compile("[a-zA-Z]{1}:\\\\.*");
	/** 服务目录 */
	private static final String SERVICE_ROOT = "SYSTEM\\CurrentControlSet\\Services";
	/** 服务名称 */
	private static final String SERVICE_NAME = "FATHService";

	/**
	 * 获取文件夹路径
	 * 
	 * @return
	 */
	static String getPath(String name) {
		return loadProperty().getProperty(name);
	}
	
	/**
	 * 装载资源文件
	 */
	private static Properties loadProperty() {
		// 资源文件
		Properties properties = new Properties();
		// 文件流
		FileInputStream in = null;
		
		try{
			// 工程根目录
			String base = getBasePath();
			// 资源文件对象
			File p = getPropertiesFile(new File(base));
			
			// 装载资源文件到properties中
			if(p != null){
				in = new FileInputStream(p);
				properties.load(in);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// 资源清理
			if(in != null){
				try{
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					in = null;
				}
			}
		}
		return properties;
	}

	/**
	 * 配置文件目录
	 * 
	 * @return
	 */
	private static File getPropertiesFile(File dir) {
		// 遍历目录
		for (File f : dir.listFiles()) {
			// 发现子目录，则继续向下遍历
			if (f.isDirectory()) {
				return getPropertiesFile(f);
			}
			// 找到指定的properties文件， 结束查找
			if ("configuration.properties".equalsIgnoreCase(f.getName())) {
				return f;
			}
		}
		return null;
	}

	/**
	 * 工程根目录
	 * 
	 * @return
	 */
	private static String getBasePath() {
		// 读取注册表
		RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE,
				SERVICE_ROOT + File.separator + SERVICE_NAME + File.separator
						+ "Parameters");
		// 结果数据
		String result = "";
		// 通过正则截取指定字串
		java.util.regex.Matcher matcher = pattern.matcher(r.getValue(
				"AppDir").toString());
		// 获得截取文本
		if (matcher.find()) {
			result = matcher.group();
		}
		return result;
	}
}
