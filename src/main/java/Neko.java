/*
 * @(#)Neko.java  2.0.1  2019-02-29
 *
 * Copyright (c) 2019 Jerry Reno
 * This is public domain software, under the terms of the UNLICENSE
 * http://unlicense.org 
 * 
 * This is an extended version of the earlier Java Neko 1.0:
 * Copyright (c) 2010 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 *
 * This source code is free to everyone.
 *
 * This is a desktop adaptation of the applet
 * JAVA NEKO V1.0 by Chris Parent, 1999.
 * http://mysite.ncnetwork.net/res8t1xo/class/neko.htm
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.WindowConstants;

/**
 * Neko the cat.
 * <p>
 * This program loads in 32 images of Neko, and tests them. (to show you that
 * they've been loaded). Neko will chase you mouse cursor around the desktop.
 * Once she's over it and the mouse doesn't move she'll prepare to take a nap.
 * If the mouse go's outside the desktop she will reach the border and try
 * to dig for it. She'll eventually give up, and fall asleep.
 *
 *
 * @author Werner Randelshofer (adaption for desktop)
 *		 Chris Parent (original code)
 * @version 1.0.1 2010-07-17 Fixes timers. Sets longer sleep times when the
 * cat sleeps.
 * <br>1.0 2010-07-16 Created.
 */
public class Neko {
	//
	// UI Components
	private JWindow invisibleWindow;
	private JLabel freeLabel;
	private NekoSettings settings;
	private NekoController controller;

	/** Creates new form Neko */
	public Neko() {
		settings=new NekoSettings();
		initComponents();
		invisibleWindow.setLocation(100,100);
		controller=new NekoController(settings,invisibleWindow,freeLabel);
		setWindowMode(false);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	private void initComponents() {
		invisibleWindow=new JWindow();
		invisibleWindow.getRootPane().putClientProperty("Window.shadow", false);
		invisibleWindow.setBackground(new Color(200,200,200,0)); // transparent, light grey of not supported
		invisibleWindow.setAlwaysOnTop(true);

		freeLabel = new JLabel();

		PopupMenu popup = new PopupMenu();
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popup.add(exitItem);

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage(Neko.class.getResource("/images/systemtray.png"));
		TrayIcon trayIcon = new TrayIcon(image, "oneko", popup);
		trayIcon.setImageAutoSize(true);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}

		invisibleWindow.getContentPane().add(freeLabel, BorderLayout.CENTER);
		invisibleWindow.pack();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Neko();
			}
		});
	}

	public void setWindowMode(boolean windowed)
	{
		controller.setWindowMode(windowed);
		settings.load();
		invisibleWindow.setVisible(true);
		controller.moveCatInBox();
	}

}

