/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jcamera.examples.jogl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLException;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.VectorI3F;
import com.jogamp.common.nio.Buffers;

final class ExampleScene
{
  private static final int VERTEX_COLOR_OFFSET_BYTES;
  private static final int VERTEX_COMPONENTS;
  private static final int VERTEX_COUNT;
  private static final int VERTEX_SIZE_BYTES;
  private static final int VERTICES_TOTAL_ELEMENTS;
  private static final int VERTICES_TOTAL_SIZE_BYTES;

  static {
    VERTEX_COUNT = 4;
    VERTEX_COMPONENTS = 2 * 3;
    VERTEX_COLOR_OFFSET_BYTES = 3 * 4;
    VERTEX_SIZE_BYTES = ExampleScene.VERTEX_COMPONENTS * 4;
    VERTICES_TOTAL_ELEMENTS =
      ExampleScene.VERTEX_COUNT * ExampleScene.VERTEX_COMPONENTS;
    VERTICES_TOTAL_SIZE_BYTES =
      ExampleScene.VERTEX_COUNT * ExampleScene.VERTEX_SIZE_BYTES;
  }

  private static float noise(
    final float x)
  {
    final int z = (int) x;
    final int q = (z << 13) ^ z;
    final int s =
      ((q * ((q * q * 15731) + 789221)) + 1376312589) & 0x7fffffff;
    final float r = 1.0f - (s / 1073741824.0f);
    return Math.abs(r);
  }

  private static int compileFragmentShader(
    final GL3 g,
    final IntBuffer status)
    throws FileNotFoundException,
      IOException
  {
    final InputStream f_stream =
      ExampleScene.class.getResourceAsStream("basic.f");
    if (f_stream == null) {
      throw new FileNotFoundException("basic.f");
    }

    final List<String> fragment_source = ShaderUtilities.readLines(f_stream);
    final IntBuffer fragment_lengths =
      Buffers.newDirectIntBuffer(fragment_source.size());
    final String[] fragment_lines = new String[fragment_source.size()];
    for (int index = 0; index < fragment_source.size(); ++index) {
      final String line = fragment_source.get(index);
      fragment_lines[index] = line;
      fragment_lengths.put(index, line.length());
    }

    final int fs = g.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
    g.glShaderSource(
      fs,
      fragment_source.size(),
      fragment_lines,
      fragment_lengths);
    g.glCompileShader(fs);
    g.glGetShaderiv(fs, GL2ES2.GL_COMPILE_STATUS, status);

    if (status.get(0) == GL.GL_FALSE) {
      final ByteBuffer log_buffer = Buffers.newDirectByteBuffer(8192);
      final IntBuffer buffer_length = Buffers.newDirectIntBuffer(1);
      g.glGetShaderInfoLog(fs, 8192, buffer_length, log_buffer);

      final byte[] raw = new byte[log_buffer.remaining()];
      log_buffer.get(raw);
      final String tt = new String(raw);
      throw new GLException("compile: fragment shader: " + tt);
    }
    return fs;
  }

  private static int compileVertexShader(
    final GL3 g,
    final IntBuffer status)
    throws FileNotFoundException,
      IOException
  {
    final InputStream v_stream =
      ExampleScene.class.getResourceAsStream("basic.v");
    if (v_stream == null) {
      throw new FileNotFoundException("basic.v");
    }

    final List<String> vertex_source = ShaderUtilities.readLines(v_stream);
    final IntBuffer vertex_lengths =
      Buffers.newDirectIntBuffer(vertex_source.size());
    final String[] vertex_lines = new String[vertex_source.size()];
    for (int index = 0; index < vertex_source.size(); ++index) {
      final String line = vertex_source.get(index);
      vertex_lines[index] = line;
      vertex_lengths.put(index, line.length());
    }

    final int vs = g.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
    g.glShaderSource(vs, vertex_source.size(), vertex_lines, vertex_lengths);
    g.glCompileShader(vs);
    g.glGetShaderiv(vs, GL2ES2.GL_COMPILE_STATUS, status);

    if (status.get(0) == GL.GL_FALSE) {
      final ByteBuffer log_buffer = Buffers.newDirectByteBuffer(8192);
      final IntBuffer buffer_length = Buffers.newDirectIntBuffer(1);
      g.glGetShaderInfoLog(vs, 8192, buffer_length, log_buffer);

      final byte[] raw = new byte[log_buffer.remaining()];
      log_buffer.get(raw);
      final String tt = new String(raw);
      throw new GLException("compile: vertex shader: " + tt);
    }

    v_stream.close();
    return vs;
  }

  private static int createProgram(
    final GL3 g,
    final int vs,
    final int fs,
    final IntBuffer status)
  {
    final int id = g.glCreateProgram();
    if (id == 0) {
      throw new GLException("glCreateProgram failed");
    }

    g.glAttachShader(id, vs);
    g.glAttachShader(id, fs);
    g.glLinkProgram(id);
    g.glGetProgramiv(id, GL2ES2.GL_LINK_STATUS, status);

    if (status.get(0) == GL.GL_FALSE) {
      final ByteBuffer buffer = Buffers.newDirectByteBuffer(8192);
      final IntBuffer buffer_length = Buffers.newDirectIntBuffer(1);
      g.glGetProgramInfoLog(id, 8192, buffer_length, buffer);

      final byte[] raw = new byte[buffer.remaining()];
      buffer.get(raw);
      final String tt = new String(raw);
      throw new GLException("compile: program: " + tt);
    }

    return id;
  }

  private static int makeIndices(
    final GL3 g)
  {
    final IntBuffer data = Buffers.newDirectIntBuffer(2 * 3);

    data.put(0, 0);
    data.put(1, 1);
    data.put(2, 2);
    data.put(3, 0);
    data.put(4, 2);
    data.put(5, 3);

    final IntBuffer buffers = Buffers.newDirectIntBuffer(1);
    g.glGenBuffers(1, buffers);
    final int id = buffers.get(0);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, id);
    g
      .glBufferData(
        GL.GL_ELEMENT_ARRAY_BUFFER,
        6 * 4,
        data,
        GL.GL_STATIC_DRAW);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    return id;
  }

  private static int makeMesh(
    final GL3 g)
  {
    final FloatBuffer data =
      Buffers.newDirectFloatBuffer(ExampleScene.VERTICES_TOTAL_ELEMENTS);

    int base = 0;
    data.put(base + 0, -0.5f);
    data.put(base + 1, 0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 0.0f);
    data.put(base + 4, 1.0f);
    data.put(base + 5, 0.0f);

    base += ExampleScene.VERTEX_COMPONENTS;
    data.put(base + 0, -0.5f);
    data.put(base + 1, -0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    base += ExampleScene.VERTEX_COMPONENTS;
    data.put(base + 0, 0.5f);
    data.put(base + 1, -0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 1.0f);
    data.put(base + 5, 0.0f);

    base += ExampleScene.VERTEX_COMPONENTS;
    data.put(base + 0, 0.5f);
    data.put(base + 1, 0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 1.0f);
    data.put(base + 5, 1.0f);

    final IntBuffer buffers = Buffers.newDirectIntBuffer(1);
    g.glGenBuffers(1, buffers);
    final int id = buffers.get(0);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
    g.glBufferData(
      GL.GL_ARRAY_BUFFER,
      ExampleScene.VERTICES_TOTAL_SIZE_BYTES,
      data,
      GL.GL_STATIC_DRAW);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    return id;
  }

  private static int makeProgram(
    final GL3 g)
    throws IOException
  {
    final IntBuffer status = Buffers.newDirectIntBuffer(1);
    assert status != null;
    final int vs = ExampleScene.compileVertexShader(g, status);
    final int fs = ExampleScene.compileFragmentShader(g, status);
    final int p = ExampleScene.createProgram(g, vs, fs, status);
    return p;
  }

  private final GL3         gl;
  private final int         indices;
  private final int         mesh;
  private final int         program;
  private final MatrixM4x4F projection;
  private final MatrixM4x4F model;
  private final MatrixM4x4F modelview;

  ExampleScene(
    final GL3 in_gl)
    throws IOException
  {
    this.gl = NullCheck.notNull(in_gl, "GL");
    this.program = ExampleScene.makeProgram(this.gl);
    this.mesh = ExampleScene.makeMesh(this.gl);
    this.indices = ExampleScene.makeIndices(this.gl);
    this.projection = new MatrixM4x4F();
    this.model = new MatrixM4x4F();
    this.modelview = new MatrixM4x4F();
  }

  void draw(
    final MatrixReadable4x4FType view_matrix)
  {
    NullCheck.notNull(view_matrix, "View matrix");

    this.gl.glClearDepth(1.0f);
    this.gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
    this.gl.glClear(GL.GL_COLOR_BUFFER_BIT
      | GL.GL_DEPTH_BUFFER_BIT
      | GL.GL_STENCIL_BUFFER_BIT);
    this.gl.glEnable(GL.GL_DEPTH_TEST);
    this.gl.glDepthFunc(GL.GL_LEQUAL);
    this.gl.glDisable(GL.GL_CULL_FACE);

    this.gl.glUseProgram(this.program);
    this.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, this.mesh);

    final int position_attrib;
    final int color_attrib;

    {
      position_attrib =
        this.gl.glGetAttribLocation(this.program, "v_position");
      assert position_attrib != -1;

      this.gl.glEnableVertexAttribArray(position_attrib);
      this.gl.glVertexAttribPointer(
        position_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleScene.VERTEX_SIZE_BYTES,
        0);
    }

    {
      color_attrib = this.gl.glGetAttribLocation(this.program, "v_color");
      assert color_attrib != -1;

      this.gl.glEnableVertexAttribArray(color_attrib);
      this.gl.glVertexAttribPointer(
        color_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleScene.VERTEX_SIZE_BYTES,
        ExampleScene.VERTEX_COLOR_OFFSET_BYTES);
    }

    {
      final int uid =
        this.gl.glGetUniformLocation(this.program, "m_projection");
      assert uid != -1;
      this.gl.glUniformMatrix4fv(
        uid,
        1,
        false,
        this.projection.getFloatBuffer());
    }

    this.gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, this.indices);

    {
      final int uid =
        this.gl.glGetUniformLocation(this.program, "m_modelview");
      assert uid != -1;

      for (int index = 0; index < 100; ++index) {
        final float x = 10.0f - (ExampleScene.noise(index * 10) * 20.0f);
        final float y = 0.0f;
        final float z = 10.0f - (ExampleScene.noise(index * 100) * 20.0f);

        MatrixM4x4F.setIdentity(this.model);
        MatrixM4x4F.translateByVector3FInPlace(this.model, new VectorI3F(
          x,
          y,
          -z));

        MatrixM4x4F.setIdentity(this.modelview);
        MatrixM4x4F.multiply(view_matrix, this.model, this.modelview);

        this.gl.glUniformMatrix4fv(
          uid,
          1,
          false,
          this.modelview.getFloatBuffer());

        this.gl.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0L);
      }
    }

    this.gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    this.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

    this.gl.glDisableVertexAttribArray(position_attrib);
    this.gl.glDisableVertexAttribArray(color_attrib);
    this.gl.glUseProgram(0);
  }

  void reshape(
    final int width,
    final int height)
  {
    final float fw = width;
    final float fh = height;
    ProjectionMatrix.makePerspectiveProjection(
      this.projection,
      0.01f,
      100.0f,
      fw / fh,
      Math.toRadians(90.0f));
  }
}
