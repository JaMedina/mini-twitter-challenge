mini-twitter-challenge
======================

# Content Negotiation

Responses from the servers can be received in both JSON and XML format
The default format is JSON
A request can change its response format by setting the appropriated headers:

**JSON**

	Content-Type=application/json
	Accept=application/json

**XML**

	Content-Type=application/json
	Accept=application/json

# Security
**_ALL_** requests, except the "login" and the creation of a user are secured to non authorised access.
The authentication process is started by a call to the login action:

	[POST]   /public/login
	
**Example:**

	Request
		POST /public/login HTTP/1.1
		Host: twitter-challenge.twimba.com
		Content-Type: application/json
		Accept: application/json
		Cache-Control: no-cache
	Response
	{
	    "token": "a0e82ec2-a83f-41f8-952a-d7580f9de681",
	    "user": {
	        "id": 1,
	        "name": "Jorge",
	        "username": "jorge"
	    }
	}

The response of the login is an object containing the token which will be send to all the request from now and, and the user that is holding the token.

**Example:**

	Request
		GET /rest/users?token=1c30ed4d-934b-43f4-b5e7-3afbed5e1bc8&username=jorge
		Host: http://jbossews-rameseum.rhcloud.com
		Content-Type: application/json
		Accept: application/json
		Cache-Control: no-cache

	Response
	{
	    "users": [
	        {"id": 1, "name": "Jorge", "username": "jorge"},
	        {"id": 2, "name": "Jorge Medina","username": "jmedina"},
	        {"id": 3, "name": "Jorge ","username": "jorge.medina"}
	    ]
	 }


# Available actions

## Public
* [POST]    */public/login*
	* Creates a token that must be used in all future request made by the user
* [POST]    */public/user*
	* Creates a new user for the application

## Tweets	
* [GET]     */rest/tweets[?filter=...]*
	* Return the list of tweets that compose the user timeline (his, and the ones from the users that he follows). An optional search string can be used to filter out tweets
* [POST]    */rest/tweets?authorId=&message=*
	* Adds a new tweet to the user timeline

## Users
* [POST]    */rest/users/logout*
	* Invalidates the token so it cannot be used again. New authentication is required
* [POST]    *rest/follow?followeeUsername=...*
	* The current users starts following another user
* [DELETE]  */rest/follow/{username}/{followeeUsername}*
	* The current user stops following another user
* [GET]     */rest/followee/{username}*
	* Gets all the users that a user follows
* [GET]     */rest/follow/{username}*
	* Gets all the followers of the user
* [GET]    */rest/users*
	* Lists all the users in the system

# Command line client
If you prefer, there is a command line client for the application available here
https://github.com/JaMedina/mini-twitter-client
