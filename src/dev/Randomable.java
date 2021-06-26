package dev;

public interface Randomable {
	/**
	 * Phương thức tạo 1 Senssor với tọa độ X Y bất kì <i>10.5.21</i>
	 * @param rCover - bán kính bao phủ
	 * @param rCon -  bán kính kết nốt
	 * @param length - chiều cao của dải Senssor cần đước khởi tạo
	 * @param width - chiều cao của dải Senssor cần đước khởi tạo
	 * @return - Một đối tượng senssor
	 */
	Sensor randomCoordinatesXY(int xStart, double rCover, double rCon,int length, int width);
}
