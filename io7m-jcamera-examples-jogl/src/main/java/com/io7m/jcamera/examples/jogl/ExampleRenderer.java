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
import java.util.concurrent.atomic.AtomicBoolean;

import javax.media.opengl.GL;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLException;

import com.io7m.jcamera.JCameraContext;
import com.io7m.jcamera.JCameraReadableSnapshotType;
import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.Window;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleRenderer implements ExampleRendererType
{
  private static final int VERTEX_COMPONENTS;
  private static final int VERTEX_COUNT;
  private static final int VERTICES_TOTAL_COMPONENTS;
  private static final int VERTICES_TOTAL_SIZE_BYTES;
  private static final int VERTEX_POSITION_COMPONENTS;
  private static final int VERTEX_POSITION_SIZE;
  private static final int VERTEX_POSITION_OFFSET;
  private static final int VERTEX_COLOR_COMPONENTS;
  private static final int VERTEX_COLOR_SIZE;
  private static final int VERTEX_COLOR_OFFSET;
  private static final int VERTEX_UV_COMPONENTS;
  private static final int VERTEX_UV_SIZE;
  private static final int VERTEX_UV_OFFSET;
  private static final int VERTEX_SIZE;

  static {
    VERTEX_POSITION_COMPONENTS = 3;
    VERTEX_POSITION_SIZE = ExampleRenderer.VERTEX_POSITION_COMPONENTS * 4;
    VERTEX_POSITION_OFFSET = 0;

    VERTEX_COLOR_COMPONENTS = 3;
    VERTEX_COLOR_SIZE = ExampleRenderer.VERTEX_COLOR_COMPONENTS * 4;
    VERTEX_COLOR_OFFSET =
      ExampleRenderer.VERTEX_POSITION_OFFSET
        + ExampleRenderer.VERTEX_POSITION_SIZE;

    VERTEX_UV_COMPONENTS = 2;
    VERTEX_UV_SIZE = ExampleRenderer.VERTEX_UV_COMPONENTS * 4;
    VERTEX_UV_OFFSET =
      ExampleRenderer.VERTEX_COLOR_OFFSET + ExampleRenderer.VERTEX_COLOR_SIZE;

    VERTEX_COUNT = 4;
    VERTEX_COMPONENTS =
      ExampleRenderer.VERTEX_POSITION_COMPONENTS
        + ExampleRenderer.VERTEX_COLOR_COMPONENTS
        + ExampleRenderer.VERTEX_UV_COMPONENTS;
    VERTEX_SIZE =
      ExampleRenderer.VERTEX_POSITION_SIZE
        + ExampleRenderer.VERTEX_COLOR_SIZE
        + ExampleRenderer.VERTEX_UV_SIZE;

    VERTICES_TOTAL_COMPONENTS =
      ExampleRenderer.VERTEX_COUNT * ExampleRenderer.VERTEX_COMPONENTS;
    VERTICES_TOTAL_SIZE_BYTES =
      ExampleRenderer.VERTEX_COUNT * ExampleRenderer.VERTEX_SIZE;
  }

  private static int compileFragmentShader(
    final GL3 g,
    final IntBuffer status,
    final String file)
    throws FileNotFoundException,
      IOException
  {
    final InputStream f_stream =
      ExampleRenderer.class.getResourceAsStream(file);
    if (f_stream == null) {
      throw new FileNotFoundException("basic.f");
    }

    final List<String> fragment_source = ShaderUtilities.readLines(f_stream);
    f_stream.close();

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
    final IntBuffer status,
    final String file)
    throws FileNotFoundException,
      IOException
  {
    final InputStream v_stream =
      ExampleRenderer.class.getResourceAsStream(file);
    if (v_stream == null) {
      throw new FileNotFoundException("basic.v");
    }

    final List<String> vertex_source = ShaderUtilities.readLines(v_stream);
    v_stream.close();

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

  private static Texture loadTexture()
    throws GLException,
      IOException
  {
    InputStream f_stream = null;
    try {
      f_stream = ExampleRenderer.class.getResourceAsStream("ground.jpg");
      return NullCheck.notNull(TextureIO.newTexture(f_stream, false, ".jpg"));
    } finally {
      if (f_stream != null) {
        f_stream.close();
      }
    }
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

  private static int makeLyingQuadMesh(
    final GL3 g)
  {
    final FloatBuffer data =
      Buffers.newDirectFloatBuffer(ExampleRenderer.VERTICES_TOTAL_COMPONENTS);

    int base = 0;
    data.put(base + 0, -0.5f);
    data.put(base + 1, 0.0f);
    data.put(base + 2, 0.5f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    data.put(base + 6, 0.0f);
    data.put(base + 7, 1.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
    data.put(base + 0, -0.5f);
    data.put(base + 1, 0.0f);
    data.put(base + 2, -0.5f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    data.put(base + 6, 0.0f);
    data.put(base + 7, 0.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
    data.put(base + 0, 0.5f);
    data.put(base + 1, 0.0f);
    data.put(base + 2, -0.5f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    data.put(base + 6, 1.0f);
    data.put(base + 7, 0.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
    data.put(base + 0, 0.5f);
    data.put(base + 1, 0.0f);
    data.put(base + 2, 0.5f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    data.put(base + 6, 1.0f);
    data.put(base + 7, 1.0f);

    final IntBuffer buffers = Buffers.newDirectIntBuffer(1);
    g.glGenBuffers(1, buffers);
    final int id = buffers.get(0);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
    g.glBufferData(
      GL.GL_ARRAY_BUFFER,
      ExampleRenderer.VERTICES_TOTAL_SIZE_BYTES,
      data,
      GL.GL_STATIC_DRAW);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    return id;
  }

  private static int makeProgram(
    final GL3 g,
    final String vertex_file,
    final String fragment_file)
    throws IOException
  {
    final IntBuffer status = Buffers.newDirectIntBuffer(1);
    assert status != null;
    final int vs =
      ExampleRenderer.compileVertexShader(g, status, vertex_file);
    final int fs =
      ExampleRenderer.compileFragmentShader(g, status, fragment_file);
    final int p = ExampleRenderer.createProgram(g, vs, fs, status);
    return p;
  }

  private static int makeUprightQuadMesh(
    final GL3 g)
  {
    final FloatBuffer data =
      Buffers.newDirectFloatBuffer(ExampleRenderer.VERTICES_TOTAL_COMPONENTS);

    int base = 0;
    data.put(base + 0, -0.5f);
    data.put(base + 1, 0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 0.0f);
    data.put(base + 4, 1.0f);
    data.put(base + 5, 0.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
    data.put(base + 0, -0.5f);
    data.put(base + 1, -0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 0.0f);
    data.put(base + 5, 0.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
    data.put(base + 0, 0.5f);
    data.put(base + 1, -0.5f);
    data.put(base + 2, 0.0f);

    data.put(base + 3, 1.0f);
    data.put(base + 4, 1.0f);
    data.put(base + 5, 0.0f);

    base += ExampleRenderer.VERTEX_COMPONENTS;
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
      ExampleRenderer.VERTICES_TOTAL_SIZE_BYTES,
      data,
      GL.GL_STATIC_DRAW);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    return id;
  }

  /**
   * A "repeatable random" function. Will always return the same pseudo-random
   * value for a given <code>x</code> value.
   */

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

  private final JCameraContext                              ctx;
  private @Nullable GL3                                     gl;
  private int                                               indices;
  private int                                               mesh_lying;
  private int                                               mesh_upright;
  private final MatrixM4x4F                                 model;
  private final MatrixM4x4F                                 modelview;
  private int                                               program;
  private final MatrixM4x4F                                 projection;
  private @Nullable Texture                                 texture;
  private final PMatrixM4x4F<WorldSpaceType, ViewSpaceType> view;
  private AtomicBoolean                                     want_warp;
  private @Nullable Window                                  window;
  private int                                               program_uv;

  public ExampleRenderer()
  {
    this.gl = null;
    this.indices = -1;
    this.mesh_upright = -1;
    this.mesh_lying = -1;
    this.program = -1;
    this.projection = new MatrixM4x4F();
    this.model = new MatrixM4x4F();
    this.view = new PMatrixM4x4F<WorldSpaceType, ViewSpaceType>();
    this.modelview = new MatrixM4x4F();
    this.want_warp = new AtomicBoolean(false);
    this.ctx = new JCameraContext();
    this.texture = null;
  }

  @Override public void draw(
    final JCameraReadableSnapshotType s,
    final OptionType<VectorReadable3FType> target)
  {
    final GL3 g = this.gl;

    if (g != null) {
      s.cameraMakeViewPMatrix(this.ctx, this.view);

      g.glClearDepth(1.0f);
      g.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
      g.glClear(GL.GL_COLOR_BUFFER_BIT
        | GL.GL_DEPTH_BUFFER_BIT
        | GL.GL_STENCIL_BUFFER_BIT);
      g.glEnable(GL.GL_DEPTH_TEST);
      g.glDepthFunc(GL.GL_LEQUAL);
      g.glDisable(GL.GL_CULL_FACE);

      this.drawGround(g);
      this.drawSceneQuads(g);

      if (target.isSome()) {
        final Some<VectorReadable3FType> some =
          (Some<VectorReadable3FType>) target;
        final VectorReadable3FType pos = some.get();
        this.drawSceneTarget(g, pos);
      }
    }

    if (this.want_warp.get()) {
      final Window w = this.window;
      if (w != null) {
        w.warpPointer(w.getWidth() / 2, w.getHeight() / 2);
        this.want_warp.set(false);
      }
    }
  }

  private void drawQuad(
    final GL3 g,
    final int uid,
    final float x,
    final float y,
    final float z)
  {
    MatrixM4x4F.setIdentity(this.model);
    MatrixM4x4F.makeTranslation3FInto(new VectorI3F(x, y, -z), this.model);

    MatrixM4x4F.setIdentity(this.modelview);
    MatrixM4x4F.multiply(this.view, this.model, this.modelview);

    g
      .glUniformMatrix4fv(
        uid,
        1,
        false,
        this.modelview.getDirectFloatBuffer());

    g.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0L);
  }

  private void drawSceneQuads(
    final GL3 g)
  {
    g.glUseProgram(this.program);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, this.mesh_upright);

    final int position_attrib;
    final int color_attrib;

    {
      position_attrib = g.glGetAttribLocation(this.program, "v_position");
      assert position_attrib != -1;

      g.glEnableVertexAttribArray(position_attrib);
      g.glVertexAttribPointer(
        position_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        ExampleRenderer.VERTEX_POSITION_OFFSET);
    }

    {
      color_attrib = g.glGetAttribLocation(this.program, "v_color");
      assert color_attrib != -1;

      g.glEnableVertexAttribArray(color_attrib);
      g.glVertexAttribPointer(
        color_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        ExampleRenderer.VERTEX_COLOR_OFFSET);
    }

    {
      final int uid = g.glGetUniformLocation(this.program, "m_projection");
      assert uid != -1;
      g.glUniformMatrix4fv(
        uid,
        1,
        false,
        this.projection.getDirectFloatBuffer());
    }

    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, this.indices);

    {
      final int uid = g.glGetUniformLocation(this.program, "m_modelview");
      assert uid != -1;

      for (int index = 0; index < 100; ++index) {
        final float x = 10.0f - (ExampleRenderer.noise(index * 10) * 20.0f);
        final float y = 0.0f;
        final float z = 10.0f - (ExampleRenderer.noise(index * 100) * 20.0f);
        this.drawQuad(g, uid, x, y, z);
      }

      this.drawQuad(g, uid, 0.0f, 0.0f, 0.0f);
    }

    g.glDisableVertexAttribArray(position_attrib);
    g.glDisableVertexAttribArray(color_attrib);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    g.glUseProgram(0);
  }

  private void drawGround(
    final GL3 g)
  {
    g.glUseProgram(this.program_uv);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, this.mesh_lying);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, this.indices);

    final int position_attrib;
    final int uv_attrib;

    {
      position_attrib = g.glGetAttribLocation(this.program_uv, "v_position");
      assert position_attrib != -1;

      g.glEnableVertexAttribArray(position_attrib);
      g.glVertexAttribPointer(
        position_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        ExampleRenderer.VERTEX_POSITION_OFFSET);
    }

    {
      uv_attrib = g.glGetAttribLocation(this.program_uv, "v_uv");
      assert uv_attrib != -1;

      g.glEnableVertexAttribArray(uv_attrib);
      g.glVertexAttribPointer(
        uv_attrib,
        2,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        ExampleRenderer.VERTEX_UV_OFFSET);
    }

    {
      final int uid = g.glGetUniformLocation(this.program_uv, "m_projection");
      assert uid != -1;
      g.glUniformMatrix4fv(
        uid,
        1,
        false,
        this.projection.getDirectFloatBuffer());
    }

    {
      final int uid = g.glGetUniformLocation(this.program_uv, "f_texture");
      assert uid != -1;

      g.glActiveTexture(GL.GL_TEXTURE0);
      assert this.texture != null;
      g.glBindTexture(GL.GL_TEXTURE_2D, this.texture.getTextureObject());
      g.glUniform1i(uid, 0);
    }

    {
      final int uid = g.glGetUniformLocation(this.program_uv, "m_modelview");
      assert uid != -1;

      MatrixM4x4F.setIdentity(this.model);
      MatrixM4x4F.makeTranslation3FInto(
        new VectorI3F(0.0f, -0.5f, 0.0f),
        this.model);
      MatrixM4x4F.set(this.model, 0, 0, 128.0f);
      MatrixM4x4F.set(this.model, 2, 2, 128.0f);

      MatrixM4x4F.setIdentity(this.modelview);
      MatrixM4x4F.multiply(this.view, this.model, this.modelview);

      g.glUniformMatrix4fv(
        uid,
        1,
        false,
        this.modelview.getDirectFloatBuffer());

      g.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0L);
    }

    g.glDisableVertexAttribArray(uv_attrib);
    g.glDisableVertexAttribArray(position_attrib);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    g.glUseProgram(0);
    g.glBindTexture(GL.GL_TEXTURE_2D, 0);
  }

  private void drawSceneTarget(
    final GL3 g,
    final VectorReadable3FType pos)
  {
    g.glUseProgram(this.program);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, this.mesh_lying);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, this.indices);

    final int position_attrib;
    final int color_attrib;

    {
      position_attrib = g.glGetAttribLocation(this.program, "v_position");
      assert position_attrib != -1;

      g.glEnableVertexAttribArray(position_attrib);
      g.glVertexAttribPointer(
        position_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        0);
    }

    {
      color_attrib = g.glGetAttribLocation(this.program, "v_color");
      assert color_attrib != -1;

      g.glEnableVertexAttribArray(color_attrib);
      g.glVertexAttribPointer(
        color_attrib,
        3,
        GL.GL_FLOAT,
        false,
        ExampleRenderer.VERTEX_SIZE,
        ExampleRenderer.VERTEX_COLOR_OFFSET);
    }

    {
      final int uid = g.glGetUniformLocation(this.program, "m_projection");
      assert uid != -1;
      g.glUniformMatrix4fv(
        uid,
        1,
        false,
        this.projection.getDirectFloatBuffer());
    }

    {
      final int uid = g.glGetUniformLocation(this.program, "m_modelview");
      assert uid != -1;

      this.drawQuad(g, uid, pos.getXF(), pos.getYF(), -pos.getZF());
    }

    g.glDisableVertexAttribArray(color_attrib);
    g.glDisableVertexAttribArray(position_attrib);
    g.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    g.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    g.glUseProgram(0);
  }

  @Override public void init(
    final GLWindow in_window,
    final GL3 in_gl)
    throws IOException
  {
    final GL3 g = NullCheck.notNull(in_gl, "GL");
    this.gl = g;
    this.window = NullCheck.notNull(in_window, "Drawable");
    this.program = ExampleRenderer.makeProgram(g, "basic.v", "basic.f");
    this.program_uv = ExampleRenderer.makeProgram(g, "basic.v", "basic_uv.f");
    this.mesh_upright = ExampleRenderer.makeUprightQuadMesh(g);
    this.mesh_lying = ExampleRenderer.makeLyingQuadMesh(g);
    this.indices = ExampleRenderer.makeIndices(g);
    this.want_warp = new AtomicBoolean(false);
    this.texture = ExampleRenderer.loadTexture();
  }

  @Override public void reshape(
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

  @Override public void sendWantWarpPointer()
  {
    this.want_warp.set(true);
  }
}
