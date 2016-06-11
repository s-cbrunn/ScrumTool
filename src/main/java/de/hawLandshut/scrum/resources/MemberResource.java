package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.model.Member;
import de.hawLandshut.scrum.services.MemberService;

@Path("/master/member")
public class MemberResource {
	
	@Inject
	private MemberService memberService;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> getAllMembers(){
		List<Member> members = memberService.getAllMembers();
		return members;
	}

}
