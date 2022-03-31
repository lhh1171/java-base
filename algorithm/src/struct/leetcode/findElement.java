package struct.leetcode;


public class findElement {
    public static void main(String[] args) {
        int[] nums={1,2,2,2,2,2,3,3,3,3,3,3,3,3,35};
        int[] ints = searchRange(nums, 2);
        for (int i:ints){
            System.out.println(i);
        }
    }
//    public static int[] searchRange(int[] nums, int target) {
//        int n = nums.length;
//        if (n < 1) {
//            return new int[]{-1, -1};
//        } else if (n < 2) {
//            if (nums[0] == target) {
//                return new int[]{0, 0};
//            } else {
//                return new int[]{-1, -1};
//            }
//        }
//        for (int i = 0; i < n; ++i) {
//            if (nums[i] == target) {
//                int right = i;
//                while (right < n && nums[right] == target) {
//                    right++;
//                }
//                return new int[]{i, right - 1};
//            }
//        }
//        return new int[]{-1, -1};
//    }
public static int[] searchRange(int[] nums, int target) {
    int l = 0;
    int r = nums.length-1;
    int mid;
    while(l<=r){
        //中间位置,不断计算中间位置
        mid = l + (r - l) / 2;

        //先找到中间一个值，与target相等，向两边拓展
        if (nums[mid] == target){
            r = l = mid;
            //l大于0且
            while(l >= 0 && nums[l] == target ){
                while(r <= nums.length-1 && nums[r]== target){
                    r++;
                }
                l--;
            }
            //l等于0
            return new int[]{l+1,r-1};
        } else if(nums[mid]<target){
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return new int[]{-1,-1};
}

}
