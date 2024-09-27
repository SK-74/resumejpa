package com.resumejpa.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resumejpa.entity.Member;
import com.resumejpa.repository.MemberJpaRepository;
import com.resumejpa.repository.ResumeJpaRepository;
import com.resumejpa.request.MemberSearchCriteria;
import com.resumejpa.result.Result;
import com.resumejpa.specification.MemberSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MemberService {

	//会員リポジトリ
	private MemberJpaRepository memberRepo;
	
	//経歴リポジトリ
	private ResumeJpaRepository resumeRepo;
	
	//メッセージ
	private MessageSource messageSrc;
	
    @PersistenceContext
    private EntityManager entityManager;
    
	/**
	 * コンストラクタ
	 * @param memberRepo 会員リポジトリ
	 * @param resumeRepo 経歴リポジトリ
	 * @param messageSrc メッセージ
	 */
	public MemberService(MemberJpaRepository memberRepo, ResumeJpaRepository resumeRepo, MessageSource messageSrc) {
		this.memberRepo = memberRepo;
		this.resumeRepo = resumeRepo;
		this.messageSrc = messageSrc;
	}

	/**
	 * 年齢取得
	 * @param id ID
	 * @return
	 * @throws Exception
	 */
	public String getAge(Long id) throws Exception {
		LocalDate today = LocalDate.now();
		Member member = this.findById(id);
		
		long age = ChronoUnit.YEARS.between(member.getBirth(), today);
		
		String ageStr = "-";
		if(age >= 0) {
			ageStr = String.valueOf(age);
		}
		return ageStr;
	}

	/**
	 * 誕生日を条件に会員を検索する
	 * @param criteria 検索条件
	 * @return
	 * @throws Exception
	 */
	public List<Member> findByBirth(MemberSearchCriteria criteria) throws Exception {
		Specification<Member> spcBirth = Specification
				.where(MemberSpecification.birthGreaterThanOrEqualTo(criteria.getDateFrom()))
				.and(MemberSpecification.birthLessThanOrEqualTo(criteria.getDateTo()));
		return this.memberRepo.findAll(spcBirth);
	}

	/**
	 * IDを条件に会員を検索する
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Member findById(Long id) throws Exception {
		//Repositoryからの戻り値がOptionalなのでnull時は空のMemberオブジェクトを返す
		return this.memberRepo.findById(id).orElse(new Member());
	}
	
	/**
	 * 会員追加
	 * @param member 会員エンティティ
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Result insertMember(Member member) throws Exception {
		Result result = new Result();
		//メアドの重複登録をガードする
		long count = this.memberRepo.countByEmail(member.getEmail());
		if(count > 0) {
			result.setOk(false);
			//message.propertiesから取得する
			result.setErrMsg(messageSrc.getMessage("err.msg.duplicateemail", null, Locale.getDefault()));
		}else {
			this.memberRepo.save(member);

			result.setOk(true);
		}
		return result;
	}

	/**
	 * 会員更新
	 * @param member 会員エンティティ
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Result updateMember(Member member) throws Exception {
		Result result = new Result();
		//メアドの重複登録をガードする
		long count = this.memberRepo.countByEmailAndIdNot(member.getEmail(), member.getId());
		if(count > 0) {
			result.setOk(false);
			//message.propertiesから取得する
			result.setErrMsg(messageSrc.getMessage("err.msg.duplicateemail", null, Locale.getDefault()));
		}else {
			this.memberRepo.save(member);

			result.setOk(true);
		}
		return result;
	}
	
	/**
	 * 会員削除（経歴も同時に削除する）
	 * @param id ID
	 * @throws Exception
	 */
	@Transactional
	public void deleteMember(Long id) throws Exception {
		this.resumeRepo.deleteByMember(this.memberRepo.findById(id).orElse(new Member()));
		this.memberRepo.deleteById(id);
	}
}
