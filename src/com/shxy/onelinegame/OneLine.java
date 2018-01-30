package com.shxy.onelinegame;

import java.util.List;
import java.util.Stack;

/**
 * time: 2018.1.30
 * @author shxy
 * 用于求解一笔连通整个矩阵的路径
 */
public class OneLine {

	
	/**
	 * 为供python使用提供接口 jpype
	 * @param s1 行数
	 * @param s2 列数
	 * @param s3 矩阵
	 * @param s4 起始x
	 * @param s5 起始y
	 * @return 点集
	 */
	public String forPython(String s1, String s2, String s3, String s4, String s5) {
		
		//地图大小
		Integer n = Integer.parseInt(s1);
		Integer m = Integer.parseInt(s2);

		s3 = s3.substring(1);//除去第一个星号
		String[] mapFragment = s3.split("\\*");

		//起始点
		Integer sx = Integer.parseInt(s4);
		Integer sy = Integer.parseInt(s5);

		int[][] map = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.valueOf(mapFragment[i * m + j]);
			}
		}
		
		OneLine oneLine = new OneLine();
		List<Point> way = oneLine.getWay(map, sx, sy);
		String res = "";
		for(Point p:way){
			res += p;
		}
		//去除结尾分隔符
		res = res.substring(0, res.length() - 1);
		return res;
	}

	private static final int[] DX = { -1, 1, 0, 0 };
	private static final int[] DY = { 0, 0, -1, 1 };

	private int[][] map;// -1:墙体,0:路,1:走过的路
	private int sx;// 起始 x
	private int sy;// 起始 y
	private int n;// 行数
	private int m;// 列数
	private Stack<Point> way;// 当前道路
	private Stack<Point> realWay;// 结果道路
	private int wayCount;// 空格个数
	private boolean findWay;// 找到一条路

	/**
	 * 
	 * @param map 矩阵
	 * @param sx 起始x
	 * @param sy 起始y
	 * @return 点集
	 */
	public Stack<Point> getWay(int[][] map, int sx, int sy) {
		findWay = false;
		wayCount = 0;
		way = new Stack<>();
		realWay = new Stack<>();
		this.map = map;
		this.sx = sx;
		this.sy = sy;
		this.n = map.length;
		this.m = map[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] != -1) {
					wayCount++;
				}
			}
		}
		way.push(new Point(sx, sy));
		f(sx, sy);
		return realWay;
	}

	/**
	 * 递归dfs求解
	 * @param nx 当前坐标
	 * @param ny 当前坐标
	 */
	private void f(int nx, int ny) {
		for (int i = 0; i < 4; i++) {
			if (findWay) {
				return;
			}
			int x = nx + DX[i];
			int y = ny + DY[i];

			if ((x >= 0 && x < n && y >= 0 && y < m) && (map[x][y] == 0)) {
				map[x][y] = 1;
				way.push(new Point(x, y));
				if (isSuccess()) {
					findWay = true;
					realWay.addAll(way);
					return;
				} else {
					f(x, y);
					way.pop();
					map[x][y] = 0;
				}
			}
		}
	}

	/**
	 * 成功判断
	 * @return 是否成功
	 */
	private boolean isSuccess() {
		return way.size() == wayCount;
	}

	/**
	 * 点类
	 */
	private class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return x + "," + y + "\n";
		}
	}
}

