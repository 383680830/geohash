/*
 * Copyright 2010, Silvio Heuberger @ IFS www.ifs.hsr.ch
 *
 * This code is release under the Apache License 2.0.
 * You should have received a copy of the license
 * in the LICENSE file. If you have not, see
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package ch.hsr.geohash.queries;

import java.io.Serializable;
import java.util.List;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

/**
 * 根据提供中心点坐标和圆半径确定geohash
 * 将圆近似为正方形
 */
public class GeoHashCircleQuery implements GeoHashQuery, Serializable {
	private static final long serialVersionUID = 1263295371663796291L;
	private double radius;
	private GeoHashBoundingBoxQuery query;
	private WGS84Point center;

	/**
	 * 根据中心点坐标和半径(米)创建{@link GeoHashCircleQuery}
	 */
	public GeoHashCircleQuery(WGS84Point center, double radius) {
		this.radius = radius;
		this.center = center;
		WGS84Point northEast = VincentyGeodesy.moveInDirection(VincentyGeodesy.moveInDirection(center, 0, radius), 90,
				radius);
		WGS84Point southWest = VincentyGeodesy.moveInDirection(VincentyGeodesy.moveInDirection(center, 180, radius),
				270, radius);
		BoundingBox bbox = new BoundingBox(northEast, southWest);
		query = new GeoHashBoundingBoxQuery(bbox);
	}

	@Override
	public boolean contains(GeoHash hash) {
		return query.contains(hash);
	}

	@Override
	public String getWktBox() {
		return query.getWktBox();
	}

	@Override
	public List<GeoHash> getSearchHashes() {
		return query.getSearchHashes();
	}

	@Override
	public String toString() {
		return "Cicle Query [center=" + center + ", radius=" + getRadiusString() + "]";
	}

	private String getRadiusString() {
		if (radius > 1000) {
			return radius / 1000 + "km";
		} else {
			return radius + "m";
		}
	}

	@Override
	public boolean contains(WGS84Point point) {
		return query.contains(point);
	}
}
