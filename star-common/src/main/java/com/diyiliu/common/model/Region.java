package com.diyiliu.common.model;

/**
 * Description: Region
 * Author: DIYILIU
 * Update: 2017-09-19 09:36
 */
public class Region implements Area {

    /** 顶点的个数 */
    private int pointCount;

    /** 顺时针，顶点的位置 */
    private Point[] points;

    private double max(double x1, double x2) {
        return ((x1 > x2) ? x1 : x2);
    }

    private double min(double x1, double x2) {
        return ((x1 < x2) ? x1 : x2);
    }

    public Region() {
    }

    public Region(Point[] points) {
        this.pointCount = points.length;
        this.points = points;
    }

    /**
     * 0:点在区域外;1:点在区域内;2:点在顶点;3:点在区域边上.
     *
     * @param p
     * @return
     */
    @Override
    public int isPointInArea(Point p) {
        double fX = p.getLng();
        double fY = p.getLat();
        int nCrossCount = 0;
        for (int i = 0; i < this.pointCount; ++i) {
            int nEndP;
            int nStartP = i;
            if (i + 1 == this.pointCount)
                nEndP = 0;
            else
                nEndP = i + 1;
            if ((fY > this.points[nStartP].getLat()) && (fY > this.points[nEndP].getLat()))
                continue;
            if ((fY < this.points[nStartP].getLat()) && (fY < this.points[nEndP].getLat()))
                continue;
            if (((Math.abs(fY - this.points[nStartP].getLat()) <= 0.00001) && (Math.abs(fX - this.points[nStartP].getLng()) <= 0.00001))
                    || ((Math.abs(fY - this.points[nEndP].getLat()) <= 0.00001) && (Math.abs(fX - this.points[nEndP].getLng()) <= 0.00001)))
                return 2;
            if ((Math.abs(fY - this.points[nStartP].getLat()) <= 0.00001)
                    && (Math.abs(fY - this.points[nEndP].getLat()) <= 0.0000)) {
                if (fX > max(this.points[nStartP].getLng(), this.points[nEndP].getLng()))
                    continue;
                if ((fX < max(this.points[nStartP].getLng(), this.points[nEndP].getLng()))
                        && (fX > min(this.points[nStartP].getLng(), this.points[nEndP].getLng()))) {
                    return 3;
                }

                if (i - 1 < 0) {
                    nStartP = this.pointCount - 1;
                } else
                    nStartP = i - 1;
                if (i + 2 >= this.pointCount)
                    nEndP = i + 2 - this.pointCount;
                else
                    nEndP = i + 2;
                ++i;
                if ((fY < max(this.points[nStartP].getLat(), this.points[nEndP].getLat()))
                        || (fY > min(this.points[nStartP].getLat(), this.points[nEndP].getLat()))) {
                    ++nCrossCount;
                }

            } else if (Math.abs(fY - this.points[nStartP].getLat()) <= 0.0000) {
                if (fX < this.points[nStartP].getLng())
                    ++nCrossCount;

            } else if (Math.abs(fY - this.points[nEndP].getLat()) <= 0.0000) {
                nStartP = i;
                if (i + 2 >= this.pointCount)
                    nEndP = i + 2 - this.pointCount;
                else
                    nEndP = i + 2;
                if ((fY > max(this.points[nStartP].getLat(), this.points[nEndP].getLat()))
                        || (fY < min(this.points[nStartP].getLat(), this.points[nEndP].getLat())))
                    ++nCrossCount;

            } else {
                double fX0 = this.points[nStartP].getLng() + (this.points[nEndP].getLng() - this.points[nStartP].getLng())
                        * (fY - this.points[nStartP].getLat()) / (this.points[nEndP].getLat() - this.points[nStartP].getLat());
                if (Math.abs(fX0 - fX) < 0.00001d)
                    return 3;
                if (fX0 > fX) {
                    ++nCrossCount;
                }
            }
        }
        return (nCrossCount % 2);
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
        this.pointCount = points.length;
    }
}
