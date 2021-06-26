package dev;

public interface Connect {
	/**
	 * Phương thức kiểm tra tính kết nối của 2 Sensor được truyền vào<i>10.5.21</i>
	 * @param s1
	 * @param s2
	 * @return - true: kêt nối, false: không kết nối
	 */
	boolean checkConnect(Sensor s1, Sensor s2);
	public int checkConnectStrip(int xStart, int xEnd, double rCover, double rCon, int width, int aver);
}
