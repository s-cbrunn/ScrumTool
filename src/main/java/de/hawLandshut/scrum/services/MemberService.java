package de.hawLandshut.scrum.services;

import java.util.List;

import de.hawLandshut.scrum.model.Member;


public interface MemberService {

	List<Member> getAllMembers();
	Member getMember(String email);
	void addMember(Member member);
	void deleteMember(Member member);
	void updateMember(Member member);
	
}
