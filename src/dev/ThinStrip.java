package dev;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThinStrip implements Connect {
    private int length;
    private int width;

    public ThinStrip() {
        this(0, 0);
    }

    public ThinStrip(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public ThinStrip setLength(int length) {
        this.length = length;
        return this;
    }

    public ThinStrip setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public String toString() {
        return "ThinStrip [length=" + length + ", width=" + width + "]";
    }

    @Override
    public boolean checkConnect(Sensor s1, Sensor s2) {
        if (s2.getCoordinatesX() - s1.getCoordinatesX() > s1.getRadiusConnection()) {
            return false;
        } else {
            double d = (double) Math.sqrt(Math.pow((s2.getCoordinatesX() - s1.getCoordinatesX()), 2)
                    + Math.pow((s2.getCoordinatesY() - s1.getCoordinatesY()), 2));
            if (d > s1.getRadiusConnection())
                return false;
        }
        return true;
    }

    /**
     * Phương thức kiểm tra kết nối của một dải mảnh<i>12.5.21</i>
     *
     * @param xStart - vị trí bắt đầu
     * @param xEnd   - vị trí kết thúc
     * @param rCover - bán kính bao phủ
     * @param rCon   - bán kính kết nối
     * @param width  - chiều rộng của dải
     * @return - true: chuỗi đã kết nối, false: chuỗi không kết nối
     */

	@Override
    public int checkConnectStrip(int xStart, int xEnd, double rCover, double rCon, int width, int aver) {
        ArrayList<Sensor> list = new ArrayList<>();
        int length = xEnd - xStart;
        if (rCover >= length) {
            return 1;
        }

        // Số Lượng sensor
        int i = 2;

        while (true) {
            list = Sensor.radomCoordinatesXY(xStart, rCover, rCon, length, width, i);

            while (list.get(0).getCoordinatesX() >= (xStart + rCon)
                    || list.get(i - 1).getCoordinatesX() <= (xEnd - rCon)) {
                i++;
                list = Sensor.radomCoordinatesXY(xStart, rCover, rCon, length, width, i);
            }


            // những sensor trong khoảng xStart -> xStart + rCon
            List<Sensor> a = new ArrayList();
            // những sensor trong khoảng xEnd- rCon -> xEnd
            List<Sensor> b = new ArrayList();
            // những sensor trong khoảng xStart + rCon -> xEnd- rCon
            List<Sensor> c = new ArrayList();

            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).getCoordinatesX() <= xStart + rCon)
                    a.add(list.get(k));
                else if (list.get(k).getCoordinatesX() >= xEnd - rCon)
                    b.add(list.get(k));
                else
                    c.add(list.get(k));
            }


            //ktra tinh kết nối của các dải với mỗi 1 sensor trong khoảng xStart ->xStart + rCon
            for (int k = 0; k < a.size(); k++) {

                //copy list sensor trong khoảng xStart + rCon ->xEnd - rCon
                List<Sensor> listTem = c;

                // khởi tạo 1 list chứa các sensor đã kết nối
                List<Sensor> arrayCon = new ArrayList<Sensor>();
                arrayCon.add(a.get(k));

                while (true) {

                    if (arrayCon.size() == 0 || listTem.size() == 0)
                        break;

                    //check xem dải đã kết nối hay chưa.

                    for (int m = 0; m < arrayCon.size(); m++) {
                        for (int n = 0; n < b.size(); n++) {
                            if (checkConnect(arrayCon.get(m), b.get(n)) == true) {
                                FileWriter out = null;
                                BufferedWriter bfw = null;

                                try {
                                    out = new FileWriter(".\\output.txt", true);
                                    bfw = new BufferedWriter(out);
                                    bfw.newLine();
                                    bfw.write("Lặp lần " + aver + ":");
                                    bfw.newLine();
                                    bfw.write(list.toString());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (bfw != null) {
                                        try {
                                            bfw.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                return i;
                            }

                        }
                    }

                    int lengthOfArrayCon = arrayCon.size();

                    //thêm những sensor kết nối với arrayCon vào arrayCon và loại bỏ sensor đã xét.
                    for (int l = 0; l < lengthOfArrayCon; l++) {
                        for (int j = 0; j < listTem.size(); j++) {
                            if (checkConnect(arrayCon.get(l), listTem.get(j)) == true) {
                                arrayCon.add(listTem.get(j));
                                listTem.remove(j);
                                j--;
                            } else if (arrayCon.get(l).getCoordinatesX() + rCon > listTem.get(j).getCoordinatesX())
                                continue;
                            else
                                break;
                        }
                    }

//					xóa những phần tử đã xét kết nối với dãy sensor list
                    for (int n = 0; n < lengthOfArrayCon; n++) {
                        arrayCon.remove(0);
                    }
                }

            }
            i++;
        }
    }

    /**
     * Phương thức lấy trung bình số các Sensor được sắp xếp theo số lần được truyền
     * vào<i>12.5.21</i>
     *
     * @param dt - DataHandle
     * @return
     */
    public int getAverageSensor(DataHandle dt) {
        int average = 0;
        for (int i = 0; i < dt.getAver(); i++) {
            average += checkConnectStrip(dt.getxStart(), dt.getxEnd(), dt.getrCover(), dt.getrCon(), dt.getWidth(), i + 1);
        }
        return (int) (average / dt.getAver());
    }

}
