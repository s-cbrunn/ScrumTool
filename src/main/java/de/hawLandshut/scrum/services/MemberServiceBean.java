package de.hawLandshut.scrum.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Member;



@Stateless
public class MemberServiceBean implements MemberService {
	
	@Inject
	EntityManager entityManager;
	
	@Override
	public List<Member> getAllMembers() {
		TypedQuery<Member> query = entityManager.createNamedQuery(Member.findAllAvailableMembers, Member.class);
		return query.getResultList();
	}

	@Override
	public void addMember(Member member) {
		entityManager.persist(member);
	}

	@Override
	public void deleteMember(Member member) {
		entityManager.remove(entityManager.find(Member.class, member.getEmail()));
	}

	@Override
	public void updateMember(Member member) {
		entityManager.merge(member);
	}

	@Override
	public Member getMember(String email) {
		Member user = entityManager.find(Member.class, email);
		return user;
	}


}
