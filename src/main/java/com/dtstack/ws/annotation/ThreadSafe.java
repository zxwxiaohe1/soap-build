package com.dtstack.ws.annotation;

import java.lang.annotation.*;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.CLASS)
public @interface ThreadSafe {
}
