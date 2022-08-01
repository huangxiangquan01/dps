package cn.xqhuang.dps.algorithms;

public class BitMap {
    /**
     * bitMap中可以加入的最大数字（范围是从0到MAX_VALUE）
     */
    public int max_value;

    BitMap(int max_value) {
        this.max_value = max_value;
        this.a = new int[max_value / 32 + 1];
    }

    /**
     * 存放bitmap的数组，每个int有32位，对应32个数字
     */
    private int[] a;


    /**
     * 在bitmap中加入元素n
     *
     * @param n 范围为[0,MAX_VALUE]
     */
    public void addValue(int n) {
        if (n < 0 || n > max_value) {
            System.out.println("不再0到" + max_value + "的范围内，不能加入");
            return;
        }
        //n对应数组的哪个元素，是n/32
        int row = n >> 5;
        //n对应的int中的位置，是n mod 32
        int index = n & 0x1F;
        //在n对应的int，对应的位置，置1
        a[row] |= 1 << index;
    }

    /**
     * 查找bitmap中是否有元素n
     *
     * @param n
     * @return 如果存在，返回true  不存在，返回false
     */
    public boolean existValue(int n) {
        if (n < 0 || n > max_value) {
            System.out.println("不再0到" + max_value + "的范围内，一定没有");
            return false;
        }
        //n对应数组的哪个元素，是n/32
        int row = n >> 5;
        //n对应的int中的位置，是n mod 32    & 31
        int index = n & 0x1F;
        //result为哪个位置上现在保存的值（为10000(index个0)或者0）
        int result = a[row] & (1 << index);
        //如果不为0，则那个位置一定为1
        return result != 0;

    }

    /**
     * 在bitmap中删除元素n
     *
     * @param n
     */
    public void removeValue(int n) {
        if (n < 0 || n > max_value) {
            System.out.println("不再0到" + max_value + "的范围内，一定没有");
            return;
        }
        //n对应数组的哪个元素，是n/32
        int row = n >> 5;
        //n对应的int中的位置，是n mod 32
        int index = n & 0x1F;
        //对应位置0，与 111101111进行与运算，那位一定变0
        a[row] &= ~(1 << index);
    }


    /**
     * 展示第row行的情况，元素的二进制情况，和有的元素
     *
     * @param row
     */
    public void displayRow(int row) {
        System.out.print("bitmap展示第" + row + "行:" + Integer.toBinaryString(a[row]) + " 有：");
        //对应row：32*row到32*row+31
        int now = row << 5;
        //temp为与对应位进行与运算的数字
        int temp = 1;
        for (int i = 0; i < 32; i++) {
            int result = a[row] & temp;
            if (result != 0) {
                System.out.print("  " + now + "  ");
            }
            now++;
            temp = temp << 1;
        }
        System.out.println();

    }




    public static void main(String[] args) {

        System.out.println(0x1F);

        BitMap bitMap = new BitMap(10000000);
        bitMap.addValue(32);
        bitMap.addValue(64);
        bitMap.displayRow(1);
        System.out.println(bitMap.existValue(1));
       // System.out.println(bitMap.existValue(31));
        bitMap.removeValue(0);
        System.out.println(bitMap.existValue(0));
        bitMap.displayRow(0);

        bitMap.addValue(34);
        bitMap.displayRow(1);
    }
}