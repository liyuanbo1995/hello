package com.xinba.active.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageCompressUtil {

	/**
	 * 保存文件到服务器临时路径(用于文件上传)
	 * @param fileName
	 * @param is
	 * @return 文件全路径
	 */
	public static String writeFile(String fileName, InputStream is) {
		if (fileName == null || fileName.trim().length() == 0) {
			return null;
		}
		try {
			/** 首先保存到临时文件 */
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] readBytes = new byte[512];// 缓冲大小
			int readed = 0;
			while ((readed = is.read(readBytes)) > 0) {
				fos.write(readBytes, 0, readed);
			}
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 等比例压缩算法： 
	 * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
	 * @param comBase 压缩基数
	 * @param scale 压缩限制(宽/高)比例  一般用1：
	 * 当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
	 * @throws Exception
	 * @author tsw
	 * @createTime 2017-01-16
	 * @lastModifyTime 2017-01-16
	 */
	public static InputStream saveMinPhoto(InputStream inInputStream, double comBase,
			double scale) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream is = null;
		try {
		Image src = ImageIO.read(inInputStream);
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		int deskHeight = 0;// 缩略图高
		int deskWidth = 0;// 缩略图宽
		double srcScale = (double) srcHeight / srcWidth;
		/**缩略图宽高算法*/
		if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > comBase) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;
		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
		/*FileOutputStream deskImage = new FileOutputStream(deskURL); //输出到文件流
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		encoder.encode(tag); //近JPEG编码
		deskImage.close();*/
		ImageIO.write(tag, "jpg", os);
		is = new ByteArrayInputStream(os.toByteArray());
		}catch (Exception e) {
			//抛出异常
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return is;
	}


	public static InputStream saveMinPhoto500(InputStream inInputStream,double comBase,double scale) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			BufferedImage src = ImageIO.read(inInputStream);
			int type = src.getType();
			//判断文件大小是否大于size,循环压缩，直到大小小于给定的值
				int srcHeight = src.getHeight(null);
				int srcWidth = src.getWidth(null);
				int deskHeight = 0;// 缩略图高
				int deskWidth = 0;// 缩略图宽
				double srcScale = (double) srcHeight / srcWidth;
				/**缩略图宽高算法*/
				if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
					if (srcScale >= scale || 1 / srcScale > scale) {
						if (srcScale >= scale) {
							deskHeight = (int) comBase;
							deskWidth = srcWidth * deskHeight / srcHeight;
						} else {
							deskWidth = (int) comBase;
							deskHeight = srcHeight * deskWidth / srcWidth;
						}
					} else {
						if ((double) srcHeight > comBase) {
							deskHeight = (int) comBase;
							deskWidth = srcWidth * deskHeight / srcHeight;
						} else {
							deskWidth = (int) comBase;
							deskHeight = srcHeight * deskWidth / srcWidth;
						}
					}
				} else {
					deskHeight = srcHeight;
					deskWidth = srcWidth;
				}
				BufferedImage tag = new BufferedImage(deskWidth, deskHeight, type);
				tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
				outputStream.reset();
				ImageIO.write(src, "JPEG", outputStream);
			    is = new ByteArrayInputStream(outputStream.toByteArray());
		}catch (Exception e) {
			//抛出异常
			throw e;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	return is;
	}
}
