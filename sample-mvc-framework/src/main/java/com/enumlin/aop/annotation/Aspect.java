/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.annotation;

import java.lang.annotation.*;

/*
 * 切面注解
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-16
 * 
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Aspect {
    Class<? extends Annotation> value();
}
