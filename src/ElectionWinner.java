import java.util.*;

/**
 * A class to find the winner of a presidential election.
 */
public class ElectionWinner {

    /**
     * Finds the identifier of the candidate with the most votes in an array of votes.
     *
     * @param votes an array of integers representing the votes for each candidate
     * @return the identifier of the candidate with the most votes
     */
    public static int findWinner(int[] votes) {
        // Use merge-sort to sort the votes
        mergeSort(votes, 0, votes.length - 1);

        // Use a TreeMap to count the votes for each candidate
        TreeMap<Integer, Integer> voteCounts = new TreeMap<>();
        for (int i = 0; i < votes.length; i++) {
            int candidate = votes[i];
            int count = voteCounts.getOrDefault(candidate, 0);
            voteCounts.put(candidate, count + 1);
        }

        // Find the candidate with the most votes
        int winner = -1;
        int maxVotes = 0;
        for (int candidate : voteCounts.keySet()) {
            int votesForCandidate = voteCounts.get(candidate);
            if (votesForCandidate > maxVotes) {
                winner = candidate;
                maxVotes = votesForCandidate;
            }
        }

        return winner;
    }

    /**
     * Sorts an array of integers using the merge-sort algorithm.
     *
     * @param array the array to be sorted
     * @param left the leftmost index of the subarray to be sorted
     * @param right the rightmost index of the subarray to be sorted
     */
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays into a single sorted array.
     *
     * @param array the array containing the subarrays to be merged
     * @param left the leftmost index of the left subarray
     * @param mid the rightmost index of the left subarray
     * @param right the rightmost index of the right subarray
     */
    public static void merge(int[] array, int left, int mid, int right) {
        int[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);
        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }
        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }

    /**
     * A test program that creates an example input array and calls findWinner to find the winner of the election.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int[] votes = {3, 4, 1, 2, 4, 4, 3, 3, 1};
        int winner = findWinner(votes);
        System.out.println("The winner is candidate " + winner);
    }
}
