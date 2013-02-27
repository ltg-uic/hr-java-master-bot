/**
 * 
 */
package ltg.ps.helioroom;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import ltg.commons.LTGEvent;
import ltg.commons.LTGEventHandler;
import ltg.commons.LTGEventListener;

/**
 * @author tebemis
 *
 */
public class HelioRoomMasterAgent {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final LTGEventHandler eh = new LTGEventHandler("hr-master@54.243.60.48", "hr-master", "helio-sp-13@conference.54.243.60.48");

		// init event
		eh.registerHandler("event_a", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generatePrivateEvent("gugo@54.243.60.48", new LTGEvent("gugo_event", JsonNodeFactory.instance.objectNode()));
			}
		});		
	

		// Run event listener
		eh.runSynchronously();
	}
	
	
	

}
