package cn.heapstack.sudoku.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;
import javax.swing.border.AbstractBorder;

public class ImageBorder extends AbstractBorder {

	private static final long serialVersionUID = -6659286044262494076L;
	
	Image top_center, top_left, top_right;
	Image left_center, right_center;
	Image bottom_center, bottom_left, bottom_right;
	Insets insets;

	public ImageBorder(Image top_left, Image top_center, Image top_right,
			Image left_center, Image right_center, Image bottom_left,
			Image bottom_center, Image bottom_right) {

		this.top_left = top_left;
		this.top_center = top_center;
		this.top_right = top_right;
		this.left_center = left_center;
		this.right_center = right_center;
		this.bottom_left = bottom_left;
		this.bottom_center = bottom_center;
		this.bottom_right = bottom_right;
	}

	public void setInsets(Insets insets) {
		this.insets = insets;
	}

	public Insets getBorderInsets(Component c) {
		if (insets != null) {
			return insets;
		} else {
			return new Insets(top_center.getHeight(null), left_center
					.getWidth(null), bottom_center.getHeight(null),
					right_center.getWidth(null));
		}
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);

		Graphics2D g2 = (Graphics2D) g;

		int tlw = top_left.getWidth(null);
		int tlh = top_left.getHeight(null);
		//int tcw = top_center.getWidth(null);
		int tch = top_center.getHeight(null);
		int trw = top_right.getWidth(null);
		int trh = top_right.getHeight(null);

		int lcw = left_center.getWidth(null);
		//int lch = left_center.getHeight(null);
		int rcw = right_center.getWidth(null);
		//int rch = right_center.getHeight(null);

		int blw = bottom_left.getWidth(null);
		int blh = bottom_left.getHeight(null);
		//int bcw = bottom_center.getWidth(null);
		int bch = bottom_center.getHeight(null);
		int brw = bottom_right.getWidth(null);
		int brh = bottom_right.getHeight(null);
		
		fillTexture(g2, top_left, x, y, tlw, tlh);
		fillTexture(g2, top_center, x + tlw, y, width - tlw - trw, tch);
		fillTexture(g2, top_right, x + width - trw, y, trw, trh);

		fillTexture(g2, left_center, x, y + tlh, lcw, height - tlh - blh);
		fillTexture(g2, right_center, x + width - rcw, y + trh, rcw, height
				- trh - brh);

		fillTexture(g2, bottom_left, x, y + height - blh, blw, blh);
		fillTexture(g2, bottom_center, x + blw, y + height - bch, width - blw
				- brw, bch);
		fillTexture(g2, bottom_right, x + width - brw, y + height - brh, brw,
				brh);		
	}

	public void fillTexture(Graphics2D g2, Image img, int x, int y, int w, int h) {
		BufferedImage buff = createBufferedImage(img);
		Rectangle anchor = new Rectangle(x, y, img.getWidth(null), img
				.getHeight(null));
		TexturePaint paint = new TexturePaint(buff, anchor);
		g2.setPaint(paint);
		g2.fillRect(x, y, w, h);
		
	}

	public BufferedImage createBufferedImage(Image img) {
		BufferedImage buff = new BufferedImage(img.getWidth(null), img
				.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics gfx = buff.createGraphics();
		gfx.drawImage(img, 0, 0, null);
		gfx.dispose();
		return buff;
	}

	
	   // This method returns the color model of an image
    public static ColorModel getColorModel(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel();
        }
    
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
         try {
             pg.grabPixels();
         } catch (InterruptedException e) {
         }
        ColorModel cm = pg.getColorModel();
        
        return cm;
    }
    
    public static ImageBorder generateDefaultImageBorder()
    {
    	return new ImageBorder(
	            new ImageIcon(ImageBorder.class.getResource("img/upper_left.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/upper.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/upper_right.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/left_center.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/right_center.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/bottom_left.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/bottom_center.png")).getImage(),
	            new ImageIcon(ImageBorder.class.getResource("img/bottom_right.png")).getImage());
    }

}
