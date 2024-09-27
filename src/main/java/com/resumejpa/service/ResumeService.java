package com.resumejpa.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resumejpa.convertor.LocalDate2YearMonthConvertor;
import com.resumejpa.convertor.YearMonth2DateConvertor;
import com.resumejpa.entity.Member;
import com.resumejpa.entity.Resume;
import com.resumejpa.repository.MemberJpaRepository;
import com.resumejpa.repository.ResumeJpaRepository;

@Service
public class ResumeService {

	//会員リポジトリ
	private MemberJpaRepository memberRepo;

	//経歴リポジトリ
	private ResumeJpaRepository resumeRepo;
	
	/**
	 * コンストラクタ
	 * @param resumeRepo 経歴リポジトリ
	 */
	public ResumeService(MemberJpaRepository memberRepo, ResumeJpaRepository resumeRepo) {
		this.memberRepo = memberRepo;
		this.resumeRepo = resumeRepo;
	}
	
	/**
	 * 経歴検索
	 * @param id ID
	 * @return
	 * @throws Exception
	 */
	public List<Resume> findByMemberId(Long id) throws Exception {
		return this.resumeRepo.findByMember(
				this.memberRepo.findById(id).orElse(new Member()), 
				Sort.by(Sort.Direction.ASC, "typ", "ym"));
	}

	/**
	 * 経歴検索
	 * @param id ID
	 * @return
	 * @throws Exception
	 */
	public Resume findById(Long id) throws Exception {
		//Repositoryからの戻り値がOptionalなのでnull時は空のResumeオブジェクトを返す
		Resume resume = this.resumeRepo.findById(id).orElse(new Resume());
		//DBから取得した年月を画面用に型変換する
		LocalDate2YearMonthConvertor convertor = new LocalDate2YearMonthConvertor();
		resume.setRequestYm(convertor.convert(resume.getYm()));
		
		return resume;
	}
	
	/**
	 * 経歴追加
	 * @param resume 経歴エンティティ
	 * @throws Exception
	 */
	@Transactional
	public void insertResume(Resume resume) throws Exception {
		//画面からもらった年月をDB用に型変換する
		YearMonth2DateConvertor convertor = new YearMonth2DateConvertor();
		resume.setYm(convertor.convert(resume.getRequestYm()));
		//INSERT文の実行
		this.resumeRepo.save(resume);
	}

	/**
	 * 経歴更新
	 * @param resume 経歴エンティティ
	 * @throws Exception
	 */
	@Transactional
	public void updateResume(Resume resume) throws Exception {
		//画面からもらった年月をDB用に型変換する
		YearMonth2DateConvertor convertor = new YearMonth2DateConvertor();
		resume.setYm(convertor.convert(resume.getRequestYm()));
		//UPDATE文の実行
		this.resumeRepo.save(resume);
	}
	
	/**
	 * 経歴削除
	 * @param id ID
	 * @throws Exception
	 */
	@Transactional
	public void deleteResume(Long id) throws Exception {
		this.resumeRepo.deleteById(id);
	}

}
