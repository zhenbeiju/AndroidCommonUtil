package commanutil.inf;

public interface IGlobalValueOperation {
	/**
	 * 
	 * @param key 全局对象的key值
	 * @param value 全局对象key对应的value值
	 */
	void setGlobalBoolean(String key, boolean value) ;
	 /**
	  * 
	  * @param key 全局对象对应的key值
	  * @param value 全局对象对应的value值
	  */
	 void setGlobalString(String key, String value) ;
	 /**
	  * 
	  * @param key 全局对象的key值
	  * @param value 全局对象key对应的value值
	  */
	 void setGlobalInteger(String key, Integer value);
	 /**
		 * 
		 * @param key 全局对象的key值
		 * @param value 全局对象key对应的value值
		 */
	 void setGlobalLong(String key, Long value);
	 /**
		 * 
		 * @param key 全局对象的key值
		 * @param value 全局对象key对应的value值
		 */
	 void setGlobalObject(String key, Object value);
	 /**
		 * 
		 * @param key 全局对象的key值
		 * @param defVal 全局对象key对应的默认value值
		 * @return 对应的value值
		 */
	 boolean getGlobalBoolean(String key, boolean defVal);
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @param defVal 全局对象key对应的默认value值
	   	 * @return 对应的value值
	   	 */
	 String getGlobalString(String key, String defVal) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @param defVal 全局对象key对应的默认value值
	   	 * @return 对应的value值
	   	 */
	 int getGlobalInteger(String key, Integer defVal) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @param defVal 全局对象key对应的默认value值
	   	 * @return 对应的value值
	   	 */
	 long getGlobalLong(String key, Long defVal) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @param defVal 全局对象key对应的默认value值
	   	 * @return 对应的value值
	   	 */
	 Object getGlobalObject(String key, Object defVal) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 boolean getGlobalBoolean(String key);
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 float getGlobalFloat(String key) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 String getGlobalString(String key) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 int getGlobalInteger(String key) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 long getGlobalLong(String key) ;
	 /**
	   	 * 
	   	 * @param key 全局对象的key值
	   	 * @return key对应的value值
	   	 */
	 Object getGlobalObject(String key);
}
