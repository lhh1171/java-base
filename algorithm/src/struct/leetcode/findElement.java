package struct.leetcode;


public class findElement {
    public static void main(String[] args) {
        int[] nums={1,2,2,2,2,2,5};
        int[] ints = searchRange(nums, 2);
        for (int i:ints){
            System.out.println(i);
        }
    }
    public static int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        if (n < 1) {
            return new int[]{-1, -1};
        } else if (n < 2) {
            if (nums[0] == target) {
                return new int[]{0, 0};
            } else {
                return new int[]{-1, -1};
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] == target) {
                int right = i;
                while (right < n && nums[right] == target) {
                    right++;
                }
                return new int[]{i, right - 1};
            }
        }
        return new int[]{-1, -1};
    }
}
