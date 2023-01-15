# Chat-system
This project is a client-server chat system
# Modules
This project contains three modules:
- Chat Server  is the server which accepts connections and serves as mediator for message exhcange between clients.
- Chat Client is the desktop client.
- File Server is a costum file server which serves to transfer files between clients. This is to avoid to put this charge on the chat server.
# Data
The users data is stored in a MySQL database (the sql script "chatserveur.sql" conains the database structure.) which contains:
- A table for users and,
- A relation table which links each user to its friends.

![image](https://user-images.githubusercontent.com/17766512/209360618-90bebf92-926c-41d5-af2e-07d6624c6938.png)
