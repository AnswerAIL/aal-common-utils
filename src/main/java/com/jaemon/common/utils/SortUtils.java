/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     8种排序算法
 * </p>
 *
 * <refer>http://www.jianshu.com/p/5e171281a387</refer>
 *
 * @author Jaemon
 * @date 2019-12-17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SortUtils {


    /**
     * <b>直接插入排序</b>： 经常碰到这样一类排序问题：把新的数据插入到已经排好的数据列中
     *
     * <ul>
     *     <li>
     *         将第一个数和第二个数排序，然后构成一个有序序列
     *     </li>
     *     <li>
     *         将第三个数插入进去，构成一个新的有序序列
     *     </li>
     *     <li>
     *         对第四个数、第五个数……直到最后一个数，重复第二步
     *     </li>
     * </ul>
     *
     *
     * <b><i>如何写成代码</i></b>
     * <ol>
     *     <li>
     *         首先设定插入次数，即循环次数，for(int i=1;i<length;i++)，1个数的那次不用插入
     *     </li>
     *     <li>
     *         设定插入数和得到已经排好序列的最后一个数的位数。insertNum和j=i-1
     *     </li>
     *     <li>
     *         从最后一个数开始向前循环，如果插入数小于当前数，就将当前数向后移动一位
     *     </li>
     *     <li>
     *         将当前数放置到空着的位置，即j+1
     *     </li>
     * </ol>
     *
     * @param a 待排序数组
     */
    public static void insertSort(int[] a) {
        // 数组长度, 将这个提取出来是为了提高速度。
        int length = a.length;
        // 要插入的数
        int insertNum;
        // 插入的次数
        for (int i = 1; i < length; i++) {
            // 要插入的数
            insertNum = a[i];
            // 已经排序好的序列元素个数
            int j = i - 1;
            // 序列从后到前循环, 将大于insertNum的数向后移动一格
            while (j >= 0 && a[j] > insertNum) {
                // 元素移动一格
                a[j + 1] = a[j];
                j--;
            }
            // 将需要插入的数放在要插入的位置
            a[j + 1] = insertNum;
        }
    }


    /**
     * <b>希尔排序</b>： 对于直接插入排序问题，数据量巨大时
     *
     * <ul>
     *     <li>
     *         将数的个数设为n，取奇数k=n/2，将下标差值为k的数分为一组，构成有序序列
     *     </li>
     *     <li>
     *         再取k=k/2 ，将下标差值为k的书分为一组，构成有序序列
     *     </li>
     *     <li>
     *         重复第二步，直到k=1执行简单插入排序
     *     </li>
     * </ul>
     *
     * <b><i>如何写成代码</i></b>
     * <ol>
     *     <li>
     *         首先确定分的组数
     *     </li>
     *     <li>
     *         然后对组中元素进行插入排序
     *     </li>
     *     <li>
     *         然后将length/2，重复1,2步，直到length=0为止
     *     </li>
     * </ol>
     *
     * @param a 待排序数组
     */
    public static void sheelSort(int[] a) {
        int d = a.length;
        while (d != 0) {
            d = d / 2;
            // 分的组数
            for (int x = 0; x < d; x++) {
                // 组中的元素, 从第二个数开始
                for (int i = x + d; i < a.length; i += d) {
                    // j为有序序列最后一位的位数
                    int j = i - d;
                    // 要插入的元素
                    int temp = a[i];
                    // 从后往前遍历
                    for (; j >= 0 && temp < a[j]; j -= d) {
                        // 向后移动d位
                        a[j + d] = a[j];
                    }
                    a[j + d] = temp;
                }
            }
        }
    }


    /**
     * <b>简单选择排序</b>： 常用于取序列中最大最小的几个数时
     * <blockquote>
     *     如果每次比较都交换，那么就是交换排序；如果每次比较完一个循环再交换，就是简单选择排序
     * </blockquote>
     *
     * <ul>
     *     <li>
     *         遍历整个序列，将最小的数放在最前面
     *     </li>
     *     <li>
     *         遍历剩下的序列，将最小的数放在最前面
     *     </li>
     *     <li>
     *         重复第二步，直到只剩下一个数
     *     </li>
     * </ul>
     *
     * <b><i>如何写成代码</i></b>
     * <ol>
     *     <li>
     *         首先确定循环次数，并且记住当前数字和当前位置
     *     </li>
     *     <li>
     *         将当前位置后面所有的数与当前数字进行对比，小数赋值给key，并记住小数的位置
     *     </li>
     *     <li>
     *         比对完成后，将最小的值与第一个数的值交换
     *     </li>
     *     <li>
     *         重复2、3步
     *     </li>
     * </ol>
     *
     * @param a 待排序数组
     */
    public static void selectSort(int[] a) {
        int length = a.length;
        // 循环次数
        for (int i = 0; i < length; i++) {
            int key = a[i];
            int position = i;
            // 选出最小的值和位置
            for (int j = i + 1; j < length; j++) {
                if (a[j] < key) {
                    key = a[j];
                    position = j;
                }
            }
            // 交换位置
            a[position] = a[i];
            a[i] = key;
        }
    }


    /**
     * <b>堆排序</b>： 对简单选择排序的优化
     *
     * <ul>
     *     <li>
     *         将序列构建成大顶堆
     *     </li>
     *     <li>
     *         将根节点与最后一个节点交换，然后断开最后一个节点
     *     </li>
     *     <li>
     *         重复第一、二步，直到所有节点断开
     *     </li>
     * </ul>
     *
     * @param a 待排序数组
     */
    public static void heapSort(int[] a) {
        System.out.println("开始排序");
        int arrayLength = a.length;
        // 循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            // 建堆
            buildMaxHeap(a, arrayLength - 1 - i);
            // 交换堆顶和最后一个元素
            swap(a, 0, arrayLength - 1 - i);
//            System.out.println(Arrays.toString(a));
        }
    }

    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    // 对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(int[] data, int lastIndex) {
        // 从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            // k保存正在判断的节点
            int k = i;
            // 如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                // k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                // 如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    // 若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        // biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                // 如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    // 交换他们
                    swap(data, k, biggerIndex);
                    // 将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }


    /**
     * 冒泡排序
     *
     * <ul>
     *     <li>
     *         将序列中所有元素两两比较，将最大的放在最后面
     *     </li>
     *     <li>
     *         将剩余序列中所有元素两两比较，将最大的放在最后面
     *     </li>
     *     <li>
     *         重复第二步，直到只剩下一个数
     *     </li>
     * </ul>
     *
     * <b><i>如何写成代码</i></b>
     * <ol>
     *     <li>
     *         设置循环次数
     *     </li>
     *     <li>
     *         设置开始比较的位数，和结束的位数
     *     </li>
     *     <li>
     *         两两比较，将最小的放到前面去
     *     </li>
     *     <li>
     *         重复2、3步，直到循环次数完毕
     *     </li>
     * </ol>
     *
     * @param a 待排序数组
     */
    public static void bubbleSort(int[] a) {
        int length = a.length;
        int temp;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }


    /**
     * <b>快速排序</b>： 要求时间最快时
     *
     * <ul>
     *     <li>
     *         选择第一个数为p，小于p的数放在左边，大于p的数放在右边
     *     </li>
     *     <li>
     *         递归的将p左边和右边的数都按照第一步进行，直到不能递归
     *     </li>
     * </ul>
     *
     * @param numbers 待排序数组
     * @param start 起始下标
     * @param end  终止下标
     */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            // 选定的基准值（第一个数值作为基准值）
            int base = numbers[start];
            // 记录临时中间值
            int temp;
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end)) {
                    i++;
                }

                while ((numbers[j] > base) && (j > start)) {
                    j--;
                }

                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j) {
                quickSort(numbers, start, j);
            }

            if (end > i) {
                quickSort(numbers, i, end);
            }
        }
    }


    /**
     * <b>归并排序</b>： 速度仅次于快排，内存少的时候使用，可以进行并行计算的时候使用
     *
     * <ul>
     *     <li>
     *         选择相邻两个数组成一个有序序列
     *     </li>
     *     <li>
     *         选择相邻的两个有序序列组成一个有序序列
     *     </li>
     *     <li>
     *         重复第二步，直到全部组成一个有序序列
     *     </li>
     * </ul>
     *
     * @param numbers 待排序数组
     * @param left .
     * @param right .
     */
    public static void mergeSort(int[] numbers, int left, int right) {
        // 每组元素个数
        int t = 1;
        int size = right - left + 1;
        while (t < size) {
            // 本次循环每组元素个数
            int s = t;
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(numbers, i, i + (s - 1), i + (t - 1));
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(numbers, i, i + (s - 1), right);
            }
        }
    }

    private static void merge(int[] data, int p, int q, int r) {
        int[] B = new int[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (data[s] <= data[t]) {
                B[k] = data[s];
                s++;
            } else {
                B[k] = data[t];
                t++;
            }
            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }

        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }


    /**
     * <b>基数排序</b>： 用于大量数，很长的数进行排序时
     *
     * <ul>
     *     <li>
     *         将所有的数的个位数取出，按照个位数进行排序，构成一个序列
     *     </li>
     *     <li>
     *         将新构成的所有的数的十位数取出，按照十位数进行排序，构成一个序列
     *     </li>
     * </ul>
     *
     * @param array 待排序数组
     */
    public static void sort(int[] array) {
        // 首先确定排序的趟数;
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        int time = 0;
        // 判断位数;
        while (max > 0) {
            max /= 10;
            time++;
        }
        // 建立10个队列;
        List<ArrayList<Integer>> queue = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> queue1 = new ArrayList<>();
            queue.add(queue1);
        }
        // 进行time次分配和收集;
        for (int i = 0; i < time; i++) {
            // 分配数组元素;
            for (int j = 0; j < array.length; j++) {
                // 得到数字的第time+1位数;
                int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(array[j]);
                queue.set(x, queue2);
            }
            // 元素计数器
            int count = 0;
            // 收集队列元素;
            for (int k = 0; k < 10; k++) {
                while (queue.get(k).size() > 0) {
                    ArrayList<Integer> queue3 = queue.get(k);
                    array[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{8, 12, 25, 65, 200, 581, 357, 425, 219, 100, 215};

        insertSort(arr);

        sheelSort(arr);

        selectSort(arr);

        heapSort(arr);

        bubbleSort(arr);

        quickSort(arr, 0, arr.length - 1);

        mergeSort(arr, 0, arr.length - 1);

        sort(arr);
    }
}