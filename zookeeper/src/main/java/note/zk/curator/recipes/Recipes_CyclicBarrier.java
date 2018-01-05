package note.zk.curator.recipes;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Recipes_CyclicBarrier {

	public static CyclicBarrier barrier = new CyclicBarrier( 3 );
	public  static  CountDownLatch latch = new CountDownLatch(3);
	public static void main( String[] args ) throws IOException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool( 3 );
		executor.submit( new Thread( new Runner( "number 1" ) ) );
		executor.submit( new Thread( new Runner( "number 2" ) ) );
		executor.submit( new Thread( new Runner( "number 3" ) ) );
		latch.countDown();
		executor.shutdown();
	}
}
class Runner implements Runnable {
	private String name;
	public Runner( String name ) {
		this.name = name;
	}
	public void run() {
		System.out.println( name + " ready." );
		try {
			//如果在一台机器上和countdownLatch的效果一样
			//latch.countDown();
			//latch.await();
			Recipes_CyclicBarrier.barrier.await();

		} catch ( Exception e ) {}
		System.out.println( name + " run!" );
	}
}