package de.hawLandshut.scrum.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;

import de.hawLandshut.scrum.data.MemberProducer;
import de.hawLandshut.scrum.data.TeamProducer;
import de.hawLandshut.scrum.model.Member;
import de.hawLandshut.scrum.model.Team;
import de.hawLandshut.scrum.services.TeamService;
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
	TeamService teamsService;
	@Inject
    private TeamProducer teamProducer;
	@Inject
	private MemberProducer memberProducer;
	
	private Member selectedMember;
	
	
	public void doAddTeam(){
		teamProducer.prepareAddTeam();
		openDialog(Pages.DIALOG_ADD_TEAM);
	}
	
	public String doAddTeamMobile(){
		teamProducer.prepareAddTeam();
		return Pages.ADD_TEAM_MOBILE;
	}
	
	public void doAddTeamSave(){
		teamAddEvent.fire(teamProducer.getSelectedTeam());
		closeDialog(null);
		pushToAllClients(notify,"Add Team", teamProducer.getSelectedTeam().getName() + " was created");
	}
	
	public String doAddTeamSaveMobile(){
		teamAddEvent.fire(teamProducer.getSelectedTeam());
		pushToAllClients(notify,"Add Team", teamProducer.getSelectedTeam().getName() + " was created");
		teamProducer.prepareAddTeam();
		return Pages.MAIN;
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
	
	public String doDeleteTeamMobile(){
		doDeleteTeam(getTeam());
		return Pages.MAIN;
	}
	
	public void doAddMember(){
		memberProducer.prepareAddMember();
		openDialog(Pages.DIALOG_ADD_MEMBER);
	}
	
	public String doAddMemberMobile(){
		memberProducer.prepareAddMember();
		return Pages.ADD_MEMBER_MOBILE;
	}
	
	
	public void doAddMemberSave(){
		memberAddEvent.fire(memberProducer.getSelectedMember());
		closeDialog(null);
		pushToAllClients(notify,"Add Person", memberProducer.getSelectedMember().getFirstname() + " " + memberProducer.getSelectedMember().getLastname() + " was created");
	}
	
	public String doAddMemberSaveMobile(){
		memberAddEvent.fire(memberProducer.getSelectedMember());
		pushToAllClients(notify,"Add Person", memberProducer.getSelectedMember().getFirstname() + " " + memberProducer.getSelectedMember().getLastname() + " was created");
		memberProducer.prepareAddMember();
		return Pages.MAIN;
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
		memberProducer.setSelectedMember(member);
		this.selectedMember = member;

	}
	
	public String doSelectMemberMobile(Member member){
		memberProducer.setSelectedMember(member);
		return Pages.SHOW_MEMBER_MOBILE;
	}
	
	public String doAssignMember(Member member){
		memberProducer.setSelectedMember(member);
		return Pages.ASSIGN_MEMBER;
	}
	
	private Member getSelectedMember(){
		return selectedMember;
	}
	
	public String doSelectTeam(Team team){
		teamProducer.setSelectedTeam(team);
		return Pages.SHOW_TEAM_MOBILE;
	}
	
	public List<Member> getTeamMembers(){
		List<Team> teams = teamsService.getAllTeams();
		for(Team t:teams){
			if(t.getId().equals(teamProducer.getSelectedTeam().getId())){
				teamProducer.setSelectedTeam(t);
				return t.getMembers();
			}		
		}
		return null;
	}
	
	public Team getTeam(){
		List<Team> teams = teamsService.getAllTeams();
		for(Team t:teams){
			if(t.getId().equals(teamProducer.getSelectedTeam().getId())){
				teamProducer.setSelectedTeam(t);
				return t;
			}		
		}
		return null;
	}
	
	public String doAssignTeam(Team team){
		
		Member member = memberProducer.getSelectedMember();
		member.setTeam(team);
		
		memberUpdateEvent.fire(member);
		pushToAllClients(notify,"Move", "User was moved");
		return Pages.MAIN;
		
	}
	
	public String doUnassingTeam(Member member){
		member.setTeam(null);
		memberUpdateEvent.fire(member);
		pushToAllClients(notify,"Move", "User was moved");
		return Pages.MAIN;
	}
	
	
	

		
}
