public class SharedCell {
	public static void main(String args[]) {
		HoldInteger h = new HoldInteger();
		ProduceInteger p = new ProduceInteger(h);
		ConsumeInteger c1 = new ConsumeInteger(h);
		ConsumeInteger c2 = new ConsumeInteger(h);

		p.start();
		c1.start();
		c2.start();
	}
}

class ProduceInteger extends Thread {
	private HoldInteger pHold;

	public ProduceInteger(HoldInteger h) {
		pHold = h;
	}

	public void run() {
		for (int count = 0; count < 10; count++) {
			// sleep for a random interval
			try {
				Thread.sleep((int) (Math.random() * 3000));
			} catch (InterruptedException e) {
				System.err.println(e.toString());
			}
			pHold.setSharedInt(count);
			System.out.println("Producer set sharedInt to " + count);
		}

		pHold.setMoreData(false);
	}
}

class ConsumeInteger extends Thread {
	private HoldInteger cHold;

	public ConsumeInteger(HoldInteger h) {
		cHold = h;
	}

	public void run() {
		int val;

		while (cHold.hasMoreData()) {
			// sleep for a random interval
			try {
				Thread.sleep((int) (100));
			} catch (InterruptedException e) {
				System.err.println(e.toString());
			}
			val = cHold.getSharedInt();
			System.out.println("Consumer retrieved " +  this.toString().substring(7, 16) + val);
		}
	}
}

class HoldInteger {
	private int sharedInt = -1;
	private int count = 0;
	private boolean moreData = true;
	private boolean writeable = true;

	public synchronized void setSharedInt(int val) {
		while (!writeable) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		sharedInt = val;
		writeable = false;
		notify();
	}

	public synchronized int getSharedInt() {
		while (writeable) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (count++ == 1){
			count = 0;
			writeable = true;
			notify();
		}
		return sharedInt;
	}

	public void setMoreData(boolean b) {
		moreData = b;
	}

	public boolean hasMoreData() {
		return moreData;
	}
}
