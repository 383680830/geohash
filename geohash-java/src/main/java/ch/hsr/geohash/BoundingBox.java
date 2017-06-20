/*
 * Copyright 2010, Silvio Heuberger @ IFS www.ifs.hsr.ch
 *
 * This code is release under the Apache License 2.0.
 * You should have received a copy of the license
 * in the LICENSE file. If you have not, see
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package ch.hsr.geohash;

import java.io.Serializable;

/**
 * 边界框
 */
public class BoundingBox implements Serializable {
	private static final long serialVersionUID = -7145192134410261076L;
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;

	/**
	 * @param 坐标1
	 * @param 坐标2
	 */
	public BoundingBox(WGS84Point p1, WGS84Point p2) {
		this(p1.getLatitude(), p2.getLatitude(), p1.getLongitude(), p2.getLongitude());
	}

	/**
	 * @param 纬度1
	 * @param 纬度2
	 * @param 经度1
	 * @param 经度2
	 */
	public BoundingBox(double y1, double y2, double x1, double x2) {
		minLon = Math.min(x1, x2);
		maxLon = Math.max(x1, x2);
		minLat = Math.min(y1, y2);
		maxLat = Math.max(y1, y2);
	}

	/**
	 * @param 边界框
	 */
	public BoundingBox(BoundingBox that) {
		this(that.minLat, that.maxLat, that.minLon, that.maxLon);
	}

	/**
	 * @return 左上角坐标
	 */
	public WGS84Point getUpperLeft() {
		return new WGS84Point(maxLat, minLon);
	}

	/**
	 * @return 右下角坐标
	 */
	public WGS84Point getLowerRight() {
		return new WGS84Point(minLat, maxLon);
	}

	/**
	 * @return 纬度差
	 */
	public double getLatitudeSize() {
		return maxLat - minLat;
	}

	/**
	 * @return 经度差
	 */
	public double getLongitudeSize() {
		return maxLon - minLon;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof BoundingBox) {
			BoundingBox that = (BoundingBox) obj;
			return minLat == that.minLat && minLon == that.minLon && maxLat == that.maxLat && maxLon == that.maxLon;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + hashCode(minLat);
		result = 37 * result + hashCode(maxLat);
		result = 37 * result + hashCode(minLon);
		result = 37 * result + hashCode(maxLon);
		return result;
	}

	/**
	 * @param 精度(bit)
	 * @return 指定精度的哈希值
	 */
	private static int hashCode(double x) {
		long f = Double.doubleToLongBits(x);
		return (int) (f ^ (f >>> 32));
	}

	/**
	 * @param 坐标
	 * @return 是否包含该坐标
	 */
	public boolean contains(WGS84Point point) {
		return (point.getLatitude() >= minLat) && (point.getLongitude() >= minLon) && (point.getLatitude() <= maxLat)
				&& (point.getLongitude() <= maxLon);
	}

	public boolean intersects(BoundingBox other) {
		return !(other.minLon > maxLon || other.maxLon < minLon || other.minLat > maxLat || other.maxLat < minLat);
	}

	@Override
	public String toString() {
		return getUpperLeft() + " -> " + getLowerRight();
	}

	/**
	 * @return 中心点坐标
	 */
	public WGS84Point getCenterPoint() {
		double centerLatitude = (minLat + maxLat) / 2;
		double centerLongitude = (minLon + maxLon) / 2;
		return new WGS84Point(centerLatitude, centerLongitude);
	}

	/**
	 * @param 边界框
	 * @description 扩容(扩容后边界框仍为矩形)
	 */
	public void expandToInclude(BoundingBox other) {
		if (other.minLon < minLon) {
			minLon = other.minLon;
		}
		if (other.maxLon > maxLon) {
			maxLon = other.maxLon;
		}
		if (other.minLat < minLat) {
			minLat = other.minLat;
		}
		if (other.maxLat > maxLat) {
			maxLat = other.maxLat;
		}
	}

	/**
	 * @return 最小经度
	 */
	public double getMinLon() {
		return minLon;
	}

	/**
	 * @return 最小纬度
	 */
	public double getMinLat() {
		return minLat;
	}

	/**
	 * @return 最大经度
	 */
	public double getMaxLat() {
		return maxLat;
	}

	/**
	 * @return 最大纬度
	 */
	public double getMaxLon() {
		return maxLon;
	}
}
