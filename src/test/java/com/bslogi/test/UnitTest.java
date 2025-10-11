package com.bslogi.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.test.annotation.IfProfileValue;

/**
 * Annotate your unit tests with this. (See https://moelholm.com/2016/10/22/spring-boot-separating-tests/.)
 */
@Retention(RetentionPolicy.RUNTIME)
@IfProfileValue(name = "testprofile", value = "unittest")
public @interface UnitTest {

}
