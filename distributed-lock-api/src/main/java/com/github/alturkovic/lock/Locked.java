/*
 * MIT License
 *
 * Copyright (c) 2018 Alen Turkovic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.alturkovic.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Locked {

  /**
   * Flag to indicate if lock will be manually released.
   * By default, lock will be released after method execution
   */
  boolean manuallyReleased() default false;

  /**
   * Id of a specific store for lock to use.
   * For JDBC, this would be a lock table.
   * For Mongo, this would be a collection name.
   */
  String storeId() default "lock";

  /**
   * Prefix of all generated lock keys
   */
  String prefix() default "lock:";

  /**
   * SpEL expression with all arguments passed as SpEL variables {@code args} and available execution context.
   * By default, it will evaluate to the absolute method path (class + method) by evaluating a special 'executionPath' variable
   */
  String expression() default "#executionPath";

  /**
   * Names of parameters in expression.
   * Parameters will be available to the SpEL as: 'parameter' + index (default: p0, p1, ...)
   */
  String parameter() default "p";

  /**
   * Lock expiration interval
   */
  Interval expiration() default @Interval(value = "10", unit = TimeUnit.SECONDS);

  /**
   * Lock timeout interval
   */
  Interval timeout() default @Interval(value = "1", unit = TimeUnit.SECONDS);

  /**
   * Lock retry interval
   */
  Interval retry() default @Interval(value = "50");

  /**
   * Lock type, see implementations of {@link Lock}
   */
  Class<? extends Lock> type() default Lock.class;
}
