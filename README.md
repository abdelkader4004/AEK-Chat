# Chat-system
This project is a client-server chat system
# Modules
This project contains three modules:
- Chat Server  is the server which accepts connections and serves as mediator for message exhcange between clients.
- Chat Client is the desktop client.
- File Server is a costum file server which serves to transfer files between clients. This is to avoid to put this charge on the chat server.
# Data
The users data is stored in a MySQL data base which contains:
- A table for users and,
- A relation table which links each users to its friends.