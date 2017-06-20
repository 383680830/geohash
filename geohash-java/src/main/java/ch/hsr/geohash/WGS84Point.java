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
 * 将地球表面的坐标封装为{@link WGS84Point}<br>
 * 坐标投影最终会使用这个类...
 */
public class WGS84Point implements Serializable {
	private static final long serialVersionUID = 7457963026513014856L;
	private final double longitude;
	private final double latitude;

	/**
	 * @param 纬度
	 * @param 经度
	 */
	public WGS84Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
			throw new IllegalArgumentException("The supplied coordinates " + this + " are out of range.");
		}
	}

	/**
	 * @param 其他坐标
	 */
	public WGS84Point(WGS84Point other) {
		this(other.latitude, other.longitude);
	}

	/**
	 * @return 纬度
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return 经度
	 */
	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return String.format("(" + latitude + "," + longitude + ")");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WGS84Point) {
			WGS84Point other = (WGS84Point) obj;
			return latitude == other.latitude && longitude == other.longitude;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 42;
		long latBits = Double.doubleToLongBits(latitude);
		long lonBits = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (latBits ^ (latBits >>> 32));
		result = 31 * result + (int) (lonBits ^ (lonBits >>> 32));
		return result;
	}
}
