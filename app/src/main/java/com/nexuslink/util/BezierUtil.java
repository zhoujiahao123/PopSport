package com.nexuslink.util;

public class BezierUtil {
    /**
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 终止点
     * @return t对应的点
     */
    public static Point CalculateBezierPointForQuadratic(float t, Point p0, Point p1, Point p2) {
        Point point = new Point();
        float temp = 1 - t;
        point.setX(temp * temp * p0.getX()+ 2 * t * temp * p1.getX() + t * t * p2.getX());
        point.setY(temp * temp * p0.getY() + 2 * t * temp * p1.getY() + t * t * p2.getY());
        return point;
    }

    /**
     * B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点1
     * @param p2 控制点2
     * @param p3 终止点
     * @return t对应的点
     */
    public static Point CalculateBezierPointForCubic(float t, Point p0, Point p1, Point p2, Point p3) {
        Point point = new Point();
        float temp = 1 - t;
        point.setX(p0.getX() * temp * temp * temp + 3 * p1.getX() * t * temp * temp + 3 * p2.getX() * t * t * temp + p3.getX() * t * t * t); 
        point.setY(p0.getY() * temp * temp * temp + 3 * p1.getY() * t * temp * temp + 3 * p2.getY() * t * t * temp + p3.getY() * t * t * t); 
        return point;
    }
}