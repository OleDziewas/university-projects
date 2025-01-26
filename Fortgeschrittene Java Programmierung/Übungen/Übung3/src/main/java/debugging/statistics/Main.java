package debugging.statistics;

public class Main {

	public static void main(String[] args) {
		long[] numbers1 = { 5, 8, 3, 1l, 13 };
		System.out.println(computeStatistics(numbers1));

		System.out.println();

		long[] numbers2 = { 5, 8, 3, 11, 14 };
		System.out.println(computeStatistics(numbers2));
	}

	private static StatisticsResult computeStatistics(long[] numbers) {
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		int count = 0;
		long sum = 0;
		for (int i = 0; i < numbers.length; i++) {
			long n = numbers[i];
			count++;
			if (n < min) {
				min = n;
			}
			if (n > max) {
				max = n;
			}
			sum += n;
		}
		double average = sum / count;

		return new StatisticsResult(min, max, sum, count, average);
	}

	private static class StatisticsResult {
		public final long min, max, sum;
		public final int count;
		public final double average;

		public StatisticsResult(long min, long max, long sum, int count, double average) {
			super();
			this.min = min;
			this.max = max;
			this.sum = sum;
			this.count = count;
			this.average = average;
		}

		public String toString() {
			return String.format("Count: %d, Sum: %d%nMin: %d, Max: %d%nAverage: %f", count, sum, min, max, average);
		}
	}

}
