package ch.hsr.geohash.queries;

import java.util.List;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;

public interface GeoHashQuery {

	/**
	 * @param hash
	 *            GeoHash
	 * @return 是否包含
	 */
	public boolean contains(GeoHash hash);

	/**
	 * @param point
	 *            坐标
	 * @return 是否包含
	 */
	public boolean contains(WGS84Point point);

	/**
	 * @return 搜索时提供的GeoHash
	 */
	public List<GeoHash> getSearchHashes();

	public String getWktBox();

}