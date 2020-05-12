package com.wemarkbenches.cobenchmarkapp.benchmark.HDDbenchmark;

import android.os.Environment;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;
import com.wemarkbenches.cobenchmarkapp.timing.Timer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class HDDRandomAccess implements IBenchmark {

	private final static String PATH = Environment.getDataDirectory().toString() + "/testraf.dat";
	private String result;

	@Override
	public void initialize(Object... params) {
		File tempFile = new File(PATH);
		RandomAccessFile rafFile;
		long fileSizeInBytes = (Long) params[0];

		// Create a temporary file with random content to be used for
		// reading and writing
		try {
			rafFile = new RandomAccessFile(tempFile, "rw");
			Random rand = new Random();
			int bufferSize = 4 * 1024;
			long toWrite = fileSizeInBytes / bufferSize;
			byte[] buffer = new byte[bufferSize];
			long counter = 0;

			while (counter++ < toWrite) {
				rand.nextBytes(buffer);
				rafFile.write(buffer);
			}
			rafFile.close();
			tempFile.deleteOnExit();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void warmUp() {
		// have a Mountain Dew or Red Bull
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException("Use run(Object[]) instead");
	}

	@Override
	public void run(Object ...options) {
		// ex. {"r", "fs", 4*1024}
		Object[] param = (Object[]) options;
		// used by the fixed size option
		final int steps = 10000;
		// used by the fixed time option
		final int runtime = 5000; // ms

		try {
			// read benchmark
			if (String.valueOf(param[0]).toLowerCase().equals("r")) {
				// buffer size given as parameter
				int bufferSize = Integer.parseInt(String.valueOf(param[2]));

				// read a fixed size and measure time
				if (String.valueOf(param[1]).toLowerCase().equals("fs")) {

					long timeMs = new RandomAccess().randomReadFixedSize(PATH,
							bufferSize, steps);
					result = steps + " random reads in " + timeMs + " ms ["
							+ (steps * bufferSize / 1024 / 1024) + " MB, "
							+ 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
				}
				// read for a fixed time amount and measure time
				else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {

					int ios = new RandomAccess().randomReadFixedTime(PATH,
							bufferSize, runtime);
					result = ios + " I/Os per second ["
							+ (ios * bufferSize / 1024 / 1024) + " MB, "
							+ 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
				} else
					throw new UnsupportedOperationException("Read option \""
							+ String.valueOf(param[1])
							+ "\" is not implemented");

			}
			// write benchmark
			else if (String.valueOf(param[0]).toLowerCase().equals("w")) {
                // buffer size given as parameter
                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                // write a fixed size and measure time
                if (String.valueOf(param[1]).toLowerCase().equals("fs")) {
                    long timeMs = new RandomAccess().randomWriteFixedSize(PATH,
                            bufferSize, steps);
                    result = steps + " random writes in " + timeMs + " ms ["
                            + (steps * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                }
                // write for a fixed time amount and measure time
                else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {
                    int ios = new RandomAccess().randomWriteFixedTime(PATH,
                            bufferSize, runtime);
                    result = ios + " I/Os per second ["
                            + (ios * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                } else
                    throw new UnsupportedOperationException("Write option \""
                            + String.valueOf(param[1])
                            + "\" is not implemented");
			} else
				throw new UnsupportedOperationException("Benchmark option \""
						+ String.valueOf(param[0]) + "\" is not implemented");

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.clean();
	}

	@Override
	public void clean() {
		File fileToClear = new File(PATH);
		fileToClear.delete();
	}

	@Override
	public void cancel() {
		//
	}

	@Override
	public String getResult() {
		return String.valueOf(result);
	}

	class RandomAccess {
		private Random random;

		RandomAccess() {
			random = new Random();
		}

		/**
		 * Reads data from random positions into a fixed size buffer from a
		 * given file using RandomAccessFile
		 *
		 * @param filePath
		 *            Full path to file on disk
		 * @param bufferSize
		 *            Size of byte buffer to read at each step
		 * @param toRead
		 *            Number of steps to repeat random read
		 * @return Amount of time needed to finish given reads in milliseconds
		 * @throws IOException
		 */
		public long randomReadFixedSize(String filePath, int bufferSize,
				int toRead) throws IOException {
			// file to read from
			RandomAccessFile file = new RandomAccessFile(filePath, "rw");
			// size of file
			int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
			// counter for number of reads
			int counter = 0;
			// timer
			Timer timer = new Timer();
			//where to read from in the file
            int position;

			timer.start();
			while (counter++ < toRead) {

			    position = random.nextInt(fileSize - bufferSize);
				readFromFile(filePath, position, bufferSize);
			}

			file.close();
			return timer.stop() / 1000000; // ns to ms!
		}

        public long randomWriteFixedSize(String filePath, int bufferSize, int toWrite) throws IOException {
		    RandomAccessFile file = new RandomAccessFile(filePath, "rw");

		    int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
		    int counter = 0;
		    Timer timer = new Timer();

            byte[] bytes = new byte[bufferSize];
            String bytesToPrint = "";

		    int position;
            Random rand = new Random();

		    timer.start();
		    while(counter++ < toWrite){
		        position = random.nextInt(fileSize - bufferSize);
		        rand.nextBytes(bytes);

		        bytesToPrint = String.valueOf(bytes);
                writeToFile(filePath, bytesToPrint, position);
            }

		    file.close();
		    return timer.stop() / 1000000;
        }

		/**
		 * Reads data from random positions into a fixed size buffer from a
		 * given file using RandomAccessFile for one second, or any other given
		 * time
		 *
		 * @param filePath
		 *            Full path to file on disk
		 * @param bufferSize
		 *            Size of byte buffer to read at each step
		 * @param millis
		 *            Total time to read from file
		 * @return Number of reads in the given amount of time
		 * @throws IOException
		 */
		public int randomReadFixedTime(String filePath, int bufferSize,
				int millis) throws IOException {
			// file to read from
			RandomAccessFile file = new RandomAccessFile(filePath, "rw");
			// size of file
			int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
			// counter for number of reads
			int counter = 0;
			//where to read from in file
            int position;

			long start = System.nanoTime();

			// read for a fixed amount of time
			while (System.nanoTime() - start  < millis/1000000) {
				position = random.nextInt(fileSize - bufferSize);

                readFromFile(filePath, position, bufferSize);

				counter++;
			}

			file.close();
			return counter;
		}

        public int randomWriteFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            Random rand = new Random();

            int position;
            long start = System.nanoTime();
            String bytesToWrite = "";

            while (System.nanoTime() - start  < millis/1000000) {
                position = random.nextInt(fileSize - bufferSize);

                rand.nextBytes(bytes);
                bytesToWrite = String.valueOf(bytes);
                writeToFile(filePath, bytesToWrite, position);

                counter++;
            }

            file.close();
            return counter;
        }

		/**
		 * Read data from a file at a specific position
		 *
		 * @param filePath
		 *            Path to file
		 * @param position
		 *            Position in file
		 * @param size
		 *            Number of bytes to reads from the given position
		 * @return Data that was read
		 * @throws IOException
		 */
		public byte[] readFromFile(String filePath, int position, int size)
				throws IOException {

			RandomAccessFile file = new RandomAccessFile(filePath, "rw");
			file.seek(position);
			byte[] bytes = new byte[size];
			file.read(bytes);
			file.close();
			return bytes;
		}

		/**
		 * Write data to a file at a specific position
		 *
		 * @param filePath
		 *            Path to file
		 * @param data
		 *            Data to be written
		 * @param position
		 *            Start position in file
		 * @throws IOException
		 */
		public void writeToFile(String filePath, String data, int position)
				throws IOException {

			RandomAccessFile file = new RandomAccessFile(filePath, "rw");
			file.seek(position);
			file.write(data.getBytes());
			file.close();
		}
    }

}