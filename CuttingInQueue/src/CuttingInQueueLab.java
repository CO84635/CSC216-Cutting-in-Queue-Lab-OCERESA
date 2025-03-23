import java.util.Arrays;

public class CuttingInQueueLab {

    static class MaxHeap<T extends Comparable<T>> {
        private int capacity;
        private int size;
        private T[] items;

        @SuppressWarnings("unchecked")
        MaxHeap() {
            capacity = 10;
            size = 0;
            items = (T[]) new Comparable[capacity];
        }

        public int size() {
            return size;
        }

        public boolean empty() {
            return (size == 0);
        }

        public int compare(T a, T b) {
            return a.compareTo(b);
        }
        
        private int getLeftChildIndex(int parentIndex) {
            return 2 * parentIndex + 1;
        }

        private int getRightChildIndex(int parentIndex) {
            return 2 * parentIndex + 2;
        }

        private int getParentIndex(int childIndex) {
            return (childIndex - 1) / 2;
        }

        private boolean hasLeftChildIndex(int index) {
            return getLeftChildIndex(index) < size;
        }

        private boolean hasRightChildIndex(int index) {
            return getRightChildIndex(index) < size;
        }

        private boolean hasParent(int index) {
            return getRightChildIndex(index) >= 0;
        }

        private T parent(int index) {
            return items[getParentIndex(index)];
        }
        
        private void swap(int indexOne, int indexTwo) {
            T temp = items[indexOne];
            items[indexOne] = items[indexTwo];
            items[indexTwo] = temp;
        }

        private void increaseCapacity() {
            if (size == capacity) {
                items = Arrays.copyOf(items, capacity * 2);
                capacity *= 2;
            }
        }

        public T peek() {
            if (size == 0) {
                throw new IllegalStateException();
            }
            return items[0];
        }

        public T poll() {
            if (size == 0) {
                throw new IllegalStateException();
            }

            @SuppressWarnings("unchecked")
            T item = items[0];
            items[0] = items[size - 1];
            size--;
            heapifyDown();

            return item;
        }

        public void add(T item) {
            increaseCapacity();
            items[size] = item;
            size++;
            heapifyUp();
        }
        
        public void heapifyUp() {
            int index = size - 1;
            while(hasParent(index) && compare(parent(index), items[index]) < 0) {
                swap(getParentIndex(index), index);
                index = getParentIndex(index);
            }
        }

        public String heapToString() {
            return Arrays.toString(items);
        }

        @SuppressWarnings("unchecked")
        public void heapifyDown() {
            int index = 0;

            while(hasLeftChildIndex(index)) {
                int greaterChildIndex = getLeftChildIndex(index);
                if (hasRightChildIndex(index) && compare(items[greaterChildIndex], items[getRightChildIndex(index)]) < 0) {
                    greaterChildIndex = getRightChildIndex(index);
                }
                
                if (compare( items[index], items[greaterChildIndex]) < 0) {
                    swap(index, greaterChildIndex);
                } else {
                    break;
                }

                index = greaterChildIndex;
            }
        }
    }

    static class PriorityQueue<T extends Comparable<T>> {
        MaxHeap heap;
        PriorityQueue(){
            heap = new MaxHeap<>();
        }

        int size() {
            return heap.size();
        }

        void add(T item ){
            heap.add(item);
        }

        T removeMax() {
            return (T) heap.poll();
        }
    }

    static class JobQueue {
        private PriorityQueue<Job> queue;

        public JobQueue() {
            queue = new PriorityQueue<Job>();
        }

        public void insert(Job job) {
            queue.add(job);
        }

        public void runHighestPriority() {
            Job job = (Job) queue.removeMax();
            if (job != null) {
                job.execute();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        {
        JobQueue jobQueue = new JobQueue();
 
        Job jobA = new Job("This is job a", 5);
        Job jobB = new Job("This is job b", 2);
        Job jobC = new Job("This is job c", 9);
        Job jobD = new Job("This is job d", 8);
        Job jobE = new Job("This is job e", 1);
        jobQueue.insert(jobA);
        jobQueue.insert(jobB);
        jobQueue.insert(jobC);
        jobQueue.insert(jobD);
        jobQueue.insert(jobE);
        
        jobQueue.runHighestPriority(); // Calls jobC.execute() since job C has the highest priority
        jobQueue.runHighestPriority(); // Calls jobD.execute() since job C has the highest priority
        jobQueue.runHighestPriority(); // Calls jobA.execute() since job C has the highest priority
        jobQueue.runHighestPriority(); // Calls jobB.execute() since job C has the highest priority
        jobQueue.runHighestPriority();
        }

        {
        JobQueue jobQueue = new JobQueue();
        
        try {
            jobQueue.runHighestPriority(); // Empty list
        } catch (Exception e) {
            System.out.println("Successfully caught exception for an empty queue.");
        }
        }

        {
            JobQueue jobQueue = new JobQueue();

            Job jobA = new Job("This is job a", 0);
            jobQueue.insert(jobA);
            jobQueue.runHighestPriority();
        }

        {
            JobQueue jobQueue = new JobQueue();

            Job jobA = new Job("I have the same priority job 1", 1);
            Job jobB = new Job("I have the same priority job 2", 1);

            jobQueue.insert(jobA);
            jobQueue.insert(jobB);

            jobQueue.runHighestPriority();
            jobQueue.runHighestPriority();
        }

        {
            JobQueue jobQueue = new JobQueue();

            Job jobA = new Job("I have the max priority job 1", Integer.MAX_VALUE);
            Job jobB = new Job("I have the min priority job 2", Integer.MIN_VALUE);

            jobQueue.insert(jobA);
            jobQueue.insert(jobB);

            jobQueue.runHighestPriority();
            jobQueue.runHighestPriority();
        }
    }
}
