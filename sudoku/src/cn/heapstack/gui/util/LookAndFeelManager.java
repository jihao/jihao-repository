package cn.heapstack.gui.util;

import java.util.HashMap;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeelManager {

	private static HashMap<String, String> lafMap = new HashMap<String, String>();
	static
	{
		lafMap.put("Java", UIManager.getCrossPlatformLookAndFeelClassName());
		lafMap.put("System", UIManager.getSystemLookAndFeelClassName());
		lafMap.put("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		lafMap.put("Monif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		lafMap.put("Metal", "javax.swing.plaf.metal.MetalLookAndFeel");
		lafMap.put("Napkin", "net.sourceforge.napkinlaf.NapkinLookAndFeel");	
	}
	
	public static HashMap<String, String> getLookAndFeelMap()
	{
		return lafMap;
	}
	
	public static void setCrossPlatformLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setSystemLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setWindowsLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setMetalLookAndFeel()
	{
		// Set cross-platform Java L&F (also called "Metal")
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setMotifLookAndFeel()
	{
		// Set Motif L&F on any platform
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setNapkinLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
	}
	
    /**
     * A utility function that layers on top of the LookAndFeel's
     * isSupportedLookAndFeel() method. Returns true if the LookAndFeel is
     * supported. Returns false if the LookAndFeel is not supported and/or if
     * there is any kind of error checking if the LookAndFeel is supported.
     * <p/>
     * The L&F menu will use this method to determine whether the various L&F
     * options should be active or inactive.
     */
    public static boolean isAvailableLookAndFeel(String laf) {
        try {
            Class<?> lnfClass = Class.forName(laf);
            LookAndFeel newLAF = (LookAndFeel) (lnfClass.newInstance());
            return newLAF.isSupportedLookAndFeel();
        } catch (Exception e) { // If ANYTHING weird happens, return false
            return false;
        }
    }
}
