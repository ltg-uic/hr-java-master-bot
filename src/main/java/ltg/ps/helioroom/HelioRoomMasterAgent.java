/**
 * 
 */
package ltg.ps.helioroom;

import java.net.UnknownHostException;

import ltg.commons.LTGEvent;
import ltg.commons.LTGEventHandler;
import ltg.commons.LTGEventListener;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * @author tebemis
 *
 */
public class HelioRoomMasterAgent {

	private LTGEventHandler eh = null;
	private DBCollection db;


	public HelioRoomMasterAgent() {

		// -------------------
		// Init network and DB
		// -------------------
		eh =  new LTGEventHandler("hr-master@54.243.60.48", "hr-master", "helio-sp-13@conference.54.243.60.48");
		try {
			db = new MongoClient("localhost").getDB("helio-sp-13").getCollection("notes");
		} catch (UnknownHostException e1) {
			System.err.println("Impossible to connect to MongoDB");
			System.exit(0);
		}


		// -----------------
		//Register listeners
		// -----------------
		eh.registerHandler("initialize_observation_tab", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generateEvent("observations_status", e.getOrigin(), getObservationStatusFromDB());

			}
		});

		eh.registerHandler("new_observation", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				storeObservation(e.getOrigin(), e.getPayload());
			}
		});		

		eh.registerHandler("remove_observation", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				deleteObservation(e.getOrigin(), e.getPayload());
			}
		});

		eh.registerHandler("initialize_theories_tab", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generateEvent("theories_status", e.getOrigin(), getTheoriesStatusFromDB());

			}
		});

		eh.registerHandler("new_theory", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				storeTheory(e.getOrigin(), e.getPayload());
			}
		});

		eh.registerHandler("delete_theory", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				deleteTheory(e.getOrigin(), e.getPayload());
			}
		});


		// ------------------
		// Run event listener
		// ------------------
		eh.runSynchronously();
	}


	protected JsonNode getObservationStatusFromDB() {
		// TODO Auto-generated method stub
		/*
		 * {
"event": "observations_status",
"origin": "master-agent",
"payload": {
"anchors": [
    {
        "anchor": "red",
        "colors": [
            {
                "color": "blue",
                "people": [
                    "Susie",
                    "Johnny",
                    "Amanda"
                ]
            },
            {
                "color": "green",
                "people": [
                    "Lisa"
                ]
            }
        ]
    },
    {
        "anchor": "white",
        "colors": [
            {
                "color": "purple",
                "people": [
                    "Lisa",
                    "Bart",
                    "Margie"
                ]
            }
        ]
    }
]
}
}
		 */
		return null;
	}


	protected void storeObservation(String origin, JsonNode payload) {
		BasicDBObject observation = 
				new BasicDBObject("origin", origin)
				.append("color", payload.get("color").textValue())
				.append("anchor", payload.get("anchor").textValue())
				.append("reason", payload.get("reason").textValue())
				.append("active", true);
		db.insert(observation);
	}


	protected void deleteObservation(String origin, JsonNode payload) {
		BasicDBObject query = 
				new BasicDBObject("origin", origin)
				.append("color", payload.get("color").textValue())
				.append("anchor", payload.get("anchor").textValue());
		BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("active", false));
		db.update(query, update);
	}


	protected JsonNode getTheoriesStatusFromDB() {
		// TODO Auto-generated method stub
		/*
		 * {
"event": "theoris_status",
"origin": "master-agent",
"payload": {
"anchors": [
    {
        "anchor": "mercury",
        "colors": [
            {
                "color": "green",
                "people": [
                    {
                        "name": "Susie",
                        "note": "Susie's note"
                    },
                    {
                        "name": "Johnny",
                        "note": "Johnny's note"
                    }
                ]
            },
            {
                "color": "green",
                "people": [
                    {
                        "name": "Lisa",
                        "note": "Lisa's note"
                    }
                ]
            }
        ]
    }
]
}
}
		 */
		return null;
	}


	protected void storeTheory(String origin, JsonNode payload) {
		/*
		 * "payload": {
		        "color": "red",
		        "anchor": "mercury",
		        "reason": "Because all the other planets go behind",
		        "thumbnail": ""
		    }
		 */
	}


	protected void deleteTheory(String origin, JsonNode payload) {
		// TODO Auto-generated method stub
		/*
		 * "payload": {
        "color": "red",
        "anchor": "mercury"
    }
		 */

	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		new HelioRoomMasterAgent();
	}






}
