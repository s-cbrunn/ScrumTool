package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Member;
import de.hawLandshut.scrum.services.MemberService;
import de.hawLandshut.scrum.util.Events.AddedMember;
import de.hawLandshut.scrum.util.Events.DeletedMember;
import de.hawLandshut.scrum.util.Events.UpdatedMember;


@RequestScoped
public class MemberListProducer {

	private List<Member> members;
	
	@Inject
	private MemberService memberService;
	
	@PostConstruct
	public void init(){
		members = memberService.getAllMembers();
	}
	
	@Produces
	@Named
	public List<Member> getMembers(){
		return members;
	}
	
	public void onMemberAdded(@Observes @AddedMember Member member){
		memberService.addMember(member);
		init();
	}
	
	public void onMemberDeleted(@Observes @DeletedMember Member member){
		memberService.deleteMember(member);
		init();
	}
		
	public void onMemberUpdated(@Observes @UpdatedMember Member member){
		memberService.updateMember(member);
		init();
	}

}
