package commanutil.utl;

import android.util.LruCache;

import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * 全局的程序空间。程序的Application 要实现该接口。
 * 并对所有方法进行响应。
 * 且方法的返回值不能为 null.
 * </pre>
 *
 */
public interface IGloableHeap {
    /**
     * @return 全局存放String类型对象的Map
     */
    Map<String, String> getGloabalString();

    /**
     * @return 全局存放Object类型对象的Map
     */
    Map<String, Object> getGlobalObjectMap();

    /**
     * @return 全局存放Integer类型对象的Map
     */
    Map<String, Integer> getGlobalIntegerMap();

    /**
     * @return 全局存放Float类型对象的Map
     */
    Map<String, Float> getGlobalFloatMap();

    /**
     * @return 全局存放Long类型对象的Map
     */
    Map<String, Long> getGlobalLongMap();

    /**
     * @return 全局存放Boolean类型对象的Map
     */
    Map<String, Boolean> getGlobalBooleanMap();


    LruCache<String, Object> getFragmentCache();
}
