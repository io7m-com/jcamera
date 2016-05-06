/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jcamera.tests;

import com.io7m.jequality.validator.AnnotationRequirement;
import com.io7m.jequality.validator.EqualityValidator;
import com.io7m.jequality.validator.ValidatorResult;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@SuppressWarnings("static-method") public final class EqualityTest
{
  private static Reflections setupReflections()
  {
    final List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    classLoadersList.add(ClasspathHelper.contextClassLoader());
    classLoadersList.add(ClasspathHelper.staticClassLoader());

    final FilterBuilder filter =
      new FilterBuilder()
        .include(FilterBuilder.prefix("com.io7m.jcamera"))
        .exclude(FilterBuilder.prefix("com.io7m.jcamera.tests"));

    final Collection<URL> urls =
      ClasspathHelper.forClassLoader(classLoadersList
        .toArray(new ClassLoader[0]));

    return new Reflections(new ConfigurationBuilder()
      .setScanners(
        new SubTypesScanner(false /* don't exclude Object.class */),
        new ResourcesScanner())
      .setUrls(urls)
      .filterInputsBy(filter));
  }

  @Test public void testEqualities()
  {
    final Reflections ref = EqualityTest.setupReflections();
    final Set<Class<? extends Object>> types =
      ref.getSubTypesOf(Object.class);

    final SortedMap<String, Class<?>> by_name =
      new TreeMap<String, Class<?>>();
    for (final Class<? extends Object> c : types) {
      assert c != null;

      if (Modifier.isInterface(c.getModifiers())) {
        continue;
      }
      if (c.isAnonymousClass()) {
        continue;
      }
      if (c.getCanonicalName() == null) {
        continue;
      }

      final String name = c.getCanonicalName();
      assert by_name.containsKey(name) == false;
      by_name.put(name, c);
    }

    final SortedMap<String, ValidatorResult> errors =
      new TreeMap<String, ValidatorResult>();

    for (final String name : by_name.keySet()) {
      final Class<?> c = by_name.get(name);
      assert c != null;

      System.out.printf("Checking %s\n", name);
      System.out.flush();

      final ValidatorResult r =
        EqualityValidator.validateClass(
          c,
          AnnotationRequirement.ANNOTATIONS_REQUIRED,
          true);

      if (r != ValidatorResult.VALIDATION_OK) {
        assert errors.containsKey(name) == false;
        errors.put(name, r);
      }
    }

    System.out.printf("--\n");
    System.out.flush();

    if (errors.size() > 0) {
      for (final String name : errors.keySet()) {
        final ValidatorResult v = errors.get(name);
        System.out.printf("Error: %s result %s\n", name, v);
        System.out.flush();
      }
      throw new AssertionError("Some classes failed");
    }
  }

}
