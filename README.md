This is the master bot that orchestrates the "knowledge building" portion of HelioRoom. The role of the bot is to respond to clients requests and talk to the DB to keep a centralized state of the system. 

# Use
Launch the bot with `java -jar helioroom-master-agent-1.2.1.jar hr-master hr-test`. Make sure there is an existing XMPP user `hr-master` with the username as password. Also make sure a chatroom exists with name `hr-test` and that a MongoDB database also exists in the same host, also with name `hr-test`. Inside this db create a `notes` collection which will store the centralized system state.

# Compile
Simply run `mvn package` and the shade plugin will take care of creating an "uber-jar" with all the dependencies in it.

