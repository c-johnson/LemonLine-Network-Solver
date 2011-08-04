package up.controller;

import javax.ws.rs.*;

// The Java class will be hosted at the URI path "/networksolver"

@Path("/networksolver")

public class NetworkService {

    // and implement the following GET method

    @GET 
    @Produces("text/plain")
	@Path("/sup")
    public String getGreeting() {
    	
        return "No, don't do work, bitch";

    }

}