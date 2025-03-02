package com.heny.demo.tree;

import lombok.SneakyThrows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 */
public class HeatmapGenerator {

    @SneakyThrows
    public static void generateHeatmap(List<DataPoint> dataPoints, String outputPath)  {
        // 计算数据范围
        double minX = dataPoints.stream().mapToDouble(DataPoint::getX).min().orElse(0);
        double maxX = dataPoints.stream().mapToDouble(DataPoint::getX).max().orElse(0);
        double minY = dataPoints.stream().mapToDouble(DataPoint::getY).min().orElse(0);
        double maxY = dataPoints.stream().mapToDouble(DataPoint::getY).max().orElse(0);
        double minTemp = dataPoints.stream().mapToDouble(DataPoint::getTemperature).min().orElse(0);
        double maxTemp = dataPoints.stream().mapToDouble(DataPoint::getTemperature).max().orElse(0);

        // 图像尺寸
        int imageWidth = 800;
        int imageHeight = 600;

        // 创建透明背景的图像
        BufferedImage heatmap = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = heatmap.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.clearRect(0, 0, imageWidth, imageHeight);

        // 绘制温度点
        for (DataPoint dp : dataPoints) {
            // 转换坐标到图像位置
            int px = (int) map(dp.getX(), minX, maxX, 0, imageWidth - 1);
            int py = (int) map(dp.getY(), minY, maxY, 0, imageHeight - 1);

            // 归一化温度并计算透明度
            double normalizedTemp = normalize(dp.getTemperature(), minTemp, maxTemp);
            int alpha = (int) (normalizedTemp * 255);
            int radius = 15; // 影响范围半径

            // 绘制半透明白色圆形
            g2d.setColor(new Color(255, 255, 255, alpha));
            g2d.fillOval(px - radius, py - radius, 2 * radius, 2 * radius);
        }
        g2d.dispose();

        // 应用高斯模糊
        BufferedImage blurredImage = applyGaussianBlur(heatmap, 5, 2.0f);

        // 将模糊后的灰度转换为热力图颜色
        applyColorGradient(blurredImage);

        // 保存图像
        ImageIO.write(blurredImage, "PNG", new File(outputPath));
    }

    // 数值范围映射
    private static double map(double value, double start1, double end1, double start2, double end2) {
        return start2 + (value - start1) * (end2 - start2) / (end1 - start1);
    }

    // 归一化温度
    private static double normalize(double temp, double min, double max) {
        return (max == min) ? 0.5 : (temp - min) / (max - min);
    }

    // 应用高斯模糊
    private static BufferedImage applyGaussianBlur(BufferedImage image, int radius, float sigma) {
        int size = 2 * radius + 1;
        float[] kernel = createGaussianKernel(radius, sigma);
        ConvolveOp op = new ConvolveOp(new Kernel(size, size, kernel), ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    // 创建高斯核
    private static float[] createGaussianKernel(int radius, float sigma) {
        int size = 2 * radius + 1;
        float[] kernel = new float[size * size];
        float sum = 0;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                float value = (float) Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
                kernel[(y + radius) * size + (x + radius)] = value;
                sum += value;
            }
        }

        // 归一化
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] /= sum;
        }
        return kernel;
    }

    // 应用颜色渐变
    private static void applyColorGradient(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                int alpha = (argb >> 24) & 0xFF;
                int gray = (argb >> 16) & 0xFF; // 提取亮度值

                // 根据亮度映射颜色（蓝→红）
                float hue = 0.7f * (1 - gray / 255f);
                Color color = Color.getHSBColor(hue, 1f, 1f);

                // 合并颜色和原始透明度
                int rgb = (alpha << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
                image.setRGB(x, y, rgb);
            }
        }
    }

    // 数据点类
    public static class DataPoint {
        private double x;
        private double y;
        private double temperature;

        public DataPoint(double x, double y, double temperature) {
            this.x = x;
            this.y = y;
            this.temperature = temperature;
        }

        // Getters
        public double getX() { return x; }
        public double getY() { return y; }
        public double getTemperature() { return temperature; }
    }
}