#!/bin/sh

cat <<EOF
<?xml version="1.0" encoding="UTF-8"?>

<!--
  Copyright © 2016 <code@io7m.com> http://io7m.com

  Permission to use, copy, modify, and/or distribute this software for any
  purpose with or without fee is hereby granted, provided that the above
  copyright notice and this permission notice appear in all copies.

  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
  -->

<s:section
  xml:id="usage.files"
  xmlns:s="http://schemas.io7m.com/structural/2.0.0"
  xmlns:xi="http://www.w3.org/2001/XInclude">
  <s:section-title>Example sources</s:section-title>
  <s:section-contents/>
  <s:paragraph>
    The list of example source files:
  </s:paragraph>
  <s:formal-item s:kind="listings">
    <s:formal-item-title>Source files</s:formal-item-title>
    <s:list-unordered>
EOF

CWD=`pwd` || exit 1
cd ../../../../io7m-jcamera-examples-jogl/src/main/java || exit 1

for f in `echo com/io7m/jcamera/examples/jogl/*`
do
  BASE=`basename "$f"` || exit 1
  cat <<EOF
      <s:list-item><s:link-external s:target="${f}">${BASE}</s:link-external></s:list-item>
EOF
done

cd "${CWD}" || exit 1
cd ../../../../io7m-jcamera-examples-jogl/src/main/resources || exit 1

for f in `echo com/io7m/jcamera/examples/jogl/*`
do
  BASE=`basename "$f"` || exit 1
  cat <<EOF
      <s:list-item><s:link-external s:target="${f}">${BASE}</s:link-external></s:list-item>
EOF
done

cat <<EOF
    </s:list-unordered>
  </s:formal-item>
</s:section>
EOF
