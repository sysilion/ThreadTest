public class PrintTest {
	public static void main(String args[]) {
		PrintThread thread1, thread2, thread3, thread4;
		thread1 = new PrintThread();
		thread2 = new PrintThread();
		thread3 = new PrintThread();
		thread4 = new PrintThread();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
}

class PrintThread extends Thread {
	int sleepTime;

	public PrintThread() {
		sleepTime = (int) (Math.random() * 5000);

		System.out.println("Name: " + getName() + ";  sleep: " + sleepTime);
	}

	public void run() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException exception) {
			System.err.println(exception.toString());
		}

		// print thread name
		System.out.println(getName());
	}
}
