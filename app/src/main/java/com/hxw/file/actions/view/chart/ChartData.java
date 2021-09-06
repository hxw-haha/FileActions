package com.hxw.file.actions.view.chart;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class ChartData {
    public ChartData() {
    }

    public ChartData(String name, String color, float count) {
        this.name = name;
        this.color = color;
        this.count = count;
    }

    public String name;
    public String color;
    public float count;
}
