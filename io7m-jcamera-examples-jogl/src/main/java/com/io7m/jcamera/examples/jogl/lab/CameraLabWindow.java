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

package com.io7m.jcamera.examples.jogl.lab;

import com.io7m.jcamera.examples.jogl.ExampleRenderer;
import com.io7m.jcamera.examples.jogl.ExampleRendererType;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import net.java.dev.designgridlayout.DesignGridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SuppressWarnings("synthetic-access")
final class CameraLabWindow extends
  JFrame
{
  private static final class Panel extends JPanel
  {
    private static final long serialVersionUID;

    static {
      serialVersionUID = -3681497290868816970L;
    }

    private @Nullable CameraSimulationType              current;
    private final     JButton                           grab_mouse;
    private final     JLabel                            grab_mouse_info;
    private final     JComboBox<String>                 selector;
    private final     Map<String, CameraSimulationType> simulations;
    private final     GLWindow                          window;

    Panel(
      final ExecutorService in_background_workers,
      final ExampleRendererType in_renderer,
      final JPanel in_canvas_panel,
      final GLWindow in_window)
    {
      this.window = NullCheck.notNull(in_window, "Window");

      this.simulations =
        CameraSimulations.newSimulations(
          in_background_workers,
          in_renderer,
          in_window);

      this.selector = CameraSimulations.newSelector(this.simulations);
      this.selector.addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(
          final @Nullable ActionEvent e)
        {
          final String name = (String) Panel.this.selector.getSelectedItem();
          assert name != null;
          Panel.this.selectSimulation(name);
        }
      });

      this.grab_mouse = new JButton("Grab mouse/keyboard");
      this.grab_mouse.addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(
          final @Nullable ActionEvent e)
        {
          final CameraSimulationType c = Panel.this.current;
          if (c != null) {
            c.cameraSetEnabled(true);
            in_window.requestFocus();
          }
        }
      });

      this.grab_mouse_info =
        new JLabel("Press 'm' to release mouse/keyboard");

      final DesignGridLayout dg = new DesignGridLayout(this);
      dg.row().grid(new JLabel("Type")).add(this.selector);

      for (final String name : this.simulations.keySet()) {
        final CameraSimulationType sim = this.simulations.get(name);
        sim.controlsAddToLayout(dg);
        sim.controlsHide();
      }

      dg.row().grid().add(this.grab_mouse);
      dg.row().grid().add(this.grab_mouse_info);

      /**
       * Draw a bright border around the GL canvas when keyboard and mouse are
       * grabbed.
       */

      this.window.addGLEventListener(new GLEventListener()
      {
        @Override
        public void display(
          final @Nullable GLAutoDrawable drawable)
        {
          final CameraSimulationType c = Panel.this.current;
          if ((c != null) && c.cameraIsEnabled()) {
            in_canvas_panel.setBorder(CameraLabWindow.BORDER_ACTIVE);
            c.updatePeriodic();
          } else {
            in_canvas_panel.setBorder(CameraLabWindow.BORDER_INACTIVE);
          }
        }

        @Override
        public void dispose(
          final @Nullable GLAutoDrawable drawable)
        {
          // Nothing
        }

        @Override
        public void init(
          final @Nullable GLAutoDrawable drawable)
        {
          // Nothing
        }

        @Override
        public void reshape(
          final @Nullable GLAutoDrawable drawable,
          final int x,
          final int y,
          final int width,
          final int height)
        {
          // Nothing
        }
      });

      this.selector.setSelectedIndex(0);
    }

    private void selectSimulation(
      final String name)
    {
      final CameraSimulationType old_current = this.current;
      final CameraSimulationType new_current =
        NullCheck.notNull(this.simulations.get(name));

      if (old_current != null) {
        this.window.removeKeyListener(old_current.getKeyListener());
        this.window.removeMouseListener(old_current.getMouseListener());
        this.window.removeGLEventListener(old_current.getGLEventListener());
        old_current.controlsHide();
      }

      this.window.addKeyListener(new_current.getKeyListener());
      this.window.addGLEventListener(new_current.getGLEventListener());
      this.window.addMouseListener(new_current.getMouseListener());
      this.current = new_current;

      new_current.controlsShow();
    }
  }

  private static final Border BORDER_ACTIVE;
  private static final Border BORDER_INACTIVE;
  private static final long   serialVersionUID;

  static {
    BORDER_INACTIVE =
      NullCheck.notNull(BorderFactory.createLineBorder(Color.GRAY, 3));
    BORDER_ACTIVE =
      NullCheck.notNull(BorderFactory.createLineBorder(Color.RED, 3));
  }

  static {
    serialVersionUID = 3690294437796540203L;
  }

  private static JMenuBar menu(
    final JFrame window)
  {
    final JMenuItem quit = new JMenuItem("Quit");
    quit.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final WindowEvent ev =
          new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
        window.dispatchEvent(ev);
      }
    });

    final JMenu file = new JMenu("File");
    file.add(quit);

    final JMenuBar bar = new JMenuBar();
    bar.add(file);
    return bar;
  }

  CameraLabWindow()
  {
    final Container content = this.getContentPane();

    this.setJMenuBar(CameraLabWindow.menu(this));

    final ExecutorService background_workers =
      NullCheck.notNull(Executors.newFixedThreadPool(1));

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    final GLCapabilities caps = new GLCapabilities(profile);
    final GLWindow window = GLWindow.create(caps);
    window.setSize(512, 512);

    final NewtCanvasAWT canvas = new NewtCanvasAWT(window);
    canvas.requestFocus();
    final JPanel canvas_panel = new JPanel();
    canvas_panel.add(canvas);

    final ExampleRenderer renderer = new ExampleRenderer();
    final Panel controls =
      new Panel(background_workers, renderer, canvas_panel, window);

    final FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 8, 8);
    layout.setAlignOnBaseline(true);
    content.setLayout(layout);
    content.add(canvas_panel);
    content.add(controls);

    final Animator anim = new Animator();
    anim.add(window);
    anim.start();
  }
}
