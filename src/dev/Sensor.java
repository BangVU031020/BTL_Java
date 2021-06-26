package dev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sensor implements Randomable,Comparable<Sensor> {
	private double radiusCoverage;
	private double radiusConnection;
	private int coordinatesX;
	private int coordinatesY;

	public Sensor() {
		this(0, 0, 0, 0);
	}

	public Sensor(double radiusCoverage, double radiusConnection, int coordinatesX, int coordinatesY) {
		this.radiusCoverage = radiusCoverage;
		this.radiusConnection = radiusConnection;
		this.coordinatesX = coordinatesX;
		this.coordinatesY = coordinatesY;
	}

	public double getRadiusCoverage() {
		return radiusCoverage;
	}

	public double getRadiusConnection() {
		return radiusConnection;
	}

	public int getCoordinatesX() {
		return coordinatesX;
	}

	public int getCoordinatesY() {
		return coordinatesY;
	}

	public Sensor setRadiusCoverage(double radiusCoverage) {
		this.radiusCoverage = radiusCoverage;
		return this;
	}

	public Sensor setRadiusConnection(double radiusConnection) {
		this.radiusConnection = radiusConnection;
		return this;
	}

	public Sensor setCoordinatesX(int coordinatesX) {
		this.coordinatesX = coordinatesX;
		return this;
	}

	public Sensor setCoordinatesY(int coordinatesY) {
		this.coordinatesY = coordinatesY;
		return this;
	}

	@Override
	public String toString() {
		return  "(x,y) = (" + coordinatesX + "," + coordinatesY + ")" ;
	}
	@Override
	public Sensor randomCoordinatesXY(int xStart, double rCover, double rCon, int length, int width) {
		Sensor random = new Sensor();
		random.setRadiusCoverage(rCover);
		random.setRadiusConnection(rCon);
		random.setCoordinatesX((int) (xStart + length * Math.random()));
		random.setCoordinatesY((int) (width * Math.random()));
		return random;
	}

	@Override
	public int compareTo(Sensor o) {
		if(coordinatesX == o.coordinatesX)
			return 0;
		else if(coordinatesX > o.coordinatesX)
			return 1;
		else
			return -1;
	}

	/**
	 * Phương thức tạo 1 mảng Senssor với tọa độ X Y bất kì đã được sắp xếp theo
	 * tăng dần X<i>10.5.21</i>
	 * 
	 * @param rCover - bán kính bao phủ
	 * @param rCon   - bán kính kết nốt
	 * @param length - chiều dài của dải Senssor cần đước khởi tạo
	 * @param width  - chiều rộng của dải Senssor cần đước khởi tạo
	 * @param n      - số đối tượng của mảng
	 * @return - Một mảng đối tượng sensor
	 */
	public static ArrayList<Sensor> radomCoordinatesXY(int xStart, double rCover, double rCon, int length, int width, int n) {
		ArrayList<Sensor> random = new ArrayList<Sensor>();
		Sensor x = new Sensor();
		for (int i = 0; i < n; i++) {
			random.add(x.randomCoordinatesXY(xStart, rCover, rCon, length, width));
		}
		Collections.sort(random);
		return random;
	}


}
