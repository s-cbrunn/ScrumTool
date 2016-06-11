package de.hawLandshut.scrum.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Member;



@SessionScoped
public class MemberProducer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Member member;
	
	@Produces
	@Named
	public Member getSelectedMember(){
		return member;
	}
	
	public void setSelectedMember(Member member){
		this.member = member;
	}
	
	public void prepareAddMember() {
        this.member = new Member();

    }

    public void prepareEditMember(Member member) {
        this.member = member;
    }

}
