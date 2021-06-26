package dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static FileReader in = null;
	public static BufferedReader bfr = null;

	public static FileWriter out = null;
	public static BufferedWriter bfw = null;

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println(
				"=============================================================================================");
		System.out.println(
				"------------BTL đề tài: Tính toán mật độ đáng tin cậy của Sensor trêm một dải mỏng-----------");

		int choose;
		do {
			choose = 1;
			while (true) {
				System.out.println("\n_______________________________________");
				System.out.println("Các Lựa chọn:");
				System.out.println("1: Thêm trường hợp tính toán");
				System.out.println("2: Sửa trường hợp tính toán");
				System.out.println("3: Xóa trường hợp tính toán");
				System.out.println("4: Xem kết quả trường hợp");
				System.out.println("0: Thoát");
				System.out.println("Chọn:");
				try {
					String stringChoose = sc.nextLine();
					choose = Integer.parseInt(stringChoose);
					switch (choose) {
					case 1:
						addCase();
						break;
					case 2:
						modifyCase();
						break;
					case 3:
						deletCase();
						break;
					case 4:
						watchResult();
						break;
					case 0:
						break;
					default:
						System.out.println("Không có lựa chọn này");
						break;
					}
				} catch (Exception e) {
					// System.out.println("Nhập sai");
					e.printStackTrace();
				} finally {
					if (choose == 0) {
						break;
					}
				}
			}
		} while (choose != 0);

	}

	private static void watchResult() {
		try {
			System.out.println("=====================================================");
			System.out.println("----------------Bảng kết quả-------------------------");
			ArrayList<DataHandle> dataHandles = new ArrayList<DataHandle>();
			in = new FileReader(".\\input.txt");
			bfr = new BufferedReader(in);
			String line;
			String[] data = null;
			while ((line = bfr.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				data = line.trim().split(";");
				try {
					int id = Integer.parseInt(data[0]);
					int xStart = Integer.parseInt(data[1]);
					int xEnd = Integer.parseInt(data[2]);
					double rCover = Double.parseDouble(data[3]);
					double rCon = Double.parseDouble(data[4]);
					if (rCon > 2 * rCover) {
						throw new ConnectedException("Trường hợp " + id
								+ "\nKhông đảm bảo tính bao phủ \nVì bán kính kết nối lớn hơn 2 lần bán kính bao phủ");
					}
					int width = Integer.parseInt(data[5]);
					int aver = Integer.parseInt(data[6]);
					DataHandle dt = new DataHandle(id, xStart, xEnd, rCover, rCon, width, aver);
					dataHandles.add(dt);
				} catch (ConnectedException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Cần truyền đúng dữ liệu để tính toán");
				}
			}
			bfr.close();
			out = new FileWriter(".\\output.txt", true);
			bfw = new BufferedWriter(out);
			ThinStrip ts = new ThinStrip();
			for (DataHandle d : dataHandles) {
				int a = ts.getAverageSensor(d);
				System.out.println("________________________________");
				System.out.println("Trường hợp: " + d.getId());
				System.out.println("vị trí bắt đầu: " + d.getxStart() + " Vị trí kết thúc: " + d.getxEnd());
				System.out.println("Bán kính bao phủ: " + d.getrCover() + " Bán kính kết nối: " + d.getrCon());
				System.out.println("Số lần lặp: " + d.getAver());
//				int a = ts.getAverageSensor(d);
				System.out.println("Mật độ Sensor đáng tin cậy: " + a);
				bfw.newLine();
				bfw.write(String.valueOf(a));
				bfw.flush();
			}
			bfw.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				bfr.close();
				if (bfw != null) {
					bfw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void deletCase() {
		try {
			ArrayList<DataHandle> dataHandles = new ArrayList<DataHandle>();
			in = new FileReader(".\\input.txt");
			bfr = new BufferedReader(in);
			String line;
			String[] data = null;
			while ((line = bfr.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				data = line.trim().split(";");
				try {
					int id = Integer.parseInt(data[0]);
					int xStart = Integer.parseInt(data[1]);
					int xEnd = Integer.parseInt(data[2]);
					double rCover = Double.parseDouble(data[3]);
					double rCon = Double.parseDouble(data[4]);
					int width = Integer.parseInt(data[5]);
					int aver = Integer.parseInt(data[6]);
					DataHandle dt = new DataHandle(id, xStart, xEnd, rCover, rCon, width, aver);
					dataHandles.add(dt);
				} catch (Exception e) {

					// TODO: handle exception
					System.out.println("Cần truyền đúng dữ liệu để tính toán");
				}
			}
			bfr.close();

			System.out.println("Nhập 'id' của trường hợp bạn muốn xóa:");
			int idModify = 0;
			try {
				String s = sc.nextLine();
				idModify = Integer.valueOf(s);
				int count = 0;
				for (DataHandle d : dataHandles) {
					if (d.getId() == idModify) {
						dataHandles.remove(d);
						out = new FileWriter(".\\input.txt");
						bfw = new BufferedWriter(out);
						bfw.write("#id;xStart;xEnd;rCover;rCon;width;aver");
						bfw.newLine();
						for (DataHandle dt : dataHandles) {
							bfw.write(dt.toString());
							bfw.newLine();
						}

						System.out.println("Xóa thành công!");
						count++;
						break;
					}
				}
				if (count == 0) {
					System.out.println("Không có mã vừa nhập");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Bạn cần nhập 1 số nguyên");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bfr.close();
				if (bfw != null) {
					bfw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void modifyCase() {
		try {
			ArrayList<DataHandle> dataHandles = new ArrayList<DataHandle>();
			in = new FileReader(".\\input.txt");
			bfr = new BufferedReader(in);
			String line;
			String[] data = null;
			while ((line = bfr.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				data = line.trim().split(";");
				try {
					int id = Integer.parseInt(data[0]);
					int xStart = Integer.parseInt(data[1]);
					int xEnd = Integer.parseInt(data[2]);
					double rCover = Double.parseDouble(data[3]);
					double rCon = Double.parseDouble(data[4]);
					int width = Integer.parseInt(data[5]);
					int aver = Integer.parseInt(data[6]);
					DataHandle dt = new DataHandle(id, xStart, xEnd, rCover, rCon, width, aver);
					dataHandles.add(dt);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Cần truyền đúng dữ liệu để tính toán");
				}
			}
			bfr.close();

			System.out.println("Nhập 'id' của trường hợp bạn muốn sửa:");
			int idModify = 0;
			try {
				String s = sc.nextLine();
				idModify = Integer.valueOf(s);
				int count = 0;
				for (DataHandle d : dataHandles) {
					if (d.getId() == idModify) {
						dataHandles.remove(d);
						System.out.println("Nhập thông số của trường hợp cần sửa theo mẫu:");
						System.out.println("xStart;xEnd;rCover;rCon;width;aver");
						System.out.println("Trường hợp muốn sửa: " + d.getxStart() + ";" + d.getxEnd() + ";"
								+ d.getrCover() + ";" + d.getrCon() + ";" + d.getWidth() + ";" + +d.getAver());
						String st = sc.nextLine();
						String[] addData = st.trim().split(";");
						try {
							int xStart = Integer.parseInt(addData[0]);
							int xEnd = Integer.parseInt(addData[1]);
							double rCover = Double.parseDouble(addData[2]);
							double rCon = Double.parseDouble(addData[3]);
							int width = Integer.parseInt(addData[4]);
							int aver = Integer.parseInt(addData[5]);
							DataHandle dt = new DataHandle(idModify, xStart, xEnd, rCover, rCon, width, aver);
							dataHandles.add(dt);
							Collections.sort(dataHandles,
									(DataHandle o1, DataHandle o2) -> o1.getId() > o2.getId() ? 1 : -1);
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("Cần truyền đúng dữ liệu");
						}

						out = new FileWriter(".\\input.txt");
						bfw = new BufferedWriter(out);
						bfw.write("#id;xStart;xEnd;rCover;rCon;width;aver");
						bfw.newLine();
						for (DataHandle dt : dataHandles) {
							bfw.write(dt.toString());
							bfw.newLine();
						}

						System.out.println("Sửa thành công!");
						count++;
						break;
					}
				}
				if (count == 0) {
					System.out.println("Không có mã vừa nhập");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Bạn cần nhập 1 số nguyên");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bfr.close();
				if (bfw != null) {
					bfw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void addCase() {
		try {
			ArrayList<DataHandle> dataHandles = new ArrayList<DataHandle>();
			in = new FileReader(".\\input.txt");
			bfr = new BufferedReader(in);
			String line;
			String[] data = null;
			while ((line = bfr.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				data = line.trim().split(";");
				try {
					int id = Integer.parseInt(data[0]);
					int xStart = Integer.parseInt(data[1]);
					int xEnd = Integer.parseInt(data[2]);
					double rCover = Double.parseDouble(data[3]);
					double rCon = Double.parseDouble(data[4]);
					int width = Integer.parseInt(data[5]);
					int aver = Integer.parseInt(data[6]);
					DataHandle dt = new DataHandle(id, xStart, xEnd, rCover, rCon, width, aver);
					dataHandles.add(dt);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Cần truyền đúng dữ liệu để tính toán");
				}
			}
			bfr.close();
			System.out.println("Nhập thông số của trường hợp cần thêm theo mẫu:");
			System.out.println("xStart;xEnd;rCover;rCon;width;aver");
			System.out.println("VD: 5;100;2;2;10;100");
			String[] addData = (sc.nextLine()).trim().split(";");
			int max = 0;
			for (DataHandle dataHandle : dataHandles) {
				max = Math.max(max, dataHandle.getId());
			}
			try {
				int id = max + 1;
				int xStart = Integer.parseInt(addData[0]);
				int xEnd = Integer.parseInt(addData[1]);
				double rCover = Double.parseDouble(addData[2]);
				double rCon = Double.parseDouble(addData[3]);
				int width = Integer.parseInt(addData[4]);
				int aver = Integer.parseInt(addData[5]);
				DataHandle dt = new DataHandle(id, xStart, xEnd, rCover, rCon, width, aver);
				dataHandles.add(dt);
				out = new FileWriter(".\\input.txt");
				bfw = new BufferedWriter(out);
				bfw.write("#id;xStart;xEnd;rCover;rCon;width;aver");
				bfw.newLine();
				for (DataHandle d : dataHandles) {
					bfw.write(d.toString());
					bfw.newLine();
				}
				bfw.close();

				System.out.println("Thêm thành công!");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Cần truyền đúng dữ liệu");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bfr.close();
				if (bfw != null) {
					bfw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
