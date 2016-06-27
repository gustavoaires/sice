package br.ufc.sice.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/userresource")
public class UserResources {

	@GET
    @Path("/userprofile")
    public String getUserProfile() {
        return "Welcome in protected Area. User enabled.";
    }
}
