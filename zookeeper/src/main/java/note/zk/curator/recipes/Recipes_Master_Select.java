package note.zk.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Recipes_Master_Select {

	static String master_path = "/curator_recipes_master_path";
	
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("kafka1:2222")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
    public static void main( String[] args ) throws Exception {
    	client.start();
        LeaderSelector selector = new LeaderSelector(client, 
        		master_path, 
        		new LeaderSelectorListenerAdapter() {
		            public void takeLeadership(CuratorFramework client) throws Exception {
		                System.out.println("Be Master");
		                Thread.sleep( 30000 );
		                System.out.println( "Killed Master");
		            }
		        });
        selector.autoRequeue();
        selector.start();
        Thread.sleep( Integer.MAX_VALUE );
	}
}