/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

import com.enumlin.aop.proxy.CGLibProxy;

/*
 * 描述
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-05
 *
        */
public class Hello {
    public void sayHelo() {
        System.out.println("Hello.sayHelo");
    }

    public static void main(String[] args) {
        Hello proxy = CGLibProxy.getInstance().getProxy(Hello.class);
        proxy.sayHelo();
    }
}
