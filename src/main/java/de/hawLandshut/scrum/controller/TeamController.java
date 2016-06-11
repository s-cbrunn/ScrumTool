package de.hawLandshut.scrum.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import org.primefaces.event.DragDropEvent;


import de.hawLandshut.scrum.data.MemberProducer;
import de.hawLandshut.scrum.data.TeamProducer;
import de.hawLandshut.scrum.model.Member;
import de.hawLandshut.scrum.model.Team;
import de.hawLandshut.scrum.util.Events.AddedMember;
import de.hawLandshut.scrum.util.Events.AddedTeam;
import de.hawLandshut.scrum.util.Events.DeletedMember;
import de.hawLandshut.scrum.util.Events.DeletedTeam;
import de.hawLandshut.scrum.util.Events.UpdatedMember;


@Named
@ViewScoped
public class TeamController extends Controller implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String notify = "/teamNotify";
	
	@Inject @AddedTeam
	private Event<Team> teamAddEvent;
	
	@Inject @DeletedTeam
	private Event<Team> teamDeleteEvent;
		
	@Inject @AddedMember
	private Event<Member> memberAddEvent;
	
	@Inject @DeletedMember 
	private Event<Member> memberDeleteEvent;
	
	@Inject @UpdatedMember
	private Event<Member> memberUpdateEvent;

	@Inject
    private TeamProducer teamProducer;
	@Inject
	private MemberProducer memberProducer;
	
	private Member selectedMember;
	
	
	public void doAddTeam(){
		teamProducer.prepareAddTeam();
		openDialog(Pages.DIALOG_ADD_TEAM);
	}
	
	public void doAddTeamSave(){
		teamAddEvent.fire(teamProducer.getSelectedTeam());
		closeDialog(null);
		pushToAllClients(notify,"Add Team", teamProducer.getSelectedTeam().getName() + " was created");
	}
	
	public void doShowTeam(Team team){
		teamProducer.setSelectedTeam(team);
		openDialog(Pages.DIALOG_SHOW_TEAM);
	}
	
	public void doDeleteTeam(Team team){
		teamProducer.setSelectedTeam(team);
		
		for(Member m:team.getMembers()){
			m.setTeam(null);
			memberUpdateEvent.fire(m);
		}
		teamDeleteEvent.fire(team);
		
		pushToAllClients(notify,"Delete Team", team.getName() + " was deleted");
		
	}
	
	public void doAddMember(){
		memberProducer.prepareAddMember();
		openDialog(Pages.DIALOG_ADD_MEMBER);
	}
	
	public void doAddMemberSave(){
		memberAddEvent.fire(memberProducer.getSelectedMember());
		closeDialog(null);
		pushToAllClients(notify,"Add Person", memberProducer.getSelectedMember().getFirstname() + " " + memberProducer.getSelectedMember().getLastname() + " was created");
	}
	
	public void doShowMember(Member member){
		memberProducer.setSelectedMember(member);
		openDialog(Pages.DIALOG_SHOW_MEMBER);
	}
	
	public void doDeleteMember(Member member){
		memberProducer.setSelectedMember(member);
		pushToAllClients(notify,"Delete Person", memberProducer.getSelectedMember().getFirstname() + " " + memberProducer.getSelectedMember().getLastname() + " was deleted");
		memberDeleteEvent.fire(member);
	}
	
	
	public void doDropMember(DragDropEvent ddEvent){
		 	Team destinationTeam = (Team) ddEvent.getComponent().getAttributes().get("team");
			if(destinationTeam != null){
				getSelectedMember().setTeam(destinationTeam);
				memberUpdateEvent.fire(getSelectedMember());
			}
			else{
				getSelectedMember().setTeam(null);
				memberUpdateEvent.fire(getSelectedMember());
			}

			pushToAllClients(notify,"Move", "User was moved");
	}
	
	public void doSelectMember(Member member){
		this.selectedMember = member;
	}
	
	private Member getSelectedMember(){
		return selectedMember;
	}

		
}
