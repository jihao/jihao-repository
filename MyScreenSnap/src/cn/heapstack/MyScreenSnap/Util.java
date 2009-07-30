package cn.heapstack.MyScreenSnap;

import java.io.File;

public class Util {

}

//下面的代码来自千里冰封的程序

//保存BMP格式的过滤器
class BMPfilter extends javax.swing.filechooser.FileFilter{
    public BMPfilter(){
        
    }
    public boolean accept(File file){
        if(file.toString().toLowerCase().endsWith(".bmp")||
                file.isDirectory()){
            return true;
        } else
            return false;
    }
    public String getDescription(){
        return "*.BMP(BMP图像)";
    }
}
//保存JPG格式的过滤器
class JPGfilter extends javax.swing.filechooser.FileFilter{
    public JPGfilter(){
        
    }
    public boolean accept(File file){
        if(file.toString().toLowerCase().endsWith(".jpg")||
                file.isDirectory()){
            return true;
        } else
            return false;
    }
    public String getDescription(){
        return "*.JPG(JPG图像)";
    }
}
//保存GIF格式的过滤器
class GIFfilter extends javax.swing.filechooser.FileFilter{
    public GIFfilter(){
        
    }
    public boolean accept(File file){
        if(file.toString().toLowerCase().endsWith(".gif")||
                file.isDirectory()){
            return true;
        } else
            return false;
    }
    public String getDescription(){
        return "*.GIF(GIF图像)";
    }
}

//保存PNG格式的过滤器
class PNGfilter extends javax.swing.filechooser.FileFilter{
    public boolean accept(File file){
        if(file.toString().toLowerCase().endsWith(".png")||
                file.isDirectory()){
            return true;
        } else
            return false;
    }
    public String getDescription(){
        return "*.PNG(PNG图像)";
    }
}